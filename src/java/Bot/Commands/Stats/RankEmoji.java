package Bot.Commands.Stats;

public class RankEmoji {

    private String rankEmojiId;

    public RankEmoji(String rank){

        this.rankEmojiId = setRankEmojiId(rank);

    }
    public String getRankEmojiId() {
        return rankEmojiId;
    }

    private String setRankEmojiId(String rank) {
        this.rankEmojiId = rankEmojiId;

        return switch (rank) {
            case "Bronze 1" -> "<:bronze1:858922286220312576>";
            case "Bronze 2" -> "<:bronze2:858922286212186142>";
            case "Bronze 3" -> "<:bronze3:858922286294630400>";
            case "Bronze 4" -> "<:bronze4:858922286244954122>";
            case "Silver 1" -> "<:silver1:858922288833495070>";
            case "Silver 2" -> "<:silver2:858922286365802517>";
            case "Silver 3" -> "<:silver3:858922286299742218>";
            case "Silver 4" -> "<:silver4:858922285976911893>";
            case "Gold 1" -> "<:gold1:858922286254260234>";
            case "Gold 2" -> "<:gold2:858922286265794600>";
            case "Gold 3" -> "<:gold3:858922286135902219>";
            case "Gold 4" -> "<:gold4:858922286311276584>";
            case "Platinum 1" -> "<:platinum1:858922285854752789>";
            case "Platinum 2" -> "<:platinum2:858922286236696576>";
            case "Platinum 3" -> "<:platinum3:858922285796818955>";
            case "Platinum 4" -> "<:platinum4:858922286295678976>";
            case "Diamond 1" -> "<:diamond1:858922286223458315>";
            case "Diamond 2" -> "<:diamond2:858922286307868682>";
            case "Diamond 3" -> "<:diamond3:858922286240497675>";
            case "Diamond 4" -> "<:diamond4:858922286249279528>";
            case "Master" -> "<:master:858922286710259722>";
            case "Apex Predator" -> "<:apexpredator:858922286064467999>";
            default -> ":question:";
        };
    }
}
