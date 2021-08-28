package net.yusuf.bot.prefix_commands.admin;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.yusuf.bot.prefix_commands.CommandContext;
import net.yusuf.bot.prefix_commands.ICommand;

import java.util.List;

public class TransferOwnerShipCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Message message = ctx.getMessage();
        final Member member = ctx.getMember();
        final List<String> args = ctx.getArgs();

        if(args.size() < 1 || message.getMentionedMembers().isEmpty()) {
            channel.sendMessage("missing argument").queue();
            return;
        }

        final Member target = message.getMentionedMembers().get(0);

        if(!member.canInteract(target) || !member.hasPermission(Permission.ADMINISTRATOR)) {
            channel.sendMessage("You are not the onwer of the server").queue();
            return;
        }

        final Member selfMember = ctx.getSelfMember();

        if(!selfMember.canInteract(target) || !selfMember.hasPermission(Permission.ADMINISTRATOR)) {
            channel.sendMessage("I am missing permissions to use this command.").queue();
            return;
        }

        ctx.getGuild()
                .transferOwnership(target)
                .queue(
                        (__) -> channel.sendMessage("Server owner transferred.").queue(),
                        (error) -> channel.sendMessageFormat("Could not transfer the ownership of the server", error.getMessage()).queue()
                );
    }

    @Override
    public String getName() {
        return "transferowner";
    }

    @Override
    public String getHelp() {
        return "Transfers onwership of server\n" +
                "&transferowner <@user> ";
    }
}


