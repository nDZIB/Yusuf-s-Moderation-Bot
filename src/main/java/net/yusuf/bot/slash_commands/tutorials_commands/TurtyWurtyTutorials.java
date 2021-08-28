package net.yusuf.bot.slash_commands.tutorials_commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;

public class TurtyWurtyTutorials extends Command {

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
            event.reply("https://www.youtube.com/channel/UCicAXLV4w2X6bn2EuM4To4w").queue(); // reply immediately
    }

    @Override
    public String getName() {
        return "turty_wurty_tutorials";
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
