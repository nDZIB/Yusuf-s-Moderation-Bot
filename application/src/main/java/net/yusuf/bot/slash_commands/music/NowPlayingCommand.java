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

package net.yusuf.bot.slash_commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import github.io.yusuf.core.bot.Command;
import github.io.yusuf.core.lavaplayer.ApiMusicManager;
import github.io.yusuf.core.lavaplayer.PlayerManager;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.InteractionBase;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandInteraction;

public class NowPlayingCommand implements Command {
    @Override
    public void onSlashCommand(SlashCommandCreateEvent slashCommandCreateEvent) {
        SlashCommandInteraction interaction = slashCommandCreateEvent.getSlashCommandInteraction();
        InteractionBase interactionBase = slashCommandCreateEvent.getInteraction();

        // Api Music manger
        final ApiMusicManager musicManager = PlayerManager.getInstance()
            .getMusicManager(interaction.getServer().get(), interaction.getApi());
        final AudioPlayer audioPlayer = musicManager.audioPlayer;
        final AudioTrack track = audioPlayer.getPlayingTrack();

        if (track == null) {
            interactionBase.createImmediateResponder()
                .setContent("There is no track playing currently")
                .respond();
            return;
        }

        final AudioTrackInfo info = track.getInfo();
        interaction.getChannel()
            .get()
            .sendMessage(String.format("Now playing `%s` by `%s` (Link: <%s>)", info.title,
                    info.author, info.uri));
    }

    @Override
    public String getName() {
        return "now_playing";
    }

    @Override
    public String getDescription() {
        return "Shows what you are playing";
    }

    @Override
    public SlashCommandBuilder getCommandData() {
        return new SlashCommandBuilder().setName(getName()).setDescription(getDescription());
    }
}
