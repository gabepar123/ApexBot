package Bot.Commands;

import Bot.ApexBot;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

import static Bot.ApexBot.jda;

public class Ping extends Command {

    public Ping() {
        this.name = "ping";
        this.help = "Displays the Bot's Ping.";
    }

    @Override
    protected void execute(CommandEvent event) {
        jda.getRestPing().queue( (time) ->
                event.reply(getEmbed(time))
        );
    }

    private MessageEmbed getEmbed(Long time){

        EmbedBuilder eb = new EmbedBuilder();
        eb.addField("Apex Bot Ping", ":incoming_envelope: " + time + " ms", true);
        eb.setColor(Color.cyan);
        return eb.build();

    }

}
