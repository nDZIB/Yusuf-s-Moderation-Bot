package net.yusuf.bot.slash_commands.normal_commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.yusuf.bot.slash_commands.Command;

public class PingCommand extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        JDA jda = event.getJDA();
        jda.getRestPing().queue(
                (ping) -> event.getChannel()
                        .sendMessageFormat("Reset ping: %sms\nWS ping: %sms", ping, jda.getGatewayPing()).queue()
        );
        event.reply("Here is the ping").queue();
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Shows the latency of the bot";
    }

    @Override
    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription());
    }
}
