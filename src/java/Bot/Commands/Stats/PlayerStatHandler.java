package Bot.Commands.Stats;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Color;

//Puts a players stats into its own class
//the only public method is getEb(), as anything else will probably not do what you want it to do
public class PlayerStatHandler {

    private final String user;
    private final String platform;
    private final String rank;
    private final String level;
    private final String battlePassLevel;
    private final String selectLegend;
    private final JSONObject playerStats;
    private final boolean isOnline;
    private final String avatarURL;
    private final Tracker t1;
    private final Tracker t2;
    private final Tracker t3;
    private final String badge1;
    private final String badge2;
    private final String badge3;
    private final MessageEmbed eb;

    public PlayerStatHandler(JSONObject playerStats)  {

        this.playerStats = playerStats;
        this.user = setUser();
        this.platform = setPlatform();
        this.rank = setRank();
        this.level = setLevel();
        this.selectLegend = setSelectedLegend();
        this.battlePassLevel = setBattlePassLevel();
        this.isOnline = setIsOnline();
        this.avatarURL = setAvatar();
        this.t1 = setTracker(0);
        this.t2 = setTracker(1);
        this.t3 = setTracker(2);
        this.badge1 = setBadge(0);
        this.badge2 = setBadge(1);
        this.badge3 = setBadge(2);
        this.eb = setEb();

    }

    private String setAvatar() {

        JSONObject global = (JSONObject) playerStats.get("global");
        return (String) global.get("avatar");

    }

    private String setBadge(int i) {

        JSONObject legends = (JSONObject) playerStats.get("legends");
        JSONObject selected = (JSONObject) legends.get("selected");
        JSONObject gameInfo = (JSONObject) selected.get("gameInfo");
        JSONArray badgeArr = (JSONArray) gameInfo.get("badges");
        if (badgeArr.size() <= i ){
            return "N/A";
        }

        JSONObject currBadge = (JSONObject) badgeArr.get(i);
        String badgeName = (String) currBadge.get("name");
        if (badgeName == null)
            return "N/A";
        return badgeName;
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

    public MessageEmbed getEb() {
        return eb;
    }

    //creates the embed for the player's stats
    private MessageEmbed setEb() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("Apex Bot", null, "https://i.imgur.com/Cj5QZd8.jpg");
        eb.setTitle("Stats for " + user);
        eb.addField("Name", user, true);
        eb.addField("Platform", platform, true);
        String onlineEmote = getOnlineEmote();
        eb.addField("Online", onlineEmote, true);
        eb.addField("Level", level, true);
        eb.addField("BattlePass Tier", battlePassLevel, true);
        String rankEmoji = new RankEmoji(rank).getRankEmojiId();
        eb.addField("Rank",rankEmoji + rank, true);
        eb.addField("Selected Legend", selectLegend, true);
        eb.addField("","**Trackers**", false);
        eb.addField(t1.getName(),String.valueOf(t1.getValue()), true);
        eb.addField(t2.getName(),String.valueOf(t2.getValue()), true);
        eb.addField(t3.getName(),String.valueOf(t3.getValue()), true);
        eb.addField("","**Badges**", false);
        if (!avatarURL.equals("Not available"))
            eb.setThumbnail(avatarURL);
        eb.addField(badge1,"", false);
        eb.addField(badge2,"", false);
        eb.addField(badge3,"", false);
        eb.setFooter("Any inaccuracies are likely due to the limited information that EA gives us on player profiles.", "https://i.imgur.com/Cj5QZd8.jpg");



        eb.setColor(Color.RED);
        return eb.build();

    }


    private String getOnlineEmote() {
        String onlineEmote = ":white_check_mark:";
        if (!isOnline) onlineEmote = ":x:";
        return onlineEmote;
    }

    private String getUser() {
        return user;
    }

    private String getPlatform() {
        return platform;
    }

    private String getRank() {
        return rank;
    }

    private String getLevel() {
        return level;
    }

    private String getSelectLegend() {
        return selectLegend;
    }

    private boolean isOnline() {
        return isOnline;
    }

    private String getBattlePassLevel() {
        return battlePassLevel;
    }

    private Tracker getT1() {
        return t1;
    }

    private Tracker getT2() {
        return t2;
    }

    private Tracker getT3() {
        return t3;
    }

    private String getAvatarURL() {
        return avatarURL;
    }

    private String getBadge1() {
        return badge1;
    }

    private String getBadge2() {
        return badge2;
    }

    private String getBadge3() {
        return badge3;
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
                ", avatarURL='" + avatarURL + '\'' +
                ", t1=" + t1 +
                ", t2=" + t2 +
                ", t3=" + t3 +
                ", badge1='" + badge1 + '\'' +
                ", badge2='" + badge2 + '\'' +
                ", badge3='" + badge3 + '\'' +
                '}';
    }
}