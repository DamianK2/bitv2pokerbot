package poker;
import java.util.ArrayList;
import java.util.Scanner;

public class RoundOfPoker {

    private int currentBet;
    private ArrayList<PokerPlayer> players;

    public RoundOfPoker(ArrayList<PokerPlayer> players)
    {
        this.players = players;
        this.currentBet = 0;
    }

    public void play()
    {
        DeckOfCards deck = new DeckOfCards();
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to the Automated Poker Machine ...");
        System.out.print("What is your name?");
        String name = input.nextLine();
        System.out.println("Let's play POKER ...");

        // MAKE HUMAN PLAYER, PASS A NAME

        // START GAME
        System.out.println("New Deal:");

        for (PokerPlayer player : this.players)
            System.out.println(player.getName() + " has " + player.getCoinsBalance() + " coins in the bank");

        // CHECK IF ANY PLAYER CAN OPEN
        boolean canOpen = false;
        for (PokerPlayer player : this.players)
            if (player.askOpenBet()) {
                System.out.println(player.getName() + " says: I can open");
                canOpen = true;
            }
            else
                System.out.println(player.getName() + " says: I cannot open");

        // TODO CHECK IF GAME OPENED
        System.out.println("You have been dealt the following hand:");
        // PRINT THE TYPE OF HAND YOU HAVE
        

    }

    public static void main(String[] args) {
        DeckOfCards deck = new DeckOfCards();
        PokerPlayer p1 = new PokerPlayer(deck, "Bananaman");
        PokerPlayer p2 = new PokerPlayer(deck, "Appleboy");
        ArrayList<PokerPlayer> players = new ArrayList<PokerPlayer>();

        players.add(p1);
        players.add(p2);
        RoundOfPoker round = new RoundOfPoker(players);

        //System.out.println(round.players.get(0).name);

        round.play();

    }
}
