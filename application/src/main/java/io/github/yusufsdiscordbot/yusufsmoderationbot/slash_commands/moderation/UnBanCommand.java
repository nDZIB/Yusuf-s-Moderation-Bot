/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Arfan Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.moderation;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandVisibility;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.Command;
import net.dv8tion.jda.api.requests.ErrorResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


public class UnBanCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(UnBanCommand.class);
    private static final String USER_OPTION = "user";
    private static final String REASON_OPTION = "reason";

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        User targetUser = Objects.requireNonNull(event.getOption(USER_OPTION), "The member is null")
            .getAsUser();

        Member author = Objects.requireNonNull(event.getMember(), "The member is null");

        if (!author.hasPermission(Permission.BAN_MEMBERS)) {
            event.reply(
                    "You can not unban users in this guild since you do not have the BAN_MEMBERS permission.")
                .setEphemeral(true)
                .queue();
            return;
        }

        Member bot = Objects.requireNonNull(event.getGuild(), "The Bot is null").getSelfMember();
        if (!bot.hasPermission(Permission.BAN_MEMBERS)) {
            event.reply(
                    "I can not unban users in this guild since I do not have the BAN_MEMBERS permission.")
                .setEphemeral(true)
                .queue();

            logger.error("The bot does not have BAN_MEMBERS permission on the server '{}' ",
                    Objects.requireNonNull(event.getGuild()).getName());
            return;
        }

        String reason = Objects.requireNonNull(event.getOption(REASON_OPTION), "The reason is null")
            .getAsString();

        boolean reasonIsUnderLimit = ModerationUtils.handleReason(reason, event);
        if (reasonIsUnderLimit) {
            unban(targetUser, reason, author, event);
        }
    }

    private static void unban(@NotNull User targetUser, @NotNull String reason,
            @NotNull Member author, @NotNull SlashCommandEvent event) {
        event.getGuild().unban(targetUser).reason(reason).queue(v -> {
            event
                .reply("The user " + author.getUser().getAsTag() + " unbanned the user "
                        + targetUser.getAsTag() + " for: " + reason)
                .queue();

            logger.info(" {} ({}) unbanned the user '{}' for: '{}'", author.getUser().getAsTag(),
                    author.getIdLong(), targetUser.getAsTag(), reason);
        }, throwable -> {
            if (throwable instanceof ErrorResponseException errorResponseException
                    && errorResponseException.getErrorResponse() == ErrorResponse.UNKNOWN_USER) {

                event.reply("The specified user doesn't exist").setEphemeral(true).queue();

                logger.debug("I could not unban the user '{}' because they do not exist",
                        targetUser.getAsTag());
            } else {
                event.reply("Sorry, but something went wrong.").setEphemeral(true).queue();

                logger.warn("Something went wrong during the process of unbanning the user ",
                        throwable);
            }
        });
    }

    @Override
    public String getName() {
        return "unban";
    }

    @Override
    public String getDescription() {
        return "Unban a user";
    }

    @Override
    public CommandVisibility getVisibility() {
        return CommandVisibility.UNIVERSAL;
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription())
            .addOption(OptionType.USER, USER_OPTION, "The user who you want to unban", true)
            .addOption(OptionType.STRING, REASON_OPTION, "Why the user should be unbanned", true);

    }
}

