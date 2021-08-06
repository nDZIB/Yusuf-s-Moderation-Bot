package net.yusuf.bot;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Bot {
    private Bot() throws LoginException {

        JDABuilder.createDefault(Config.get("token"))
                .addEventListeners(new Listener())
                .setActivity(Activity.watching("&help"))
                .setStatus(OnlineStatus.ONLINE)
                .build();

    }
    public static void main(String[] args) throws LoginException {
            new Bot();
    }
}
