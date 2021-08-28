package net.yusuf.bot.prefix_commands.commands;

import net.yusuf.bot.prefix_commands.CommandContext;
import net.yusuf.bot.prefix_commands.ICommand;

public class VanityUrlCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        ctx.getGuild()
                .getVanityUrl();
    }

    @Override
    public String getName() {
        return "vanityurl";
    }

    @Override
    public String getHelp() {
        return "Shows the discord link for the server";
    }
}
