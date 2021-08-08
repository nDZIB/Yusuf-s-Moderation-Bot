package net.yusuf.bot;

import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.yusuf.bot.database.*;

import javax.security.auth.login.LoginException;
import java.sql.SQLException;
import java.util.EnumSet;

public class Bot {
    private Bot() throws LoginException, SQLException {
        SQLiteDataSource.getConnection();

        EmbedUtils.setEmbedBuilder(
                () -> new EmbedBuilder()
                        .setColor(0x3883d9)
                        .setFooter("Yusuf Ismail Moderation Bot")
        );

        JDABuilder.createDefault(
                Config.get("token"),
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES
        )
                .disableCache(EnumSet.of(
                CacheFlag.CLIENT_STATUS,
                CacheFlag.ACTIVITY,
                CacheFlag.EMOTE
        ))
                .enableCache(CacheFlag.VOICE_STATE)
                .addEventListeners(new Listener())
                .setActivity(Activity.watching("&help"))
                .setStatus(OnlineStatus.ONLINE)
                .build();

    }
    public static void main(String[] args) throws LoginException, SQLException {
            new Bot();
    }
}
