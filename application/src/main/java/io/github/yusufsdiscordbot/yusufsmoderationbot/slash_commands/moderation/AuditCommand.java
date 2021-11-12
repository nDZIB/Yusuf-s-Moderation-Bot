/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007 Copyright (C) 2021 Free Software Foundation,
 * Inc. <https://fsf.org/> Everyone is permitted to copy and distribute verbatim copies of this
 * license document, but changing it is not allowed. Yusuf Arfan Ismail The GNU General Public
 * License is a free, copyleft license for software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.moderation;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class AuditCommand extends CommandConnector {
    private static final String WARN_COMMAND = "warn";
    private static final String KICK_COMMAND = "kick";
    private static final String BAN_COMMAND = "ban";
    private static final String WARN_USER = "user";
    private static final String BAN_USER = "user";
    private static final String KICK_USER = "user";

    /**
     * Were the command is registered.
     */
    public AuditCommand() {
        super("audit", "get the log for warns, kicks and bans", CommandVisibility.SERVER);

        getCommandData().addSubcommands(
                new SubcommandData(WARN_COMMAND, "Gets the specific warns for that user").addOption(
                        OptionType.USER, WARN_USER, "The user who you want get the warns for",
                        true),
                new SubcommandData(KICK_COMMAND, "Gets you the reason of the kick for that user")
                    .addOption(OptionType.USER, KICK_USER,
                            "The user who you want get the kick reason for", true),
                new SubcommandData(BAN_COMMAND, "Gets you the reason of the ban for that user")
                    .addOption(OptionType.USER, BAN_USER,
                            "The user who you want get the ban reason for", true));
    }

    @Override
    public void onSlashCommand(@NotNull YusufSlashCommandEvent yusufSlashCommandEvent) {
        switch (Objects.requireNonNull(yusufSlashCommandEvent.getSubcommandName())) {
            case WARN_COMMAND -> handleWarnCommand(yusufSlashCommandEvent);
            case KICK_COMMAND -> handleKickCommand(yusufSlashCommandEvent);
            case BAN_COMMAND -> handleBanCommand(yusufSlashCommandEvent);
            default -> throw new AssertionError();
        }
    }

    private void handleWarnCommand(@NotNull YusufSlashCommandEvent event) {
        YusufGuild guild = event.getGuild();
        YusufOptionMapping userOption =
                Objects.requireNonNull(event.getYusufOption(WARN_USER), "The target is null");
        YusufUser user = userOption.getAsUser();
        YusufMember author = Objects.requireNonNull(event.getMember(), "The author is null");
        YusufMember targetMember = userOption.getAsMember();


        if (targetMember != null && !ModerationHelper.handleCanInteractWithTarget(targetMember,
                guild.getBot(), author, event, WARN_COMMAND)) {
            return;
        }

        if (!ModerationHelper.handleHasPermissions(event.getMember(), guild.getBot(), event, guild,
                WARN_COMMAND)) {
            return;
        }

        event.replyEmbed(new EmbedBuilder().setTitle("Warns")
            .setDescription("The user " + user.getUserTag() + " has "
                    + ModerationHelper.getCurrentAmountOfWarns(user.getUserIdLong(),
                            guild.getIdLong())
                    + " warns")
            .setColor(Color.CYAN)
            .build());
    }



    private void handleKickCommand(@NotNull YusufSlashCommandEvent event) {

    }

    private void handleBanCommand(@NotNull YusufSlashCommandEvent event) {

    }
}
