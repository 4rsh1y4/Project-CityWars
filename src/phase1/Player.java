package phase1;
import java.util.*;

public class Player {
    private User user;
    int hp;
    private boolean makhfi = false;
    public boolean[] ruined = new boolean[21];

    void firstBoard() {
        for (int i = 0; i < ruined.length; i++) {
            ruined[i] = false;

        }
        Arrays.fill(board, null);
    }
    Charachter character;
    Card[] board = new Card[21];
    ArrayList<Card> hand = new ArrayList<>();
    int round;
    public int corruptedFinder()
    {
        for(int i = 0;i<21;i++)
        {
            if(corrupted[i])
                return i;
        }
        return 0;
    }
    public boolean isMakhfi() {
        return makhfi;
    }

    public void setMakhfi(boolean makhfi) {
        this.makhfi = makhfi;
    }



    //ArrayList<Integer> corrupted = new ArrayList<>();
    public boolean[] corrupted = new boolean[21];


    public void setUser(User user) {
        this.user = user;

    }


    public Charachter getCharacter() {
        return character;
    }

    public void setCharacter(Charachter character) {
        this.character = character;
    }

    public Card[] getBoard() {
        return board;
    }

    public void setBoard(Card[] board) {
        this.board = board;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }





    public Player(User user) {
        this.user = user;
        this.round = 4;
        this.setHand((ArrayList<Card>) user.getCards());
        for (int i = 0; i < corrupted.length; i++) {
            corrupted[i] = false;

        }
        firstBoard();
        Random random = new Random();
        corrupted[(random.nextInt(21))]=true;
        this.hp = this.user.getHp();
    }

    public void resetRound() {
        this.round = 4;

    }
    public boolean putCard(Card card, int startingBlock) {
        for(int i = 0; i < card.getDuration();i++)
        {
            if(corrupted[i] || ruined[i] || board[i]!=null)
                return false;
        }
        for(int i = 0; i < card.getDuration();i++)
        {
            board[i]=card;
        }
        return true;

    }
    public User getUser() {
        return user;
    }

    public String getNickname() {
        return user.getNickname();
    }

    public int getLevel() {
        return user.getLevel();
    }

    public int getHp() {
        return user.getHp();
    }
    public void setHp(int t)
    {
        this.hp = t;
    }

    public Card cardChooser() {
        List<Card> cards = this.user.getCards();
        if (cards == null || cards.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int index = random.nextInt(cards.size());
        return cards.get(index);
    }
    public void addToHand(Card card)
    {
        hand.add(card);
    }


    public void toStringHand()
    {
        if(!makhfi)
        {
            for(int i = 0 ; i < hand.size(); i++)
            {
                System.out.println((i+1)+": "+hand.get(i).toString());
            }
        }
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public String boardToString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            if (i > 0) {
                sb.append(" | ");
            }
            if (corrupted[i]) {
                sb.append("-");
            } else if (board[i] == null) {
                sb.append("null");
            }
            else if(ruined[i]) {
                sb.append("X");
            }
            else {
                sb.append(board[i].getPlayerDamage()+" "+board[i].getName()+" "+board[i].getCardAttackDefence());
            }
        }
        return sb.toString();
    }

    public int getDamage()
    {
        int dam = 0 ;
        for(int i = 0 ; i < 21 ; i++)
        {
            if(!corrupted[i])
            {
                if(!ruined[i])
                    dam+=board[i].getPlayerDamage();
            }
        }
        return dam;
    }
}

