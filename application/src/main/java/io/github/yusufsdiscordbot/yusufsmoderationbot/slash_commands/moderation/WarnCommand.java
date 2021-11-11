/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007 Copyright (C) 2021 Free Software Foundation,
 * Inc. <https://fsf.org/> Everyone is permitted to copy and distribute verbatim copies of this
 * license document, but changing it is not allowed. Yusuf Arfan Ismail The GNU General Public
 * License is a free, copyleft license for software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.moderation;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.*;
import io.github.yusufsdiscordbot.yusufsmoderationbot.DataBase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * The user will be able to type {@code /warn user reason} which will store the user who has been
 * warned, the reason and the amount of times they have been warned.
 */
public class WarnCommand extends CommandConnector {
    private static final Logger logger = LoggerFactory.getLogger(WarnCommand.class);
    private static final String USER_OPTION = "user";
    private static final String REASON_OPTION = "reason";
    private static final String COMMAND_TYPE = "warn";

    /**
     * Were the command is registered.
     */
    public WarnCommand() {
        super("warn", "Warn a user", CommandVisibility.SERVER);

        getCommandData().addOption(OptionType.USER, USER_OPTION, "The user to warn", true)
            .addOption(OptionType.STRING, REASON_OPTION, "The reason for the warn", true);
    }

    @Override
    public void onSlashCommand(YusufSlashCommandEvent yusufSlashCommandEvent) {
        YusufGuild guild = yusufSlashCommandEvent.getGuild();
        YusufOptionMapping userOption = Objects.requireNonNull(
                yusufSlashCommandEvent.getYusufOption(USER_OPTION), "The target is null");
        YusufUser user = userOption.getAsUser();
        String reason = Objects.requireNonNull(yusufSlashCommandEvent.getOption(REASON_OPTION))
            .getAsString();

        if (!ModerationHelper.handleHasPermissions(yusufSlashCommandEvent.getMember(),
                guild.getBot(), yusufSlashCommandEvent, guild, COMMAND_TYPE)) {
            return;
        }

        Integer oldAmountOfWarns = getCurrentAmountOfWarns(user.getUserIdLong(), guild.getIdLong());
        int amountOfWarns = oldAmountOfWarns + 1;

        updateWarn(user.getUserIdLong(), guild.getIdLong(), reason, amountOfWarns);
        yusufSlashCommandEvent.replyEmbed(new EmbedBuilder().setTitle("Success")
            .setDescription("I have warned the user " + user.getUserTag())
            .setColor(Color.CYAN)
            .build());
    }


    private void updateWarn(long userId, long guildId, @NotNull String reason, int amountOfWarns) {
        try (final PreparedStatement preparedStatement = DataBase.getConnection()
            // language=SQLite
            .prepareStatement(
                    "UPDATE warn_settings SET user_id = ?, guild_id = ?, warn_reason = ?, amount_of_warns = ?")) {

            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, guildId);
            preparedStatement.setString(3, reason);
            preparedStatement.setInt(4, amountOfWarns);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to update the warn settings", e);
        }
    }

    private Integer getCurrentAmountOfWarns(long userId, long guildId) {
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
