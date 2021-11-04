package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.normal_commands;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.YusufSlashCommandEvent;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;

public class NormalUtils {
    public static void Success(String duncteUrl, YusufSlashCommandEvent event, Boolean meme) {
        WebUtils.ins.getJSONObject("https://apis.duncte123.me/joke").async((json) -> {
            if (!json.get("success").asBoolean()) {
                event.replyEphemeralMessage("Something went wrong, try again later");
                System.out.println(json);
                return;
            }

            final JsonNode data = json.get("data");
            final String title = data.get("title").asText();
            final String url = data.get("url").asText();
            final String body = data.get("body").asText();
            final EmbedBuilder embed;


            if (meme) {
                final String image = data.get("image").asText();
                embed = EmbedUtils.embedImageWithTitle(title, url, image);
            } else {
                embed = EmbedUtils.getDefaultEmbed().setTitle(title, url).setDescription(body);
            }

            event.replyEmbed(embed.build());
        });
    }
}
