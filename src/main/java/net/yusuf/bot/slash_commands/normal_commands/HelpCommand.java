package net.yusuf.bot.slash_commands.normal_commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;


public class HelpCommand extends Command {

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        User sender = event.getUser();
            builder.setAuthor("Made by " + event.getMember().getEffectiveName(), null, sender.getEffectiveAvatarUrl());
            builder.setTitle("Help");
            builder.setDescription("Commands can be found by doing /commands for support type /support");
            builder.setColor(0x34d8eb);
            event.replyEmbeds(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Provides help";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription());
    }
}
