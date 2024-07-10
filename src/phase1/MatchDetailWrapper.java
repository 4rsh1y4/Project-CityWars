package phase1;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MatchDetailWrapper {
    private MatchDetail matchDetail;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public MatchDetailWrapper(MatchDetail matchDetail) {
        this.matchDetail = matchDetail;
    }

    public String getDate() {
        return matchDetail.getDateTime().format(dateFormatter);
    }

    public String getTime() {
        return matchDetail.getDateTime().format(timeFormatter);
    }

    public String getStatus() {
        return matchDetail.getStatus();
    }

    public String getEnemyName() {
        return matchDetail.getEnemyName();
    }

    public int getEnemyLevel() {
        return matchDetail.getEnemyLevel();
    }

    public int getXpChange() {
        return matchDetail.getXpChange();
    }

    public int getCoinChange() {
        return matchDetail.getCoinChange();
    }
}
