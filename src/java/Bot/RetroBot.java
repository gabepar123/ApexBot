package Bot;

import Bot.Commands.ApexStats;
import Bot.Commands.Sheesh;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class RetroBot {

    public static JDA jda;
    public static CommandClientBuilder builder;

    public RetroBot() {

    }

    public static void main(String[] args) throws Exception {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("src/resources/token.json"));
        JSONObject jsonObject = (JSONObject)obj;
        String token = (String)jsonObject.get("token");
        String ownerId = (String)jsonObject.get("owner ID");

        jda = JDABuilder.createDefault(token).build();


        makeCommandBuilder(ownerId);
        addCommands();

        CommandClient client = builder.build();
        jda.addEventListener(client);

    }

    private static void makeCommandBuilder(String ownerId) {
        builder = new CommandClientBuilder();
        builder.setOwnerId(ownerId);
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.setHelpWord("halpme");
        builder.setPrefix(">>");
    }

    private static void addCommands() {
        builder.addCommand(new Sheesh());
        builder.addCommand(new ApexStats());

    }
}
