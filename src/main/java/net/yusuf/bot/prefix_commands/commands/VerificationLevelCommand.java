package net.yusuf.bot.prefix_commands.commands;

import net.yusuf.bot.prefix_commands.CommandContext;
import net.yusuf.bot.prefix_commands.ICommand;


public class VerificationLevelCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        ctx.getGuild()
                .getVerificationLevel();
    }

    @Override
    public String getName() {
        return "verificationlevel";
    }

    @Override
    public String getHelp() {
        return "Shows the verification level of the server";
    }
}
