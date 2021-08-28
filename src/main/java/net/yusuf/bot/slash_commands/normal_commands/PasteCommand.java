package net.yusuf.bot.slash_commands.normal_commands;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;
import org.menudocs.paste.PasteClient;
import org.menudocs.paste.PasteClientBuilder;

public class PasteCommand extends Command {
    private final PasteClient client = new PasteClientBuilder()
            .setUserAgent("Yusuf Arfan Ismail Moderation Bot")
            .setDefaultExpiry("2880m")
            .build();

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        final TextChannel channel = event.getTextChannel();


    }

    @Override
    public String getName() {
        return "paste";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public CommandData getCommandData() {
            return new CommandData(getName(), getDescription());
    }
}
