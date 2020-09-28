package shiro;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class Bot {

    private Bot() throws LoginException {
        new JDABuilder()
            .setToken(Config.get("TOKEN"))
            .addEventListeners(new Listener())
            .setActivity(Activity.watching("Você"))
            .build();
    }

    public static void main(final String[] args) throws LoginException {
        new Bot();
    }
}