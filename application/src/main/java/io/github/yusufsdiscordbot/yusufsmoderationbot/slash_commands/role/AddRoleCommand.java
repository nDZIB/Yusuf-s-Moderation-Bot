/*
 * GNU GENERAL PUBLIC LICENSE
 *                        Version 3, 29 June 2007
 *
 *  Copyright (C) 2021 Free Software Foundation, Inc. <https://fsf.org/>
 *  Everyone is permitted to copy and distribute verbatim copies
 *  of this license document, but changing it is not allowed.
 *
 *                            Yusuf Arfan Ismail
 *
 *   The GNU General Public License is a free, copyleft license for
 * software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.role;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandVisibility;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.Command;

import static net.dv8tion.jda.api.interactions.commands.OptionType.ROLE;
import static net.dv8tion.jda.api.interactions.commands.OptionType.USER;

public class AddRoleCommand implements Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        final Member member = event.getMember();

        Member target = event.getOption("user").getAsMember();

        if (!member.canInteract(target) || !member.hasPermission(Permission.MANAGE_ROLES)) {
            event.reply("You are missing permission to add a role this member")
                .setEphemeral(true)
                .queue();
            return;
        }

        final Member selfMember = event.getGuild().getSelfMember();

        if (!selfMember.canInteract(target) || !selfMember.hasPermission(Permission.MANAGE_ROLES)) {
            event.reply("I am missing permissions to add a role to that member")
                .setEphemeral(true)
                .queue();
            return;
        }

        Role role = event.getOption("role").getAsRole();

        event.getGuild()
            .addRoleToMember(target, role)
            .queue((__) -> event.reply("The role was given.").queue(),
                    (error) -> event.reply("Could not give the role").setEphemeral(true).queue());
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
    public CommandVisibility getVisibility() {
        return CommandVisibility.SERVER;
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
