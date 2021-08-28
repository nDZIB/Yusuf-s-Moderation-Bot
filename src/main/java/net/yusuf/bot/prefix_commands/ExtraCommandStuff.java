package net.yusuf.bot.prefix_commands;

import com.jagrosh.jdautilities.commons.utils.FixedSizeCache;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.function.Consumer;

public class ExtraCommandStuff {

    private static final FixedSizeCache<Long, TLongSet> MESSAGE_LINK_MAP = new FixedSizeCache<>(20);

    public static void linkMessage(long commandId, long responseId) {
        TLongSet set;
        if(!MESSAGE_LINK_MAP.contains(commandId))
        {
            set = new TLongHashSet(2);
            MESSAGE_LINK_MAP.add(commandId, set);
        }
        else
        {
            set = MESSAGE_LINK_MAP.get(commandId);
        }
        set.add(responseId);
    }

    public static void reply(GuildMessageReceivedEvent event, String message)
    {
        reply(event, message, null);
    }

    protected static void reply(GuildMessageReceivedEvent event, String message, Consumer<Message> successConsumer)
    {
        reply(event, new MessageBuilder(message).build(), successConsumer);
    }

    protected void reply(GuildMessageReceivedEvent event, MessageEmbed embed)
    {
        reply(event, embed, null);
    }

    protected void reply(GuildMessageReceivedEvent event, MessageEmbed embed, Consumer<Message> successConsumer)
    {
        reply(event, new MessageBuilder(embed).build(), successConsumer);
    }

    protected void reply(GuildMessageReceivedEvent event, Message message)
    {
        reply(event, message, null);
    }

    protected static void reply(GuildMessageReceivedEvent event, Message message, Consumer<Message> successConsumer)
    {
        event.getChannel().sendMessage(message).queue(linkReply(event, successConsumer));
    }

    protected static Consumer<Message> linkReply(GuildMessageReceivedEvent event, Consumer<Message> successConsumer) {
        return msg ->
        {
            linkMessage(event.getMessageIdLong(), msg.getIdLong());
            if (successConsumer != null)
                successConsumer.accept(msg);
        };
    }
}
