package net.yusuf.bot.prefix_commands;

import java.util.List;

public interface ICommand {
    void handle(CommandContext ctx);

    String getName();

    String getHelp();

    default List<String> getAliases() {
        return List.of(); // Arrays.asLis in java 8
    }
}
