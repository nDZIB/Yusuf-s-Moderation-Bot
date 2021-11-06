/*
 *  GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
 *  Copyright (C) 2021 Free Software Foundation, Inc. <https://fsf.org/> Everyone is permitted to
 *  copy and distribute verbatim copies of this license document, but changing it is not allowed.
 *  Yusuf Arfan Ismail
 *  The GNU General Public License is a free, copyleft license for software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.normal_commands;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandConnector;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandVisibility;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.YusufSlashCommandEvent;

public class MemeCommand extends CommandConnector {

    /**
     * Were the command is registered.
     */
    public MemeCommand() {
        super("meme", "Shows you a random meme", CommandVisibility.SERVER);
    }

    @Override
    public void onSlashCommand(YusufSlashCommandEvent event) {
        NormalUtils.success("https://apis.duncte123.me/meme", event, true);
    }
}
