package net.yusuf.bot.slash_commands;

import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public abstract class Command {
    public abstract void onSlashCommand(SlashCommandEvent event);


    public abstract String getName();

    public abstract String getDescription();

    public abstract CommandData getCommandData();
}
