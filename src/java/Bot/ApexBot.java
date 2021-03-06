package Bot;

import Bot.Commands.*;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class ApexBot extends ListenerAdapter {

    public static JDA jda;
    public static CommandClientBuilder builder;


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
        builder.setActivity(Activity.listening("Type a?help!"));
        builder.useHelpBuilder(false);
        builder.setPrefix("a?");
    }

    private static void addCommands() throws Exception {
        //add about me
        builder.addCommand(new ApexStats());
        builder.addCommand(new ApexNews());
        builder.addCommand(new MapRotation());
        builder.addCommand(new RandomLegend());
        builder.addCommand(new RandomLoadout());
        builder.addCommand(new RandomTeam());
        builder.addCommand(new Help());
        builder.addCommand(new Ping());
    }

    //returns api key for various commands
    public static String getAPIKey() throws Exception {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("src/resources/token.json"));
        JSONObject jsonObject = (JSONObject)obj;
        return (String)jsonObject.get("AL API KEY");

    }

}
