package shiro.Command.Commands;

import net.dv8tion.jda.api.JDA;
import shiro.Command.CommandContext;
import shiro.Command.ICommand;

public class ArquivoCommand implements ICommand{
    @Override
    public void handle(CommandContext ctx) {
        //
    }

    @Override
    public String getHelp() {
        return "Descrição do Help";
    }

    @Override
    public String getName() {
        return "Nome do Comando Aqui";
    }

    @Override
    public List<String> getAliases() {
        return List.of("") # Aliases dentro dos ""
    }
}