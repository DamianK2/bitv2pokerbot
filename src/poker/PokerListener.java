package poker;

import twitter4j.*;

import java.util.ArrayList;

public class PokerListener implements StatusListener {

    private ArrayList<GameOfPoker> games;
    private int gameIndex = 0;

    public PokerListener() {
        this.games = new ArrayList<>();
    }

    public synchronized void onStatus(Status status) {
        System.out.println("ID: " + status.getId() + " @" + status.getUser().getScreenName() + " " + status.getText()); // print tweet text to console

        // Process manager, check if thread is alive and remove if not.
        for (GameOfPoker game : this.games)
            if (!game.isAlive()) {
                this.games.remove(game);
                this.gameIndex--;
            }

        if (this.games.size() < Tweet.GAMES_LIMIT) {
            this.games.add(gameIndex, new GameOfPoker(status.getId(), "@" + status.getUser().getScreenName()));
            System.out.println("New game created with game index " + this.gameIndex);
            this.games.get(gameIndex).start();
            this.gameIndex++;
        } else
            System.out.println("The limit of games has been reached.");

        // Thread testing only
        int j = 0;
        for (GameOfPoker game : this.games)
            System.out.println(j + " " + game.isAlive());

    }

        @Override
        public void onException(Exception e) {
            System.out.println("Exception occured:" + e.getMessage());
            e.printStackTrace();
        }

        @Override
        public void onTrackLimitationNotice(int n) {
            System.out.println("Track limitation notice for " + n);
        }

        @Override
        public void onStallWarning(StallWarning arg0) {
            System.out.println("Stall warning");
        }

        @Override
        public void onScrubGeo(long arg0, long arg1) {
            System.out.println("Scrub geo with:" + arg0 + ":" + arg1);
        }

        @Override
        public void onDeletionNotice(StatusDeletionNotice arg0) {
            System.out.println("Status deletion notice");
        }
}