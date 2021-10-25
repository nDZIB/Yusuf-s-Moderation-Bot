/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Yusuf Arfan Ismail
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.yusuf.bot.slash_commands.simple_commands;

import github.io.yusuf.core.bot.Command;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.*;
import org.javacord.api.interaction.callback.InteractionImmediateResponseBuilder;
import org.javacord.api.util.logging.ExceptionLogger;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class RuleCommand implements Command {
    @Override
    public void onSlashCommand(SlashCommandCreateEvent slashCommandCreateEvent) {
        InteractionBase interactionBase = slashCommandCreateEvent.getInteraction();
        EmbedBuilder builder = new EmbedBuilder();
        // Rules number
        slashCommandCreateEvent.getSlashCommandInteraction()
            .getFirstOptionStringValue()
            .map(rule_number -> {
                var responder = interactionBase.createImmediateResponder();
                switch (rule_number) {
                    case "0" -> responder.addEmbed(builder.setDescription(
                            "Rule #1: Act appropriately. Everyone likes to have fun, but sometimes things are just too immature for people. No one acting under the age of thirteen. Gauge the flow of the chat and go from there, if any problems arise, This applies to voice channels too.\n"
                                    + "\n"
                                    + "Rule #2: Only post where content belongs .Post content appropriate things in corresponding channels. including emojis. For example, if you wanna talk about something random, put it in the miscellaneous channel, if you wanna play music on our music bot, keep the commands in music.\n"
                                    + "\n"
                                    + "Rule #3: No overly explicit words or expressions are allowed in the general channels.Some people are offended and some arena’t, but if someone doesn’t want to see a word, just don’t act like a dick over something you said.\n"
                                    + "\n"
                                    + "Rule #4: If you have a problem with anyone, be an adult.Attempt to work it out with the other person. Should you need to involve any of the staff, please be prepared to provide proof of your efforts to communicate your issue with them and go from there. No harassment allowed.\n"
                                    + "\n"
                                    + "Rule #5: Do not spam.Spamming is an action of repeating messages a lot, or posting things so quickly that it disrupts the chats. Do not spam emoji. Do not do this anywhere on the server, and includes jumping from channel to channel in a spam-like fashion. Changing your nickname excessively will result in a warn then mute\n"
                                    + "\n"
                                    + "Rule #6: Advertise properly and please don't spam the whole server (Check rule 5)  just to advertise your Content.\n"
                                    + "\n"
                                    + "Treat everyone with respect. Absolutely no harassment, witch hunting, sexism, racism, or hate speech will be tolerated.\n"
                                    + "\n"
                                    + "No NSFW or obscene content. This includes text, images, or links featuring nudity, sex, hard violence, or other graphically disturbing content. \n"
                                    + "\n"
                                    + "If any of the moderators think it is \"against the rules\", we reserve the right to take action. \n"
                                    + "\n"
                                    + "permanent invite link: https://discord.gg/hpY6s6mh3N\n"
                                    + "[Yusuf's Discord bot Org](https://github.com/Yusuf-s-Discord-bot)"));
                    case "1" -> responder.addEmbed(builder.setDescription(
                            "Rule #1: Act appropriately. Everyone likes to have fun, but sometimes things are just too immature for people. No one acting under the age of thirteen. Gauge the flow of the chat and go from there, if any problems arise, This applies to voice channels too."));
                    case "2" -> responder.addEmbed(builder.setDescription(
                            "Rule #2: Only post where content belongs .Post content appropriate things in corresponding channels. including emojis. For example, if you wanna talk about something random, put it in the miscellaneous channel, if you wanna play music on our music bot, keep the commands in music."));
                    case "3" -> responder.addEmbed(builder.setDescription(
                            "Rule #3: No overly explicit words or expressions are allowed in the general channels.Some people are offended and some aren’t, but if someone doesn’t want to see a word, just don’t act like a dick over something you said.\n"));
                    case "4" -> responder.addEmbed(builder.setDescription(
                            "Rule #4: If you have a problem with anyone, be an adult.Attempt to work it out with the other person. Should you need to involve any of the staff, please be prepared to provide proof of your efforts to communicate your issue with them and go from there. No harassment allowed."));
                    case "5" -> responder.addEmbed(builder.setDescription(
                            "Rule #5: Do not spam.Spamming is an action of repeating messages alot, or posting things so quickly that it disrupts the chats. Do not spam emoji. Do not do this anywhere on the server, and includes jumping from channel to channel in a spam-like fashion. Changing your nickname excessively will result in a warn then mute"));
                    case "6" -> responder.addEmbed(builder.setDescription(
                            "Rule #6: Advertise properly and please don't spam the whole server (Check rule 5) just to advertise your Content."
                                    + "\n"
                                    + "Treat everyone with respect. Absolutely no harassment, witch hunting, sexism, racism, or hate speech will be tolerated. No NSFW or obscene content. This includes text, images, or links featuring nudity, sex, hard violence, or other graphically disturbing content.\n"
                                    + "\n"
                                    + "If any of the moderators think it is \"against the rules\", we reserve the right to take action."));
                    default -> responder.addEmbed(builder.setDescription("That all the rules"));
                }
                builder.setTitle("Rules");
                builder.setColor(Color.CYAN);
                builder.setFooter("Made by Yusuf's Discord bot");
                return responder;
            })
            .map(InteractionImmediateResponseBuilder::respond)
            .ifPresent(future -> future.exceptionally(ExceptionLogger.get()));
    }

    @Override
    public String getName() {
        return "rule";
    }

    @Override
    public String getDescription() {
        return "posts my discord servers rules";
    }

    @Override
    public SlashCommandBuilder getCommandData() {
        return new SlashCommandBuilder().setName(getName())
            .setDescription(getDescription())
            .addOption(SlashCommandOption.createWithChoices(SlashCommandOptionType.INTEGER, "rules",
                    "Which rule you want.", true, rules));
    }

    private final List<SlashCommandOptionChoice> rules = Arrays.asList(
            SlashCommandOptionChoice.create("all", 0), SlashCommandOptionChoice.create("rule_1", 1),
            SlashCommandOptionChoice.create("rule_2", 2),
            SlashCommandOptionChoice.create("rule_3", 3),
            SlashCommandOptionChoice.create("rule_4", 4),
            SlashCommandOptionChoice.create("rule_5", 5),
            SlashCommandOptionChoice.create("rule_6", 6));
}
