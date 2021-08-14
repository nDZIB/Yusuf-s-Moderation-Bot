package net.yusuf.bot.command.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.yusuf.bot.command.CommandContext;
import net.yusuf.bot.command.ICommand;
import net.yusuf.bot.lavaplayer.GuildMusicManager;
import net.yusuf.bot.lavaplayer.PlayerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class QueueCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel channel = ctx.getChannel();
        final GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(ctx.getGuild());
        final BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

        if (queue.isEmpty()) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Queue");
            builder.setDescription("The queue is currently empty");
            builder.setColor(0x34d8eb);
            ctx.getChannel().sendTyping().queue();
            ctx.getChannel().sendMessage(builder.build()).queue();
            return;
        }
        EmbedBuilder builder = new EmbedBuilder();
        final int trackCount = Math.min(queue.size(), 20);
        final List<AudioTrack> trackList = new ArrayList<>(queue);
        builder.setTitle("**Current Queue:**\n");

        for (int i = 0; i <  trackCount; i++) {
            final AudioTrack track = trackList.get(i);
            final AudioTrackInfo info = track.getInfo();

            builder.appendDescription("#")
                    .appendDescription(String.valueOf(i + 1))
                    .appendDescription(" `")
                    .appendDescription(String.valueOf(info.title))
                    .appendDescription(" by ")
                    .appendDescription(info.author)
                    .appendDescription("` [`")
                    .appendDescription(formatTime(track.getDuration()))
                    .appendDescription("`]\n");
        }

        if (trackList.size() > trackCount) {
            builder.appendDescription("And `")
                    .appendDescription(String.valueOf(trackList.size() - trackCount))
                    .appendDescription("` more...");
        }

        ctx.getChannel().sendTyping().queue();
        ctx.getChannel().sendMessage(builder.build()).queue();
    }

    private String formatTime(long timeInMillis) {
        final long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        final long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        final long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public String getName() {
        return "queue";
    }

    @Override
    public String getHelp() {
        return "shows the queued up songs";
    }
}
