/*
 * GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007 Copyright (C) 2021 Free Software Foundation,
 * Inc. <https://fsf.org/> Everyone is permitted to copy and distribute verbatim copies of this
 * license document, but changing it is not allowed. Yusuf Arfan Ismail The GNU General Public
 * License is a free, copyleft license for software and other kinds of works.
 */

package io.github.yusufsdiscordbot.yusufsmoderationbot;

import io.github.yusufsdiscordbot.yusufsdiscordcore.bot.slash_command.CoreSlashCommandHandler;
import io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.moderation.*;
import io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.normal_commands.*;
import io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.role.AddRoleCommand;
import io.github.yusufsdiscordbot.yusufsmoderationbot.slash_commands.role.RemoveRoleCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

public class CommandHandler extends CoreSlashCommandHandler {

    public CommandHandler(JDA jda, Guild guild) {
        super(jda, guild);
        addCommand(new HelloWorld());
        addCommand(new PingCommand());
        addCommand(new HelpCommand());
        addCommand(new UptimeCommand());
        addCommand(new Support());
        addCommand(new MemeCommand());
        addCommand(new JokeCommand());
        addCommand(new GithubCommand());
        addCommand(new DiscordCommand());
        addCommand(new BotInfoCommand());
        addCommand(new VcEventsCommand());

        addCommand(new BanCommand());
        addCommand(new UnBanCommand());
        addCommand(new WarnCommand());
        // addCommand(new MuteCommand());
        addCommand(new KickCommand());
        addCommand(new PurgeCommand());

        addCommand(new AddRoleCommand());
        addCommand(new RemoveRoleCommand());
        globalCommandsData.queue();
        guildCommandsData.queue();
    }

}
