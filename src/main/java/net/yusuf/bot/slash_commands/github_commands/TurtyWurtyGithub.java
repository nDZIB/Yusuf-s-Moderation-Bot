package net.yusuf.bot.slash_commands.github_commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;

public class TurtyWurtyGithub extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
            event.reply("https://github.com/DaRealTurtyWurty").queue(); // reply immediately
    }

    @Override
    public String getName() {
        return "turtywurty_github";
    }

    @Override
    public String getDescription() {
        return "Shows Github link";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription());
    }
}
