package phase1;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class Card {
    private String code;
    private String name;
    private int cardAttackDefence;
    private int duration;
    private int playerDamage;
    private int upgradeLevel;
    private int upgradeCost;
    private int level;
    private int character;
    private String type; //a for normal and b for spells
    public String url;
    public static List<Card> cards = new ArrayList<>();

    public String getCode() {
        return code;
    }
    Card(Card card)
    {
        this.code = card.code;
        this.name = card.name;
        this.level = 1;
        this.cardAttackDefence = card.cardAttackDefence;
        this.duration = card.duration;
        this.playerDamage = card.playerDamage;
        this.upgradeLevel = card.upgradeLevel;
        this.upgradeCost = card.upgradeCost;
        this.character = card.character;
        this.type = card.type;
    }

    public void setCode(String code) {
        this.code = code;
    }

    Card(String code , String name, int cardDefence, int playerDamage, int duration, int upgradeLevel, int upgradeCost, int character, String type){
        this.code = code;
        this.name = name;
        this.level = 1;
        this.cardAttackDefence = cardDefence;
        this.duration = duration;
        this.playerDamage = playerDamage;
        this.upgradeLevel = upgradeLevel;
        this.upgradeCost = upgradeCost;
        this.character = character;
        this.type = type;
        this.url = "/resources/"+type+","+name+".png";
        cards.add(this);
    }
    public static Card getAcard(String code){
        for(Card c : cards){
            if(code.equals(c.code)){
                return c;
            }
        }
        return null;
    }

    public void setCardLevel(int level)
    {
        this.level = level;
        this.playerDamage += (level-1)*2;
        this.cardAttackDefence += (level-1)*duration;
    }

    @Override
    public String toString() {
        //return Character.toString(code) + tkrr;
        return "Card{" + level +
                "character='" + character + '\'' +
                ", name='" + name + '\''+
                ", cardDefence=" + cardAttackDefence +
                ", duration=" + duration +
                ", playerDamage=" + playerDamage +
                ", upgradeLevel=" + upgradeLevel +
                ", upgradeCost=" + upgradeCost +
                '}';
    }

    public String getType() {
        return type;
    }

    public String toStringCarePackage() {
        return "[name='" + name +
                ", cardDefence=" + cardAttackDefence +
                ", duration=" + duration +
                ", playerDamage=" + playerDamage +
                ']';
    }


    public int getLevel() {
        return level;
    }
    public void levelUp(){
        this.level++;
    }
    public int getId() {
        return character;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCardAttackDefence() {
        return cardAttackDefence;
    }
    public void setCardAttackDefence(int cardDefence) {
        if(cardDefence >= 10 && cardDefence <= 100) {
            this.cardAttackDefence = cardDefence;
        } else {
            throw new IllegalArgumentException("Card defence must be between 10 and 100.");
        }
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        if(duration >= 1 && duration <= 5) {
            this.duration = duration;
        } else {
            throw new IllegalArgumentException("Duration must be between 1 and 5.");
        }
    }
    public int getPlayerDamage() {
        return playerDamage;
    }
    public void setPlayerDamage(int playerDamage) {
        if(playerDamage >= 10 && playerDamage <= 50) {
            this.playerDamage = playerDamage;
        } else {
            throw new IllegalArgumentException("Player damage must be between 10 and 50.");
        }
    }
    public int getUpgradeLevel() {
        return upgradeLevel;
    }
    public void setUpgradeLevel(int upgradeLevel) {
        this.upgradeLevel = upgradeLevel;
    }
    public int getUpgradeCost() {
        return upgradeCost;
    }
    public void setUpgradeCost(int upgradeCost) {
        this.upgradeCost = upgradeCost;
    }
    public void upgradeCard() {
        this.level++;
        this.upgradeLevel += 1;
        this.upgradeCost = (int)(this.upgradeCost * 1.25);
        this.cardAttackDefence += 2;
        this.playerDamage += duration;
    }


}