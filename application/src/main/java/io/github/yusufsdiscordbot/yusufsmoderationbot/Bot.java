/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007 Copyright (C) 2021 Free Software Foundation,
 * Inc. <https://fsf.org/> Everyone is permitted to copy and distribute verbatim copies of this
 * license document, but changing it is not allowed. Yusuf Arfan Ismail The GNU General Public
 * License is a free, copyleft license for software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;
import java.util.EnumSet;

public class Bot {
    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    public static void main(String[] args)
            throws LoginException, InterruptedException, SQLException {
        final int cores = Runtime.getRuntime().availableProcessors();

        if (cores <= 1) {
            logger.info("Available Cores '{}' setting Parallelism Flag", cores);
            System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "1");
        }

        DataBase.getConnection();
        JDA jda = JDABuilder
            .createDefault(Config.get("TOKEN"), GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_VOICE_STATES)
            .disableCache(EnumSet.of(CacheFlag.CLIENT_STATUS, CacheFlag.ACTIVITY, CacheFlag.EMOTE))
            .enableCache(CacheFlag.VOICE_STATE)
            .setActivity(Activity.watching("/help"))
            .setStatus(OnlineStatus.ONLINE)
            .build();

        jda.awaitReady()
            .addEventListener(new CommandHandler(jda, jda.getGuildById(Config.get("GUILD_ID"))));
    }
}
