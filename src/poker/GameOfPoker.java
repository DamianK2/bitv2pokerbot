package poker;


import java.util.ArrayList;
import java.util.Scanner;

public class GameOfPoker extends Thread {

    public final static int COMPUTER_PLAYERS = 4;
    private TwitterInformation twitterInformation;

    public GameOfPoker(long messageId, String name) {
        this.twitterInformation = new TwitterInformation(messageId, name);
    }

    public void run() {
        this.playPoker();
    }

    public void playPoker() {
        DeckOfCards deck = new DeckOfCards();
        Parser parser = new Parser();

        Scanner input = new Scanner(System.in);
        HumanPlayer humanPlayer = new HumanPlayer(deck, this.twitterInformation, twitterInformation.getPlayerName());

        twitterInformation.updateGameMessage("Hello " + humanPlayer.getName() + " Let's play POKER ...");

        // MAKE HUMAN PLAYER, PASS A NAME

        ArrayList<PokerPlayer> players = new ArrayList<>();
        players.add(humanPlayer);

        for (int i = 0; i <= COMPUTER_PLAYERS; i++)
            players.add(i, new ComputerPlayer(deck));


        // MAIN GAME
        boolean playAgain;
        do {

            // Play one round
            RoundOfPoker round = new RoundOfPoker(players, deck, this.twitterInformation);
            round.play();

            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getCoinsBalance() == 0)
                    players.remove(i);
            }

            // Reset the game, to make sure that players have fresh cards
            this.resetGame(deck, players);

            twitterInformation.updateGameMessage("Would like to play another round of poker (y/n)");
            playAgain = humanPlayer.getResponse();

        } while (players.contains(humanPlayer) && playAgain);

        twitterInformation.updateGameMessage("The game is over! Thank you for playing with us.");
        humanPlayer.tweetMessage();

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
        GameOfPoker game = new GameOfPoker(854669882297901056L, "Marcin");
        game.playPoker();
    }
}
