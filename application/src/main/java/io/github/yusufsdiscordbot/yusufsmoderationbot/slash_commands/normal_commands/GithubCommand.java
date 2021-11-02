/*
 * GNU GENERAL PUBLIC LICENSE
 *                        Version 3, 29 June 2007
 *
 *  Copyright (C) 2021 Free Software Foundation, Inc. <https://fsf.org/>
 *  Everyone is permitted to copy and distribute verbatim copies
 *  of this license document, but changing it is not allowed.
 *
 *                            Yusuf Arfan Ismail
 *
 *   The GNU General Public License is a free, copyleft license for
 * software and other kinds of works.
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
    private static final String COMMAND_OPTION_NAME = "github_user";

    @Override
    public void onSlashCommand(SlashCommandEvent slashCommandEvent) {
        User sender = slashCommandEvent.getUser();
        EmbedBuilder builder = new EmbedBuilder();

        final String github = slashCommandEvent.getOption(COMMAND_OPTION_NAME).getAsString();

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
                new OptionData(STRING, COMMAND_OPTION_NAME, "The Github link for the user you want")
                    .setRequired(true)
                    .addChoices(githubUsers));
    }

    public static final List<net.dv8tion.jda.api.interactions.commands.Command.Choice> githubUsers =
            List.of(new net.dv8tion.jda.api.interactions.commands.Command.Choice("RealYusufIsmail",
                    "realyusufismail"),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice("SilentChaos512",
                            "silentchaos512"),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice("TogetherJava",
                            "togetherjava"),
                    new net.dv8tion.jda.api.interactions.commands.Command.Choice("TurtyWurty",
                            "turtywurty"));

}
