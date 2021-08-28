package net.yusuf.bot.slash_commands.server_commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;

public class TeamCitcraftServer extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
            event.reply("https://discord.gg/d5cGhKQ").queue(); // reply immediately
    }

    @Override
    public String getName() {
        return "team_citcraft_sever";
    }

    @Override
    public String getDescription() {
        return "Shows Discord server link";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription());
    }
}
