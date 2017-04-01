package poker;
import java.util.ArrayList;

public class RoundOfPoker {

    private int currentBet;
    private ArrayList<PokerPlayer> players;

    public RoundOfPoker(ArrayList<PokerPlayer> players)
    {
        this.players = players;
        this.currentBet = 0;
    }

    public static void main(String[] args) {
        DeckOfCards deck = new DeckOfCards();
        PokerPlayer p1 = new PokerPlayer(deck);
        PokerPlayer p2 = new PokerPlayer(deck);
        ArrayList<PokerPlayer> players = new ArrayList<PokerPlayer>();

        players.add(p1);
        players.add(p2);
        RoundOfPoker round = new RoundOfPoker(players);
        //System.out.println();

    }
}
