package phase1;

import java.time.LocalDateTime;

public class MatchDetail {
    private String status;
    private String enemyName;
    private int enemyLevel;
    private int xpChange;
    private int coinChange;
    private LocalDateTime dateTime;


    public MatchDetail(LocalDateTime dateTime, String status, String enemyName, int enemyLevel, int xpChange, int coinChange) {
        this.dateTime = dateTime;
        this.status = status;
        this.enemyName = enemyName;
        this.enemyLevel = enemyLevel;
        this.xpChange = xpChange;
        this.coinChange = coinChange;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }



    public String getStatus() {
        return status;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public int getEnemyLevel() {
        return enemyLevel;
    }

    public int getXpChange() {
        return xpChange;
    }

    public int getCoinChange() {
        return coinChange;
    }
}
