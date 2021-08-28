package net.yusuf.bot.slash_commands.normal_commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;

public class Support extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
            event.reply(event.getOption("This is the discord support server").getAsString()).queue();
            event.reply(event.getOption("https://discord.gg/hpY6s6mh3N").getAsString()).queue(); // reply immediately
    }

    public String getName() {
        return "support";
    }

    public String getDescription() {
        return "Provides Support";
    }

    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription());
    }
}
