/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Arfan Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.yusuf.bot.slash_commands.simple_commands;

import github.io.yusuf.core.bot.Command;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.InteractionBase;
import org.javacord.api.interaction.SlashCommandBuilder;

import java.awt.*;

public class BotInfo implements Command {
    @Override
    public void onSlashCommand(SlashCommandCreateEvent slashCommandCreateEvent) {
        InteractionBase interactionBase = slashCommandCreateEvent.getInteraction();

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("Yusuf's Moderation Bot Info")
                .setDescription("""
                        Running on Yusuf's Moderation bot V2-1.0.0
                        
                        Developer:
                        • [RealYusufIsmail](https://github.com/RealYusufIsmail)
                        
                        Runtime-Environment:
                        Java 16
                        
                        Library's:
                        [JavaCord](https://github.com/Javacord/Javacord)
                        [YusufIsmail's Discord core](https://github.com/Yusuf-s-Discord-bot/YusufIsmails-Discord-core)
                        
                        Bot's Repo and Org:
                        [Yusuf's Moderation bot](https://github.com/Yusuf-s-Discord-bot/Yusuf-s-Moderation-Bot)
                        [Yusuf's Discord Bot org](https://github.com/Yusuf-s-Discord-bot)
                        """
                )
                .setAuthor("Made by " + slashCommandCreateEvent.getSlashCommandInteraction().getApi().getYourself().getName(), null, interactionBase.getUser().getAvatar())
                .setFooter("Yusuf's Moderation Bot by Yusuf-s Discord bot org")
                .setColor(Color.CYAN);
        interactionBase.createImmediateResponder().addEmbed(builder).respond();
    }

    @Override
    public String getName() {
        return "botinfo";
    }

    @Override
    public String getDescription() {
        return "Shows the bot info";
    }

    @Override
    public SlashCommandBuilder getCommandData() {
        return new SlashCommandBuilder().setName(getName()).setDescription(getDescription());
    }
}
