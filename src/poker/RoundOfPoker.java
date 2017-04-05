package poker;
import java.util.ArrayList;
import java.util.Scanner;

public class RoundOfPoker {

    private int currentBet;
    private ArrayList<PokerPlayer> players;

    public RoundOfPoker(ArrayList<PokerPlayer> players, DeckOfCards deck)
    {
        this.players = players;
        this.currentBet = 0;
    }

    public void play()
    {
        Scanner input = new Scanner(System.in);

        // START ROUND
        System.out.println("New Deal:");

        for (PokerPlayer player : this.players)
            System.out.println("> " + player.getName() + " has " + player.getCoinsBalance() + " coins in the bank");

        // CHECK IF ANY PLAYER CAN OPEN
        boolean canOpen = false;
        for (PokerPlayer player : this.players)
            if (player.askOpenBet()) {
                System.out.println("> " + player.getName() + " says: I can open");
                canOpen = true;
            }
            else
                System.out.println("> " + player.getName() + " says: I cannot open");

        if (!canOpen) {
            System.out.println("Sorry, we cannot open the game.");
            return;
        }

        System.out.println("You have been dealt the following hand:");
        // PRINT THE TYPE OF HAND THAT HUMAN PLAYER OWNS
        for (PokerPlayer player : this.players)
            if (player.isHuman())
                System.out.println(player.getHand());

        // DISCARD
        /*for (PokerPlayer player : this.players) {
            if (player.isHuman()) {
                System.out.println(">> Which card(s) would you like to discard (e.g., 1,3): ");
                String discardCards = input.nextLine();
                // !!! TEMPORARY SOLUTION
                int card = Integer.parseInt(discardCards);
                player.discard()
            }
        }*/



    }

    public static void main(String[] args) {
        DeckOfCards deck = new DeckOfCards();

        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to the Automated Poker Machine ...");
        System.out.print("What is your name? ");
        String name = input.nextLine();
        System.out.println("Let's play POKER ...");

        // MAKE HUMAN PLAYER, PASS A NAME
        HumanPlayer humanPlayer = new HumanPlayer(deck, name);
        ComputerPlayer p1 = new ComputerPlayer(deck);
        ComputerPlayer p2 = new ComputerPlayer(deck);
        ArrayList<PokerPlayer> players = new ArrayList<PokerPlayer>();
        players.add(humanPlayer);
        players.add(p1);
        players.add(p2);
        RoundOfPoker round = new RoundOfPoker(players, deck);

        //System.out.println(round.players.get(0).name);

        round.play();

    }
}
