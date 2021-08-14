package net.yusuf.bot.command.role;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.yusuf.bot.command.CommandContext;
import net.yusuf.bot.command.ICommand;

import java.util.List;

public class AddRoleCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Message message = ctx.getMessage();
        final Member member = ctx.getMember();
        final List<String> args = ctx.getArgs();
        final Role role;

        if(args.size() < 2 || message.getMentionedMembers().isEmpty()) {
            channel.sendMessage("missing argument").queue();
            return;
        }

        final Member target = message.getMentionedMembers().get(0);

        if(!member.canInteract(target) || !member.hasPermission(Permission.MANAGE_ROLES)) {
            channel.sendMessage("You are missing permission to add a role this member").queue();
            return;
        }

        final Member selfMember = ctx.getSelfMember();

        if(!selfMember.canInteract(target) || !selfMember.hasPermission(Permission.MANAGE_ROLES)) {
            channel.sendMessage("I am missing permissions to add a role to that member").queue();
            return;
        }

        role = ctx.getJDA().getRoleById(args.get(1));
        ctx.getGuild()
                .addRoleToMember(target, role)
                .queue(
                        (__) -> channel.sendMessage("The role was given.").queue(),
                        (error) -> channel.sendMessageFormat("Could not give the role to %s", error.getMessage()).queue()
                );
    }

    @Override
    public String getName() {
        return "addrole";
    }

    @Override
    public String getHelp() {
        return "Gives a role\n" +
                "&addrole <@user> <roleid>;";
    }
}
