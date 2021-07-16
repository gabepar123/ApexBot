package Bot.Commands;

import Bot.ApexBot;
import Bot.Commands.Stats.PlayerStatHandler;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.Color;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApexStats extends Command {

    static String defaultURL = "https://api.mozambiquehe.re/bridge?version=5&platform={platform}&player={user}";

    public ApexStats(){
        this.name  = "stats";
        this.aliases = new String[]{"apexstats", "stats", "as", "astats", "s"};
        this.arguments = "[user] [platform(PC/XBOX/PSN)]";
        this.help = "Displays a user's Apex Stats.";
    }

    @Override
    protected void execute(CommandEvent event) {

        String message = event.getMessage().getContentRaw();
        String content = message.substring(message.indexOf(' ') + 1).toLowerCase().trim();
        String regex = "([ a-zA-Z0-9_-]+ (pc|xbox|psn))";
        MessageEmbed embed;
        if (!content.matches(regex)){
            embed = createEmbed("Invalid usage!", "**Usage:** [user] [platform(PC/XBOX/PSN)]\n**Ex:** a!stats Rogue_NRG PC");
            event.getMessage().replyEmbeds(embed).queue();
            return;
        }

        embed = createEmbed("Fetching account stats, please wait...", "");
        Message response = event.getMessage().replyEmbeds(embed).complete();

        String stringURL = getURL(content);
        //try to get the players stats
        try {
            JSONObject playerStats = getStats(stringURL);

            PlayerStatHandler stats = new PlayerStatHandler(playerStats);

            response.editMessageEmbeds(stats.getEb()).queue();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            int error = Integer.parseInt(e.getMessage());
            if (error >= 400 & error < 500) {
                embed = createEmbed(":x:Account not found!",
                        "If you are attempting to find a PC player's account, make sure you are using the ORIGIN username, not STEAM.");
            }
            else if (error >= 500){
                embed = createEmbed(":x:Error Finding Player stats, this probably isn't your fault, please try again later", ":frowning:");
            }
            response.editMessageEmbeds(embed).queue();
        }

    }

    //returns URL with the given user and platform
    private String getURL(String content) {
        int index = content.lastIndexOf(" ");
        String user = content.substring(0,index);
        if (user.contains(" ")) user = user.replace(" ", "%20");
        String tempPlatform = content.substring(index + 1);
        String platform = getPlatform(tempPlatform);

        String stringURL = defaultURL;
        stringURL = stringURL.replace("{platform}", platform);
        stringURL = stringURL.replace("{user}", user);
        return stringURL;
    }

    //Returns JSONObject received from the API
    private JSONObject getStats(String stringURL) throws Exception {

        String APIkey = ApexBot.getAPIKey();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(stringURL))
                .setHeader("Authorization", APIkey)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();

        if (statusCode >= 400) throw new Exception(String.valueOf(statusCode));

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

    //returns an  embed with given title and body
    private MessageEmbed createEmbed(String title, String body){

        EmbedBuilder eb = new EmbedBuilder();
        eb.addField(title, body, true);
        eb.setColor(Color.RED);
        return eb.build();
    }

}
