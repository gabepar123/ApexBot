package Bot.Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.Color;

public class Help extends Command {

    public Help() {
        this.name = "help";
        this.aliases = new String[]{"commands", "command", "helpme", "h"};
        this.help = "Displays all commands";
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply(getEmbed());
    }

    private MessageEmbed getEmbed(){
        EmbedBuilder eb = new EmbedBuilder();

        //eb.setTitle("Apex Bot's Commands");
        eb.setAuthor("Apex Bot", null, "https://i.imgur.com/Cj5QZd8.jpg");
        eb.addField("Apex Bot's Commands", """
                `a?stats [user] [platform(PC/XBOX/PSN)]` - Displays a user's Apex Stats.\n
                `a?news` - Displays the latest news in Apex!\n
                `a?map` - Displays the current map rotation\n
                `a?random` - Chooses a random legend to play as.\n
                `a?loadout` - Chooses a random loadout.\n
                `a?team` - Chooses random legends and loadouts for you're team.\n
                `a?ping` - Displays the bot's ping.""", true);
        eb.setColor(Color.red);
        eb.setFooter("Contact Retro#4670 if you have any issues! :)", "https://i.imgur.com/Cj5QZd8.jpg");
        return eb.build();
    }
}
