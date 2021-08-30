package net.yusuf.bot.slash_commands.moderation;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.yusuf.bot.slash_commands.Command;


import static net.dv8tion.jda.api.interactions.commands.OptionType.INTEGER;

public class DeleteCommand extends Command {
    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        final Member member = event.getMember();
        final TextChannel channel = event.getTextChannel();


        if (!member.hasPermission(Permission.MESSAGE_MANAGE)) {
            event.reply("You are missing permission to manage the message").queue();
            return;
        }

        final Member selfMember = event.getGuild().getSelfMember();

        if (!selfMember.hasPermission(Permission.MESSAGE_MANAGE)) {
            event.reply("I am missing permissions to manage these messages").queue();
            return;
        }


        int amount = (int) event.getOption("amount").getAsLong();

        var messageHistory = channel.getHistory().retrievePast(amount).complete();

        if(amount < 100) {
            event.reply("You can only delete 1 to 100 messages");
        } else {
            channel.purgeMessages(messageHistory);
        }

    }

    @Override
    public String getName() {
        return "delete_messages";
    }

    @Override
    public String getDescription() {
        return "Delete 1 to 100 messages";
    }

    public CommandData getCommandData() {
        return new CommandData(getName(), getDescription())
                .addOptions(new OptionData(INTEGER, "amount", "1 to 100")
                        .setRequired(true));
    }

}

