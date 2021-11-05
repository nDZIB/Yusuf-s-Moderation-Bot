// Originally from https://github.com/Together-Java/TJ-Bot/pull/196, then modified by Yusuf
/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
 *
 * Copyright (C) 2021 Free Software Foundation, Inc. <https://fsf.org/> Everyone is permitted to
 * copy and distribute verbatim copies of this license document, but changing it is not allowed.
 *
 * Yusuf Arfan Ismail
 *
 * The GNU General Public License is a free, copyleft license for software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.moderation;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.*;
import net.dv8tion.jda.annotations.ForRemoval;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.Objects;

public class BanCommand extends CommandConnector {
    private static final String USER_OPTION = "user";
    private static final String DELETE_HISTORY_OPTION = "delete-history";
    private static final String REASON_OPTION = "reason";
    private static final Logger logger = LoggerFactory.getLogger(BanCommand.class);

    public BanCommand() {
        super("ban", "Bans a given user", CommandVisibility.SERVER);

        getCommandData()
            .addOption(OptionType.USER, USER_OPTION, "The user who you want to ban", true)
            .addOption(OptionType.STRING, REASON_OPTION, "Why the user should be banned", true)
            .addOptions(new OptionData(OptionType.INTEGER, DELETE_HISTORY_OPTION,
                    "the amount of days of the message history to delete, none means no messages are deleted.",
                    true).addChoice("none", 0).addChoice("recent", 1).addChoice("all", 7));
    }


    private static void banUser(@NotNull YusufUser target, @NotNull YusufMember author,
            @NotNull String reason, int deleteHistoryDays, @NotNull YusufGuild guild,
            @NotNull YusufSlashCommandEvent event) {
        event.getJDA()
            .openPrivateChannelById(target.getUserId())
            .flatMap(channel -> channel.sendMessage(
                    """
                            Hey there, sorry to tell you but unfortunately you have been banned from the server %s.
                            If you think this was a mistake, please contact a moderator or admin of the server.
                            The reason for the ban is: %s
                            """
                        .formatted(guild.getName(), reason)))
            .mapToResult()
            .flatMap(result -> guild.ban(target, deleteHistoryDays, reason))
            .flatMap(v -> event.reply(target.getUserTag() + " was banned by "
                    + author.getUser().getAsTag() + " for: " + reason))
            .queue();

        logger.info(
                " '{} ({})' banned the user '{} ({})' and deleted their message history of the last '{}' days. Reason being'{}'",
                author.getUser().getAsTag(), author.getUserId(), target.getUserTag(),
                target.getUserId(), deleteHistoryDays, reason);
    }

    private static boolean handleCanInteractWithTarget(YusufMember target, YusufMember bot,
            @NotNull YusufMember author, @NotNull YusufSlashCommandEvent event) {
        String targetTag = target.getUser().getAsTag();
        if (!author.canInteract(target)) {
            event.replyEphemeral("The user " + targetTag + " is too powerful for you to ban.");
            return false;
        }

        if (!bot.canInteract(target)) {
            event.replyEphemeral("The user " + targetTag + " is too powerful for me to ban.");
            return false;
        }
        return true;
    }

    private static boolean handleHasPermissions(@NotNull YusufMember author,
            @NotNull YusufMember bot, @NotNull YusufSlashCommandEvent event,
            @NotNull YusufGuild guild) {
        if (!author.hasPermission(Permission.BAN_MEMBERS)) {
            event.replyEphemeral(
                    "You can not ban users in this guild since you do not have the BAN_MEMBERS permission.");
            return false;
        }

        if (!bot.hasPermission(Permission.BAN_MEMBERS)) {
            event.replyEphemeral(
                    "I can not ban users in this guild since I do not have the BAN_MEMBERS permission.");

            logger.error("The bot does not have BAN_MEMBERS permission on the server '{}' ",
                    guild.getName());
            return false;
        }
        return true;
    }

    @Override
    public void onSlashCommand(YusufSlashCommandEvent event) {
        YusufOptionMapping userOption =
                Objects.requireNonNull(event.getYusufOption(USER_OPTION), "The target is null");

        YusufMember target = userOption.getAsMember();
        @NotNull
        YusufMember author = Objects.requireNonNull(event.getMember(), "The author is null");

        String reason = Objects.requireNonNull(event.getOption(REASON_OPTION), "The reason is null")
            .getAsString();

        YusufGuild guild = Objects.requireNonNull(event.getGuild());
        YusufMember bot = guild.getBot();

        // Member doesn't exist if attempting to ban a user who is not part of the guild.
        if (target != null && !handleCanInteractWithTarget(target, bot, author, event)) {
            return;
        }

        if (!handleHasPermissions(author, bot, event, guild)) {
            return;
        }

        int deleteHistoryDays = Math
            .toIntExact(Objects.requireNonNull(event.getOption(DELETE_HISTORY_OPTION)).getAsLong());

        if (!event.getGuild().checkReasonLength(reason, event)) {
            return;
        }

        banUser(userOption.getAsUser(), event.getMember(), reason, deleteHistoryDays, guild, event);
    }
}
