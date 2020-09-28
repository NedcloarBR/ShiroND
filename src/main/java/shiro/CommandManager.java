package shiro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import shiro.Command.CommandContext;
import shiro.Command.ICommand;
import shiro.Command.Commands.HelpCommand;
import shiro.Command.Commands.PingCommand;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new PingCommand());
        addCommand(new HelpCommand(this));
    }

    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream()
            .anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));
        if(nameFound) {
            throw new IllegalArgumentException("Este Comando já existe");
        }

        commands.add(cmd);
    }

    public List<ICommand> getCommand() {
        return commands;
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();

        for(ICommand cmd : this.commands) {
            if(cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower)) {
                return cmd;
            }
        }

        return null;
    }

    void handle(GuildMessageReceivedEvent event) {
        String[] split = event.getMessage().getContentRaw()
            .replaceFirst("(?i)" + Pattern.quote(Config.get("prefix")), "")
            .split("\\s+");
        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if(cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);
        }
    }
}
