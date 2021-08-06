package net.yusuf.bot.command.github_commands;

import net.dv8tion.jda.api.entities.TextChannel;
import net.yusuf.bot.command.CommandContext;
import net.yusuf.bot.command.ICommand;

import java.util.List;

public class DungeonMakersGithub implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if(args.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("https://github.com/Dungeon-Makers");
            channel.sendMessage(builder.toString()).queue();
        }
    }

    @Override
    public String getName() {
        return "dungeon_makers_github";
    }

    @Override
    public String getHelp() {
        return "Shows Github link";
    }
}
