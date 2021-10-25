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

package net.yusuf.bot.slash_commands.simple_commands;

import github.io.yusuf.core.bot.Command;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.*;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;
import org.javacord.api.util.logging.ExceptionLogger;

import java.awt.*;
import java.util.Arrays;

public class HelloWorld implements Command {
    @Override
    public void onSlashCommand(SlashCommandCreateEvent slashCommandCreateEvent) {
        InteractionBase interactionBase = slashCommandCreateEvent.getInteraction();
        EmbedBuilder builder = new EmbedBuilder();
        slashCommandCreateEvent.getSlashCommandInteraction()
            .getFirstOptionStringValue()
            .map(language -> {
                var responder = interactionBase.createImmediateResponder();
                switch (language) {
                    case "java" -> responder.addEmbed(builder
                        .setDescription("System.out.println(\\\\\\\"Hello World\\\\\\\")"));
                    case "javascript" -> responder
                        .addEmbed(builder.setDescription("console.log(\\\"Hello World\\\");"));
                    case "c#" -> responder.addEmbed(
                            builder.setDescription("Console.WriteLine(\\\"Hello World\\\");"));
                    case "c++" -> responder
                        .addEmbed(builder.setDescription("std::cout << \\\"Hello World\\\";"));
                    case "python" -> responder
                        .addEmbed(builder.setDescription("print(\\\"Hello World\\\")"));
                    case "ruby" -> responder
                        .addEmbed(builder.setDescription("print(\\\"Hello World\\\")"));
                    case "c" -> responder
                        .addEmbed(builder.setDescription("printf(\"Hello World\");"));
                    case "swift" -> responder
                        .addEmbed(builder.setDescription("print(\\\"Hello World\\\")"));
                    case "list" -> responder.addEmbed(builder.setDescription(
                            "\n1. java\n2. javascript\n3. c#\n4. c++\n5. python\n6. ruby\n7. c\n8. swift"));
                    default -> responder
                        .addEmbed(builder.setDescription("More usernames to be added."));
                }
                builder.setAuthor("Made by " + slashCommandCreateEvent.getSlashCommandInteraction()
                    .getApi()
                    .getYourself()
                    .getName(), null, interactionBase.getUser().getAvatar());
                builder.setTitle("Hello World");
                builder.setColor(Color.CYAN);
                return responder;
            })
            .map(InteractionImmediateResponseBuilder::respond)
            .ifPresent(future -> future.exceptionally(ExceptionLogger.get()));
    }


    @Override
    public String getName() {
        return "hello_world";
    }

    @Override
    public String getDescription() {
        return "Get hello world for a certain programming language";
    }

    @Override
    public SlashCommandBuilder getCommandData() {
        return new SlashCommandBuilder().setName(getName())
            .setDescription(getDescription())
            .addOption(SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING,
                    "programming_language", "The language which you want hello world in.", true,
                    Arrays.asList(SlashCommandOptionChoice.create("java", 1),
                            SlashCommandOptionChoice.create("javascript", 2),
                            SlashCommandOptionChoice.create("c#", 3),
                            SlashCommandOptionChoice.create("c++", 4),
                            SlashCommandOptionChoice.create("python", 5),
                            SlashCommandOptionChoice.create("ruby", 6),
                            SlashCommandOptionChoice.create("c", 7),
                            SlashCommandOptionChoice.create("swift", 8))));
    }
}
