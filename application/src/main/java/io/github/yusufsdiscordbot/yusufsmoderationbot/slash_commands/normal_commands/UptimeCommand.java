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

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.normal_commands;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.Command;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandVisibility;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.lang.management.ManagementFactory;

import static me.duncte123.botcommons.StringUtils.replaceLast;

/**
 * Code form <a href=
 * "https://github.com/DV8FromTheWorld/Yui/blob/master/src/main/java/net/dv8tion/discord/commands/UptimeCommand.java">here</a>
 * and modified by Yusuf
 */
public class UptimeCommand implements Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        // Taken from Almighty Alpaca
        // https://github.com/Java-Discord-Bot-System/Plugin-Uptime/blob/master/src/main/java/com/almightyalpaca/discord/bot/plugin/uptime/UptimePlugin.java#L28-L42
        final long duration = ManagementFactory.getRuntimeMXBean().getUptime();

        final long years = duration / 31104000000L;
        final long months = duration / 2592000000L % 12;
        final long days = duration / 86400000L % 30;
        final long hours = duration / 3600000L % 24;
        final long minutes = duration / 60000L % 60;
        final long seconds = duration / 1000L % 60;
        // final long milliseconds = duration % 1000;

        String uptime = (years == 0 ? "" : "**" + years + "** Years, ")
                + (months == 0 ? "" : "**" + months + "** Months, ")
                + (days == 0 ? "" : "**" + days + "** Days, ")
                + (hours == 0 ? "" : "**" + hours + "** Hours, ")
                + (minutes == 0 ? "" : "**" + minutes + "** Minutes, ") + (seconds == 0 ? ""
                        : "**" + seconds + "** Seconds, ") /*
                                                            * + (milliseconds == 0 ? "" :
                                                            * milliseconds + " Milliseconds, ")
                                                            */;

        uptime = replaceLast(uptime, ", ", "");
        uptime = replaceLast(uptime, ",", " and");

        event.reply("I've been online for:\n" + uptime);
    }

    @Override
    public String getName() {
        return "uptime";
    }

    @Override
    public String getDescription() {
        return "Tells you how long the discord bot has been up fors";
    }

    @Override
    public CommandVisibility getVisibility() {
        return CommandVisibility.SERVER;
    }

    @Override
    public CommandData getCommandData() {
        return null;
    }
}