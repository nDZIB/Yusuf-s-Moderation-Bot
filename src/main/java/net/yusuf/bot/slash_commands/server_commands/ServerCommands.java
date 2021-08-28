package net.yusuf.bot.slash_commands.server_commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;

public class ServerCommands extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        User sender = event.getUser();
        builder.setAuthor("Made by " + event.getMember().getEffectiveName(), null, sender.getEffectiveAvatarUrl());
        builder.setTitle("Commands");
        builder.setDescription("1. cy4_server\n2. programming_sever\n3. team_citcraft_sever\n4. turty_wurty_sever");
        builder.setColor(0x34d8eb);
        event.replyEmbeds(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "server_commands";
    }

    @Override
    public String getDescription() {
        return "shows the list of server commands`";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription());
    }
}
