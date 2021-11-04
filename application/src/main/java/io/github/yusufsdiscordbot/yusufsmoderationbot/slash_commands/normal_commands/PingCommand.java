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
import net.dv8tion.jda.api.JDA;

public class PingCommand extends CommandConnector {

    /**
     * Were the command is registered.
     */
    public PingCommand() {
        super("ping", "Shows the latency of the bot", CommandVisibility.SERVER);
    }

    /**
     * The command itself.
     *
     * @param event The event that triggered the command.
     */
    @Override
    public void onSlashCommand(YusufSlashCommandEvent event) {
        JDA jda = event.getJDA();
        long ping = jda.getGatewayPing();
        event.replyMessage("Pong! " + ping + "ms");
    }
}


