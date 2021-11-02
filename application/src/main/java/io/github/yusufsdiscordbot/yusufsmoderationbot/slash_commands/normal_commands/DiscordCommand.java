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

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.Command;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandVisibility;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class DiscordCommand implements Command {
    private static final String COMMAND_OPTION_NAME = "discord-server";

    @Override
    public void onSlashCommand(SlashCommandEvent slashCommandEvent) {
        User sender = slashCommandEvent.getUser();
        EmbedBuilder builder = new EmbedBuilder();

        final String discord = slashCommandEvent.getOption(COMMAND_OPTION_NAME).getAsString();
        HashMap<String, String> dis = new HashMap<String, String>();

        dis.put("cy4", "https://discord.gg/j5tBQx7uny");
        dis.put("turtywurty", "https://discord.gg/BxD5JukyQs");
        dis.put("yusufsdiscordbot", "https://discord.gg/hpY6s6mh3N");
        dis.put("togetherjava", "https://discord.gg/GzvQjhv");

        builder.setAuthor("Made by " + slashCommandEvent.getMember().getEffectiveName(), null,
                sender.getEffectiveAvatarUrl());
        builder.setTitle("Discord server");
        builder.setDescription("The Discord server" + dis);
        builder.setColor(0x34d8eb);

        if (dis.containsKey(discord)) {
            builder.setDescription(dis.get(discord));
        } else {
            slashCommandEvent.reply("Could not find the Discord server").queue();
            return;
        }

        slashCommandEvent.replyEmbeds(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "discord";
    }

    @Override
    public String getDescription() {
        return "Provides a Discord server link";
    }

    @Override
    public CommandVisibility getVisibility() {
        return CommandVisibility.SERVER;
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription())
            .addOptions(new OptionData(STRING, COMMAND_OPTION_NAME,
                    "You will be provided with invite link for the server you requested")
                        .setRequired(true)
                        .addChoices(discordServers));
    }

    public static final List<net.dv8tion.jda.api.interactions.commands.Command.Choice> discordServers =
            List.of(new net.dv8tion.jda.api.interactions.commands.Command.Choice("Cy4", "cy4"),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice("TurtyWurty",
                            "turtywurty"),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice(
                            "Yusuf's_Discord_Bot", "yusufsdiscordbot"),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice("TogetherJava",
                            "togetherjava"));

}
