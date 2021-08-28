package net.yusuf.bot.slash_commands.music;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;

public class MusicCommandsList extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        User sender = event.getUser();
            builder.setAuthor("Made by " + event.getMember().getEffectiveName(), null, sender.getEffectiveAvatarUrl());
            builder.setTitle("Commands");
            builder.setDescription("1. join\n2. leave\n3. play\n4. pause");
            builder.setColor(0x34d8eb);
            event.replyEmbeds(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "music_commands";
    }

    @Override
    public String getDescription() {
        return "Shows the music command list";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription());
    }
}
