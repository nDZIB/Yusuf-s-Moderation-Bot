package net.yusuf.bot.command.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.yusuf.bot.command.CommandContext;
import net.yusuf.bot.command.ICommand;

import java.util.List;

public class HelpCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();

        if(args.isEmpty()) {
            EmbedBuilder help = new EmbedBuilder();
            help.setTitle("Help");
            help.setDescription("Commands can be found by doing &commands.");
            help.setColor(0x34d8eb);
            ctx.getChannel().sendTyping().queue();
            ctx.getChannel().sendMessage(help.build()).queue();
        }
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Shows the list with commands in the bot\n" +
                "Usage: `&help [command]`";
    }
}
