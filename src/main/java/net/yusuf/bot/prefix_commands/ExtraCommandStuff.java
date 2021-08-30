/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Arfan Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
