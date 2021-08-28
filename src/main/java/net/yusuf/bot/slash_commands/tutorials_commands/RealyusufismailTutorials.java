package net.yusuf.bot.slash_commands.tutorials_commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;

public class RealyusufismailTutorials extends Command {

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
            event.reply("https://youtube.com/UC1RUkzjpWtp4w3OoMKh7pGg").queue(); // reply immediately
    }

    @Override
    public String getName() {
        return "realyusufismail_tutorials";
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
