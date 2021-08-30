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

package net.yusuf.bot.prefix_commands.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.yusuf.bot.prefix_commands.CommandContext;
import net.yusuf.bot.prefix_commands.ICommand;
import org.menudocs.paste.PasteClient;
import org.menudocs.paste.PasteClientBuilder;

import java.util.List;

public class PasteCommand implements ICommand {
    private final PasteClient client = new PasteClientBuilder()
            .setUserAgent("Yusuf Arfan Ismail Moderation Bot")
            .setDefaultExpiry("2880m")
            .build();

    @Override
    public void handle(CommandContext ctx) {
            final List<String> args = ctx.getArgs();
            final TextChannel channel = ctx.getChannel();

            if (args.size() < 2) {
                channel.sendMessage("Missing arguments").queue();
                return;
            }

            final String language = args.get(0);
            final String contentRaw = ctx.getMessage().getContentRaw();
            final int index = contentRaw.indexOf(language) + language.length();
            final String body = contentRaw.substring(index).trim();

            client.createPaste(language, body).async(
                    (id) -> client.getPaste(id).async((paste) -> {
                        EmbedBuilder builder = new EmbedBuilder()
                                .setTitle("Paste " + id, paste.getPasteUrl())
                                .setDescription("```")
                                .appendDescription(paste.getLanguage().getId())
                                .appendDescription("\n")
                                .appendDescription(paste.getBody())
                                .appendDescription("```");

                        channel.sendMessage(builder.build()).queue();
                    })
            );
    }

    @Override
    public String getName() {
        return "paste";
    }

    @Override
    public String getHelp() {
        return "Creates a paste on the bots paste\n" +
                "Usage: `&paste [language] [text]`";
    }
}
