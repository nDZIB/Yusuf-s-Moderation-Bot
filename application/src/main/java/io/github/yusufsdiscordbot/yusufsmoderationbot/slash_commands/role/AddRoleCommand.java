/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
 *
 * Copyright (C) 2021 Free Software Foundation, Inc. <https://fsf.org/> Everyone is permitted to
 * copy and distribute verbatim copies of this license document, but changing it is not allowed.
 *
 * Yusuf Arfan Ismail
 *
 * The GNU General Public License is a free, copyleft license for software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.role;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.*;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import static net.dv8tion.jda.api.interactions.commands.OptionType.ROLE;
import static net.dv8tion.jda.api.interactions.commands.OptionType.USER;

public class AddRoleCommand extends CommandConnector {
    /**
     * Were the command is registered.
     */
    public AddRoleCommand() {
        super("add_role", "add a role to the given user", CommandVisibility.SERVER);

        getCommandData()
                .addOptions(new OptionData(USER, RoleCommandUtil.USER, "The user which you want to give the role to.")
                        .setRequired(true))
                .addOptions(new OptionData(ROLE, "role", "The role which you want to give")
                        .setRequired(true));
    }

    @Override
    public void onSlashCommand(YusufSlashCommandEvent event) {
        final YusufMember member = event.getMember();

        Member target = event.getOption(RoleCommandUtil.USER).getAsMember();

        if (!member.canInteract(target) || !member.hasPermission(Permission.MANAGE_ROLES)) {
            event.replyEphemeralMessage("You are missing permission to add a role this member");
            return;
        }

        final Member selfMember = event.getGuild().getBot();

        if (!selfMember.canInteract(target) || !selfMember.hasPermission(Permission.MANAGE_ROLES)) {
            event.replyEphemeralMessage("I am missing permissions to add a role to that member");
            return;
        }

        Role role = event.getOption("role").getAsRole();

        event.getGuild()
            .addRoleToMember(target, role)
            .queue((__) -> event.replyMessage("The role was given."),
                    (error) -> event.replyEphemeralMessage("Could not give the role"));
    }
}
