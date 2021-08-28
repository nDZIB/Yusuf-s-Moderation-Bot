package net.yusuf.bot.prefix_commands.commands;

import me.duncte123.botcommons.StringUtils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.yusuf.bot.prefix_commands.CommandContext;
import net.yusuf.bot.prefix_commands.ICommand;

import java.lang.management.ManagementFactory;
import java.util.List;

import static net.yusuf.bot.prefix_commands.ExtraCommandStuff.reply;

public class UptimeCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();
        GuildMessageReceivedEvent event = ctx.getEvent();
        Message message = ctx.getMessage();

        final long duration = ManagementFactory.getRuntimeMXBean().getUptime();

        final long years = duration / 31104000000L;
        final long months = duration / 2592000000L % 12;
        final long days = duration / 86400000L % 30;
        final long hours = duration / 3600000L % 24;
        final long minutes = duration / 60000L % 60;
        final long seconds = duration / 1000L % 60;

        String uptime = "";
        uptime += years == 0 ? "" : years + " Year" + (years > 1 ? "s" : "") + ", ";
        uptime += months == 0 ? "" : months + " Month" + (months > 1 ? "s" : "") + ", ";
        uptime += days == 0 ? "" : days + " Day" + (days > 1 ? "s" : "") + ", ";
        uptime += hours == 0 ? "" : hours + " Hour" + (hours > 1 ? "s" : "") + ", ";
        uptime += minutes == 0 ? "" : minutes + " Minute" + (minutes > 1 ? "s" : "") + ", ";
        uptime += seconds == 0 ? "" : seconds + " Second" + (seconds > 1 ? "s" : "") + ", ";

        uptime = StringUtils.replaceLast(uptime, ", ", "");
        uptime = StringUtils.replaceLast(uptime, ",", " and");

        reply(event, uptime);
    }

    @Override
    public String getName() {
        return "uptime";
    }

    @Override
    public String getHelp() {
        return "Shows the uptime of the bot";
    }
}
