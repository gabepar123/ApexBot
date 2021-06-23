package Bot.Commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class ApexStats extends Command {

    public ApexStats(){
        this.name  = "astats";
        this.aliases = new String[]{"apexstats"};
        //TODO
        //this.help = "Gives "
    }

    @Override
    protected void execute(CommandEvent event) {

        String message = event.getMessage().getContentRaw();
        String content = message.substring(message.indexOf(' ') + 1).toLowerCase().trim();
        String regex = "([a-zA-Z0-9_-]+ (pc|xbox|psn))";
        if (!content.matches(regex)){
            event.reply("Usage: `astats [User] [PC/XBOX/PSN]`");
            return;
        }
        
        String[] args = content.split("\\s+");
        String user = args[0];
        String platform = getPlatform(args[1]).toUpperCase();


        String stringURL = "https://api.mozambiquehe.re/bridge?version=5&platform={platform}&player={user}";
        stringURL = stringURL.replace("{platform}", platform);
        stringURL = stringURL.replace("{user}", user);

        try {
            //TODO hide key in json file
            String key = "3027fcd8-4ea8-4778-a008-4a733dcd518b";
            String ALkey = "BB5dhmB8Pn3UlqPiavGr";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(stringURL))
                    .setHeader("Authorization", ALkey)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.body());


            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJson = gson.toJson(json);
            System.out.println(prettyJson);


        } catch (InterruptedException | IOException | ParseException e) {
            e.printStackTrace();
            event.reply("Error Finding Player stats, this probably isn't your fault, please try again later");
        }

    }

    //Parses platform to conform to API standards
    //ex: "pc" should be origin, "xbox" should be "xbl"
    private String getPlatform(String s){

        s = s.toLowerCase();
        if (s.equals("pc"))
            return "pc";
        else if (s.equals("xbox"))
            return "x1";
        return "ps4";

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
