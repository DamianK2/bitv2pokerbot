package poker;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.Scanner;

public class HumanPlayer extends PokerPlayer {

    private Scanner scanner;
    private Parser parser;
    private Tweet tweet;
    private TwitterInformation twitterInformation;
    private static int warning_count = 1;

    public HumanPlayer(DeckOfCards deck, TwitterInformation twitterInformation, String name) {
        super(deck);
        this.isHuman = true;
        this.scanner = new Scanner(System.in);
        this.parser = new Parser();
        this.tweet = new Tweet();
        this.twitterInformation = twitterInformation;
        this.name = name;
    }

    public int askDiscard()
    {
    	int counter = 0;
    	boolean check = false;
    	String discardCards = "";

		do {
			
			try {
				this.twitterInformation.updateCurrentMessageId(this.tweet.replyToTweet(this.twitterInformation.getGameMessage(), this.twitterInformation.getOriginalMessageId(), this.name));
			} catch (TwitterException e) {
				// DO SOMETHING
				System.out.println("Something went wrong while posting tweet Ask discard");
			}

			this.twitterInformation.clearGameMessage();

			// Wait until there is any response from user
			do {
				try {
					discardCards = this.tweet.getUserReply(this.twitterInformation.getCurrentMessageId(), this.name);
				} catch (TwitterException e) {
					// DO SOMETHING
					System.out.println("Something went wrong while posting tweet Ask discard");
				}
			} while (discardCards.equals(""));
			
        	if (!this.parser.checkAmountDiscards(discardCards)) {
        		this.twitterInformation.updateGameMessage("Warning number " + warning_count + "!");
        		this.twitterInformation.updateGameMessage("You can only discard a maximum of 3 cards.");
        		this.twitterInformation.updateGameMessage("Please type in the cards you would like to discard again.");

	    	}
	    	else if (!this.parser.checkDiscardNumbers(discardCards)) {
	    		this.twitterInformation.updateGameMessage("Warning number " + warning_count + "!");
	    		this.twitterInformation.updateGameMessage("You can only enter positions from 0 to 4 inclusive. Please try again.");
	    	}
	    	else
	    		check = true;  
        	warning_count++;
        } while(!check);
        		
        int[] cards = this.parser.convertDiscards(discardCards);
       
        for (int element : cards)
            if (element != -1)
                counter++;

        this.hand.discard(cards);      
        
        return counter;
    }
    
    public boolean askFold(int currentBet) {

    	return this.getResponse();
    }
 
    public boolean askOpenBet(int currentBet) {
    	return this.getResponse();
	}
    
    public boolean askRaiseBet(int currentBet) {
    	return this.getResponse();
	}
    
    public boolean getResponse() {
    	boolean check = false;
    	int response = -2;
		String inputResponse = "";

    	do {

			try {
				this.twitterInformation.updateCurrentMessageId(this.tweet.replyToTweet(this.twitterInformation.getGameMessage(), this.twitterInformation.getOriginalMessageId(), this.name));
			} catch (TwitterException e) {
				// DO SOMETHING
				System.out.println("Something went wrong while posting tweet to ask for response");
			}

			this.twitterInformation.clearGameMessage();

			// Wait until there is any response from user
			do {
				try {
					inputResponse = this.tweet.getUserReply(this.twitterInformation.getCurrentMessageId(), this.name);
				} catch (TwitterException e) {
					// DO SOMETHING
					System.out.println("Something went wrong while getting user response");
				}
			} while (inputResponse.equals(""));

			response = this.parser.convertResponse(inputResponse);
			if (response == -1) {
<<<<<<< HEAD
				this.game.updateGameMessage("Warning number " + warning_count + "!");
				game.updateGameMessage("The acceptable answers are yes/no or y/n. Please try again");
=======
				this.twitterInformation.updateGameMessage("Warning number " + warning_count + "!");
				this.twitterInformation.updateGameMessage("The acceptable answers are yes/no or y/n. Please try again");
>>>>>>> origin/twitter
				warning_count++;
			}
			else
				check = true;
		} while (!check);
    	
    	if (response == 1)
    		return true;
    	else
    		return false;
    }

    public void tweetMessage() {
        try {
            this.twitterInformation.updateCurrentMessageId(this.tweet.replyToTweet(this.twitterInformation.getGameMessage(), this.twitterInformation.getOriginalMessageId(), this.name));
        } catch (TwitterException e) {
            // DO SOMETHING
            System.out.println("Something went wrong while posting tweet message");
        }

        this.twitterInformation.clearGameMessage();
    }


}
