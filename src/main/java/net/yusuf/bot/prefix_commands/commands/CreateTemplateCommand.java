package net.yusuf.bot.prefix_commands.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.yusuf.bot.prefix_commands.CommandContext;
import net.yusuf.bot.prefix_commands.ICommand;

import java.util.List;

public class CreateTemplateCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final Message message = ctx.getMessage();
        final Member member = ctx.getMember();
        final List<String> args = ctx.getArgs();


        if (ctx.getArgs().isEmpty()) {
            channel.sendMessage("Correct usage is `&createtemplate <template name> <template description>`").queue();
            return;
        }

        if(!member.hasPermission(Permission.MANAGE_SERVER)) {
            channel.sendMessage("You are missing permission to ban this member").queue();
            return;
        }

        final Member selfMember = ctx.getSelfMember();

        if(!selfMember.hasPermission(Permission.MANAGE_SERVER)) {
            channel.sendMessage("I am missing permissions to ban that member").queue();
            return;
        }

        final String name = String.join("", args);
        final String description = String.join(" ", args.subList(1, args.size()));


        ctx.getGuild()
                .createTemplate(name, description)
                .queue(
                        (__) -> channel.sendMessage("The template was created").queue(),
                        (error) -> channel.sendMessageFormat("Could not create the template ", error.getMessage()).queue()
                );
    }

    @Override
    public String getName() {
        return "createtemplate";
    }

    @Override
    public String getHelp() {
        return "Creates a server template\n" +
                "&createtemplate <template name> <template description>";
    }
}
