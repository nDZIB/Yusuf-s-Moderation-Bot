/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007 Copyright (C) 2021 Free Software Foundation,
 * Inc. <https://fsf.org/> Everyone is permitted to copy and distribute verbatim copies of this
 * license document, but changing it is not allowed. Yusuf Arfan Ismail The GNU General Public
 * License is a free, copyleft license for software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.normal_commands;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.*;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;
import java.util.Objects;

public class VcEventsCommand extends CommandConnector {
    private static final String COMMAND_NAME = "vc_events";
    private static final String ACTIVITY = "activity";
    private static final String CHANNEL = "channel";
    private static final String MAX_AGE = "max_age";
    private static final String MAX_USE = "max_use";

    /**
     * List comes from <a href="https://github.com/DV8FromTheWorld/JDA/pull/1628">the "Implement
     * invite targets" PR on JDA</a>. There is no official list from Discord themselves, so this is
     * our best bet.
     */
    private static final List<Command.Choice> VC_APPLICATIONS =
            List.of(new Command.Choice("YouTube Together", "755600276941176913"),
                    new Command.Choice("Poker", "755827207812677713"),
                    new Command.Choice("Betrayal.io", "773336526917861400"),
                    new Command.Choice("Fishington.io", "814288819477020702"),
                    new Command.Choice("Chess / CG 2 Dev", "832012586023256104"),
                    new Command.Choice("Awkword", "879863881349087252"),
                    new Command.Choice("Spellcast", "852509694341283871"),
                    new Command.Choice("Doodlecrew", "878067389634314250"),
                    new Command.Choice("Wordsnack", "879863976006127627"),
                    new Command.Choice("Lettertile", "879863686565621790"));

    /**
     * Were the command is registered.
     */
    public VcEventsCommand() {
        super(COMMAND_NAME, "Use this to get a vc activity", CommandVisibility.SERVER);

        getCommandData()
            .addOptions(new OptionData(OptionType.STRING, ACTIVITY,
                    "the activity that you want to use in the vc", true)
                        .addChoices(VC_APPLICATIONS))
            .addOption(OptionType.CHANNEL, CHANNEL, "the vc channel to use the activity in", true)
            .addOption(OptionType.INTEGER, MAX_AGE, "the max age of the invite", false)
            .addOption(OptionType.STRING, MAX_USE, "the max uses of the invite", false);
    }

    @Override
    public void onSlashCommand(YusufSlashCommandEvent yusufSlashCommandEvent) {
        YusufMember member =
                Objects.requireNonNull(yusufSlashCommandEvent.getMember(), "member is null");
        GuildVoiceState voiceState = Objects.requireNonNull(member.getVoiceState(),
                "Voice states aren't being cached, check the JDABuilder");

        YusufMember bot = yusufSlashCommandEvent.getGuild().getBot();

        if (!voiceState.inVoiceChannel()) {
            yusufSlashCommandEvent
                .replyEphemeral("You need to be in a voice channel to run this command!");
            return;
        }
        if (!bot.hasPermission(Permission.CREATE_INSTANT_INVITE)) {
            yusufSlashCommandEvent.replyEphemeral(
                    "I need the permission CREATE_INSTANT_INVITE for this command to work");
        }

        Integer maxAge = Math.toIntExact(
                Objects.requireNonNull(yusufSlashCommandEvent.getOption(MAX_AGE)).getAsLong());

        Integer maxUse = Math.toIntExact(
                Objects.requireNonNull(yusufSlashCommandEvent.getOption(MAX_USE)).getAsLong());

        OptionMapping channel = yusufSlashCommandEvent.getOption(CHANNEL);

        VoiceChannel voiceChannel = Objects.requireNonNull(voiceState.getChannel());

        voiceChannel.createInvite()
            .setTargetStream(channel.getAsString())
            .setMaxAge(maxAge)
            .setMaxUses(maxUse)
            .flatMap(invite -> yusufSlashCommandEvent
                .reply("Here is the link " + invite.getUrl() + "And Thank you for using our bot."))
            .queue();
    }
}
