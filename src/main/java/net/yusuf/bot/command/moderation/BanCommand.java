package net.yusuf.bot.command.moderation;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.yusuf.bot.command.CommandContext;
import net.yusuf.bot.command.ICommand;

import java.util.List;

public class BanCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Message message = ctx.getMessage();
        final Member member = ctx.getMember();
        final List<String> args = ctx.getArgs();

        if(args.size() < 2 || message.getMentionedMembers().isEmpty()) {
            channel.sendMessage("missing argument").queue();
            return;
        }

        final Member target = message.getMentionedMembers().get(0);

        if(!member.canInteract(target) || !member.hasPermission(Permission.BAN_MEMBERS)) {
            channel.sendMessage("You are missing permission to ban this member").queue();
            return;
        }

        final Member selfMember = ctx.getSelfMember();

        if(!selfMember.canInteract(target) || !selfMember.hasPermission(Permission.BAN_MEMBERS)) {
            channel.sendMessage("I am missing permissions to ban that member").queue();
            return;
        }

        final String reason = String.join(" ", args.subList(1, args.size()));

            ctx.getGuild()
                .ban(target, 5, reason)
                .reason(reason)
                .queue(
                        (__) -> channel.sendMessage("Ban was successful").queue(),
                        (error) -> channel.sendMessageFormat("Could not ban %s", error.getMessage()).queue()
                );
    }

    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public String getHelp() {
        return "bans a member off the server.\n" +
                "Usage: `&ban <@user> <time> <reason>`";
    }
}
