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
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.awt.*;

public class BotInfoCommand implements Command {
    @Override
    public void onSlashCommand(SlashCommandEvent slashCommandEvent) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle(slashCommandEvent.getGuild().getSelfMember().getEffectiveName() + " Info")
            .setDescription(
                    """
                            Running on Yusuf's Moderation bot V2-1.0.2
                            Developer:
                            • [RealYusufIsmail](https://github.com/RealYusufIsmail)
                            Runtime-Environment:
                            Gradle 7.3
                            Java 17
                            Library's:
                            [JDA](https://github.com/Javacord/Javacord)
                            [YusufIsmail's Discord core](https://github.com/YusufsDiscordbot/YusufIsmails-Discord-core)
                            Bots repo and org:
                            [Yusuf's moderation bot](https://github.com/YusufsDiscordbot/Yusuf-s-Moderation-Bot)
                            [Yusuf's Discord bot](https://github.com/YusufsDiscordbot)
                            """)
            .setFooter("Made by Yusuf's Discord bot")
            .setColor(Color.CYAN);

        slashCommandEvent.replyEmbeds(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "botinfo";
    }

    @Override
    public String getDescription() {
        return "Provides the user with info about the bot.";
    }

    @Override
    public CommandVisibility getVisibility() {
        return CommandVisibility.UNIVERSAL;
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription());
    }
}
