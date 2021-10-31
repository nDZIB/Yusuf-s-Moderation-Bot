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

package net.yusuf.bot.slash_commands;

import github.io.yusuf.core.bot.slash_command.CoreSlashCommandHandler;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.yusuf.bot.slash_commands.github_commands.*;
import net.yusuf.bot.slash_commands.moderation.*;
import net.yusuf.bot.slash_commands.music.*;
import net.yusuf.bot.slash_commands.normal_commands.*;
import net.yusuf.bot.slash_commands.role.*;
import net.yusuf.bot.slash_commands.server_commands.*;
import net.yusuf.bot.slash_commands.tutorials_commands.*;


import java.util.HashMap;
import java.util.Map;

public class CommandHandler extends CoreSlashCommandHandler {

    public CommandHandler() {
        addCommand(new CommandsList());
        addCommand(new ServerCommands());
        addCommand(new HelloWorld());
        addCommand(new PingCommand());
        //addCommand(new PasteCommand());
        addCommand(new HelpCommand());
        addCommand(new Support());
        addCommand(new MemeCommand());
        addCommand(new JokeCommand());
        addCommand(new DeleteCommand());
        //addCommand(new UptimeCommand());

        addCommand(new BanCommand());
        addCommand(new UnBanCommand());
        //addCommand(new WarnCommand());
        //addCommand(new MuteCommand());
        addCommand(new KickCommand());

        addCommand(new RealYusufIsmailGithub());
        addCommand(new DungeonMakersGithub());
        addCommand(new TurtyWurtyGithub());
        addCommand(new SilentChaos512Github());
        addCommand(new ForgeGithub());

        addCommand(new TeamCitcraftServer());
        addCommand(new TurtyWurtyServer());
        addCommand(new ProgrammingServer());
        addCommand(new Cy4Server());

        addCommand(new Cy4shotTutorials());
        addCommand(new RealyusufismailTutorials());
        addCommand(new McjtyTutorials());
        addCommand(new Silentchaos512Tutorials());
        addCommand(new TurtyWurtyTutorials());
        addCommand(new TutorialsCommandsList());


        addCommand(new JoinCommand());
        addCommand(new LeaveCommand());
        addCommand(new PlayCommand());
        addCommand(new PauseCommand());
        addCommand(new MusicCommandsList());
        addCommand(new SkipCommand());
        addCommand(new StopCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new QueueCommand());
        addCommand(new ResumeCommand());
        addCommand(new RepeatCommand());

        addCommand(new AddRoleCommand());
        addCommand(new RemoveRoleCommand());
    }
}
