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

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandConnector;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CommandVisibility;
import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.YusufSlashCommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * This command can remove specific messages using their ids. The command requires the user to input
 * the id of message they want to delete from and the id of the messages they want to delete to.
 * <p>
 * The command fails if one of the message id that was given is invalid or the user lacks the
 * permission to delete messages.
 */
public class PurgeCommand extends CommandConnector {
    // TODO Test the command
    private static final Logger logger = LoggerFactory.getLogger(PurgeCommand.class);
    private static final String FIRST_MESSAGE_ID = "first_message_id";
    private static final String LAST_MESSAGE_ID = "last_message_id";

    public PurgeCommand() {
        super("purge_by_id", "Delete specific messages using there ids.", CommandVisibility.SERVER);

        getCommandData()
            .addOption(OptionType.NUMBER, FIRST_MESSAGE_ID,
                    "The id of the message you want to delete from.", true)
            .addOption(OptionType.NUMBER, LAST_MESSAGE_ID,
                    "The id of the message you want to delete to.", true);
    }

    @Override
    public void onSlashCommand(YusufSlashCommandEvent event) {
        final Member author = event.getMember().getAuthor();

        if (!author.hasPermission(Permission.MESSAGE_MANAGE)) {
            event.replyEphemeral("You are missing MESSAGE_MANAGE permission to delete these the message");
            return;
        }

        final Member bot = Objects.requireNonNull(event.getGuild()).getBot();
        if (!bot.hasPermission(Permission.MESSAGE_MANAGE)) {
            event.replyEphemeral(
                    "I am missing MESSAGE_MANAGE permission which means I am unable to delete messages in this server.");

            logger.error("The bot does not have MESSAGE_MANAGE permission on the server '{}' ",
                    event.getGuild().getId());
            return;
        }

        long firstMessageId = Objects.requireNonNull(event.getOption(FIRST_MESSAGE_ID)).getAsLong();
        long lastMessageId = Objects.requireNonNull(event.getOption(LAST_MESSAGE_ID)).getAsLong();

        deleteMessagesById(firstMessageId, lastMessageId, event.getEvent().getMessageChannel(),
                strings -> {

                });
    }

    private static void deleteMessagesById(long firstMessageId, long lastMessageId,
            MessageChannel mc, Consumer<List<String>> cb) {
        getBetween0(firstMessageId, lastMessageId, mc, new ArrayList<>(), new ReentrantLock(), cb);
    }

    private static void getBetween0(long firstMessageId, long lastMessageId, MessageChannel mc,
            List<String> acc, ReentrantLock lock, Consumer<List<String>> cb) {
        mc.getHistoryBefore(firstMessageId, 100).queue(his -> {
            try {
                lock.lock();
                List<String> collected = his.getRetrievedHistory()
                    .stream()
                    .map(ISnowflake::getId)
                    .collect(Collectors.toList());

                int foundIdx = collected.indexOf(String.valueOf(lastMessageId));

                if (foundIdx != -1) {
                    acc.addAll(collected.subList(0, foundIdx));
                    cb.accept(acc);
                } else {
                    acc.addAll(collected);
                    getBetween0(Long.parseLong(collected.get(collected.size() - 1)), lastMessageId,
                            mc, acc, lock, cb);
                }
            } finally {
                lock.unlock();
            }
        });
    }
}

