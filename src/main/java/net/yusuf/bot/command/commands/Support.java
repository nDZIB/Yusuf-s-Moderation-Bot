package net.yusuf.bot.command.commands;

import net.dv8tion.jda.api.entities.TextChannel;
import net.yusuf.bot.command.CommandContext;
import net.yusuf.bot.command.ICommand;

import java.util.List;

public class Support implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if(args.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("For support join this discord server\n");
            builder.append("https://discord.gg/hpY6s6mh3N");
            channel.sendMessage(builder.toString()).queue();
        }
    }

    @Override
    public String getName() {
        return "support";
    }

    @Override
    public String getHelp() {
        return "The support server";
    }
}
