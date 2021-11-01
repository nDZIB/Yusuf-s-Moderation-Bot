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

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.normal_commands;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandVisibility;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.Command;

import java.util.HashMap;
import java.util.List;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;
import static net.dv8tion.jda.api.interactions.commands.Command.Choice;

public class HelloWorld implements Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        User sender = event.getUser();
        EmbedBuilder builder = new EmbedBuilder();

        final String language = event.getOption("language").getAsString();
        HashMap<String, String> langs = new HashMap<String, String>();

        langs.put("java", "System.out.println(\\\"Hello World\\\");");
        langs.put("javascript", "console.log(\\\"Hello World\\\");");
        langs.put("c#", "Console.WriteLine(\\\"Hello World\\\");");
        langs.put("c++", "std::cout << \\\"Hello World\\\";");
        langs.put("python", "print(\\\"Hello World\\\")");
        langs.put("ruby", "print(\\\"Hello World\\\")");
        langs.put("c", "printf(\\\"Hello World\\\");");
        langs.put("swift", "print(\\\"Hello World\\\")");

        builder.setAuthor("Made by " + event.getMember().getEffectiveName(), null,
                sender.getEffectiveAvatarUrl());
        builder.setTitle("Hello World");
        builder.setDescription("Hello World in " + langs);
        builder.setColor(0x34d8eb);

        if (langs.containsKey(language)) {
            builder.setDescription(langs.get(language));
        } else {
            event.reply("Could not find hello world for this language.").queue();
            return;
        }

        event.replyEmbeds(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "hello_world";
    }

    @Override
    public String getDescription() {
        return "Shows the hello world for that language";
    }

    @Override
    public CommandVisibility getVisibility() {
        return CommandVisibility.SERVER;
    }

    @Override
    public CommandData getCommandData() {

        return new CommandData(getName(), getDescription()).addOptions(new OptionData(STRING,
                "language", "which programming language you want hello world in").setRequired(true)
                    .addChoices(helloWorld));
    }

    public static final List<net.dv8tion.jda.api.interactions.commands.Command.Choice> helloWorld =
            List.of(new Choice("Java", "java"), new Choice("Javascript", "javascript"),
                    new Choice("C#", "c#"), new Choice("C++", "c++"),
                    new Choice("Python", "python"), new Choice("Ruby", "ruby"),
                    new Choice("C", "c"), new Choice("Swift", "swift"));

}
