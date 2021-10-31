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

package io.github.yusufsdiscordbot.yusufsmoderationbot;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CoreSlashCommandHandler;
import io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.moderation.BanCommand;
import io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.moderation.KickCommand;
import io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.moderation.PurgeCommand;
import io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.moderation.UnBanCommand;
import io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.normal_commands.*;
import io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.role.AddRoleCommand;
import io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.role.RemoveRoleCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

public class CommandHandler extends CoreSlashCommandHandler {

    public CommandHandler(JDA jda, Guild guild) {
        super(jda, guild);
        addCommand(new HelloWorld());
        addCommand(new PingCommand());
        // addCommand(new PasteCommand());
        addCommand(new HelpCommand());
        addCommand(new Support());
        addCommand(new MemeCommand());
        addCommand(new JokeCommand());
        addCommand(new GithubCommand());
        addCommand(new DiscordCommand());
        addCommand(new BotInfoCommand());

        addCommand(new BanCommand());
        addCommand(new UnBanCommand());
        // addCommand(new WarnCommand());
        // addCommand(new MuteCommand());
        addCommand(new KickCommand());
        addCommand(new PurgeCommand());

        addCommand(new AddRoleCommand());
        addCommand(new RemoveRoleCommand());
        globalCommandsData.queue();
        guildCommandsData.queue();
    }

}