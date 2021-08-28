package net.yusuf.bot.slash_commands.tutorials_commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;

public class TutorialsCommandsList extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        User sender = event.getUser();
            builder.setAuthor("Made by " + event.getMember().getEffectiveName(), null, sender.getEffectiveAvatarUrl());
            builder.setTitle("Commands");
            builder.setDescription("1. cy4_shot_tutorials\n2. realyusufismail_tutorials\n3. silentchaos512_tutorials\n4. turty_wurty_tutorials\n5. mcjty_tutorials");
            builder.setColor(0x34d8eb);
            event.replyEmbeds(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "tutorials_commands";
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
