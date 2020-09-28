package shiro.Command.Commands;

import java.util.List;

import net.dv8tion.jda.api.entities.TextChannel;

import shiro.CommandManager;
import shiro.Config;
import shiro.Command.CommandContext;
import shiro.Command.ICommand;

public class HelpCommand implements ICommand{
    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if(args.isEmpty()) {
            StringBuilder builder = new StringBuilder();

            builder.append("Lista de comandos ⬇\n");

            manager.getCommand().stream().map(ICommand::getName).forEach(
                (it) -> builder.append('`').append(Config.get("prefix")).append(it).append("`\n")
            );

            channel.sendMessage(builder.toString()).queue();
            return;
        }

        String search = args.get(0);
        ICommand command = manager.getCommand(search);

        if(command == null) {
            channel.sendMessage("Nenhum Comando Encontrado para" + search).queue();
            return;
        }

        channel.sendMessage(command.getHelp()).queue();
    }

    @Override
    public String getHelp() {
        return "Exibe a lista de Comandos do Bot\n" +
                "Como Usar: `S&help <Comando>`";
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public List<String> getAliases() {
        return List.of("comandos");
    }
}