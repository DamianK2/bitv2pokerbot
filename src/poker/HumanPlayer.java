package poker;

public class HumanPlayer extends PokerPlayer {

    public HumanPlayer(DeckOfCards deck, String name)
    {
        super(deck);
        this.name = name;
    }
}
