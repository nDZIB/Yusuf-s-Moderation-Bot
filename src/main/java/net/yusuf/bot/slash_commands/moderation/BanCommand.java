/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Arfan Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.yusuf.bot.slash_commands.moderation;

import github.io.yusuf.core.bot.Command;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.*;

import java.util.Optional;

public class BanCommand implements Command{
    @Override
    public void onSlashCommand(SlashCommandCreateEvent event) {
        // Interaction base
        SlashCommandInteraction interaction = event.getSlashCommandInteraction();

        // This command should only work in servers
        if (!interaction.getServer().isPresent()) {
            return;
        }

        // The server
        Server server = interaction.getServer().get();

        // The message author
        User author = interaction.getUser();

        // Calling .get() here is okay because this is a required parameter
        User userToBan = interaction.requestFirstOptionUserValue().get().join();

        //The Reason for the ban
        Optional<String> banReason = interaction.getFirstOptionStringValue();

        // Checks if the bot has permission to ban the user
        if (!server.canBanUser(author, userToBan) || !server.canYouBanUser(userToBan)) {
            interaction.createImmediateResponder().setContent("I can't ban that person!. He is too powerful").respond();

            return;
        }

        // Bans the user
        server.banUser(userToBan, 1, String.valueOf(banReason));

        // Responds
        interaction.createImmediateResponder().setContent("Banned!").respond();
    }

    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public String getDescription() {
        return "Use this command to ban a user";
    }

    @Override
    public SlashCommandBuilder getCommandData() {
        return new SlashCommandBuilder().setName(getName()).setDescription(getDescription())
                .addOption(SlashCommandOption.create(SlashCommandOptionType.USER, "User",
                        "The user which you want to ban."))
                .addOption(SlashCommandOption.create(SlashCommandOptionType.STRING,"Reason",
                        "The Reason why you want to ban the user"));
    }
}
