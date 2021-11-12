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
import java.sql.SQLException;
import java.util.Objects;

/**
 * The user will be able to type {@code /warn user reason} which will store the user who has been
 * warned, the reason and will update the amount of warns by 1
 *
 * @author Yusuf Arfan Ismail
 */
public class WarnCommand extends CommandConnector {
    private static final Logger logger = LoggerFactory.getLogger(WarnCommand.class);
    private static final String USER_OPTION = "user";
    private static final String REASON_OPTION = "reason";
    private static final String COMMAND_NAME = "warn";

    /**
     * Were the command is registered.
     */
    public WarnCommand() {
        super(COMMAND_NAME, "Warn a user", CommandVisibility.SERVER);

        getCommandData().addOption(OptionType.USER, USER_OPTION, "The user to warn", true)
            .addOption(OptionType.STRING, REASON_OPTION, "The reason for the warn", true);
    }

    @Override
    public void onSlashCommand(YusufSlashCommandEvent yusufSlashCommandEvent) {
        YusufGuild guild = yusufSlashCommandEvent.getGuild();

        YusufOptionMapping userOption = Objects.requireNonNull(
                yusufSlashCommandEvent.getYusufOption(USER_OPTION), "The target is null");

        YusufMember author =
                Objects.requireNonNull(yusufSlashCommandEvent.getMember(), "The author is null");

        YusufMember targetMember = userOption.getAsMember();
        YusufUser targetUser = userOption.getAsUser();
        String reason = Objects.requireNonNull(yusufSlashCommandEvent.getOption(REASON_OPTION))
            .getAsString();


        if (targetMember != null && !ModerationHelper.userCanInteractWithTheGivenUser(targetMember,
                guild.getBot(), author, yusufSlashCommandEvent, COMMAND_NAME)) {
            return;
        }

        if (!ModerationHelper.userAndBotHaveTheRightPerms(yusufSlashCommandEvent.getMember(),
                guild.getBot(), yusufSlashCommandEvent, guild, COMMAND_NAME)) {
            return;
        }

        Integer oldAmountOfWarns = ModerationHelper
            .getCurrentAmountOfWarns(targetUser.getUserIdLong(), guild.getIdLong());
        int amountOfWarns = oldAmountOfWarns + 1;

        if (!yusufSlashCommandEvent.getGuild().checkReasonLength(reason, yusufSlashCommandEvent)) {
            return;
        }

        updateWarn(targetUser.getUserIdLong(), guild.getIdLong(), reason, amountOfWarns);
        yusufSlashCommandEvent.replyEmbed(new EmbedBuilder().setTitle("Success")
            .setDescription("I have warned the user " + targetUser.getUserTag())
            .setColor(Color.CYAN)
            .build());
    }

    private void updateWarn(long userId, long guildId, @NotNull String reason, int amountOfWarns) {
        try (final PreparedStatement preparedStatement = DataBase.getConnection()
            // language=SQLite
            .prepareStatement(
                    "UPDATE warn_settings SET warn_reason = ?, amount_of_warns = ? WHERE user_id = ? AND guild_id = ?")) {

            preparedStatement.setString(1, reason);
            preparedStatement.setInt(2, amountOfWarns);
            preparedStatement.setLong(3, userId);
            preparedStatement.setLong(4, guildId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to update the warn settings", e);
        }
    }
}
