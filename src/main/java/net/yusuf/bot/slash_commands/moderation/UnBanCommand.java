package net.yusuf.bot.slash_commands.moderation;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.yusuf.bot.slash_commands.Command;

import static net.dv8tion.jda.api.interactions.commands.OptionType.INTEGER;

public class UnBanCommand extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        final String userId = event.getOption("user_id").getAsString();

        event.deferReply(true).queue(); // Let the user know we received the command before doing anything else
        InteractionHook hook = event.getHook(); // This is a special webhook that allows you to send messages without having permissions in the channel and also allows ephemeral messages
        hook.setEphemeral(true); // All messages here will now be ephemeral implicitly
        if (!event.getMember().hasPermission(Permission.BAN_MEMBERS))
        {
            hook.sendMessage("You do not have the required permissions to ban users from this server.").queue();
            return;
        }

        Member selfMember = event.getGuild().getSelfMember();
        if (!selfMember.hasPermission(Permission.BAN_MEMBERS))
        {
            hook.sendMessage("I don't have the required permissions to unban the user from this server.").queue();
            return;
        }

        event.getGuild().unban(userId)
                .flatMap(v -> hook.sendMessage("Unbanned user"))
                .queue();
    }

    @Override
    public String getName() {
        return "unban";
    }

    @Override
    public String getDescription() {
        return "Unban a user";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription())
                .addOptions(new OptionData(INTEGER, "user_id", "The user id for the user you want to unban")
                        .setRequired(true));
    }
}

