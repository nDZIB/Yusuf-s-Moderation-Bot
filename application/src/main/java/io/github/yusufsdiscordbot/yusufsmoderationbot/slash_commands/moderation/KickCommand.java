// Originally from https://github.com/Together-Java/TJ-Bot/pull/196, then modified by Yusuf
/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007
 *
 * Copyright (C) 2021 Free Software Foundation, Inc. <https://fsf.org/> Everyone is permitted to
 * copy and distribute verbatim copies of this license document, but changing it is not allowed.
 *
 * Yusuf Arfan Ismail
 *
 * The GNU General Public License is a free, copyleft license for software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.moderation;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.*;
import io.github.yusufsdiscordbot.yusufsmoderationbot.DataBase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class KickCommand extends CommandConnector {
    private static final Logger logger = LoggerFactory.getLogger(KickCommand.class);
    private static final String USER_OPTION = "user";
    private static final String REASON_OPTION = "reason";
    private static final String COMMAND_NAME = "kick";

    public KickCommand() {
        super(COMMAND_NAME, "Kicks a given user", CommandVisibility.SERVER);

        getCommandData()
            .addOption(OptionType.USER, USER_OPTION, "The user who you want to kick", true)
            .addOption(OptionType.STRING, REASON_OPTION, "Why the user should be kicked", true);
    }

    @Override
    public void onSlashCommand(YusufSlashCommandEvent event) {
        YusufOptionMapping userOption =
                Objects.requireNonNull(event.getYusufOption(USER_OPTION), "The target is null");
        YusufMember target = userOption.getAsMember();
        YusufMember author = Objects.requireNonNull(event.getMember(), "The author is null");

        String reason = Objects.requireNonNull(event.getOption(REASON_OPTION), "The reason is null")
            .getAsString();

        YusufGuild guild = event.getGuild();
        YusufMember bot = guild.getBot();

        if (target != null && !ModerationHelper.userCanInteractWithTheGivenUser(target, bot, author,
                event, COMMAND_NAME)) {
            return;
        }

        if (!hasKickPerms(author, bot, event)) {
            return;
        }

        if (!event.getGuild().checkReasonLength(reason, event)) {
            return;
        }

        kickUser(target, author, reason, guild, event);
        updateKickDatabase(target.getUserIdLong(), guild.getIdLong(), reason,
                author.getUserIdLong());
    }

    private static void kickUser(@NotNull YusufMember target, @NotNull YusufMember author,
            @NotNull String reason, @NotNull YusufGuild guild,
            @NotNull YusufSlashCommandEvent event) {
        event.getJDA()
            .openPrivateChannelById(target.getUser().getId())
            .flatMap(channel -> channel.sendMessage(
                    """
                            Hey there, sorry to tell you but unfortunately you have been kicked from the server %s.
                            If you think this was a mistake, please contact a moderator or admin of the server.
                            The reason for the kick is: %s
                            """
                        .formatted(guild.getName(), reason)))
            .mapToResult()
            .flatMap(result -> guild.kick(target, reason).reason(reason))
            .flatMap(v -> event.replyEmbeds(new EmbedBuilder().setTitle("Kicked")
                .setDescription(target.getUser().getAsTag() + " was kicked by "
                        + author.getUser().getAsTag() + " for: " + reason)
                .setColor(Color.CYAN)
                .build()))
            .queue();

        logger.info(" '{} ({})' kicked the user '{} ({})' due to reason being '{}'",
                author.getUser().getAsTag(), author.getUserId(), target.getUser().getAsTag(),
                target.getUser().getId(), reason);
    }

    private static boolean hasKickPerms(@NotNull YusufMember author, @NotNull YusufMember bot,
            @NotNull YusufSlashCommandEvent event) {
        if (!author.hasPermission(Permission.KICK_MEMBERS)) {
            event.replyEphemeralEmbed(new EmbedBuilder().setTitle("Lack of perms")
                .setDescription(
                        "You can not kick users in this guild since you do not have the KICK_MEMBERS permission.")
                .setColor(Color.CYAN)
                .build());
            return false;
        }

        if (!bot.hasPermission(Permission.KICK_MEMBERS)) {
            event.replyEphemeralEmbed(new EmbedBuilder().setTitle("Lack of perms")
                .setDescription(
                        "I can not kick users in this guild since I do not have the KICK_MEMBERS permission.")
                .setColor(Color.CYAN)
                .build());

            logger.error("The bot does not have KICK_MEMBERS permission on the server '{}' ",
                    event.getGuild().getName());
            return false;
        }
        return true;
    }

    private void updateKickDatabase(long userId, long guildId, @NotNull String reason,
            long authorId) {
        try (final PreparedStatement preparedStatement = DataBase.getConnection()
            // language=SQLite
            .prepareStatement(
                    "UPDATE kick_settings SET kick_reason = ? , author_id = ?, is_kicked = ? WHERE user_id = ? AND guild_id = ?")) {

            preparedStatement.setString(1, reason);
            preparedStatement.setLong(2, authorId);
            preparedStatement.setBoolean(3, true);
            preparedStatement.setLong(4, userId);
            preparedStatement.setLong(5, guildId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to update the kick settings", e);
        }
    }
}
