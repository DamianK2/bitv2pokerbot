package poker;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.Scanner;

public class HumanPlayer extends PokerPlayer {

    private Scanner scanner;
    private Parser parser;
    private Tweet tweet;
    private GameOfPoker game;

    public HumanPlayer(DeckOfCards deck, GameOfPoker game, String name) {
        super(deck);
        this.isHuman = true;
        this.scanner = new Scanner(System.in);
        this.parser = new Parser();
        this.tweet = new Tweet();
        this.game = game;
        this.name = name;
    }

    public int askDiscard()
    {
    	int counter = 0;
    	boolean check = false;
    	String discardCards = "";
    	long lastTweetId = 0;

		try {
			lastTweetId = this.tweet.replyToTweet(this.game.getGameMessage(), this.game.getCurrentMessageId(), this.name);
		} catch (TwitterException e) {
			// DO SOMETHING
			System.out.println("Something went wrong while posting tweet Ask discard");
		}

		this.game.clearGameMessage();


//    	do {
//        	discardCards = this.scanner.nextLine();
//
//        	if(!this.parser.checkAmountDiscards(discardCards)) {
//        		System.out.println("You can only discard a maximum of 3 cards.");
//                System.out.println("Please type in the cards you would like to discard again.");	
//        	}
//        	else if(!this.parser.checkDiscardNumbers(discardCards)) {
//        		System.out.println("You can only enter positions from 0 to 4 inclusive. Please try again.");
//        	}
//        	else
//        		check = true;	
//        	
//        } while(!check);
		
		do {
			try {
				discardCards = this.tweet.getUserReply(lastTweetId, this.name);
			} catch (TwitterException e) {
				// DO SOMETHING
				System.out.println("Something went wrong while posting tweet Ask discard");
			}
			
        	if(!this.parser.checkAmountDiscards(discardCards)) {
	    		System.out.println("You can only discard a maximum of 3 cards.");
	            System.out.println("Please type in the cards you would like to discard again.");	
	    	}
	    	else if(!this.parser.checkDiscardNumbers(discardCards)) {
	    		System.out.println("You can only enter positions from 0 to 4 inclusive. Please try again.");
	    	}
	    	else
	    		check = true;
        	
        	if(!check) {
        		try {
        			Thread.sleep(30000);
        		} catch (InterruptedException e) {
        			System.out.println("Something went wrong while posting tweet Ask discard");
        		}
        	}
        		
        	
		} while(!check);
        		
        int[] cards = this.parser.convertDiscards(discardCards);
       
        for (int element : cards)
            if (element != -1)
                counter++;

        this.hand.discard(cards);      
        
        return counter;
    }
    
    public boolean askFold(int currentBet) {

    	try {
			this.tweet.replyToTweet(this.game.getGameMessage(), this.game.getCurrentMessageId(), this.name);
		} catch (TwitterException e) {
    		// DO SOMETHING
			System.out.println("Something went wrong while posting tweet Ask fold");
		}

		this.game.clearGameMessage();

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
    	
    	do {
    		String inputResponse = this.scanner.nextLine();
    		response = this.parser.convertResponse(inputResponse);
    		if(response == -1) {
    			System.out.println("The acceptable answers are yes/no or y/n. Please try again");
    		}
    		else
    			check = true;
    		
    	} while(!check);
    	
    	if(response == 1) 
    		return true;
    	else
    		return false;
    }


}
