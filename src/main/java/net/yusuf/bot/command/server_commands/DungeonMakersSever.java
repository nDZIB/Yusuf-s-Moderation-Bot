package net.yusuf.bot.command.server_commands;

import net.dv8tion.jda.api.entities.TextChannel;
import net.yusuf.bot.command.CommandContext;
import net.yusuf.bot.command.ICommand;

import java.util.List;

public class DungeonMakersSever implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if(args.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("https://discord.gg/hpY6s6mh3N");
            channel.sendMessage(builder.toString()).queue();
        }
    }

    @Override
    public String getName() {
        return "dungeon_makers_sever";
    }

    @Override
    public String getHelp() {
        return "Shows Discord server link";
    }
}

