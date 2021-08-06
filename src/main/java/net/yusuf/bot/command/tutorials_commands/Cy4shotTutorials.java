package net.yusuf.bot.command.tutorials_commands;

import net.dv8tion.jda.api.entities.TextChannel;
import net.yusuf.bot.command.CommandContext;
import net.yusuf.bot.command.ICommand;

import java.util.List;

public class Cy4shotTutorials implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if(args.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("https://www.youtube.com/channel/UCJIDXtGpf4wv1ybDzdTA_vQ");
            channel.sendMessage(builder.toString()).queue();
        }
    }

    @Override
    public String getName() {
        return "cy4_shot_tutorials";
    }

    @Override
    public String getHelp() {
        return "Shows Youtube channel";
    }
}
