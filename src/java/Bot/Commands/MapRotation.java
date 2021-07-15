package Bot.Commands;

import Bot.ApexBot;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.Color;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MapRotation extends Command {

    String APIurl = "https://api.mozambiquehe.re/maprotation?version=2&";
    String arenaBadge = "<:impress_me:863189316137648160>";
    String BRBadge = "<:apex:863189316020863006>";

    public MapRotation(){
        this.name = "map";
        this.aliases = new String[]{"maps", "maprotation", "apexmaps", "apexmap", "m"};
        this.help = "Displays the current map rotation";
    }

    @Override
    protected void execute(CommandEvent event) {

        try {
            JSONObject mapInfo = getMapRotation();
            event.reply(getMapEmbed(mapInfo));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            MessageEmbed embed = createEmbed(":x:Error fetching map rotation, please try again later", ":frowning:");
            event.reply(embed);
        }
    }

    //requests the map rotation from the api
    private JSONObject getMapRotation() throws Exception {

        String APIkey = ApexBot.getAPIKey();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(APIurl))
                .setHeader("Authorization", APIkey)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();

        if (statusCode >= 400) throw new Exception(String.valueOf(statusCode));

        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(response.body());
    }

    private MessageEmbed getMapEmbed(JSONObject mapInfo){



        JSONObject arena = (JSONObject) mapInfo.get("arenas");
        JSONObject currArena = (JSONObject) arena.get("current");
        String currArenaMap = (String) currArena.get("map");
        String arenaTimeLeft = (String) currArena.get("remainingTimer");

        JSONObject nextArena = (JSONObject) arena.get("next");
        String nextArenaMap = (String) nextArena.get("map");

        JSONObject BR = (JSONObject) mapInfo.get("battle_royale");
        JSONObject currBR = (JSONObject) BR.get("current");
        String currBRMap = (String) currBR.get("map");
        String BRTimeLeft = (String) currBR.get("remainingTimer");

        JSONObject nextBR = (JSONObject) BR.get("next");
        String nextBRMap = (String) nextBR.get("map");

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Current Map Rotation");
        eb.addField("", BRBadge + " **Battle Royale**", false);
        eb.addField("Current Map", currBRMap, true);
        eb.addField("Next Map", nextBRMap, true);
        eb.addField("Time Remaining", BRTimeLeft, true);


        eb.addField("", arenaBadge + " **Arenas**", false);
        eb.addField("Current Map", currArenaMap, true);
        eb.addField("Next Map", nextArenaMap, true);
        eb.addField("Time Remaining", arenaTimeLeft, true);

        eb.setColor(Color.red);
        return eb.build();


    }

    private MessageEmbed createEmbed(String title, String body){

        EmbedBuilder eb = new EmbedBuilder();
        eb.addField(title, body, true);
        eb.setColor(Color.red);
        return eb.build();
    }


}
