/*
 *  GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
 *  Copyright (C) 2021 Free Software Foundation, Inc. <https://fsf.org/> Everyone is permitted to
 *  copy and distribute verbatim copies of this license document, but changing it is not allowed.
 *  Yusuf Arfan Ismail
 *  The GNU General Public License is a free, copyleft license for software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.moderation;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandConnector;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandVisibility;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.YusufSlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

/**
 * The user will be able to type {@code /warn user reason} which will store the user who has been warned, the reason and
 * the amount of times they have been warned.
 */
public class WarnCommand extends CommandConnector {
    private static final String USER_OPTION = "user";
    private static final String REASON_OPTION = "reason";
    /**
     * Were the command is registered.
     */
    protected WarnCommand() {
        super("warn", "Warn a user", CommandVisibility.SERVER);

        getCommandData()
                .addOption(OptionType.USER, USER_OPTION, "The user to warn",
                        true)
                .addOption(OptionType.STRING, REASON_OPTION, "The reason for the warn",
                        true);
    }

    @Override
    public void onSlashCommand(YusufSlashCommandEvent yusufSlashCommandEvent) {

    }
}
