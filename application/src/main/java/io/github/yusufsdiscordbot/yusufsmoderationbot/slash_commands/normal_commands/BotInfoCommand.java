/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007 Copyright (C) 2021 Free Software Foundation,
 * Inc. <https://fsf.org/> Everyone is permitted to copy and distribute verbatim copies of this
 * license document, but changing it is not allowed. Yusuf Arfan Ismail The GNU General Public
 * License is a free, copyleft license for software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.normal_commands;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandConnector;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandVisibility;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.YusufSlashCommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

/**
 * When the command is run the user will receive the bots' info such as the bots current version or
 * what Java version it uses.
 *
 * @author Yusuf Arfan Ismail
 */
public class BotInfoCommand extends CommandConnector {
    public BotInfoCommand() {
        super("botinfo", "Provides the user with info about the bot.", CommandVisibility.SERVER);
    }

    @Override
    public void onSlashCommand(YusufSlashCommandEvent yusufSlashCommandEvent) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(yusufSlashCommandEvent.getGuild().getBot().getName() + " Info")
            .setDescription(
                    """
                            Running on Yusuf's Moderation bot V3-1.0.3
                            Developer:
                            • [RealYusufIsmail](https://github.com/RealYusufIsmail)
                            Runtime-Environment:
                            • Gradle 7.2
                            • Java 16
                            Library's:
                            • [JDA](https://github.com/DV8FromTheWorld/JDA)
                            • [YusufIsmail's Discord core](https://github.com/YusufsDiscordbot/YusufIsmails-Discord-core)
                            Bots repo and org:
                            • [Yusuf's moderation bot](https://github.com/YusufsDiscordbot/Yusuf-s-Moderation-Bot)
                            • [Yusuf's Discord bot](https://github.com/YusufsDiscordbot)
                            """)
            .setFooter("Made by Yusuf's Discord bot")
            .setColor(Color.CYAN);

        yusufSlashCommandEvent.replyEmbed(builder.build());

    }
}
