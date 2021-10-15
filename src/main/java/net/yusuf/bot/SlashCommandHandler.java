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

package net.yusuf.bot;

import com.sedmelluq.discord.lavaplayer.remote.AbandonedTrackManager;
import github.io.yusuf.core.bot.CoreSlashCommandHandler;
import net.yusuf.bot.slash_commands.moderation.*;
import net.yusuf.bot.slash_commands.simple_commands.*;
import net.yusuf.bot.slash_commands.music.*;
import org.javacord.api.interaction.SlashCommandBuilder;

import java.util.ArrayList;
import java.util.List;


public class SlashCommandHandler extends CoreSlashCommandHandler {

    public SlashCommandHandler() {
        //slash commands name
        //Moderation commands
        addCommand(new KickCommand());
        addCommand(new BanCommand());
        addCommand(new UnBanCommand());
        addCommand(new DeleteMessagesCommand());
        //music command
        addCommand(new JoinCommand());
        addCommand(new PlayCommand());
        addCommand(new PauseCommand());
        addCommand(new LeaveCommand());
        addCommand(new ResumeCommand());
        addCommand(new QueueCommand());
        addCommand(new NowPlayingCommand());
        //Normal commands
        addCommand(new GithubUsernameCommand());
        addCommand(new DiscordServersCommand());
        addCommand(new TutorialsCommand());
        addCommand(new HelloWorld());
        addCommand(new IssueCommand());
        addCommand(new RuleCommand());
        addCommand(new InviteCommand());
        addCommand(new CreateEmbedCommand());
        addCommand(new BotInfo());
        
        //register slash commands
        //Moderation commands
        dataCommands.add(new KickCommand().getCommandData());
        dataCommands.add(new BanCommand().getCommandData());
        dataCommands.add(new UnBanCommand().getCommandData());
        dataCommands.add(new DeleteMessagesCommand().getCommandData());
        //music commands
        dataCommands.add(new PlayCommand().getCommandData());
        dataCommands.add(new JoinCommand().getCommandData());
        dataCommands.add(new PauseCommand().getCommandData());
        dataCommands.add(new ResumeCommand().getCommandData());
        dataCommands.add(new LeaveCommand().getCommandData());
        dataCommands.add(new QueueCommand().getCommandData());
        dataCommands.add(new NowPlayingCommand().getCommandData());
        //Normal commands
        dataCommands.add(new IssueCommand().getCommandData());
        dataCommands.add(new HelloWorld().getCommandData());
        dataCommands.add(new RuleCommand().getCommandData());
        dataCommands.add(new InviteCommand().getCommandData());
        dataCommands.add(new CreateEmbedCommand().getCommandData());
        dataCommands.add(new BotInfo().getCommandData());
        dataCommands.add(new DiscordServersCommand().getCommandData());
        dataCommands.add(new TutorialsCommand().getCommandData());
        dataCommands.add(new GithubUsernameCommand().getCommandData());
    }
}
