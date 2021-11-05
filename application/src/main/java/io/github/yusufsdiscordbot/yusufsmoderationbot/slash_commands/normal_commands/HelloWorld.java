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

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.normal_commands;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandConnector;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandVisibility;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.YusufSlashCommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.HashMap;
import java.util.List;

import static net.dv8tion.jda.api.interactions.commands.Command.Choice;
import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class HelloWorld extends CommandConnector {
    private static final String COMMAND_OPTION_NAME = "language";

    /**
     * Were the command is registered.
     */
    public HelloWorld() {
        super("hello_world", "Shows the hello world for that language", CommandVisibility.SERVER);


        getCommandData().addOptions(new OptionData(STRING, COMMAND_OPTION_NAME,
                "which programming language you want hello world in").setRequired(true)
                    .addChoices(helloWorld));
    }


    public static final List<net.dv8tion.jda.api.interactions.commands.Command.Choice> helloWorld =
            List.of(new Choice("Java", "java"), new Choice("Javascript", "javascript"),
                    new Choice("C#", "c#"), new Choice("C++", "c++"),
                    new Choice("Python", "python"), new Choice("Ruby", "ruby"),
                    new Choice("C", "c"), new Choice("Swift", "swift"));


    @Override
    public void onSlashCommand(YusufSlashCommandEvent event) {
        User sender = event.getUser().getUser();
        EmbedBuilder builder = new EmbedBuilder();

        final String language = event.getOption(COMMAND_OPTION_NAME).getAsString();
        HashMap<String, String> langs = new HashMap<String, String>();

        langs.put("java", "System.out.println(\\\"Hello World\\\");");
        langs.put("javascript", "console.log(\\\"Hello World\\\");");
        langs.put("c#", "Console.WriteLine(\\\"Hello World\\\");");
        langs.put("c++", "std::cout << \\\"Hello World\\\";");
        langs.put("python", "print(\\\"Hello World\\\")");
        langs.put("ruby", "print(\\\"Hello World\\\")");
        langs.put("c", "printf(\\\"Hello World\\\");");
        langs.put("swift", "print(\\\"Hello World\\\")");

        builder.setAuthor("Made by " + event.getMember().getName(), null,
                sender.getEffectiveAvatarUrl());
        builder.setTitle("Hello World");
        builder.setDescription("Hello World in " + langs);
        builder.setColor(0x34d8eb);

        if (langs.containsKey(language)) {
            builder.setDescription(langs.get(language));
        } else {
            event.replyEphemeral("Could not find hello world for this language.");
            return;
        }

        event.replyEmbed(builder.build());
    }
}
