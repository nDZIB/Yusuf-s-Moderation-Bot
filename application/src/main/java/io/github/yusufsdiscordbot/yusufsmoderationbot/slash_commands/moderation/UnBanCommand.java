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
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.ErrorResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


public class UnBanCommand extends CommandConnector {
    private static final Logger logger = LoggerFactory.getLogger(UnBanCommand.class);
    private static final String USER_OPTION = "user";
    private static final String REASON_OPTION = "reason";

    public UnBanCommand() {
        super("unban", "Unbans a user", CommandVisibility.SERVER);

        getCommandData()
            .addOption(OptionType.USER, USER_OPTION, "The user who you want to unban", true)
            .addOption(OptionType.STRING, REASON_OPTION, "Why the user should be unbanned", true);
    }

    @Override
    public void onSlashCommand(YusufSlashCommandEvent event) {
        YusufUser targetUser = Objects.requireNonNull(event.getYusufOption(USER_OPTION), "The member is null")
            .getAsUser();

        YusufMember author = event.getMember();

        if (!author.hasPermission(Permission.BAN_MEMBERS)) {
            event.replyEphemeral(
                    "You can not unban users in this guild since you do not have the BAN_MEMBERS permission.");
            return;
        }

        Member bot = Objects.requireNonNull(event.getGuild(), "The Bot is null").getBot();
        if (!bot.hasPermission(Permission.BAN_MEMBERS)) {
            event.replyEphemeral(
                    "I can not unban users in this guild since I do not have the BAN_MEMBERS permission.");

            logger.error("The bot does not have BAN_MEMBERS permission on the server '{}' ",
                    Objects.requireNonNull(event.getGuild()).getName());
            return;
        }

        String reason = Objects.requireNonNull(event.getOption(REASON_OPTION), "The reason is null")
            .getAsString();

        if (!event.getGuild().checkReasonLength(reason, event)) {
            return;
        }
        unban(targetUser, reason, author, event);
    }

    private static void unban(@NotNull YusufUser targetUser, @NotNull String reason,
                              @NotNull YusufMember author, @NotNull YusufSlashCommandEvent event) {
        event.getGuild().unBan(targetUser).reason(reason).queue(v -> {
            event.replyMessage("The user " + author.getUser().getAsTag() + " unbanned the user "
                    + targetUser.getUserTag() + " for: " + reason);

            logger.info(" {} ({}) unbanned the user '{}' for: '{}'", author.getUser().getAsTag(),
                    author.getUserId(), targetUser.getUserTag(), reason);
        }, throwable -> {
            if (throwable instanceof ErrorResponseException errorResponseException
                    && errorResponseException.getErrorResponse() == ErrorResponse.UNKNOWN_USER) {

                event.replyEphemeral("The specified user doesn't exist");

                logger.debug("I could not unban the user '{}' because they do not exist",
                        targetUser.getUserTag());
            } else {
                event.replyEphemeral("Sorry, but something went wrong.");

                logger.warn("Something went wrong during the process of unbanning the user ",
                        throwable);
            }
        });
    }
}

