package Bot.Commands;

import Bot.ApexBot;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.components.Button;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.Color;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class ApexNews extends Command implements EventListener {

    String APIurl = "https://api.mozambiquehe.re/news?lang=en-us";
    String leftArrow = "U+2B05";
    String rightArrow = "U+27A1";
    JSONArray jsonNewsArray;
    int maxNewsLength;

    public ApexNews() throws Exception {
        this.name  = "news";
        this.help = "Displays the latest news in Apex!";
        this.jsonNewsArray = getNews();
        this.maxNewsLength = jsonNewsArray.size();
    }

    @Override
    protected void execute(CommandEvent event) {

        MessageEmbed embed  = createEmbed("Fetching apex news, please wait...","");
        Message response = event.getChannel().sendMessageEmbeds(embed).complete();

        try {
            jsonNewsArray = getNews();
            maxNewsLength = jsonNewsArray.size();

            Button back = Button.primary("apexnews:index:0", Emoji.fromMarkdown(leftArrow)).asDisabled();
            Button forward = Button.primary("apexnews:index:2", Emoji.fromMarkdown(rightArrow));
            response.editMessageEmbeds(getNewsEmbed(1)).setActionRow(back,forward).complete();


        } catch (Exception e) {
            System.out.println(e.getMessage());
            embed  = createEmbed(":x:Error fetching Apex News, please try again later", ":frowning:");
            response.editMessageEmbeds(embed).queue();
        }

    }

    @Override
    public void onEvent(@NotNull GenericEvent genericEvent) {
        if (genericEvent instanceof ButtonClickEvent event) {
            if (!event.getComponentId().contains("apexnews:index:")) return;
            int index = getIndex(event.getComponentId());
            ArrayList<Button> buttonArrayList = getButtons(index);
            event.editMessageEmbeds(getNewsEmbed(index)).setActionRow(buttonArrayList).queue();

        }
    }
    //creates a list of buttons depending on which index of the news they are on
    private ArrayList<Button> getButtons(int index) {
        boolean backDisabled = false;
        boolean frontDisabled = false;
        int backIndex = index - 1;
        int frontIndex = index + 1;
        if (backIndex <= 0) {
            backIndex = 0;
            backDisabled = true;
        }
        if (frontIndex >= maxNewsLength){
            frontIndex = maxNewsLength;
            frontDisabled = true;
        }
        Button back = Button.primary("apexnews:index:" + backIndex, Emoji.fromMarkdown(leftArrow)).withDisabled(backDisabled);
        Button forward = Button.primary("apexnews:index:" + frontIndex, Emoji.fromMarkdown(rightArrow)).withDisabled(frontDisabled);

        ArrayList<Button> buttonArrayList  = new ArrayList<>();
        buttonArrayList.add(back);
        buttonArrayList.add(forward);
        return buttonArrayList;
    }

    //returns an int index from the button's ID (aka the last few characters after the final ':', ex apexnews:index:12
    private int getIndex(String ID){
        int index = Integer.parseInt(ID.substring(ID.lastIndexOf(':') + 1));
        if (index <= 0) return 1;
        return index;
    }

    //requests news from the api
    private JSONArray getNews() throws Exception {

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
        return (JSONArray)parser.parse(response.body());
    }

    //creates the embed that displays the news, and adds the buttons
    private MessageEmbed getNewsEmbed(int i){
        EmbedBuilder eb = new EmbedBuilder();

        JSONObject index = (JSONObject) jsonNewsArray.get(i);
        String title = (String) index.get("title");
        String desc = (String) index.get("short_desc");
        String img = (String) index.get("img");
        String link = (String) index.get("link");

        eb.addField("", desc, true);
        eb.setTitle(title, link);
        eb.setImage(img);
        eb.setColor(Color.blue);
        eb.setFooter(i + "/" + (maxNewsLength - 1));
        return eb.build();
    }

    private MessageEmbed createEmbed(String title, String body){

        EmbedBuilder eb = new EmbedBuilder();
        eb.addField(title, body, true);
        eb.setColor(Color.blue);
        return eb.build();
    }



}
