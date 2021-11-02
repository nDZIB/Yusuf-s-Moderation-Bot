/*
 * GNU GENERAL PUBLIC LICENSE
 *                        Version 3, 29 June 2007
 *
 *  Copyright (C) 2021 Free Software Foundation, Inc. <https://fsf.org/>
 *  Everyone is permitted to copy and distribute verbatim copies
 *  of this license document, but changing it is not allowed.
 *
 *                            Yusuf Arfan Ismail
 *
 *   The GNU General Public License is a free, copyleft license for
 * software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.moderation;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Credits for Yusuf and great help from my other thorough my
 * <a href="https://github.com/Together-Java/TJ-Bot/pull/196">pr</a> for tj bot
 */
public enum ModerationUtils {
    ;

    /**
     * As stated in {@link Guild#ban(User, int, String)} <br>
     * The reason can be only 512 characters.
     */
    private static final int REASON_MAX_LENGTH = 512;

    @Contract(value = " -> fail", pure = true)
    ModerationUtils() {
        throw new UnsupportedOperationException(
                "The Moderation Utility class has run into an error");
    }

    /**
     * The Boolean reasonLimit will check if the reason is above the provided limit. <br>
     * <br>
     * If it is it will throw an error and will tell the user that the reason can not be over the
     * {@link ModerationUtils#REASON_MAX_LENGTH} <br>
     * If the reason is under the limit it will pass and will allow the command to continue.
     */
    public static boolean handleReason(@NotNull String reason, @NotNull SlashCommandEvent event) {
        if (reason.length() <= REASON_MAX_LENGTH) {
            return true;
        }
        event.reply("The reason can not be over " + REASON_MAX_LENGTH + " characters")
            .setEphemeral(true)
            .queue();
        return false;
    }
}
