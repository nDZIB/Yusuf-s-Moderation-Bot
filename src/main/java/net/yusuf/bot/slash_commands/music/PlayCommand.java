package net.yusuf.bot.slash_commands.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.yusuf.bot.slash_commands.Command;
import net.yusuf.bot.lavaplayer.PlayerManager;

import java.net.URI;
import java.net.URISyntaxException;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class PlayCommand extends Command {
    @SuppressWarnings("ConstantConditions")
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        if (event.getName().equals("play")) {
            final Member self = event.getGuild().getSelfMember();
            final GuildVoiceState selfVoiceState = self.getVoiceState();
            event.deferReply(true).queue();
            InteractionHook hook = event.getHook();
            hook.setEphemeral(true);

            if (!selfVoiceState.inVoiceChannel()) {
                hook.sendMessage("I need to be in a voice channel for this to work").queue();
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

            String link = event.getOption("song").getAsString();

            if (!isUrl(link)) {
                link = "ytsearch:" + link;
            }

            PlayerManager.getInstance().loadAndPlay(event.getTextChannel(), link);
        }
    }
    private boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getDescription() {
        return "/play link or name";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription())
                .addOptions(new OptionData(STRING, "song", "Link or term")
                        .setRequired(true));
    }
}
