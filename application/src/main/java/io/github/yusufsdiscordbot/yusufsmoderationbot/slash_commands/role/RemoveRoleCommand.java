/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007 Copyright (C) 2021 Free Software Foundation,
 * Inc. <https://fsf.org/> Everyone is permitted to copy and distribute verbatim copies of this
 * license document, but changing it is not allowed. Yusuf Arfan Ismail The GNU General Public
 * License is a free, copyleft license for software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.role;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandConnector;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandVisibility;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.YusufMember;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.YusufSlashCommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import static net.dv8tion.jda.api.interactions.commands.OptionType.ROLE;
import static net.dv8tion.jda.api.interactions.commands.OptionType.USER;

public class RemoveRoleCommand extends CommandConnector {
    private static final String REMOVE_ROLE_USER = "user";


    public RemoveRoleCommand() {
        super("remove_role", "removes a role to the given user", CommandVisibility.SERVER);

        getCommandData()
            .addOptions(new OptionData(USER, REMOVE_ROLE_USER,
                    "The user which you want to remove the role from.").setRequired(true))
            .addOptions(new OptionData(ROLE, "role", "The role which you want to give")
                .setRequired(true));
    }

    @Override
    public void onSlashCommand(YusufSlashCommandEvent event) {
        final YusufMember member = event.getMember();

        Member target = event.getOption(REMOVE_ROLE_USER).getAsMember();

        if (!member.canInteract(target) || !member.hasPermission(Permission.MANAGE_ROLES)) {
            event.replyMessage("You are missing permission to add a role this member");
            return;
        }

        final YusufMember selfMember = event.getGuild().getBot();

        if (!selfMember.canInteract(target) || !selfMember.hasPermission(Permission.MANAGE_ROLES)) {
            event.replyMessage("I am missing permissions to add a role to that member");
            return;
        }

        Role role = event.getOption("role").getAsRole();

        event.getGuild()
            .removeRoleFromMember(target, role)
            .queue(success -> event.replyMessage("The role was remove."),
                    error -> event.replyEphemeral("Could not remove the role"));
    }
}
