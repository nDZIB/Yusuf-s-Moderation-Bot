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

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.yusuf.bot.slash_commands.CommandHandler;
import net.yusuf.bot.slash_commands.github_commands.*;
import net.yusuf.bot.slash_commands.moderation.*;
import net.yusuf.bot.slash_commands.music.*;
import net.yusuf.bot.slash_commands.normal_commands.*;
import net.yusuf.bot.slash_commands.role.*;
import net.yusuf.bot.slash_commands.server_commands.*;
import net.yusuf.bot.slash_commands.tutorials_commands.*;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class Bot {
    @SuppressWarnings("all")
    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault(Config.get("TOKEN"),
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_VOICE_STATES
                )
                .disableCache(EnumSet.of(
                        CacheFlag.CLIENT_STATUS,
                        CacheFlag.ACTIVITY,
                        CacheFlag.EMOTE
                ))
                .enableCache(CacheFlag.VOICE_STATE)
                .addEventListeners(new CommandHandler(), new Listener())
                .setActivity(Activity.watching("&help"))
                .setStatus(OnlineStatus.ONLINE)
                .build();

        CommandListUpdateAction commands = jda.updateCommands();

        //normal command
        commands.addCommands(new CommandsList().getCommandData());
        commands.addCommands(new PingCommand().getCommandData());
        commands.addCommands(new HelpCommand().getCommandData());
        commands.addCommands(new Support().getCommandData());
        commands.addCommands(new MemeCommand().getCommandData());
        commands.addCommands(new JokeCommand().getCommandData());
        commands.addCommands(new DeleteCommand().getCommandData());
        commands.addCommands(new HelloWorld().getCommandData());
        //commands.addCommands(new UptimeCommand().getCommandData());

        //GitHub commands
        commands.addCommands(new RealYusufIsmailGithub().getCommandData());
        commands.addCommands(new DungeonMakersGithub().getCommandData());
        commands.addCommands(new TurtyWurtyGithub().getCommandData());
        commands.addCommands(new SilentChaos512Github().getCommandData());
        commands.addCommands(new ForgeGithub().getCommandData());

        //server commands
        commands.addCommands(new TeamCitcraftServer().getCommandData());
        commands.addCommands(new TurtyWurtyServer().getCommandData());
        commands.addCommands(new ProgrammingServer().getCommandData());
        commands.addCommands(new Cy4Server().getCommandData());
        commands.addCommands(new ServerCommands().getCommandData());

        //tutorials commands
        commands.addCommands(new Cy4shotTutorials().getCommandData());
        commands.addCommands(new RealyusufismailTutorials().getCommandData());
        commands.addCommands(new McjtyTutorials().getCommandData());
        commands.addCommands(new Silentchaos512Tutorials().getCommandData());
        commands.addCommands(new TurtyWurtyTutorials().getCommandData());
        commands.addCommands(new TutorialsCommandsList().getCommandData());

        //music commands
        commands.addCommands(new JoinCommand().getCommandData());
        commands.addCommands(new LeaveCommand().getCommandData());
        commands.addCommands(new PlayCommand().getCommandData());
        commands.addCommands(new PauseCommand().getCommandData());
        commands.addCommands(new SkipCommand().getCommandData());
        commands.addCommands(new NowPlayingCommand().getCommandData());
        commands.addCommands(new StopCommand().getCommandData());
        commands.addCommands(new QueueCommand().getCommandData());
        commands.addCommands(new RepeatCommand().getCommandData());
        commands.addCommands(new ResumeCommand().getCommandData());
        commands.addCommands(new MusicCommandsList().getCommandData());

        //moderation
        commands.addCommands(new BanCommand().getCommandData());
        commands.addCommands(new UnBanCommand().getCommandData());
        commands.addCommands(new KickCommand().getCommandData());
        commands.addCommands(new AddRoleCommand().getCommandData());
        commands.addCommands(new RemoveRoleCommand().getCommandData());

        commands.queue();

    }

}
