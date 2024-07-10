package phase1;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String nickname;
    private String password;
    private String email;
    private int questionNumber;
    private String questionAnswer;
    private int level;
    private int xp;
    private int hp;
    private int coin;
    private String GameHistory;
    private int upgradeCost;
    private List<Card> cards = new ArrayList<>();
    public static List<User> userList = new ArrayList<>();

    public User(String username, String nickname, String password, String email, int questionNumber, String questionAnswer, int level, int xp, int hp, int coin, String cardString, String gameHistory) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.questionAnswer = questionAnswer;
        this.questionNumber = questionNumber;
        this.level = level;
        this.hp = hp;
        this.xp = xp;
        this.coin = coin;
        this.upgradeCost = 100;
        this.GameHistory = gameHistory;
        this.cards=loadCardsFromString(cardString);
        System.out.println(cards);
        userList.add(this);
    }

    public User(){

    }

    public static void setUserList(List<User> userList) {
        User.userList = userList;
    }

    void addCard(Card card){
        cards.add(card);
    }
    private List<Card> loadCardsFromString(String cardString) {
        List<Card> retur = new ArrayList<>();
        String[] cardsSplit = cardString.split(",");
        for (String string : cardsSplit) {
            if (!string.isEmpty()) {
                String code = string.substring(0, 1);
                int level = Integer.parseInt(string.substring(1, 2));
                Card card = Card.getAcard(code);
                if (card != null) {
                    card.setCardLevel(level);
                    retur.add(card);
                }
            }
        }
        return retur;
    }

    public String getGameHistory() {
        return GameHistory;
    }

    public void setCoin(int coin) {
        this.coin = coin;
        DatabaseHelper.changeCoin(username,coin);
    }

    public String setCardsToString() {
        StringBuilder ret = new StringBuilder();
        for (Card c : this.cards) {
            ret.append(c.getCode()).append(c.getLevel()).append(",");
        }
        return ret.toString();
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public List<Card> getCards() {
        return cards;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getCoin() {
        return coin;
    }

    public int getHp() {
        return hp;
    }

    public int getLevel() {
        return level;
    }

    public int getXp() {
        return xp;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static User getUser(String username){
        for(User u : userList){
            if(u.username.equals(username)){
                return u;
            }
        }
        return null;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void upgradeLevel() {
        this.level += 1;
        xp-=upgradeCost;
        this.upgradeCost = (int)(this.upgradeCost * 1.5);
        int addCoin=level*10+level*(level+1)+5;
        this.coin+=addCoin;
        System.out.println("Congratulations " + this.getNickname() + "! You have leveled up to level " + this.level + "!");
        System.out.println(this.getNickname() + " has been awarded "+addCoin+" coins for leveling up!");
    }
    public void checkUpgrade() {
        if(this.xp>=this.upgradeCost)
        {
            this.upgradeLevel();
        }
    }

    public void upgradeCardList(){
        StringBuilder cardListString = new StringBuilder();
        for(Card c : cards){
            cardListString.append(c.getCode()).append(c.getLevel()).append(",");
        }
        DatabaseHelper.changeUserCards(username, String.valueOf(cardListString));
    }

}
