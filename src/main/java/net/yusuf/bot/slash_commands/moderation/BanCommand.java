package net.yusuf.bot.slash_commands.moderation;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.yusuf.bot.slash_commands.Command;

import static net.dv8tion.jda.api.interactions.commands.OptionType.*;

public class BanCommand extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        final User user = event.getUser();
        final Member member = event.getMember();

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
            hook.sendMessage("I don't have the required permissions to ban users from this server.").queue();
            return;
        }

        if (member != null && !selfMember.canInteract(member))
        {
            hook.sendMessage("This user is too powerful for me to ban.").queue();
            return;
        }

        int delDays = 0;
        OptionMapping option = event.getOption("del_days");
        if (option != null) // null = not provided
            delDays = (int) Math.max(0, Math.min(7, option.getAsLong()));
        // Ban the user and send a success response
        event.getGuild().ban(user, delDays)
                .flatMap(v -> hook.sendMessage("Banned user " + user.getAsTag()))
                .queue();
    }

    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public String getDescription() {
        return "ban a user";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription())
                .addOptions(new OptionData(USER, "user", "The user to ban")
                        .setRequired(true)) // This command requires a parameter
                .addOptions(new OptionData(INTEGER, "del_days", "Delete messages from the past days."));
    }
}
