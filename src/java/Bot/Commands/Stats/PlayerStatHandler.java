package Bot.Commands.Stats;

import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

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
    private final EmbedBuilder eb;


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

    public EmbedBuilder getEb() {
        return eb;
    }

    private EmbedBuilder setEb() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Stats for " + user);
        eb.addField("Name", user, true);
        eb.addField("Platform", platform, true);
        String onlineEmote = getOnlineEmote();
        eb.addField("Online", onlineEmote, true);
        eb.addField("Level", level, false);
        eb.addField("BattlePass Tier", battlePassLevel, false);
        String rankEmoji = new RankEmoji(rank).getRankEmojiId();
        eb.addField("Rank",rankEmoji + rank, true);
        eb.addField("Selected Legend",selectLegend, true);
        eb.addField("","**Trackers**", false);
        eb.addField(t1.getName(),String.valueOf(t1.getValue()), true);
        eb.addField(t2.getName(),String.valueOf(t2.getValue()), true);
        eb.addField(t3.getName(),String.valueOf(t3.getValue()), true);
        eb.addField("","**Badges**", false);
        eb.setThumbnail(avatarURL);
        eb.addField(badge1,"", false);
        eb.addField(badge2,"", false);
        eb.addField(badge3,"", false);
        //String footerURL = getFooterImageUrl();
        eb.setFooter("Any inaccuracies are most likely due to the limited information that EA gives us on player profiles.", "https://i.imgur.com/PZE9HyU.png");



        eb.setColor(Color.RED);
        return eb;

    }

    private String getFooterImageUrl() throws MalformedURLException {
        File footerImage = new File("attachment://src/resources/apexlogo.jpg");
        String footerURL = footerImage.toURI().toURL().toString();
       // footerURL = footerURL.replace("file", "attachment");
        System.out.println(footerURL);
        return "attachment://src/resources/apexlogo.jpg";
    }

    private String getOnlineEmote() {
        String onlineEmote = ":white_check_mark:";
        if (!isOnline) onlineEmote = ":x:";
        return onlineEmote;
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

    public String getAvatarURL() {
        return avatarURL;
    }

    public String getBadge1() {
        return badge1;
    }

    public String getBadge2() {
        return badge2;
    }

    public String getBadge3() {
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
                ", t1=" + t1 +
                ", t2=" + t2 +
                ", t3=" + t3 +
                '}';
    }
}