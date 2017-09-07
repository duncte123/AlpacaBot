package ml.duncte123.alpacabot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class AlpacaBot extends ListenerAdapter {

    private static JDA jda;

    public static void main(String [] args) {
        if (args.length < 1)
            throw new IllegalArgumentException("You're missing a token.");

        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(args[0])
                    .setGame(Game.of("Mention me!!!"))
                    .addEventListener(new AlpacaBot())
                    .buildBlocking();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if(event.getAuthor().isBot()) {
            return;
        }

        if(event.getMessage().getMentionedUsers().contains(event.getJDA().getSelfUser())) {
            try {
                Document doc = Jsoup.connect("http://www.randomalpaca.com/").get();

                Element img = doc.select("img").first();
                event.getTextChannel().sendFile(new URL(img.attributes().get("src")).openStream(), "Alpaca_" + System.currentTimeMillis() + ".png", null).queue();
            }
            catch (Exception e) {
                e.printStackTrace();
                event.getTextChannel().sendMessage("ERROR: " + e.getMessage()).queue();
            }
        }


    }
}
