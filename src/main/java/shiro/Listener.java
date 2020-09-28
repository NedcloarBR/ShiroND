package shiro;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.duncte123.botcommons.BotCommons;

import javax.annotation.Nonnull;

public class Listener extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private final CommandManager manager = new CommandManager();

    @Override
    public void onReady(@Nonnull final ReadyEvent event) {
        LOGGER.info("{} is Ready", event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onGuildMessageReceived(final GuildMessageReceivedEvent event) {
        final User user = event.getAuthor();

        if(user.isBot() || event.isWebhookMessage()) {
            return;
        }

        final String prefix = Config.get("prefix");
        final String raw = event.getMessage().getContentRaw();

        if(raw.equalsIgnoreCase(prefix + "shutdown") 
            && user.getId().equals(Config.get("owner_id"))) {
                LOGGER.info("Shutting down");
                event.getJDA().shutdown();
                BotCommons.shutdown(event.getJDA());

            return;
        }

        if(raw.startsWith(prefix)) {
            manager.handle(event);
        }
    }
}