package Bot.Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class RandomTeam extends Command {

    String[] legends = {"Bangalore", "Bloodhound", "Caustic", "Crypto", "Fuse", "Gibraltar", "Horizon", "LIfeline", "Loba",
            "Mirage", "Octane", "Pathfinder", "Rampart", "Revenant", "Valkyrie", "Watton", "Wraith"};

    String[] guns = {"Bocek Bow", "EVA-8", "Kraber", "Peacekeeper", "Prowler", "R-301 Carbine", "R-99", "Spitfire",
            "Charge Rifle", "Flatline", "G7 Scout", "Havoc", "Mastiff", "Sentinel", "Triple Take", "Volt", "Wingman",
            "Alternator", "Devotion", "Hemlok", "Longbow", "L-Star", "RE-45", "30-30 Repeater", "Mozambique", "P2020"};

    public RandomTeam(){
        this.name = "team";
        this.help  = "Chooses random legends and loadouts for you're team.";
    }

    //OOF
    @Override
    protected void execute(CommandEvent event) {
        ArrayList<Integer> legendList = getRandomLegend();
        String l1 = this.legends[legendList.get(0)];
        String l2 = this.legends[legendList.get(1)];
        String l3 = this.legends[legendList.get(2)];

        String[] gunArr = new String[6];
        gunArr[0] = getRandomGun();
        gunArr[1] = getRandomGun();
        while (gunArr[0].equals(gunArr[1])) gunArr[1] = getRandomGun();

        gunArr[2] = getRandomGun();
        gunArr[3] = getRandomGun();
        while (gunArr[2].equals(gunArr[3])) gunArr[3] = getRandomGun();

        gunArr[4] = getRandomGun();
        gunArr[5] = getRandomGun();
        while (gunArr[4].equals(gunArr[5])) gunArr[5] = getRandomGun();

        MessageEmbed e = getEmbed(l1, l2, l3, gunArr);
        event.reply(e);

    }

    private ArrayList<Integer> getRandomLegend() {
        ArrayList<Integer> list = new ArrayList<>();
        Random randomGenerator = new Random();
        while (list.size() < 3) {

            int random = randomGenerator.nextInt(this.legends.length);
            if (!list.contains(random)) {
                list.add(random);
            }
        }
        return list;
    }

    private String getRandomGun(){
        int rnd = new Random().nextInt(this.guns.length);
        return this.guns[rnd];
    }

    private MessageEmbed getEmbed(String l1, String l2, String l3, String[] guns){
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("Your Random Team:");
        eb.addField("", "**Player :one:**", false);
        eb.addField("Legend", l1, true);
        eb.addField("Primary", guns[0], true);
        eb.addField("Secondary", guns[1], true);

        eb.addField("", "**Player :two:**", false);
        eb.addField("Legend", l2, true);
        eb.addField("Primary", guns[2], true);
        eb.addField("Secondary", guns[3], true);

        eb.addField("", "**Player :three:**", false);
        eb.addField("Legend", l3, true);
        eb.addField("Primary", guns[4], true);
        eb.addField("Secondary", guns[5], true);

        eb.setColor(Color.red);
        return eb.build();

    }




}
