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

package net.yusuf.bot.prefix_commands.commands;

import github.io.yusuf.core.bot.prefix_command.CommandContext;
import github.io.yusuf.core.bot.prefix_command.PrefixCommand;
import github.io.yusuf.core.bot.prefix_command.VeryBadDesign;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.yusuf.bot.prefix_commands.CommandManager;


import java.util.List;

public class CommandList implements PrefixCommand {
    private final CommandManager manager;


    public CommandList(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();
        GuildMessageReceivedEvent event = ctx.getEvent();
        User sender = ctx.getAuthor();
        Message message = ctx.getMessage();

        if(args.isEmpty()) {
            EmbedBuilder builder = new EmbedBuilder();
            //StringBuilder builder = new StringBuilder();
            String prefix = VeryBadDesign.PREFIXES.get(ctx.getGuild().getIdLong());

            builder.setAuthor("Made by " + message.getMember().getEffectiveName(), null, sender.getEffectiveAvatarUrl());
            builder.setTitle("List of commands\n");
            manager.getCommands().stream().map(PrefixCommand::getName).forEach(
                    (it) -> builder.appendDescription("`")
                            .appendDescription(prefix)
                            .appendDescription(it)
                            .appendDescription("`\n")
            );
            builder.setColor(0x34d8eb);
            channel.sendMessageEmbeds(builder.build()).queue();
            return;
        }

        String search = args.get(0);
        PrefixCommand command = manager.getCommand(search);

        if(command == null) {
            channel.sendMessage("Nothing found for " + search).queue();
            return;
        }

        channel.sendMessage(command.getHelp()).queue();
    }

    @Override
    public String getName() {
        return "commands";
    }



    @Override
    public String getHelp() {
        return "Shows the list with commands in the bot\n" +
                "Usage: `&commands [command]`";
    }

    @Override
    public List<String> getAliases() {
        return List.of("cmds","commandlist");
    }
}
