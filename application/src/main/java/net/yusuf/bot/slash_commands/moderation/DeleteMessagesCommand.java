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
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageFlag;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteMessagesCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(DeleteMessagesCommand.class);
    private static final Integer MAX_DELETE_MESSAGE_AMOUNT = 100;
    private static final Integer MIN_DELETE_MESSAGE_AMOUNT = 1;

    @Override
    public void onSlashCommand(SlashCommandCreateEvent slashCommandCreateEvent) {
        SlashCommandInteraction interaction = slashCommandCreateEvent.getSlashCommandInteraction();

        if (!interaction.getServer().isPresent()) {
            return;
        }

        // Amount of messages to delete
        Integer amount = interaction.getFirstOptionIntValue().get();

        // Channel
        TextChannel channel = interaction.getChannel().get();

        // The message author
        User author = interaction.getUser();

        // The bot
        User bot = interaction.getApi().getYourself();

        // The server
        Server server = interaction.getServer().get();

        // Check for perms
        if (!channel.canManageMessages(author)) {
            interaction.createImmediateResponder()
                .setContent(
                        "You can't delete messages because you dont have MANAGE_MESSAGES permission")
                .setFlags(MessageFlag.EPHEMERAL)
                .respond();
            return;
        }

        if (!channel.canManageMessages(bot)) {
            interaction.createImmediateResponder()
                .setContent(
                        "I can't delete messages because I dont have MANAGE_MESSAGES permission")
                .setFlags(MessageFlag.EPHEMERAL)
                .respond();

            logger.error("The bot does not have BAN_MEMBERS permission on the server '{}' ",
                    server.getId());
            return;
        }

        if (amount > MAX_DELETE_MESSAGE_AMOUNT || amount < MIN_DELETE_MESSAGE_AMOUNT) {
            interaction.createImmediateResponder()
                .setContent("You can only delete between 1 to 100 messages")
                .setFlags(MessageFlag.EPHEMERAL)
                .respond();
        }

        channel.deleteMessages(amount);

        // Responds
        interaction.createImmediateResponder()
            .setContent("Deleted " + amount + " messages")
            .respond();
    }

    @Override
    public String getName() {
        return "delete_messages";
    }

    @Override
    public String getDescription() {
        return "You this command to delete messages from 1 to 200";
    }

    @Override
    public SlashCommandBuilder getCommandData() {
        return new SlashCommandBuilder().setName(getName())
            .setDescription(getDescription())
            .addOption(SlashCommandOption.create(SlashCommandOptionType.INTEGER,
                    "delete_messages_number", "The amount of messages you want to delete. 1-100",
                    true));
    }
}
