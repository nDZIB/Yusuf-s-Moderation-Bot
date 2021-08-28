package net.yusuf.bot.slash_commands.normal_commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.yusuf.bot.slash_commands.Command;

import java.util.HashMap;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class HelloWorld extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        User sender = event.getUser();
        EmbedBuilder builder = new EmbedBuilder();
        event.deferReply(true).queue();
        InteractionHook hook = event.getHook();
        hook.setEphemeral(true);

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

        builder.setAuthor("Made by " + event.getMember().getEffectiveName(), null, sender.getEffectiveAvatarUrl());
        builder.setTitle("Hello World");
        builder.setDescription("Hello World in " + langs);
        builder.setColor(0x34d8eb);

        if(langs.containsKey(language)) {
            builder.setDescription(langs.get(language));
        } else {
            hook.sendMessage("Could not find hello world for this language.").queue();
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
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription())
                .addOptions(new OptionData(STRING, "language", "which programming language you want hello world in")
                .setRequired(true));
    }
}
