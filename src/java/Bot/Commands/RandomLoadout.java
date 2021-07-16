package Bot.Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;
import java.util.Random;

public class RandomLoadout extends Command {

    String[] guns = {"Bocek Bow", "EVA-8", "Kraber", "Peacekeeper", "Prowler", "R-301 Carbine", "R-99", "Spitfire",
    "Charge Rifle", "Flatline", "G7 Scout", "Havoc", "Mastiff", "Sentinel", "Triple Take", "Volt", "Wingman",
    "Alternator", "Devotion", "Hemlok", "Longbow", "L-Star", "RE-45", "30-30 Repeater", "Mozambique", "P2020"};

    public RandomLoadout(){
        this.name = "loadout";
        this.help  = "Chooses a random loadout.";
    }

    @Override
    protected void execute(CommandEvent event) {
        String primary = getRandomGun();
        String secondary = getRandomGun();
        while (primary.equals(secondary)) secondary = getRandomGun();
        MessageEmbed e = getEmbed(primary, secondary);
        event.reply(e);
    }

    private String getRandomGun(){
        int rnd = new Random().nextInt(this.guns.length);
        return this.guns[rnd];
    }

    private MessageEmbed getEmbed(String primary, String secondary){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(":gun: Random Loadout:");
        eb.addField("Primary", primary, false);
        eb.addField("Secondary", secondary, false);
        eb.setFooter("Enjoy!");
        eb.setColor(Color.red);
        return eb.build();

    }


}
