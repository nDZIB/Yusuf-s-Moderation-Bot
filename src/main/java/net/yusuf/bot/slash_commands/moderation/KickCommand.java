package net.yusuf.bot.slash_commands.moderation;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.yusuf.bot.slash_commands.Command;

import static net.dv8tion.jda.api.interactions.commands.OptionType.USER;

public class KickCommand extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        final Member member = event.getOption("user").getAsMember();
        event.deferReply(true).queue(); // Let the user know we received the command before doing anything else
        InteractionHook hook = event.getHook(); // This is a special webhook that allows you to send messages without having permissions in the channel and also allows ephemeral messages
        hook.setEphemeral(true); // All messages here will now be ephemeral implicitly

        if (!event.getMember().hasPermission(Permission.KICK_MEMBERS))
        {
            hook.sendMessage("You do not have the required permissions to kick users from this server.").queue();
            return;
        }

        Member selfMember = event.getGuild().getSelfMember();
        if (!selfMember.hasPermission(Permission.KICK_MEMBERS))
        {
            hook.sendMessage("I don't have the required permissions to kick users from this server.").queue();
            return;
        }

        if (member != null && !selfMember.canInteract(member))
        {
            hook.sendMessage("This user is too powerful for me to kick.").queue();
            return;
        }


        // Ban the user and send a success response
        event.getGuild().kick(member)
                .flatMap(v -> hook.sendMessage("kicked user " + member.getUser()))
                .queue();
    }

    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getDescription() {
        return "kicks a user";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription())
                .addOptions(new OptionData(USER, "user", "The user to kick")
                        .setRequired(true));
    }
}
