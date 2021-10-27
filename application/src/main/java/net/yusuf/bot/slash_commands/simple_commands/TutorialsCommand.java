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

package net.yusuf.bot.slash_commands.simple_commands;

import github.io.yusuf.core.bot.Command;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.*;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;
import org.javacord.api.util.logging.ExceptionLogger;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class TutorialsCommand implements Command {
    @Override
    public void onSlashCommand(SlashCommandCreateEvent event) {
        InteractionBase interactionBase = event.getInteraction();
        EmbedBuilder builder = new EmbedBuilder();
        event.getSlashCommandInteraction().getFirstOptionStringValue().map(youtubeChannel -> {
            var responder = interactionBase.createImmediateResponder();
            switch (youtubeChannel) {
                case "cy4shot" -> responder
                    .setContent("https://www.youtube.com/channel/UCJIDXtGpf4wv1ybDzdTA_vQ");
                case "mcjty" -> responder
                    .setContent("https://www.youtube.com/channel/UCYMg1JQw3syJBgPeW6m68lA");
                case "silentchaos512" -> responder
                    .setContent("https://www.youtube.com/user/SilentChaos512");
                case "turtywurty" -> responder
                    .setContent("https://www.youtube.com/channel/UCicAXLV4w2X6bn2EuM4To4");
                case "realyusufismail" -> responder
                    .setContent("https://youtube.com/UC1RUkzjpWtp4w3OoMKh7pGg");
                case "list" -> {
                    responder.addEmbed(builder.setDescription(
                            "\n1. cy4shot\n2. mcjty\n3. silentchaos512\n4. turtywurty\n5. realyusufismail"));
                    builder.setTitle("list");
                    builder.setAuthor("Made by "
                            + event.getSlashCommandInteraction().getApi().getYourself().getName(),
                            null, interactionBase.getUser().getAvatar());
                    builder.setColor(Color.CYAN);
                }
                default -> responder.setContent("More youtube channels to be added.");
            }
            return responder;
        })
            .map(InteractionImmediateResponseBuilder::respond)
            .ifPresent(future -> future.exceptionally(ExceptionLogger.get()));
    }

    @Override
    public String getName() {
        return "tutorials";
    }

    @Override
    public String getDescription() {
        return "Use this command to get a youtube channels link. For the list type list in the name section";
    }

    @Override
    public SlashCommandBuilder getCommandData() {
        return new SlashCommandBuilder().setName(getName())
            .setDescription(getDescription())
            .addOption(SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING, "name",
                    "The name of the youtube channel.", true, youtubeChannelNames));
    }

    private final List<SlashCommandOptionChoice> youtubeChannelNames =
            Arrays.asList(SlashCommandOptionChoice.create("cy4shot", "cy4shot"),
                    SlashCommandOptionChoice.create("mcjty", "mcjty"),
                    SlashCommandOptionChoice.create("silentchaos512", "silentchaos512"),
                    SlashCommandOptionChoice.create("realyusufismail", "silentchaos512"),
                    SlashCommandOptionChoice.create("turtywurty", "turtywurty"));
}

