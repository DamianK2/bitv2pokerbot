package poker;
import java.util.Scanner;

public class HumanPlayer extends PokerPlayer {

    public HumanPlayer(DeckOfCards deck, String name) {
        super(deck);
        this.name = name;
        this.isHuman = true;
    }

    /*public boolean askFold()
    {
        Scanner input = new Scanner(System.in);
        String inputResponse = input.nextLine();

        return parser.convertResponse(inputResponse);
    }*/

    public int discard()
    {
        Scanner input = new Scanner(System.in);
        String discardCards = input.nextLine();
        // !!! TEMPORARY SOLUTION
        int card = Integer.parseInt(discardCards);
        int[] arr = {-1, -1, -1};
        arr[0] = card;

        this.hand.discard(arr);

        return 1;
    }

}
