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

        // START ROUND
        System.out.println("New Deal:");
        int counter; // Used for iterations

        for (PokerPlayer player : this.players)
            System.out.println("> " + player.getName() + " has " + player.getCoinsBalance() + " coins in the bank");

        // CHECK IF ANY PLAYER CAN OPEN
        boolean canOpen = false;
        for (PokerPlayer player : this.players)
            if (player.canOpenBet()) {
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

        // DISCARD TODO PARSER
        for (PokerPlayer player : this.players) {
            if (player.isHuman()) {
                System.out.println(">> Which card(s) would you like to discard (e.g., 1,3): ");
                player.askDiscard();
            }
        }

        // PRINT THE TYPE OF HAND THAT HUMAN PLAYER OWNS
        for (PokerPlayer player : this.players)
            if (player.isHuman()) {
                System.out.println("Your hand now looks like:");
                System.out.println(player.getHand());
            }

        // SHOW DISCARDING STATS
        for (PokerPlayer player : this.players)
            if (!player.isHuman())
                System.out.println(player.getName() + " discards " + player.discard() + " card(s)");

        // ASK TO FOLD
        boolean[] fold = new boolean[this.players.size()];
        counter = 0;
        for (PokerPlayer player : this.players) {
            if (player.isHuman()) {
                System.out.println(">> Would you like to fold (y/n)? ");
            }

            fold[counter] = player.askFold(this.currentBet);
            counter++;
        }


        // OPEN BET
        int currentOpen;
        boolean[] seen = new boolean[this.players.size()];
        counter = 0;
        boolean isBettingOpen = false;
        for (int i = 0; i < this.players.size() && !isBettingOpen; i++)
        {
            if (players.get(i).isHuman() && !fold[counter])
                System.out.println("Would you like to open bet (y/n)? ");

            if (!fold[counter] && players.get(i).askOpenBet(this.currentBet))
            {


                this.currentBet = 1;
                players.get(i).updateCoinsBalance(-this.currentBet);
                System.out.println(players.get(i).getName() + " says: I open with " + this.currentBet + " chip!");
                isBettingOpen = true;
            }

            counter++;
        }

        // IF NOBODY OPENED
        if (!isBettingOpen) {
            System.out.println("sorry, the betting was not opened.");
            return;
        }



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

