/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Arfan Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
                            Yusuf's Discord bot](https://github.com/YusufsDiscordbot)
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
