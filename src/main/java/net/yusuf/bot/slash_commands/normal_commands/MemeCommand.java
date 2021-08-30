package net.yusuf.bot.slash_commands.normal_commands;

import com.fasterxml.jackson.databind.JsonNode;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;

public class MemeCommand extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        final TextChannel channel = event.getTextChannel();

        WebUtils.ins.getJSONObject("https://apis.duncte123.me/meme").async((json) -> {
            if(!json.get("success").asBoolean()) {
                channel.sendMessage("Something went wrong, try again later").queue();
                System.out.println(json);
                return;
            }

            final JsonNode data = json.get("data");
            final String title = data.get("title").asText();
            final String url = data.get("url").asText();
            final String image = data.get("image").asText();
            final EmbedBuilder embed = EmbedUtils.embedImageWithTitle(title, url, image);

            event.replyEmbeds(embed.build()).queue();
        });
    }
    @Override
    public String getName() {
        return "meme";
    }

    @Override
    public String getDescription() {
        return "Shows you a random meme";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription());
    }

}