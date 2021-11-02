/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Arfan Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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

    /**
     * This boolean checks if the reason that the user has entered violates the max character length
     * or not. <br>
     * If it does the bot will tell the user has violated the mex character length and will
     * terminate the command <br>
     * If it does bot the bot will be allowed to continue running the command.
     * 
     * @param reason the reason of the action such as banning.
     * @throws IllegalArgumentException if the reason is over 512 characters.
     *
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
