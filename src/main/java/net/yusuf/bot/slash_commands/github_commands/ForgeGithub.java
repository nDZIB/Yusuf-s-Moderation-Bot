package net.yusuf.bot.slash_commands.github_commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;

public class ForgeGithub extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
            event.reply("https://github.com/MinecraftForge").queue(); // reply immediately
    }

    @Override
    public String getName() {
        return "forge_github";
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
