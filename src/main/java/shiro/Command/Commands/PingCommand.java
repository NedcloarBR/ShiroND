package shiro.Command.Commands;

import net.dv8tion.jda.api.JDA;
import shiro.Command.CommandContext;
import shiro.Command.ICommand;

public class PingCommand implements ICommand{
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();

        jda.getRestPing().queue(
            (ping) -> ctx.getChannel()
                .sendMessageFormat("Ping: %sms\nWS Ping: %sms", ping, jda.getGatewayPing()).queue()
        );
    }

    @Override
    public String getHelp() {
        return "Exibe o Ping do Bot com os Servidores do Discord";
    }

    @Override
    public String getName() {
        return "ping";
    }
}
