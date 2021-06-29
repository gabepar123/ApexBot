package Bot.Commands.Stats;

import net.dv8tion.jda.api.EmbedBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;

public class PlayerStatHandler {

    private final String user;
    private final String platform;
    private final String rank;
    private final String level;
    private final String battlePassLevel;
    private final String selectLegend;
    private final JSONObject playerStats;
    private final boolean isOnline;
    private final Tracker t1;
    private final Tracker t2;
    private final Tracker t3;
    private final EmbedBuilder eb;


    public PlayerStatHandler(JSONObject playerStats) {

        this.playerStats = playerStats;
        this.user = setUser();
        this.platform = setPlatform();
        this.rank = setRank();
        this.level = setLevel();
        this.selectLegend = setSelectedLegend();
        this.battlePassLevel = setBattlePassLevel();
        this.isOnline = setIsOnline();
        this.t1 = setTracker(0);
        this.t2 = setTracker(1);
        this.t3 = setTracker(2);
        this.eb = setEb();

    }

    private boolean setIsOnline() {

        return ((long) ((JSONObject) playerStats.get("realtime")).get("isOnline")) != 0;

    }

    private String setSelectedLegend() {

        return (String) ((JSONObject) playerStats.get("realtime")).get("selectedLegend");

    }

    private String setBattlePassLevel() {

        JSONObject global = (JSONObject) playerStats.get("global");
        JSONObject bp = (JSONObject) global.get("battlepass");
        String bpLevel = (String) bp.get("level");
        if (bpLevel.equals("-1")) return "0";
        return bpLevel;
    }

    private String setLevel() {

        long level = (long) ((JSONObject) playerStats.get("global")).get("level");
        if (level > 500) return "500";
        return String.valueOf(level);

    }


    private String setUser() {

        return (String) ((JSONObject) playerStats.get("global")).get("name");

    }

    private String setPlatform() {

        return (String) ((JSONObject) playerStats.get("global")).get("platform");

    }

    private String setRank() {

        JSONObject global = (JSONObject) playerStats.get("global");
        JSONObject rank = (JSONObject) global.get("rank");
        String rankName = (String) rank.get("rankName");
        Long rankDiv = (Long) rank.get("rankDiv");

        String fullRank = rankName + " " + rankDiv;
        if (fullRank.contains("Apex Predator"))
            fullRank = "Apex Predator";
        else if (fullRank.contains("Master"))
            fullRank = "Master";

        return fullRank;

    }

    private Tracker setTracker(int i){

        JSONObject legends = (JSONObject) playerStats.get("legends");
        JSONObject selected = (JSONObject) legends.get("selected");
        JSONArray data = (JSONArray) selected.get("data");
        if (data.size() <= i ){
            return new Tracker();
        }
        JSONObject trackerArr = (JSONObject) data.get(i);
        String name = (String) trackerArr.get("name");
        long value = (long) trackerArr.get("value");
        return new Tracker(name, value);
    }

    public EmbedBuilder getEb() {
        return eb;
    }

    private EmbedBuilder setEb() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Stats for " + user);
        eb.addField("Name", user, true);
        eb.addField("Platform", platform, true);
        eb.addField("Level", level, false);
        eb.addField("BattlePass Tier", battlePassLevel, false);
        String rankEmoji = new RankEmoji(rank).getRankEmojiId();
        eb.addField("Rank",rankEmoji + rank, true);
        eb.addField("Selected Legend",selectLegend, true);
        eb.addField("Trackers","\u200b", false);
        eb.addField(t1.getName(),String.valueOf(t1.getValue()), true);
        eb.addField(t2.getName(),String.valueOf(t2.getValue()), true);
        eb.addField(t3.getName(),String.valueOf(t3.getValue()), true);



        eb.setColor(Color.RED);
        return eb;

    }

    public String getUser() {
        return user;
    }

    public String getPlatform() {
        return platform;
    }

    public String getRank() {
        return rank;
    }

    public String getLevel() {
        return level;
    }

    public String getSelectLegend() {
        return selectLegend;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getBattlePassLevel() {
        return battlePassLevel;
    }

    public Tracker getT1() {
        return t1;
    }

    public Tracker getT2() {
        return t2;
    }

    public Tracker getT3() {
        return t3;
    }

    @Override
    public String toString() {
        return "PlayerStatHandler{" +
                "user='" + user + '\'' +
                ", platform='" + platform + '\'' +
                ", rank='" + rank + '\'' +
                ", level='" + level + '\'' +
                ", battlePassLevel='" + battlePassLevel + '\'' +
                ", selectLegend='" + selectLegend + '\'' +
                ", isOnline=" + isOnline +
                ", t1=" + t1 +
                ", t2=" + t2 +
                ", t3=" + t3 +
                '}';
    }
}