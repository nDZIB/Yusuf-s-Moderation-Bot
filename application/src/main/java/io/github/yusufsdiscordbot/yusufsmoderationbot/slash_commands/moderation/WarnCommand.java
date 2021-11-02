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

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.moderation;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandVisibility;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.Command;


import static net.dv8tion.jda.api.interactions.commands.OptionType.*;

public class WarnCommand implements Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        final User user = event.getUser();
        final Member member = event.getMember();

        event.deferReply(true).queue(); // Let the user know we received the command before doing
                                        // anything else
        InteractionHook hook = event.getHook(); // This is a special webhook that allows you to send
                                                // messages without having permissions in the
                                                // channel and also allows ephemeral messages
        hook.setEphemeral(true); // All messages here will now be ephemeral implicitly
        if (!event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
            hook.sendMessage(
                    "You do not have the required permissions to warn users from this server.")
                .queue();
            return;
        }

        Member selfMember = event.getGuild().getSelfMember();
        if (!selfMember.hasPermission(Permission.MANAGE_ROLES)) {
            hook.sendMessage(
                    "I don't have the required permissions to warn users from this server.")
                .queue();
            return;
        }

        if (member != null && !selfMember.canInteract(member)) {
            hook.sendMessage("This user is too powerful for me to warn.").queue();
            return;
        }

    }

    @Override
    public String getName() {
        return "warn";
    }

    @Override
    public String getDescription() {
        return "Warn a user";
    }

    @Override
    public CommandVisibility getVisibility() {
        return CommandVisibility.SERVER;
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription())
            .addOptions(new OptionData(USER, "user", "The user to warn").setRequired(true))
            .addOptions(
                    new OptionData(STRING, "reason", "The reason why you want to warn the user"));
    }
}
