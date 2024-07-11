package phase1;
import java.util.ArrayList;

public class Charachter {
    int id;
    ArrayList<Card> cards;

    private String name;

    public static String[] urls = {
            "/resources/Feminist.jpg",
            "/resources/Fascist.jpg",
            "/resources/Communist.jpg",
            "/resources/Nigger.jpg"
            };
    public static String[] names = {
            "Feminist",
            "Fascist",
            "Communist",
            "Nigger"
    };
    Charachter()
    {

    }

    public Charachter(int n){
        this.cards = new ArrayList<>();
        this.name =  names[n];
        this.id = n;
        for(Card card : Card.cards){
            if(card.getCharacter()==n){
                this.cards.add(card);
            }
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString()
    {
        if(id==1)
        {
            return "Feminist";
        }
        if(id==2)
        {
            return "Fascist";
        }
        if(id==3)
        {
            return "Communist";
        }
        return "Nigger";
    }
}
