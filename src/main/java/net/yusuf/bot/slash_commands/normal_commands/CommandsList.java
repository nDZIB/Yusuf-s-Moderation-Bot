package net.yusuf.bot.slash_commands.normal_commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;

public class CommandsList extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        User sender = event.getUser();
            builder.setAuthor("Made by " + event.getMember().getEffectiveName(), null, sender.getEffectiveAvatarUrl());
            builder.setTitle("Commands");
            builder.setDescription("1. help\n2. ping\n3. commands\n4. meme\n5. ping\n6. support\n7. server_commands\n8. music_commands\n8. tutorials_commands");
            builder.setColor(0x34d8eb);
            event.replyEmbeds(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "commands";
    }

    @Override
    public String getDescription() {
        return "Shows the command list";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription());
    }
}
