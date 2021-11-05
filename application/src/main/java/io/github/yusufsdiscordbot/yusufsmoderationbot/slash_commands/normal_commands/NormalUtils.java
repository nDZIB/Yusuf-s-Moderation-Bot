package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.normal_commands;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.YusufSlashCommandEvent;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum NormalUtils {
    ;
    private static final Logger logger = LoggerFactory.getLogger(NormalUtils.class);

    public static void Success(String link, YusufSlashCommandEvent event, Boolean meme) {
        WebUtils.ins.getJSONObject(link).async(json -> {
            if (!json.get("success").asBoolean()) {
                event.replyEphemeralMessage("Something went wrong, try again later");
                logger.info("Here is the json: " + json);
                return;
            }

            final JsonNode data = json.get("data");
            final String title = data.get("title").asText();
            final String url = data.get("url").asText();
            final String body = data.get("body").asText();
            final EmbedBuilder embed;


            if (meme == true) {
                final String image = data.get("image").asText();
                embed = EmbedUtils.embedImageWithTitle(title, url, image);
            } else {
                embed = EmbedUtils.getDefaultEmbed().setTitle(title, url).setDescription(body);
            }

            event.replyEmbed(embed.build());
        });
    }
}
