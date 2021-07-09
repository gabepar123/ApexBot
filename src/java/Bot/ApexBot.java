package Bot;

import Bot.Commands.ApexNews;
import Bot.Commands.ApexStats;
import Bot.Commands.Sheesh;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class ApexBot extends ListenerAdapter {

    public static JDA jda;
    public static CommandClientBuilder builder;

    public ApexBot() {
        //TODO something?
    }

    public static void main(String[] args) throws Exception {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("src/resources/token.json"));
        JSONObject jsonObject = (JSONObject)obj;
        String token = (String)jsonObject.get("token");
        String ownerId = (String)jsonObject.get("owner ID");

        jda = JDABuilder.createDefault(token).build();


        makeCommandBuilder(ownerId);

        try {
            addCommands();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        CommandClient client = builder.build();
        jda.addEventListener(client);
        jda.addEventListener(new ApexNews());

    }

    private static void makeCommandBuilder(String ownerId) {
        builder = new CommandClientBuilder();
        builder.setOwnerId(ownerId);
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setHelpWord("help");
        builder.setPrefix("a?");


    }

    private static void addCommands() throws Exception {
        builder.addCommand(new Sheesh());
        builder.addCommand(new ApexStats());
        builder.addCommand(new ApexNews());

    }

    //returns api key for various commands
    public static String getAPIKey() throws Exception {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("src/resources/token.json"));
        JSONObject jsonObject = (JSONObject)obj;
        return (String)jsonObject.get("AL API KEY");

    }

}
