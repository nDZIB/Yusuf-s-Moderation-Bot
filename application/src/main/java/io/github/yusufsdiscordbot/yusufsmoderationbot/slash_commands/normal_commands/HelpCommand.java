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

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.normal_commands;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandConnector;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandVisibility;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.YusufSlashCommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.Command;

import java.util.Objects;


public class HelpCommand extends CommandConnector {

    /**
     * Were the command is registered.
     */
    public HelpCommand() {
        super("help", "Provides help", CommandVisibility.SERVER);
    }

    @Override
    public void onSlashCommand(YusufSlashCommandEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        User sender = event.getUser().getUser();
        builder.setAuthor("Made by " + Objects.requireNonNull(event.getMember()).getName(),
                null, sender.getEffectiveAvatarUrl());
        builder.setTitle("Help");
        builder.setDescription("Support can be found by typing /support");
        builder.setColor(0x34d8eb);
        event.replyEmbed(builder.build());
    }
}
