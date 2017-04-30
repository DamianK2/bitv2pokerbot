package poker;

import twitter4j.TwitterException;

public class HumanPlayer extends PokerPlayer {

    private Parser parser;
    private Tweet tweet;
    private TwitterInformation twitterInformation;
    private static int warning_count = 1;

    public HumanPlayer(DeckOfCards deck, TwitterInformation twitterInformation, String name) {
        super(deck);
        this.isHuman = true;
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
			
			this.tweetMessage();

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
			
			if (this.parser.checkDealMeOut(discardCards)) {
				this.twitterInformation.updateGameMessage("We got your DealMeOut.");
				this.twitterInformation.updateGameMessage("Thank you for playing with us.");
				this.twitterInformation.updateGameMessage("Come back again if you get bored :)");
				
				this.tweetMessage();
				System.out.println("Game ended.");
				
				return PokerPlayer.EXIT_GAME;
			} 
			else {
				if (discardCards.equalsIgnoreCase("none")) {
					check = true;
				}
				else if (!this.parser.checkAmountDiscards(discardCards)) {
	        		this.twitterInformation.updateGameMessage("Warning number " + warning_count + "!");
	        		this.twitterInformation.updateGameMessage("You can only discard a maximum of 3 cards.");
	        		this.twitterInformation.updateGameMessage("Please type in the cards you would like to discard again.");
	        		warning_count++;

		    	}
		    	else if (!this.parser.checkDiscardNumbers(discardCards)) {
		    		this.twitterInformation.updateGameMessage("Warning number " + warning_count + "!");
		    		this.twitterInformation.updateGameMessage("You can only enter positions from 0 to 4 inclusive OR \"none\".");
		    		this.twitterInformation.updateGameMessage("Please type in the cards you would like to discard again.");
		    		warning_count++;
		    	}
		    	else
		    		check = true;
			}
        	
        } while(!check);
        
		if(!discardCards.equalsIgnoreCase("none")) {
			int[] cards = this.parser.convertDiscards(discardCards);
		       
	        for (int element : cards)
	            if (element != -1)
	                counter++;

	        this.hand.discard(cards);
		}     
        
        return counter;
    }
    
    public int askFold(int currentBet) {

    	return this.getResponse();
    }
 
    public int askOpenBet(int currentBet) {
    	return this.getResponse();
	}
    
    public int askRaiseBet(int currentBet) {
    	return this.getResponse();
	}
    
    // CHECK THE PLAYER BET AND RETURN IT
 	public int betAmount() {
         int num_betting = 0;
         boolean check = false;
         String bet = "";

         do {
			 // Tweet message
        	 this.tweetMessage();

             // Wait until there is any response from user
             do {
                 try {
                     bet = this.tweet.getUserReply(this.twitterInformation.getCurrentMessageId(), this.name);
                 } catch (TwitterException e) {
                     // DO SOMETHING
                     System.out.println("Something went wrong while posting tweet Ask discard");
                 }
             } while (bet.equals(""));
             
             if (this.parser.checkDealMeOut(bet)) {
 				this.twitterInformation.updateGameMessage("We got your DealMeOut.");
 				this.twitterInformation.updateGameMessage("Thank you for playing with us.");
 				this.twitterInformation.updateGameMessage("Come back again if you get bored :)");
 				
 				this.tweetMessage();
				System.out.println("Game ended.");
 				
				return PokerPlayer.EXIT_GAME;
 			 }  
 			 else {
	             if (!this.parser.bettingAmount(bet)) {
	                 this.twitterInformation.updateGameMessage("Warning number " + warning_count + "!");
	                 this.twitterInformation.updateGameMessage("Incorrect Please type in a positive integer amount.");
	                 warning_count++;
	             }
	             else if(Integer.parseInt(bet) > this.getCoinsBalance()){
	                 this.twitterInformation.updateGameMessage("Warning number " + warning_count + "!");
	                 this.twitterInformation.updateGameMessage("You have " + this.getCoinsBalance() + "chips");
	                 this.twitterInformation.updateGameMessage("Please enter a number of chip(s) less than or equal to " + this.getCoinsBalance());
	                 warning_count++;
	             }
	             else
	                 check = true;
 			 }    
         } while(!check);

         num_betting = Integer.parseInt(bet);

         return num_betting;
 	}
    
    public int getResponse() {
    	boolean check = false;
    	int response = -2;
		String userResponse = "";

    	do {
			// Tweet message
    		this.tweetMessage();

			// Wait until there is any response from user
			do {
				try {
					userResponse = this.tweet.getUserReply(this.twitterInformation.getCurrentMessageId(), this.name);
				} catch (TwitterException e) {
					// DO SOMETHING
					System.out.println("Something went wrong while getting user response");
				}
			} while (userResponse.equals(""));

			if (this.parser.checkDealMeOut(userResponse)) {
				this.twitterInformation.updateGameMessage("We got your DealMeOut.");
				this.twitterInformation.updateGameMessage("Thank you for playing with us.");
				this.twitterInformation.updateGameMessage("Come back again if you get bored :)");
				
				this.tweetMessage();
				System.out.println("Game ended.");
				
				return PokerPlayer.EXIT_GAME;
			} 
			else {
				response = this.parser.convertResponse(userResponse);
				if (response == -1) {
					this.twitterInformation.updateGameMessage("Warning number " + warning_count + "!");
					this.twitterInformation.updateGameMessage("The acceptable answers are yes/no or y/n. Please try again");
					warning_count++;
				}
				else
					check = true;
			}
		} while (!check);
    	
    	if (response == 1)
    		return PokerPlayer.TRUE;
    	else
    		return PokerPlayer.FALSE;
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
