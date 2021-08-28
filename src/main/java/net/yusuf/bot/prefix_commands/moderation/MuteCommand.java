package net.yusuf.bot.prefix_commands.moderation;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.yusuf.bot.prefix_commands.CommandContext;
import net.yusuf.bot.prefix_commands.ICommand;

import java.util.List;

public class MuteCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        //TODO finish this
        final TextChannel channel = ctx.getChannel();
        final Message message = ctx.getMessage();
        final Member member = ctx.getMember();
        final List<String> args = ctx.getArgs();

        if (args.size() < 2 || message.getMentionedMembers().isEmpty()) {
            channel.sendMessage("missing argument").queue();
            return;
        }

        final Member target = message.getMentionedMembers().get(0);

        if (!member.canInteract(target) || !member.hasPermission(Permission.MANAGE_ROLES)) {
            channel.sendMessage("You are missing permission to mute this member. (Requires manage roles)").queue();
            return;
        }

        final Member selfMember = ctx.getSelfMember();

        if (!selfMember.canInteract(target) || !selfMember.hasPermission(Permission.MANAGE_ROLES)) {
            channel.sendMessage("I am missing permissions to warn that member (Requires manage roles)").queue();
            return;
        }

        final Boolean time = Boolean.getBoolean("");
        final String reason = String.join(" ", args.subList(1, args.size()));


    }
    @Override
    public String getName() {
        return "mute";
    }

    @Override
    public String getHelp() {
        return "(comming soon) mutes a member for a certain amount of time.\n" +
                "Usage: `&mute <@user> <time> <reason>`";
    }
}
