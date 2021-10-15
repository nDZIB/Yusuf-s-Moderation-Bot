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

import net.yusuf.bot.slash_commands.moderation.*;
import net.yusuf.bot.slash_commands.music.*;
import net.yusuf.bot.slash_commands.simple_commands.*;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.UserStatus;
import org.javacord.api.interaction.SlashCommandBuilder;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Bot {
    @SuppressWarnings("all")
    public static void main(String[] args) throws LoginException, Exception {
        DiscordApi api = new DiscordApiBuilder()
                .setToken(Config.get("TOKEN"))
                .addSlashCommandCreateListener(new SlashCommandHandler())
                .login().join();

        api.updateActivity("Only Supporting slash commands now.");
        api.updateStatus(UserStatus.ONLINE);

        api.addServerMemberJoinListener(event -> {
            Optional<TextChannel> channel = api.getTextChannelById(Config.get("872494635757473932"));
            channel.ifPresent(TextChannel -> TextChannel.sendMessage("Welcome to the server + " +
                    event.getUser().getMentionTag() + "We hope you have a great time"));
        });
    }
}
