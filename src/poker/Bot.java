package poker;

/**
 * Main class to start the bot.
 * Parameter in the stream method specifies the hash tag, that will start the game of poker.
 */
public class Bot {

    public static void main(String[] args) throws Exception {

        Tweet tweet = new Tweet();
        tweet.stream("#bit2_poker");
    }
}
