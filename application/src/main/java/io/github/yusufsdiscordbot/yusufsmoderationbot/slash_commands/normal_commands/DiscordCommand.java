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
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.HashMap;
import java.util.List;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class DiscordCommand extends CommandConnector {
    private static final String COMMAND_OPTION_NAME = "discord-server";

    /**
     * Were the command is registered.
     *
     */
    public DiscordCommand() {
        super("discord", "Provides a Discord server link", CommandVisibility.SERVER);

        getCommandData().addOptions(new OptionData(STRING, COMMAND_OPTION_NAME,
                "You will be provided with invite link for the server you requested")
                    .setRequired(true)
                    .addChoices(discordServers));
    }

    public static final List<net.dv8tion.jda.api.interactions.commands.Command.Choice> discordServers =
            List.of(new net.dv8tion.jda.api.interactions.commands.Command.Choice("Cy4", "cy4"),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice("TurtyWurty",
                            "turtywurty"),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice(
                            "Yusuf's Discord Bot", "yusufsdiscordbot"),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice("TogetherJava",
                            "togetherjava"));

    @Override
    public void onSlashCommand(YusufSlashCommandEvent yusufSlashCommandEvent) {
        User sender = yusufSlashCommandEvent.getUser().getUser();
        EmbedBuilder builder = new EmbedBuilder();

        final String discord = yusufSlashCommandEvent.getOption(COMMAND_OPTION_NAME).getAsString();
        HashMap<String, String> dis = new HashMap<String, String>();

        dis.put("cy4", "https://discord.gg/j5tBQx7uny");
        dis.put("turtywurty", "https://discord.gg/BxD5JukyQs");
        dis.put("yusufsdiscordbot", "https://discord.gg/hpY6s6mh3N");
        dis.put("togetherjava", "https://discord.gg/GzvQjhv");

        builder.setAuthor("Made by " + yusufSlashCommandEvent.getName(), null,
                sender.getEffectiveAvatarUrl());
        builder.setTitle("Discord server");
        builder.setDescription("The Discord server" + dis);
        builder.setColor(0x34d8eb);

        if (dis.containsKey(discord)) {
            builder.setDescription(dis.get(discord));
        } else {
            yusufSlashCommandEvent.replyEphemeralMessage("Could not find the Discord server");
            return;
        }

        yusufSlashCommandEvent.replyEmbed(builder.build());
    }
}
