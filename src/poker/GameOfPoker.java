package poker;


import java.util.ArrayList;
import java.util.Scanner;

public class GameOfPoker {

    public final static int COMPUTER_PLAYERS = 4;
    private String gameMessage;
    private long currentMessageId;

    public GameOfPoker(int messageId) {
        this.currentMessageId = messageId;
        this.gameMessage = "";
    }

    public void updateGameMessage(String message) {
        this.gameMessage += message;
    }

    public void playPoker() {
        DeckOfCards deck = new DeckOfCards();
        Parser parser = new Parser();

        Scanner input = new Scanner(System.in);
        HumanPlayer humanPlayer = new HumanPlayer(deck);

        this.updateGameMessage("Welcome to the Automated Poker Machine ...\n");
        this.updateGameMessage("What is your name? ");
        humanPlayer.askUserName();
        this.updateGameMessage("Let's play POKER ...\n");

        // MAKE HUMAN PLAYER, PASS A NAME

        ArrayList<PokerPlayer> players = new ArrayList<PokerPlayer>();
        players.add(humanPlayer);

        for (int i = 0; i <= COMPUTER_PLAYERS; i++)
            players.add(i, new ComputerPlayer(deck));


        // MAIN GAME
        boolean playAgain;
        do {

            // Play one round
            RoundOfPoker round = new RoundOfPoker(players, deck);
            round.play();

            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getCoinsBalance() == 0)
                    players.remove(i);
            }

            // Reset the game, to make sure that players have fresh cards
            this.resetGame(deck, players);

            System.out.println("Would like to play another round of poker (y/n)");
            playAgain = humanPlayer.getResponse();

        } while (players.contains(humanPlayer) && playAgain);

        System.out.println("The game is over!");

    }

    private void resetGame(DeckOfCards deck, ArrayList<PokerPlayer> players)
    {
        // RESET THE DECK
        deck.reset();

        // RESET HAND
        for (PokerPlayer player : players)
            player.resetHand();
    }

    public static void main(String[] args) {
        GameOfPoker game = new GameOfPoker(0);
        game.playPoker();
    }
}
