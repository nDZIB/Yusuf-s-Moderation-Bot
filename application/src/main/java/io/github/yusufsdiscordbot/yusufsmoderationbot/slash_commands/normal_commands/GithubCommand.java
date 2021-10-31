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

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.normal_commands;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.Command;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandVisibility;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class GithubCommand implements Command {
    @Override
    public void onSlashCommand(SlashCommandEvent slashCommandEvent) {
        User sender = slashCommandEvent.getUser();
        EmbedBuilder builder = new EmbedBuilder();

        final String github = Objects
            .requireNonNull(slashCommandEvent.getOption("Github link"), "Names is not provided")
            .getAsString();
        HashMap<String, String> git = new HashMap<String, String>();

        git.put("dungeonmakers", "https://github.com/Dungeon-Maker");
        git.put("forge", "https://github.com/MinecraftForge");
        git.put("realyusufismail", "https://github.com/RealYusufIsmail");
        git.put("silentchaos512", "https://github.com/SilentChaos512");
        git.put("togetherjava", "https://github.com/Together-Java");
        git.put("turtywurty", "https://github.com/DaRealTurtyWurty");

        builder.setAuthor("Made by " + slashCommandEvent.getMember().getEffectiveName(), null,
                sender.getEffectiveAvatarUrl());
        builder.setTitle("Github org/users");
        builder.setDescription("Github org/repo" + git);
        builder.setColor(0x34d8eb);

        if (git.containsKey(github)) {
            builder.setDescription(git.get(github));
        } else {
            slashCommandEvent.reply("Could not find the Github org/user").queue();
            return;
        }

        slashCommandEvent.replyEmbeds(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "github";
    }

    @Override
    public String getDescription() {
        return "Use this account to get Github users/org links";
    }

    @Override
    public CommandVisibility getVisibility() {
        return CommandVisibility.SERVER;
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription()).addOptions(
                new OptionData(STRING, "github_user", "The Github link for the user you want")
                    .setRequired(true)
                    .addChoices(githubUsers));
    }

    public static final List<net.dv8tion.jda.api.interactions.commands.Command.Choice> githubUsers =
            List.of(new net.dv8tion.jda.api.interactions.commands.Command.Choice("Dungeon_makers",
                    "dungeonmakers"),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice("SilentChaos512",
                            "forge"),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice("Forge", "realyusufismail"),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice("Together-Java",
                            "togetherjava"),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice("Turtywurty",
                            "turtywurty"));
}
