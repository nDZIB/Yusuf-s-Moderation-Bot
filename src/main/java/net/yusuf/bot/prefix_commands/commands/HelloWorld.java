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
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;


import java.util.List;
import java.util.HashMap;

public class HelloWorld implements PrefixCommand {
    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        final TextChannel channel = ctx.getChannel();
        Message message = ctx.getMessage();
        User sender = ctx.getAuthor();

        EmbedBuilder help = new EmbedBuilder();
        final String langauge = String.join("", args).toLowerCase();

        HashMap<String, String> langs = new HashMap<String, String>();

        langs.put("java", "System.out.println(\\\"Hello World\\\");");
        langs.put("javascript", "console.log(\\\"Hello World\\\");");
        langs.put("c#", "Console.WriteLine(\\\"Hello World\\\");");
        langs.put("c++", "std::cout << \\\"Hello World\\\";");
        langs.put("python", "print(\\\"Hello World\\\")");
        langs.put("ruby", "print(\\\"Hello World\\\")");
        langs.put("c", "printf(\\\"Hello World\\\");");
        langs.put("swift", "print(\\\"Hello World\\\")");

        help.setAuthor("Made by " + message.getMember().getEffectiveName(), null, sender.getEffectiveAvatarUrl());
        help.setTitle("Hello World in " + langauge);
        help.setColor(0x34d8eb);

        if(langs.containsKey(langauge)) {
            help.setDescription(langs.get(langauge));
        } else {
            help.setTitle("Error");
            help.setDescription("I could not find hello world for this langauge");
        }

        
        ctx.getChannel().sendMessage(help.build()).queue();
    }

    @Override
    public String getName() {
        return "helloworld";
    }

    @Override
    public String getHelp() {
        return "Print hello world for a programming language in an embed" +
                "&helloworld <programming language>";
    }
}