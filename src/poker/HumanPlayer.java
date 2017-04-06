package poker;
import java.util.Scanner;

public class HumanPlayer extends PokerPlayer {

    private Scanner scanner;
    private Parser parser;

    public HumanPlayer(DeckOfCards deck, String name) {
        super(deck);
        this.name = name;
        this.isHuman = true;
        this.scanner = new Scanner(System.in);
        this.parser = new Parser();
    }

    public int askDiscard()
    {
        String discardCards = this.scanner.nextLine();
        int[] cards = this.parser.convertDiscards(discardCards);
       
        int counter = 0;
        for (int element : cards)
            if (element != -1)
                counter++;

        this.hand.discard(cards);

        return counter;
    }
    
    public boolean askFold() {
    	return this.getResponse();
    }
 
    public boolean askOpenBet() {
    	return this.getResponse();
	}
    
    public boolean askRaiseBet() {
    	return this.getResponse();
	}
    
    public boolean getResponse() {
    	String inputResponse = this.scanner.nextLine();

        return this.parser.convertResponse(inputResponse);
    }

}
