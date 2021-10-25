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

package net.yusuf.bot.slash_commands.moderation;

import github.io.yusuf.core.bot.Command;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnBanCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(UnBanCommand.class);

    @Override
    public void onSlashCommand(SlashCommandCreateEvent slashCommandCreateEvent) {
        // Interaction base
        SlashCommandInteraction interaction = slashCommandCreateEvent.getSlashCommandInteraction();

        // This command should only work in servers
        if (!interaction.getServer().isPresent()) {
            return;
        }

        // The server
        Server server = interaction.getServer().get();

        // The message author
        User author = interaction.getUser();

        // The bot
        User bot = interaction.getApi().getYourself();

        // Calling .get() here is okay because this is a required parameter
        String userToUnBan = interaction.getFirstOptionStringValue().get();

        // Checks if the bot has permission to ban the user
        if (!server.canYouBanUser(author)) {
            interaction.createImmediateResponder()
                .setContent(
                        "You can not unban users in this guild since you do not have the BAN_MEMBERS permission.")
                .setFlags(MessageFlag.EPHEMERAL)
                .respond();

            return;
        }

        if (!server.canYouBanUser(bot)) {
            interaction.createImmediateResponder()
                .setContent(
                        "I can not unban users in this guild since I do not have the BAN_MEMBERS permission.")
                .setFlags(MessageFlag.EPHEMERAL)
                .respond();

            logger.error("The bot does not have BAN_MEMBERS permission on the server '{}' ",
                    server.getId());
            return;
        }
        // UnBans the user
        server.unbanUser(userToUnBan);

        // Responds
        interaction.createImmediateResponder().setContent("UnBanned " + userToUnBan).respond();
    }

    @Override
    public String getName() {
        return "unban";
    }

    @Override
    public String getDescription() {
        return "Use this command to unban a user";
    }

    @Override
    public SlashCommandBuilder getCommandData() {
        return new SlashCommandBuilder().setName(getName())
            .setDescription(getDescription())
            .addOption(SlashCommandOption.create(SlashCommandOptionType.STRING, "user_id",
                    "The user which you want to unban."));
    }
}
