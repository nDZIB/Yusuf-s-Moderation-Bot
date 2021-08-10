package net.yusuf.bot;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.yusuf.bot.command.CommandContext;
import net.yusuf.bot.command.ICommand;
import net.yusuf.bot.command.admin.SetPrefixCommand;
import net.yusuf.bot.command.commands.*;
import net.yusuf.bot.command.github_commands.*;
import net.yusuf.bot.command.moderation.*;
import net.yusuf.bot.command.music.*;
import net.yusuf.bot.command.server_commands.*;
import net.yusuf.bot.command.tutorials_commands.*;

import javax.annotation.Nullable;
import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new PingCommand());
        addCommand(new CommandList(this));
        addCommand(new PasteCommand());
        addCommand(new HelpCommand());
        addCommand(new Support());
        addCommand(new MemeCommand());
        addCommand(new WebhookCommand());
        addCommand(new JokeCommand());
        addCommand(new SetPrefixCommand());
        addCommand(new UptimeCommand());

        addCommand(new BanCommand());
        addCommand(new WarnCommand());
        addCommand(new MuteCommand());
        addCommand(new KickCommand());

        addCommand(new RealYusufIsmailGithub());
        addCommand(new DungeonMakersGithub());
        addCommand(new TurtyWurtyGithub());
        addCommand(new SilentChaos512Github());
        addCommand(new TeamCitcraftGithub());
        addCommand(new ForgeGithub());

        addCommand(new TeamCitcraftServer());
        addCommand(new TurtyWurtyServer());
        addCommand(new ForgeServer());
        addCommand(new DungeonMakersSever());

        addCommand(new Cy4shotTutorials());
        addCommand(new RealyusufismailTutorials());
        addCommand(new McjtyTutorials());
        addCommand(new Silentchaos512Tutorials());
        addCommand(new TurtyWurtyTutorials());

        addCommand(new JoinCommand());
        addCommand(new PlayCommand());
        addCommand(new StopCommand());
        addCommand(new NowPlayingCommand());
        addCommand(new SkipCommand());
        addCommand(new QueueCommand());
        addCommand(new RepeatCommand());
    }

    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A command with this name is already present");
        }

        commands.add(cmd);
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }

    void handle(GuildMessageReceivedEvent event, String prefix) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(prefix), "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);
        }
    }
}
