package net.yusuf.bot.slash_commands.server_commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;

public class TurtyWurtyServer extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
            event.reply("https://discord.gg/BxD5JukyQs").queue(); // reply immediately
    }

    @Override
    public String getName() {
        return "turty_wurty_sever";
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
