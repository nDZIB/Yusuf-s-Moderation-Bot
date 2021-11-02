/*
 * GNU GENERAL PUBLIC LICENSE
 *                        Version 3, 29 June 2007
 *
 *  Copyright (C) 2021 Free Software Foundation, Inc. <https://fsf.org/>
 *  Everyone is permitted to copy and distribute verbatim copies
 *  of this license document, but changing it is not allowed.
 *
 *                            Yusuf Arfan Ismail
 *
 *   The GNU General Public License is a free, copyleft license for
 * software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.moderation;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandVisibility;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.Command;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


/**
 * Credits for Yusuf and great help from my other thorough my
 * <a href="https://github.com/Together-Java/TJ-Bot/pull/196">pr</a> for tj bot
 */
public class BanCommand implements Command {
    private static final String USER_OPTION = "user";
    private static final String DELETE_HISTORY_OPTION = "delete-history";
    private static final String REASON_OPTION = "reason";
    private static final Logger logger = LoggerFactory.getLogger(BanCommand.class);

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
         OptionMapping userOption =
                Objects.requireNonNull(event.getOption(USER_OPTION), "The target is null");
        Member target = userOption.getAsMember();
        Member author = Objects.requireNonNull(event.getMember(), "The author is null");

        String reason = Objects.requireNonNull(event.getOption(REASON_OPTION), "The reason is null")
            .getAsString();

        Guild guild = Objects.requireNonNull(event.getGuild());
        Member bot = guild.getSelfMember();

        // Member doesn't exist if attempting to ban a user who is not part of the guild.
        if (target != null && !handleCanInteractWithTarget(target, bot, author, event)) {
            return;
        }

        if (!handleHasPermissions(author, bot, event, guild)) {
            return;
        }

        int deleteHistoryDays = Math
            .toIntExact(Objects.requireNonNull(event.getOption(DELETE_HISTORY_OPTION)).getAsLong());

        if (!ModerationUtils.handleReason(reason, event)) {
            return;
        }

        banUser(userOption.getAsUser(), author, reason, deleteHistoryDays, guild, event);
    }

    private static void banUser(@NotNull User target, @NotNull Member author,
            @NotNull String reason, int deleteHistoryDays, @NotNull Guild guild,
            @NotNull SlashCommandEvent event) {
        event.getJDA()
            .openPrivateChannelById(target.getIdLong())
            .flatMap(channel -> channel.sendMessage(
                    """
                            Hey there, sorry to tell you but unfortunately you have been banned from the server %s.
                            If you think this was a mistake, please contact a moderator or admin of the server.
                            The reason for the ban is: %s
                            """
                        .formatted(guild.getName(), reason)))
            .mapToResult()
            .flatMap(result -> guild.ban(target, deleteHistoryDays, reason))
            .flatMap(v -> event.reply(target.getAsTag() + " was banned by "
                    + author.getUser().getAsTag() + " for: " + reason))
            .queue();

        logger.info(
                " '{} ({})' banned the user '{} ({})' and deleted their message history of the last '{}' days. Reason being'{}'",
                author.getUser().getAsTag(), author.getIdLong(), target.getAsTag(),
                target.getIdLong(), deleteHistoryDays, reason);
    }

    private static boolean handleCanInteractWithTarget(@NotNull Member target, @NotNull Member bot,
            @NotNull Member author, @NotNull SlashCommandEvent event) {
        String targetTag = target.getUser().getAsTag();
        if (!author.canInteract(target)) {
            event.reply("The user " + targetTag + " is too powerful for you to ban.")
                .setEphemeral(true)
                .queue();
            return false;
        }

        if (!bot.canInteract(target)) {
            event.reply("The user " + targetTag + " is too powerful for me to ban.")
                .setEphemeral(true)
                .queue();
            return false;
        }
        return true;
    }

    private static boolean handleHasPermissions(@NotNull Member author, @NotNull Member bot,
            @NotNull SlashCommandEvent event, @NotNull Guild guild) {
        if (!author.hasPermission(Permission.BAN_MEMBERS)) {
            event.reply(
                    "You can not ban users in this guild since you do not have the BAN_MEMBERS permission.")
                .setEphemeral(true)
                .queue();
            return false;
        }

        if (!bot.hasPermission(Permission.BAN_MEMBERS)) {
            event.reply(
                    "I can not ban users in this guild since I do not have the BAN_MEMBERS permission.")
                .setEphemeral(true)
                .queue();

            logger.error("The bot does not have BAN_MEMBERS permission on the server '{}' ",
                    guild.getName());
            return false;
        }
        return true;
    }

    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public String getDescription() {
        return "ban a user";
    }

    @Override
    public CommandVisibility getVisibility() {
        return CommandVisibility.SERVER;
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription())
            .addOption(OptionType.USER, USER_OPTION, "The user who you want to ban", true)
            .addOption(OptionType.STRING, REASON_OPTION, "Why the user should be banned", true)
            .addOptions(new OptionData(OptionType.INTEGER, DELETE_HISTORY_OPTION,
                    "the amount of days of the message history to delete, none means no messages are deleted.",
                    true).addChoice("none", 0).addChoice("recent", 1).addChoice("all", 7));
    }
}
