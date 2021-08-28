package net.yusuf.bot.prefix_commands.tutorials_commands;

import net.dv8tion.jda.api.entities.TextChannel;
import net.yusuf.bot.prefix_commands.CommandContext;
import net.yusuf.bot.prefix_commands.ICommand;

import java.util.List;

public class Silentchaos512Tutorials implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if(args.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("https://www.youtube.com/user/SilentChaos512");
            channel.sendMessage(builder.toString()).queue();
        }
    }

    @Override
    public String getName() {
        return "silentchaos512_tutorials";
    }

    @Override
    public String getHelp() {
        return "Shows Youtube channel";
    }
}
