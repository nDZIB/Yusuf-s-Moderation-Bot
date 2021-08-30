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

package net.yusuf.bot.prefix_commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.yusuf.bot.prefix_commands.CommandContext;
import net.yusuf.bot.prefix_commands.ICommand;
import net.yusuf.bot.prefix_commands.admin.SetPrefixCommand;
import net.yusuf.bot.prefix_commands.admin.TransferOwnerShipCommand;
import net.yusuf.bot.prefix_commands.commands.*;
import net.yusuf.bot.prefix_commands.github_commands.*;
import net.yusuf.bot.prefix_commands.moderation.*;
import net.yusuf.bot.prefix_commands.music.*;
import net.yusuf.bot.prefix_commands.role.*;
import net.yusuf.bot.prefix_commands.server_commands.*;
import net.yusuf.bot.prefix_commands.tutorials_commands.*;

import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new HelloWorld());
        addCommand(new PingCommand());
        addCommand(new CommandList(this));
        addCommand(new PasteCommand());
        addCommand(new HelpCommand());
        addCommand(new Support());
        addCommand(new MemeCommand());
        addCommand(new WebhookCommand());
        addCommand(new JokeCommand());
        addCommand(new SetPrefixCommand());
        addCommand(new UptimeCommand());
        addCommand(new CreateTemplateCommand());
        addCommand(new VanityUrlCommand());
        addCommand(new VerificationLevelCommand());

        addCommand(new BanCommand());
        addCommand(new UnBanCommand());
        // addCommand(new WarnCommand());
        // addCommand(new MuteCommand());
        addCommand(new KickCommand());

        addCommand(new RealYusufIsmailGithub());
        addCommand(new DungeonMakersGithub());
        addCommand(new TurtyWurtyGithub());
        addCommand(new SilentChaos512Github());
        addCommand(new TeamCitcraftGithub());
        addCommand(new ForgeGithub());

        addCommand(new TeamCitcraftServer());
        addCommand(new TurtyWurtyServer());
        addCommand(new ForgeServer());
        addCommand(new DungeonMakersSever());

        addCommand(new Cy4shotTutorials());
        addCommand(new RealyusufismailTutorials());
        addCommand(new McjtyTutorials());
        addCommand(new Silentchaos512Tutorials());
        addCommand(new TurtyWurtyTutorials());

        addCommand(new JoinCommand());
        addCommand(new LeaveCommand());
        addCommand(new PlayCommand());
        addCommand(new StopCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new SkipCommand());
        addCommand(new QueueCommand());
        addCommand(new RepeatCommand());
        addCommand(new PauseCommand());
        addCommand(new ResumeCommand());

        addCommand(new AddRoleCommand());
        addCommand(new RemoveRoleCommand());

        addCommand(new TransferOwnerShipCommand());
    }

    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present");
        }

        commands.add(cmd);
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }

    public void handle(GuildMessageReceivedEvent event, String prefix) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(prefix), "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);
        }
    }
}
