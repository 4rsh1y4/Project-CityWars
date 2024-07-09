package phase1;
import java.util.ArrayList;

public class Charachter {
    int id;
    ArrayList<Card> cards;
    Charachter()
    {

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
