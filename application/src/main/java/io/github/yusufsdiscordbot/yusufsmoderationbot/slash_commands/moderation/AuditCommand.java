/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007 Copyright (C) 2021 Free Software Foundation,
 * Inc. <https://fsf.org/> Everyone is permitted to copy and distribute verbatim copies of this
 * license document, but changing it is not allowed. Yusuf Arfan Ismail The GNU General Public
 * License is a free, copyleft license for software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.moderation;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

/**
 * The audit allows a mod to retrieve data for a certain user such as retrieving the amount of warns
 * a certain user has or check if a certain user is banned or not and if they are banned who they
 * were banned by.
 *
 * @author Yusuf Arfan Ismail
 */
public class AuditCommand extends CommandConnector {
    private static final String WARN_COMMAND = "warn";
    private static final String KICK_COMMAND = "kick";
    private static final String BAN_COMMAND = "ban";
    private static final String WARN_USER = "user";
    private static final String BAN_USER = "user";
    private static final String KICK_USER = "user";
    private static final String THE_USER = "the user ";
    private static final String AUTHOR_ERROR = "The has author has not been given";

    /**
     * Were the command is registered.
     */
    public AuditCommand() {
        super("audit", "get the data for warns, kicks and bans", CommandVisibility.SERVER);

        getCommandData().addSubcommands(
                new SubcommandData(WARN_COMMAND, "Gets the specific warns for that user").addOption(
                        OptionType.USER, WARN_USER, "The user who you want get the warns for",
                        true),
                new SubcommandData(KICK_COMMAND, "Gets you the reason of the kick for that user")
                    .addOption(OptionType.USER, KICK_USER,
                            "The user who you want get the kick reason for", true),
                new SubcommandData(BAN_COMMAND, "Gets you the reason of the ban for that user")
                    .addOption(OptionType.USER, BAN_USER,
                            "The user who you want get the ban reason for", true));
    }

    @Override
    public void onSlashCommand(@NotNull YusufSlashCommandEvent yusufSlashCommandEvent) {
        switch (Objects.requireNonNull(yusufSlashCommandEvent.getSubcommandName())) {
            case WARN_COMMAND -> handleWarnCommand(yusufSlashCommandEvent);
            case KICK_COMMAND -> handleKickCommand(yusufSlashCommandEvent);
            case BAN_COMMAND -> handleBanCommand(yusufSlashCommandEvent);
            default -> throw new AssertionError();
        }
    }

    /**
     * This command allows you to retrieve the amount of warns a user for example the mod can type
     * /audit @yusuf and the bot will get the warns for yusuf and for this example it will reply
     * with yusuf has 1 warn.
     *
     * @param yusufSlashCommandEvent My custom slash command event. Used for events such as reply
     *        and replyEmbed
     */
    private void handleWarnCommand(@NotNull YusufSlashCommandEvent yusufSlashCommandEvent) {
        YusufGuild yusufGuild = yusufSlashCommandEvent.getGuild();
        YusufOptionMapping yusufOptionMapping =
                Objects.requireNonNull(yusufSlashCommandEvent.getYusufOption(KICK_USER));
        YusufUser yusufUser = yusufOptionMapping.getAsUser();
        YusufMember yusufAuthor =
                Objects.requireNonNull(yusufSlashCommandEvent.getMember(), AUTHOR_ERROR);
        YusufMember yusufMember = yusufOptionMapping.getAsMember();



        if (yusufMember != null && !ModerationHelper.userCanInteractWithTheGivenUser(yusufMember,
                yusufGuild.getBot(), yusufAuthor, yusufSlashCommandEvent, WARN_COMMAND)) {
            return;
        }

        if (!ModerationHelper.userAndBotHaveTheRightPerms(yusufSlashCommandEvent.getMember(),
                yusufGuild.getBot(), yusufSlashCommandEvent, yusufGuild, WARN_COMMAND)) {
            return;
        }

        if (ModerationHelper.getCurrentAmountOfWarns(yusufUser.getUserIdLong(),
                yusufGuild.getIdLong()) == null) {
            yusufSlashCommandEvent.replyEmbed(new EmbedBuilder().setTitle("No warns")
                .setDescription(THE_USER + yusufUser.getUserTag() + " has " + " no warns")
                .setColor(Color.CYAN)
                .build());
        } else {
            yusufSlashCommandEvent.replyEmbed(new EmbedBuilder().setTitle("Warns")
                .setDescription(THE_USER + yusufUser.getUserTag() + " has "
                        + ModerationHelper.getCurrentAmountOfWarns(yusufUser.getUserIdLong(),
                                yusufGuild.getIdLong())
                        + " warns")
                .setColor(Color.CYAN)
                .build());
        }
    }

    /**
     * This command allows you to retrieve the reason why the user was kicked and who kicked the
     * user.
     *
     * @param yusufSlashCommandEvent My custom slash command event. Used for events such as reply
     *        and replyEmbed
     */
    private void handleKickCommand(@NotNull YusufSlashCommandEvent yusufSlashCommandEvent) {
        YusufGuild yusufGuild = yusufSlashCommandEvent.getGuild();
        YusufOptionMapping yusufOptionMapping =
                Objects.requireNonNull(yusufSlashCommandEvent.getYusufOption(KICK_USER));
        YusufUser yusufUser = yusufOptionMapping.getAsUser();
        YusufMember yusufAuthor =
                Objects.requireNonNull(yusufSlashCommandEvent.getMember(), AUTHOR_ERROR);
        YusufMember yusufMember = yusufOptionMapping.getAsMember();

        if (yusufMember != null && !ModerationHelper.userCanInteractWithTheGivenUser(yusufMember,
                yusufGuild.getBot(), yusufAuthor, yusufSlashCommandEvent, KICK_COMMAND)) {
            return;
        }

        if (!ModerationHelper.userAndBotHaveTheRightPerms(yusufSlashCommandEvent.getMember(),
                yusufGuild.getBot(), yusufSlashCommandEvent, yusufGuild, KICK_COMMAND)) {
            return;
        }

        long userId = yusufUser.getUserIdLong();
        long guildId = yusufGuild.getIdLong();
        if (!ModerationHelper.checkIfKickIsNull(userId, guildId)) {
            yusufSlashCommandEvent.replyEphemeralEmbed(new EmbedBuilder().setTitle("Null")
                .setDescription(THE_USER + yusufUser.getUserTag() + " is not kicked")
                .setColor(Color.CYAN)
                .build());
        } else {

            yusufSlashCommandEvent
                .replyEmbed(new EmbedBuilder().setTitle("Kick")
                    .setDescription(THE_USER + yusufUser.getUserTag()
                            + " was kicked for the following reason "
                            + ModerationHelper.getKickReason(userId, guildId) + " by "
                            + ModerationHelper.getKickAuthor(userId, guildId))
                    .setColor(Color.CYAN)
                    .build());
        }
    }

    /**
     * This command allows you to retrieve the reason why the user was banned and who banned the
     * user.
     *
     * @param yusufSlashCommandEvent My custom slash command event. Used for events such as reply
     *        and replyEmbed
     */
    private void handleBanCommand(@NotNull YusufSlashCommandEvent yusufSlashCommandEvent) {
        YusufGuild yusufGuild = yusufSlashCommandEvent.getGuild();
        YusufOptionMapping yusufOptionMapping =
                Objects.requireNonNull(yusufSlashCommandEvent.getYusufOption(KICK_USER));
        YusufUser yusufUser = yusufOptionMapping.getAsUser();
        YusufMember yusufAuthor =
                Objects.requireNonNull(yusufSlashCommandEvent.getMember(), AUTHOR_ERROR);
        YusufMember yusufMember = yusufOptionMapping.getAsMember();

        if (yusufMember != null && !ModerationHelper.userCanInteractWithTheGivenUser(yusufMember,
                yusufGuild.getBot(), yusufAuthor, yusufSlashCommandEvent, KICK_COMMAND)) {
            return;
        }

        if (!ModerationHelper.userAndBotHaveTheRightPerms(yusufSlashCommandEvent.getMember(),
                yusufGuild.getBot(), yusufSlashCommandEvent, yusufGuild, KICK_COMMAND)) {
            return;
        }

        long userId = yusufUser.getUserIdLong();
        long guildId = yusufGuild.getIdLong();
        if (!ModerationHelper.checkIfBanIsNull(userId, guildId)) {
            yusufSlashCommandEvent.replyEphemeralEmbed(new EmbedBuilder().setTitle("Null")
                .setDescription("The user " + yusufUser.getUserTag() + "is not banned")
                .setColor(Color.CYAN)
                .build());
        } else {

            yusufSlashCommandEvent
                .replyEmbed(new EmbedBuilder().setTitle("Ban")
                    .setDescription(THE_USER + yusufUser.getUserTag()
                            + " was banned for the following reason "
                            + ModerationHelper.getBanReason(userId, guildId) + " by the user "
                            + ModerationHelper.getBanAuthor(userId, guildId))
                    .setColor(Color.CYAN)
                    .build());
        }

    }
}
