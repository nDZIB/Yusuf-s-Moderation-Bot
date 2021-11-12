/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007 Copyright (C) 2021 Free Software Foundation,
 * Inc. <https://fsf.org/> Everyone is permitted to copy and distribute verbatim copies of this
 * license document, but changing it is not allowed. Yusuf Arfan Ismail The GNU General Public
 * License is a free, copyleft license for software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.moderation;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.YusufGuild;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.YusufMember;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.YusufSlashCommandEvent;
import io.github.yusufsdiscordbot.yusufsmoderationbot.DataBase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public enum ModerationHelper {
    ;
    private static final Logger logger = LoggerFactory.getLogger(ModerationHelper.class);

    public static boolean handleHasPermissions(@NotNull YusufMember author,
            @NotNull YusufMember bot, @NotNull YusufSlashCommandEvent event,
            @NotNull YusufGuild guild, String commandName) {
        if (!author.hasPermission(Permission.BAN_MEMBERS)) {
            event.replyEphemeralEmbed(new EmbedBuilder().setTitle("Lack of perms")
                .setDescription("You can not " + commandName
                        + " users in this guild since you do not have the BAN_MEMBERS permission.")
                .setColor(Color.CYAN)
                .build());
            return false;
        }

        if (!bot.hasPermission(Permission.BAN_MEMBERS)) {
            event.replyEphemeralEmbed(new EmbedBuilder().setTitle("Lack of perms")
                .setDescription("I can not " + commandName
                        + " users in this guild since I do not have the BAN_MEMBERS permission.")
                .setColor(Color.CYAN)
                .build());

            logger.error("The bot does not have BAN_MEMBERS permission on the server '{}' ",
                    guild.getName());
            return false;
        }
        return true;
    }

    public static boolean handleCanInteractWithTarget(YusufMember target, YusufMember bot,
            @NotNull YusufMember author, @NotNull YusufSlashCommandEvent event,
            String commandName) {
        String targetTag = target.getUser().getAsTag();
        if (!author.canInteract(target)) {
            event.replyEphemeralEmbed(new EmbedBuilder().setTitle("To powerful")
                .setDescription("The user " + targetTag + " is too powerful for you to "
                        + commandName + ".")
                .setColor(Color.CYAN)
                .build());
            return false;
        }

        if (!bot.canInteract(target)) {
            event.replyEphemeralEmbed(new EmbedBuilder().setTitle("To powerful")
                .setDescription(
                        "The user " + targetTag + " is too powerful for me to " + commandName + ".")
                .setColor(Color.CYAN)
                .build());
            return false;
        }
        return true;
    }

    public static Integer getCurrentAmountOfWarns(long userId, long guildId) {
        try (final PreparedStatement preparedStatement = DataBase.getConnection()

            // language=SQLite
            .prepareStatement(
                    "SELECT amount_of_warns FROM warn_settings WHERE user_id = ? AND guild_id = ?")) {

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, guildId);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("amount_of_warns");
                }
            }
            try (final PreparedStatement insertStatement = DataBase.getConnection()
                // language=SQLite
                .prepareStatement("INSERT INTO warn_settings(user_id, guild_id) VALUES(?,?) ")) {

                insertStatement.setLong(1, userId);
                insertStatement.setLong(2, guildId);
                insertStatement.execute();
            }

        } catch (SQLException e) {
            logger.error("Failed to retrieve the amount of warns from the warn settings database",
                    e);
        }
        return getCurrentAmountOfWarns(userId, guildId);
    }
}
