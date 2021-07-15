package Bot.Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.util.Random;

public class RandomLegend extends Command {

    String[] legends = {"Bangalore", "Bloodhound", "Caustic", "Crypto", "Fuse", "Gibraltar", "Horizon", "LIfeline", "Loba",
                                    "Mirage", "Octane", "Pathfinder", "Rampart", "Revenant", "Valkyrie", "Wattons", "Wraith"};

    String img = "https://api.mozambiquehe.re/assets/icons/{legend}.png";

    public RandomLegend(){
        this.name = "random";
        this.help = "Chooses a random legend to play as.";
    }

    @Override
    protected void execute(CommandEvent event) {
        String randomLegend = getRandomLegend();
        String currImg = getImgString(randomLegend);
        MessageEmbed e = getEmbed(randomLegend, currImg);
        event.reply(e);

    }

    @NotNull
    private String getImgString(String randomLegend) {
        return img.replace("{legend}", randomLegend).toLowerCase();
    }

    private String getRandomLegend(){
        int rnd = new Random().nextInt(this.legends.length);
        return this.legends[rnd];
    }

    private MessageEmbed getEmbed(String legend, String img){
        EmbedBuilder eb = new EmbedBuilder();

        eb.addField("Your legend is...", legend + "!", true);
        eb.setThumbnail(img);
        eb.setColor(Color.red);
        return eb.build();

    }

}
