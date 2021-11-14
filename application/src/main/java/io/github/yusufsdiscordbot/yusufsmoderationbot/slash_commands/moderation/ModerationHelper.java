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

/**
 * All the duplicate methods that are need in multiple classes for the moderation command can be
 * found here.
 *
 * @author Yusuf Arfan Ismail
 */
public enum ModerationHelper {
    ;
    private static final Logger logger = LoggerFactory.getLogger(ModerationHelper.class);

    // Originally from https://github.com/Together-Java/TJ-Bot/pull/196, then modified by Yusuf
    public static boolean userAndBotHaveTheRightPerms(@NotNull YusufMember yusufAuthor,
            @NotNull YusufMember yusufBot, @NotNull YusufSlashCommandEvent yusufSlashCommandEvent,
            @NotNull YusufGuild yusufGuild, String commandName) {

        if (!yusufAuthor.hasPermission(Permission.BAN_MEMBERS)) {
            yusufSlashCommandEvent.replyEphemeralEmbed(new EmbedBuilder().setTitle("Lack of perms")
                .setDescription("You can not " + commandName
                        + " users in this server since you do not have the BAN_MEMBERS permission.")
                .setColor(Color.CYAN)
                .build());
            return false;
        }

        if (!yusufBot.hasPermission(Permission.BAN_MEMBERS)) {
            yusufSlashCommandEvent.replyEphemeralEmbed(new EmbedBuilder().setTitle("Lack of perms")
                .setDescription("I can not " + commandName
                        + " users in this server since I do not have the BAN_MEMBERS permission.")
                .setColor(Color.CYAN)
                .build());

            logger.error(
                    "The bot does not have BAN_MEMBERS permission which means it can not use the command '{}' on the server '{}' ",
                    commandName, yusufGuild.getName());
            return false;
        }
        return true;
    }

    // Originally from https://github.com/Together-Java/TJ-Bot/pull/196, then modified by Yusuf
    public static boolean userCanInteractWithTheGivenUser(YusufMember target, YusufMember yusufBot,
            @NotNull YusufMember yusufAuthor,
            @NotNull YusufSlashCommandEvent yusufSlashCommandEvent, String commandName) {
        String yusufUserTag = target.getUser().getAsTag();

        if (!yusufAuthor.canInteract(target)) {
            yusufSlashCommandEvent.replyEphemeralEmbed(new EmbedBuilder().setTitle("To powerful")
                .setDescription("The user " + yusufUserTag + " is too powerful for you to "
                        + commandName + ".")
                .setColor(Color.CYAN)
                .build());
            return false;
        }

        if (!yusufBot.canInteract(target)) {
            yusufSlashCommandEvent.replyEphemeralEmbed(new EmbedBuilder().setTitle("To powerful")
                .setDescription("The user " + yusufUserTag + " is too powerful for me to "
                        + commandName + ".")
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

    // language=SQLite
    private static final String INSERT_FOR_KICK =
            "INSERT INTO kick_settings(user_id, guild_id) VALUES(?,?)";

    public static String getKickReason(long userId, long guildId) {
        try (final PreparedStatement preparedStatement = DataBase.getConnection()

            // language=SQLite
            .prepareStatement(
                    "SELECT kick_reason FROM kick_settings WHERE user_id = ? AND guild_id = ?")) {

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, guildId);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("kick_reason");
                }
            }
            try (final PreparedStatement insertStatement = DataBase.getConnection()
                // language=SQLite
                .prepareStatement(INSERT_FOR_KICK)) {

                insertStatement.setLong(1, userId);
                insertStatement.setLong(2, guildId);
                insertStatement.execute();
            }

        } catch (SQLException e) {
            logger.error("Failed to retrieve the kick reason from the kick database", e);
        }
        return getKickReason(userId, guildId);
    }

    public static Long getKickAuthor(long userId, long guildId) {
        try (final PreparedStatement preparedStatement = DataBase.getConnection()

            // language=SQLite
            .prepareStatement(
                    "SELECT author_id FROM kick_settings WHERE user_id = ? AND guild_id = ?")) {

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, guildId);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("author_id");
                }
            }
            try (final PreparedStatement insertStatement = DataBase.getConnection()
                // language=SQLite
                .prepareStatement(INSERT_FOR_KICK)) {

                insertStatement.setLong(1, userId);
                insertStatement.setLong(2, guildId);
                insertStatement.execute();
            }

        } catch (SQLException e) {
            logger.error("Failed to retrieve the the author of the kick from the kick database", e);
        }
        return getKickAuthor(userId, guildId);
    }

    public static boolean checkIfKickIsNull(long userId, long guildId) {
        try (final PreparedStatement preparedStatement = DataBase.getConnection()

            // language=SQLite
            .prepareStatement(
                    "SELECT is_kicked FROM kick_settings WHERE user_id = ? AND guild_id = ?")) {

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, guildId);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("is_kicked");
                }
            }
            try (final PreparedStatement insertStatement = DataBase.getConnection()
                // language=SQLite
                .prepareStatement(INSERT_FOR_KICK)) {

                insertStatement.setLong(1, userId);
                insertStatement.setLong(2, guildId);
                insertStatement.execute();
            }

        } catch (SQLException e) {
            logger.error("Failed to retrieve the boolean is kicked.", e);
        }
        return checkIfKickIsNull(userId, guildId);
    }

    // language=SQLite
    private static final String INSERT_FOR_BAN =
            "INSERT INTO ban_settings(user_id, guild_id) VALUES(?,?)";

    public static boolean checkIfBanIsNull(long userId, long guildId) {
        try (final PreparedStatement preparedStatement = DataBase.getConnection()

            // language=SQLite
            .prepareStatement(
                    "SELECT is_banned FROM ban_settings WHERE user_id = ? AND guild_id = ?")) {

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, guildId);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean("is_banned");
                }
            }
            try (final PreparedStatement insertStatement = DataBase.getConnection()
                // language=SQLite
                .prepareStatement(INSERT_FOR_BAN)) {

                insertStatement.setLong(1, userId);
                insertStatement.setLong(2, guildId);
                insertStatement.execute();
            }

        } catch (SQLException e) {
            logger.error("Failed to retrieve the boolean is banned", e);
        }
        return checkIfBanIsNull(userId, guildId);
    }

    public static String getBanReason(long userId, long guildId) {
        try (final PreparedStatement preparedStatement = DataBase.getConnection()

            // language=SQLite
            .prepareStatement(
                    "SELECT ban_reason FROM ban_settings WHERE user_id = ? AND guild_id = ?")) {

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, guildId);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("ban_reason");
                }
            }
            try (final PreparedStatement insertStatement = DataBase.getConnection()
                // language=SQLite
                .prepareStatement(INSERT_FOR_BAN)) {

                insertStatement.setLong(1, userId);
                insertStatement.setLong(2, guildId);
                insertStatement.execute();
            }

        } catch (SQLException e) {
            logger.error("Failed to retrieve the ban reason from the ban database", e);
        }
        return getBanReason(userId, guildId);
    }

    public static Long getBanAuthor(long userId, long guildId) {
        try (final PreparedStatement preparedStatement = DataBase.getConnection()

            // language=SQLite
            .prepareStatement(
                    "SELECT author_id FROM ban_settings WHERE user_id = ? AND guild_id = ?")) {

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, guildId);

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("author_id");
                }
            }
            try (final PreparedStatement insertStatement = DataBase.getConnection()
                // language=SQLite
                .prepareStatement(INSERT_FOR_BAN)) {

                insertStatement.setLong(1, userId);
                insertStatement.setLong(2, guildId);
                insertStatement.execute();
            }

        } catch (SQLException e) {
            logger.error("Failed to retrieve the the author of the ban from the ban database", e);
        }
        return getBanAuthor(userId, guildId);
    }
}
