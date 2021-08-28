package net.yusuf.bot.slash_commands.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;
import net.yusuf.bot.lavaplayer.GuildMusicManager;
import net.yusuf.bot.lavaplayer.PlayerManager;

public class ResumeCommand extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();
        event.deferReply(true).queue();
        InteractionHook hook = event.getHook();
        hook.setEphemeral(true);

        if (!selfVoiceState.inVoiceChannel()) {
            event.reply("I need to be in a voice channel for this to work").queue();
            return;
        }

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            hook.sendMessage("You need to be in a voice channel for this command to work").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            hook.sendMessage("You need to be in the same voice channel as me for this to work").queue();
            return;
        }

        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());

        musicManager.scheduler.player.setPaused(false);
        event.reply("The player has been resumed.").queue();
    }

    @Override
    public String getName() {
        return "resume";
    }

    @Override
    public String getDescription() {
        return "resumes the bot";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription());
    }
}

