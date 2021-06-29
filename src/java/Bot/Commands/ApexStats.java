package Bot.Commands;

import Bot.Commands.Stats.PlayerStatHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApexStats extends Command {

    static String defaultURL = "https://api.mozambiquehe.re/bridge?version=5&platform={platform}&player={user}";

    public ApexStats(){
        this.name  = "astats";
        this.aliases = new String[]{"apexstats"};
        this.arguments = "[user] [platform(PC/XBOX/PSN)]";
        //TODO
        //this.help = "Gives "
    }

    @Override
    protected void execute(CommandEvent event) {

        String message = event.getMessage().getContentRaw();
        String content = message.substring(message.indexOf(' ') + 1).toLowerCase().trim();
        String regex = "([a-zA-Z0-9_-]+ (pc|xbox|psn))";
        if (!content.matches(regex)){
            event.reply("Usage: " + this.arguments);
            return;
        }

        String stringURL = getURL(content);
        //TODO check if the username actually exists
        try {
            JSONObject playerStats = getStats(stringURL);


            //TODO remove this pretty JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = gson.toJson(playerStats);
            System.out.println(prettyJson);

            System.out.println(new PlayerStatHandler(playerStats).toString());

            PlayerStatHandler stats = new PlayerStatHandler(playerStats);
            event.reply(stats.getEb().build());


        } catch (Exception e) {
            e.printStackTrace();
            event.reply("Error Finding Player stats, this probably isn't your fault, please try again later");
        }

    }

    //returns URL with the given user and platform
    private String getURL(String content) {
        String[] args = content.split("\\s+");
        String user = args[0];
        String platform = getPlatform(args[1]);


        String stringURL = defaultURL;
        stringURL = stringURL.replace("{platform}", platform);
        stringURL = stringURL.replace("{user}", user);
        return stringURL;
    }

    //Returns JSONObject received from the API
    private JSONObject getStats(String stringURL) throws Exception {
        String APIkey = getAPIKey();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(stringURL))
                .setHeader("Authorization", APIkey)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        return (JSONObject)parser.parse(response.body());
    }

    //Parses platform to conform to API standards
    //ex: "pc" should be origin, "xbox" should be "xbl"
    private String getPlatform(String s){

        if (s.equalsIgnoreCase("pc"))
            return "PC";
        else if (s.equalsIgnoreCase("xbox"))
            return "X1";
        return "PS4";

    }

    //gets api auth key from json file
    private String getAPIKey() throws Exception {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("src/resources/token.json"));
        JSONObject jsonObject = (JSONObject)obj;
        return (String)jsonObject.get("AL API KEY");

    }

    private void createEmbed(JSONObject rawStats){


    }







    /* URL url = null;
            try {
                url = new URL("https://public-api.tracker.gg/v2/apex/standard/profile/psn/Daltoosh");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                String key = "3027fcd8-4ea8-4778-a008-4a733dcd518b";

                connection.setRequestProperty("TRN-Api-Key", key);
                connection.setRequestMethod("GET");


                int status = connection.getResponseCode();
                System.out.println(status);

            } catch (IOException e) {
                e.printStackTrace();
            }

     */
}
