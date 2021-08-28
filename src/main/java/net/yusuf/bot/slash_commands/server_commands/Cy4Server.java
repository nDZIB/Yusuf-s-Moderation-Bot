package net.yusuf.bot.slash_commands.server_commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;

public class Cy4Server extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
            event.reply("https://discord.gg/j5tBQx7uny").queue(); // reply immediately
    }

    @Override
    public String getName() {
        return "cy4_server";
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
