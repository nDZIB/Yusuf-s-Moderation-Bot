/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007 Copyright (C) 2021 Free Software Foundation,
 * Inc. <https://fsf.org/> Everyone is permitted to copy and distribute verbatim copies of this
 * license document, but changing it is not allowed. Yusuf Arfan Ismail The GNU General Public
 * License is a free, copyleft license for software and other kinds of works.
 */

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

    public static void success(String link, YusufSlashCommandEvent event, Boolean meme) {
        WebUtils.ins.getJSONObject(link).async(json -> {
            if (!json.get("success").asBoolean()) {
                event.replyEphemeralMessage("Something went wrong, try again later");
                return;
            }

            final JsonNode data = json.get("data");
            final String title = data.get("title").asText();
            final String url = data.get("url").asText();
            final String body = data.get("body").asText();
            final EmbedBuilder embed;


            if (meme.equals(true)) {
                final String image = data.get("image").asText();
                embed = EmbedUtils.embedImageWithTitle(title, url, image);
            } else {
                embed = EmbedUtils.getDefaultEmbed().setTitle(title, url).setDescription(body);
            }

            event.replyEmbed(embed.build());
        });
    }
}
