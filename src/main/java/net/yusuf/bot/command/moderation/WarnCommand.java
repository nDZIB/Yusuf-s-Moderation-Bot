package net.yusuf.bot.command.moderation;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
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
        final Member member = ctx.getMember();

        if(args.size() < 2 || message.getMentionedMembers().isEmpty()) {
            channel.sendMessage("missing argument").queue();
            return;
        }

        final User target = message.getMentionedMembers().get(0).getUser();
        final String reason = String.join(" ", args.subList(1, args.size()));

        target.openPrivateChannel().queue((privateChannel) -> channel.sendMessage("You have been warned for " + reason));
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
