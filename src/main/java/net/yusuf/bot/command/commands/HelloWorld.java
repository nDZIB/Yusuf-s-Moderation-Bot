package net.yusuf.bot.command.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.yusuf.bot.command.CommandContext;
import net.yusuf.bot.command.ICommand;

import java.util.List;
import java.util.HashMap;

public class HelloWorld implements ICommand {
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

        ctx.getChannel().sendTyping().queue();
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