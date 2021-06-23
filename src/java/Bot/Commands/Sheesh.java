package Bot.Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Sheesh extends Command {

    public Sheesh() {
        this.name  = "sheesh";
        this.help = "Responsed to  \"sheesh\" with \"SHEEEEES\"";
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply("SHEEESH");
    }
}
