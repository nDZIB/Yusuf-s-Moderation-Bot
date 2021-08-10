package net.yusuf.bot.command.moderation;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.yusuf.bot.command.CommandContext;
import net.yusuf.bot.command.ICommand;

import java.util.List;

public class WarnCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Message message = ctx.getMessage();
        final List<String> args = ctx.getArgs();

        if(args.size() < 2 || message.getMentionedMembers().isEmpty()) {
            channel.sendMessage("missing argument").queue();
            return;
        }

        // final Member target = message.getMentionedMembers().get(0);
        final User user2 =  message.getMentionedMembers().get(0).getUser();
        final String reason = String.join(" ", args.subList(1, args.size()));

        user2.openPrivateChannel().queue((privateChannel) -> channel.sendMessage("You have been warned for " + reason));
    }

    @Override
    public String getName() {
        return "warn";
    }

    @Override
    public String getHelp() {
        return "warns a member.\n" +
                "Usage: `&warn <@user> <reason>`";
    }
}
