package net.yusuf.bot.slash_commands.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.managers.AudioManager;
import net.yusuf.bot.slash_commands.Command;
import net.yusuf.bot.lavaplayer.GuildMusicManager;
import net.yusuf.bot.lavaplayer.PlayerManager;

public class LeaveCommand extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        final Member self = event.getGuild().getSelfMember();
        event.deferReply(true).queue();
        final GuildVoiceState selfVoiceState = self.getVoiceState();
        InteractionHook hook = event.getHook();
        hook.setEphemeral(true);

            if (selfVoiceState.inVoiceChannel() == false) {
                hook.sendMessage("I'm not in a voice channel").queue();
                return;
            }
            final Member member = event.getMember();
            final GuildVoiceState memberVoiceState = member.getVoiceState();

            if (!memberVoiceState.inVoiceChannel()) {
                hook.sendMessage("You need to be in a voice channel for this command to work").queue();
                return;
            }
            final AudioManager audioManager = event.getGuild().getAudioManager();
            final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());

            audioManager.closeAudioConnection();
            musicManager.scheduler.queue.clear();
             event.reply("I have left the vc").queue();
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "leaves the vc";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription());
    }
}
