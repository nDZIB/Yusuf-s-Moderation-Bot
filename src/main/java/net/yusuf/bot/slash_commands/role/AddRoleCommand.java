package net.yusuf.bot.slash_commands.role;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.yusuf.bot.slash_commands.Command;

import static net.dv8tion.jda.api.interactions.commands.OptionType.ROLE;
import static net.dv8tion.jda.api.interactions.commands.OptionType.USER;

public class AddRoleCommand extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        final Member member = event.getMember();
        event.deferReply(true).queue();
        InteractionHook hook = event.getHook();
        hook.setEphemeral(true);

        Member target = event.getOption("user").getAsMember();

        if(!member.canInteract(target) || !member.hasPermission(Permission.MANAGE_ROLES)) {
            hook.sendMessage("You are missing permission to add a role this member").queue();
            return;
        }

        final Member selfMember = event.getGuild().getSelfMember();

        if(!selfMember.canInteract(target) || !selfMember.hasPermission(Permission.MANAGE_ROLES)) {
            hook.sendMessage("I am missing permissions to add a role to that member").queue();
            return;
        }

        Role role = event.getOption("role").getAsRole();

        event.getGuild()
                .addRoleToMember(target, role)
                .queue(
                        (__) -> event.reply("The role was given.").queue(),
                        (error) -> hook.sendMessage("Could not give the role").queue()
                );
    }

    @Override
    public String getName() {
        return "give_role";
    }

    @Override
    public String getDescription() {
        return "gives a role";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription())
                .addOptions(new OptionData(USER, "user", "The user which you want to give the role to.")
                        .setRequired(true))
                .addOptions(new OptionData(ROLE, "role", "The role which you want to give")
                        .setRequired(true));
    }
}
