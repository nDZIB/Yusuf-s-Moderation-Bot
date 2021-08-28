package net.yusuf.bot.slash_commands.tutorials_commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;

public class Cy4shotTutorials extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
            event.reply("https://www.youtube.com/channel/UCJIDXtGpf4wv1ybDzdTA_vQ").queue(); // reply immediately
    }

    @Override
    public String getName() {
        return "cy4_shot_tutorials";
    }

    @Override
    public String getDescription() {
        return "Shows Youtube channel";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription());
    }
}
