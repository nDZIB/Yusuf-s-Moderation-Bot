package net.yusuf.bot.prefix_commands.moderation;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.yusuf.bot.prefix_commands.CommandContext;
import net.yusuf.bot.prefix_commands.ICommand;

import java.util.List;

public class UnBanCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Member member = ctx.getMember();
        final List<String> args = ctx.getArgs();

        if (ctx.getArgs().isEmpty()) {
            channel.sendMessage("Correct usage is `&unban <user id>`").queue();
            return;
        }

        final String userId = String.join("", args);

        if(!member.hasPermission(Permission.BAN_MEMBERS)) {
            channel.sendMessage("You are missing permission to ban this member").queue();
            return;
        }

        final Member selfMember = ctx.getSelfMember();

        if(!selfMember.hasPermission(Permission.BAN_MEMBERS)) {
            channel.sendMessage("I am missing permissions to ban that member").queue();
            return;
        }

        ctx.getGuild()
                .unban(userId)
                .queue(
                        (__) -> channel.sendMessage("Unban was successful").queue(),
                        (error) -> channel.sendMessageFormat("Could not unban %s", error.getMessage()).queue()
                );
    }

    @Override
    public String getName() {
        return "unban";
    }

    @Override
    public String getHelp() {
        return "unbans a user\n" +
                "&unban <userid>";
    }
}
