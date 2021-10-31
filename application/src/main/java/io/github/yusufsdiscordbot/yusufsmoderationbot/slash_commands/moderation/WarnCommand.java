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
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.Command;


import static net.dv8tion.jda.api.interactions.commands.OptionType.*;

public class WarnCommand implements Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        final User user = event.getUser();
        final Member member = event.getMember();

        event.deferReply(true).queue(); // Let the user know we received the command before doing
                                        // anything else
        InteractionHook hook = event.getHook(); // This is a special webhook that allows you to send
                                                // messages without having permissions in the
                                                // channel and also allows ephemeral messages
        hook.setEphemeral(true); // All messages here will now be ephemeral implicitly
        if (!event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            hook.sendMessage(
                    "You do not have the required permissions to warn users from this server.")
                .queue();
            return;
        }

        Member selfMember = event.getGuild().getSelfMember();
        if (!selfMember.hasPermission(Permission.MANAGE_ROLES)) {
            hook.sendMessage(
                    "I don't have the required permissions to warn users from this server.")
                .queue();
            return;
        }

        if (member != null && !selfMember.canInteract(member)) {
            hook.sendMessage("This user is too powerful for me to warn.").queue();
            return;
        }

    }

    @Override
    public String getName() {
        return "warn";
    }

    @Override
    public String getDescription() {
        return "Warn a user";
    }

    @Override
    public CommandVisibility getVisibility() {
        return CommandVisibility.SERVER;
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription())
            .addOptions(new OptionData(USER, "user", "The user to warn").setRequired(true))
            .addOptions(
                    new OptionData(STRING, "reason", "The reason why you want to warn the user"));
    }
}
