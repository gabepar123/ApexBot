package Bot.Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class Empty extends Command {

    public Empty(){
        this.name  = "";
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply("Trash");
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
