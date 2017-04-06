package poker;
import java.util.Arrays;
import java.util.Random;

/**
 * Player class that will discard cards based on their discardProbabilities and a random event.
 */

public class PokerPlayer
{

	protected HandOfCards hand;
	protected DeckOfCards deck;
	protected int coins;
	protected String name;
	protected boolean isHuman;
	public static final int DISCARD_MAX = 3, MAX_PROBABILITY = 100, STARTING_COINS = 100;
	
	
	public PokerPlayer(DeckOfCards deck)
	{
		this.deck = deck;
		this.hand = new HandOfCards(deck);
		this.coins = STARTING_COINS;
	}

	public boolean isHuman()
	{
		return isHuman;
	}
	
	/*
	 * Discard cards from the player's hand based on computed probabilities in the HandOfCards.
	 * Go through all cards, by passing card position into getDiscardProbability and carry out action based on that.
	 * Keep counter for the amount of already discarded cards to make sure that no more than 3 cards are discarded by a player.
	 */
	public int discard()
	{
		int numberDiscarded = 0, arrayPosition = 0;
		int[] discardPositions = new int[DISCARD_MAX];
		
		// Fill in array with -1, it would be okay to set up all values in constructor, but if we have to change the amount in the future the code will take care of it without any code refactoring	
		Arrays.fill(discardPositions, -1);
		
		for (int i = 0; i < HandOfCards.SIZE && numberDiscarded <= 3; i++)
		{
			//System.out.print(hand.getDiscardProbability(i) + " ");
			if (hand.getDiscardProbability(i) > 0 && hand.getDiscardProbability(i) < PokerPlayer.MAX_PROBABILITY)
			{
				// COMPUTE RANDOM NUMBER
				if (generateRandomNumber() < hand.getDiscardProbability(i))
				{
					// DISCARD
					if (numberDiscarded != DISCARD_MAX)
					{
						discardPositions[arrayPosition++] = i;
						numberDiscarded++;
					}
				}
				
			}
			else if (hand.getDiscardProbability(i) == PokerPlayer.MAX_PROBABILITY)
			{
				// DISCARD
				if (numberDiscarded != DISCARD_MAX)
				{
					discardPositions[arrayPosition++] = i;
					numberDiscarded++;
				}
			}
		}
		
		this.hand.discard(discardPositions);
		

		/*System.out.println("\nPositions:");
		for (int i : discardPositions)
			System.out.println(" " + i + " ");*/
		
		return numberDiscarded;
	}
	
	protected int generateRandomNumber()
	{
		Random rand = new Random();
		return rand.nextInt(PokerPlayer.MAX_PROBABILITY) + 1;
	}
	
	public int getCoinsBalance() {
		return this.coins;
	}
	
	public void updateCoinsBalance(int coinsAmount) {
		this.coins += coinsAmount;
	}
	
	public String getName() {	
		return name;
	}
	
	public boolean askFold() {
		if(hand.isHighHand())
			return true;
		else
			return false;
	}
	
	public boolean askOpenBet() {
		if(hand.isHighHand())
			return false;
		else
			return true;
	}
	
	public boolean askRaiseBet() {
		if(hand.isTwoPair() || hand.isOnePair() || hand.isHighHand())
			return false;
		else
			return true;
	}
	
	public int askDiscard() {
		int discard = -1;
		
		return discard;
	}

	public String getHand()
	{
		String cards = "";

		for (int i = 0; i < HandOfCards.SIZE; i++)
			cards += i + ": " + this.hand.getCardAt(i).toString() + "\n";

		return cards;
	}
	
	
	/*
	 * For testing purposes, generate a random hand for the player and check his behaviour.
	 */
	public static void main(String[] args) {
		DeckOfCards deck = new DeckOfCards();
		deck.shuffle();
		//PokerPlayer player = new PokerPlayer(deck);
		//System.out.println(player.generateRandomNumber());
//		System.out.println(player.hand.toString());
//		System.out.println("\nDiscard amount: " + player.discard());
//		System.out.println(player.hand.toString());
		

	}

}
