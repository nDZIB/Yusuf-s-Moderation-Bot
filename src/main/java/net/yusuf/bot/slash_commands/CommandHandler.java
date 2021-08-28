package net.yusuf.bot.slash_commands;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.yusuf.bot.slash_commands.github_commands.*;
import net.yusuf.bot.slash_commands.moderation.*;
import net.yusuf.bot.slash_commands.music.*;
import net.yusuf.bot.slash_commands.normal_commands.CommandsList;
import net.yusuf.bot.slash_commands.normal_commands.*;
import net.yusuf.bot.slash_commands.role.*;
import net.yusuf.bot.slash_commands.server_commands.*;
import net.yusuf.bot.slash_commands.tutorials_commands.*;


import java.util.HashMap;
import java.util.Map;

public class CommandHandler extends ListenerAdapter {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandHandler() {
        addCommand(new CommandsList());
        addCommand(new ServerCommands());
        addCommand(new HelloWorld());
        addCommand(new PingCommand());
        //addCommand(new PasteCommand());
        addCommand(new HelpCommand());
        addCommand(new Support());
        addCommand(new MemeCommand());
        addCommand(new JokeCommand());
        //addCommand(new UptimeCommand());

        addCommand(new BanCommand());
        addCommand(new UnBanCommand());
        //addCommand(new WarnCommand());
        //addCommand(new MuteCommand());
        addCommand(new KickCommand());

        addCommand(new RealYusufIsmailGithub());
        addCommand(new DungeonMakersGithub());
        addCommand(new TurtyWurtyGithub());
        addCommand(new SilentChaos512Github());
        addCommand(new ForgeGithub());

        addCommand(new TeamCitcraftServer());
        addCommand(new TurtyWurtyServer());
        addCommand(new ProgrammingServer());
        addCommand(new Cy4Server());

        addCommand(new Cy4shotTutorials());
        addCommand(new RealyusufismailTutorials());
        addCommand(new McjtyTutorials());
        addCommand(new Silentchaos512Tutorials());
        addCommand(new TurtyWurtyTutorials());
        addCommand(new TutorialsCommandsList());


        addCommand(new JoinCommand());
        addCommand(new LeaveCommand());
        addCommand(new PlayCommand());
        addCommand(new PauseCommand());
        addCommand(new MusicCommandsList());
        addCommand(new SkipCommand());
        addCommand(new StopCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new QueueCommand());
        addCommand(new ResumeCommand());
        addCommand(new RepeatCommand());

        addCommand(new AddRoleCommand());
        addCommand(new RemoveRoleCommand());
    }

    public void addCommand(Command command){
        commands.put(command.getName(), command);
    }

    @Override
    public void onSlashCommand(SlashCommandEvent event) {
        var cmd = commands.get(event.getName());
        if(cmd == null) {
            event.reply("unknown command").queue();
            return;
        }
        cmd.onSlashCommand(event);
    }
}
