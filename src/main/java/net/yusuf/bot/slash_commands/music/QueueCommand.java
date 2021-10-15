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

package net.yusuf.bot.slash_commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import github.io.yusuf.core.bot.Command;
import github.io.yusuf.core.lavaplayer.ApiMusicManager;
import github.io.yusuf.core.lavaplayer.PlayerManager;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.InteractionBase;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


public class QueueCommand implements Command {
    @Override
    public void onSlashCommand(SlashCommandCreateEvent slashCommandCreateEvent) {
        // Interaction base
        SlashCommandInteraction interaction = slashCommandCreateEvent.getSlashCommandInteraction();
        InteractionBase interactionBase = slashCommandCreateEvent.getInteraction();

        // The server
        Server server = interaction.getServer().get();
        //APi Music Manager
        final ApiMusicManager musicManager = PlayerManager.getInstance().getMusicManager(interaction.getServer().get(), interaction.getApi());

        final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

        //Queue
        if(queue.isEmpty()) {
            EmbedBuilder builder = new EmbedBuilder()
                    .setTitle("Queue")
                    .setDescription("The queue is currently empty")
                    .setColor(Color.getColor("blue", 0x34d8eb));
        }
        EmbedBuilder builder = new EmbedBuilder();
        final int trackCount = Math.min(queue.size(), 20);
        final List<AudioTrack> trackList = new ArrayList<>(queue);
        builder.setTitle("**Current Queue:**\n");

        for (int i = 0; i <  trackCount; i++) {
            final AudioTrack track = trackList.get(i);
            final AudioTrackInfo info = track.getInfo();

            builder.setDescription("#")
                    .setDescription(String.valueOf(i + 1))
                    .setDescription(" `")
                    .setDescription(String.valueOf(info.title))
                    .setDescription(" by ")
                    .setDescription(info.author)
                    .setDescription("` [`")
                    .setDescription(formatTime(track.getDuration()))
                    .setDescription("`]\n");
        }

        if (trackList.size() > trackCount) {
            builder.setDescription("And `")
                    .setDescription(String.valueOf(trackList.size() - trackCount))
                    .setDescription("` more...");
        }

        builder.setColor(Color.getColor("blue", 0x34d8eb));
        interactionBase.createImmediateResponder().addEmbeds(builder).respond();
    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public String getDescription() {
        return "Proved the queue";
    }

    @Override
    public SlashCommandBuilder getCommandData() {
        return new SlashCommandBuilder().setName(getName()).setDescription(getDescription());
    }
}
