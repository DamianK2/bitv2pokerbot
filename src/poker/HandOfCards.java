package poker;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Creates a hand of cards, sorts the hand and provides useful information about the highest set available in the hand.
 * Contains additional "testOverload" method that will accept selected cards and will provide list of checks, when class is run.
 * Tests are also included in the main of this class, to make sure that each method returns a correct boolean.
 */
public class HandOfCards {

	// Define constant values to calculate game value.
	public static final int ROYAL_FLUSH_DEFAULT = 10000000;
	public static final int STRAIGHT_FLUSH_DEFAULT = 9000000;
	public static final int FOUR_KIND_DEFAULT = 8000000;
	public static final int FULL_HOUSE_DEFAULT = 7000000;
	public static final int FLUSH_DEFAULT = 6000000;
	public static final int STRAIGHT_DEFAULT = 5000000;
	public static final int THREE_KIND_DEFAULT = 4000000;
	public static final int TWO_PAIR_DEFAULT = 3000000;
	public static final int ONE_PAIR_DEFAULT = 2000000;
	public static final int HIGH_HAND_DEFAULT = 1000000;
	public static final int MAX_PROBABILITY = 100;
	
	// Define HandOfCards members.
	public static final int HAND_CARDS = 5; 
	private PlayingCard hand[] = new PlayingCard[HAND_CARDS];
	private DeckOfCards deck;
	
	public HandOfCards(DeckOfCards deck)
	{
		// Save reference of a deck for the future.
		this.deck = deck;
		
		for (int i = 0; i < hand.length; i++)
		{
			this.hand[i] = this.deck.dealNext();
		}
	
		this.sort();	
	}
	
	/*
	 * Added method for Assignment 6.
	 * This method accepts a cardPositions array within correct bounds and returns it to the PokerPlayer instance of a deck.
	 * After all cards are discarded, the hand is sorted.
	 */
	public void discard(int[] cardPositions)
	{
		
		for (int cardPosition : cardPositions)
		{
			if (cardPosition != -1)
			{
				this.deck.returnCard(hand[cardPosition]);
				hand[cardPosition] = this.deck.dealNext();
			}
		}
		
		this.sort();
	}
	
	/*
	 * Sort array of card objects based on their int FaceValue. Descending.
	 */
	private void sort()
	{	
		Arrays.sort(hand, new Comparator<PlayingCard>() {
	            @Override
	            public int compare(PlayingCard a, PlayingCard b)
	            {
	            	if (a.getGameValue() > b.getGameValue()) 
	            		return -1;
	            	else if (a.getGameValue() < b.getGameValue()) 
	            		return  1;
	            	else
	            		return 0;
	            }
	        });
	}
	
	public boolean isRoyalFlush()
	{
		boolean status = false;

		if (hand[0].getFaceValue() == 1 && hand[0].getSuit() == hand[1].getSuit() && hand[0].getSuit() == hand[2].getSuit() && hand[0].getSuit() == hand[3].getSuit() && hand[0].getSuit() == hand[4].getSuit())
		{
			if (hand[0].getGameValue() == hand[1].getGameValue() + 1 && hand[0].getGameValue() == hand[2].getGameValue() + 2 && hand[0].getGameValue() == hand[3].getGameValue() + 3 && hand[0].getGameValue() == hand[4].getGameValue() + 4)
				status = true;
			else
				status = false;
		}
		else
			status = false;
		
		return status;
	}
	
	public boolean isStraightFlush()
	{
		boolean status = false;
		
		if (this.isRoyalFlush())
			status = false;
		else if (hand[0].getSuit() == hand[1].getSuit() && hand[0].getSuit() == hand[2].getSuit() && hand[0].getSuit() == hand[3].getSuit() && hand[0].getSuit() == hand[4].getSuit())
		{
			if (hand[0].getGameValue() == hand[1].getGameValue() + 1 && hand[0].getGameValue() == hand[2].getGameValue() + 2 && hand[0].getGameValue() == hand[3].getGameValue() + 3 && hand[0].getGameValue() == hand[4].getGameValue() + 4)
				status = true;
			else
				status = false;
		}
		else
			status = false;
		
		return status;
	}
	
	public boolean isFourOfAKind()
	{
		boolean status = false;
		
		if (hand[0].getFaceValue() == hand[1].getFaceValue() && hand[0].getFaceValue() == hand[2].getFaceValue() && hand[0].getFaceValue() == hand[3].getFaceValue())
			status = true;
		else if (hand[1].getFaceValue() == hand[2].getFaceValue() && hand[1].getFaceValue() == hand[3].getFaceValue() && hand[1].getFaceValue() == hand[4].getFaceValue())
			status = true;
		else
			status = false;
		
		return status;
	}
	
	public boolean isFullHouse()
	{
		boolean status = false;
		
		if (this.isFourOfAKind())
			status = false;
		else
		{
			if (hand[0].getFaceValue() == hand[1].getFaceValue() && hand[2].getFaceValue() == hand[3].getFaceValue() && hand[3].getFaceValue() == hand[4].getFaceValue())
				status = true;
			else if (hand[0].getFaceValue() == hand[1].getFaceValue() && hand[0].getFaceValue() == hand[2].getFaceValue() && hand[3].getFaceValue() == hand[4].getFaceValue())
				status = true;
			else
				status = false;
		}
		
		return status;
	}
	
	public boolean isFlush()
	{
		boolean status = false;
		
		if (this.isRoyalFlush())
			status = false;
		else if (this.isStraightFlush())
			status = false;
		else if (hand[0].getSuit() == hand[1].getSuit() && hand[0].getSuit() == hand[2].getSuit() && hand[0].getSuit() == hand[3].getSuit() && hand[0].getSuit() == hand[4].getSuit())
			status = true;
		else
			status = false;
		
		return status;
	}
	
	public boolean isStraight()
	{
		boolean status = false;
		
		if (this.isRoyalFlush())
			status = false;
		else if (this.isStraightFlush())
			status = false;
		else if (this.isFlush())
			status = false;
		else if (hand[0].getGameValue() == hand[1].getGameValue() + 1 && hand[0].getGameValue() == hand[2].getGameValue() + 2 && hand[0].getGameValue() == hand[3].getGameValue() + 3 && hand[0].getGameValue() == hand[4].getGameValue() + 4)
				status = true;
		else
			status = false;
		
		return status;
	}
	
	public boolean isThreeOfAKind()
	{
		boolean status = false;
		
		if (this.isFourOfAKind())
			status = false;
		else if (this.isFullHouse())
			status = false;
		else
		{
			if (hand[0].getFaceValue() == hand[1].getFaceValue() && hand[0].getFaceValue() == hand[2].getFaceValue())
				status = true;
			else if (hand[1].getFaceValue() == hand[2].getFaceValue() && hand[1].getFaceValue() == hand[3].getFaceValue())
				status = true;
			else if (hand[2].getFaceValue() == hand[3].getFaceValue() && hand[2].getFaceValue() == hand[4].getFaceValue())
				status = true;
			else
				status = false;		
		}
		
		return status;
	}
	
	public boolean isTwoPair()
	{
		boolean status = false;
		
		if (this.isFourOfAKind())
			status = false;
		else if (this.isThreeOfAKind())
			status = false;
		else if (this.isFullHouse())
			status = false;
		else
		{
			// Check all pairs, if amount of pairs is higher than 1, it's a TwoPair.
			int count = 0;
			
			if (hand[0].getFaceValue() == hand[1].getFaceValue())
				count += 1;
			if (hand[1].getFaceValue() == hand[2].getFaceValue())
				count += 1;
			if (hand[2].getFaceValue() == hand[3].getFaceValue())
				count += 1;
			if (hand[3].getFaceValue() == hand[4].getFaceValue())
				count += 1;
			
			if (count >= 2)
				status = true;
		}
		
		return status;
	}
	
	public boolean isOnePair()
	{
		boolean status = false;
		
		if (this.isTwoPair())
			status = false;
		else if (this.isFourOfAKind())
			status = false;
		else if (this.isThreeOfAKind())
			status = false;
		else if (this.isFullHouse())
			status = false;
		else
		{
			if (hand[0].getFaceValue() == hand[1].getFaceValue())
				status = true;
			else if (hand[1].getFaceValue() == hand[2].getFaceValue())
				status = true;
			else if (hand[2].getFaceValue() == hand[3].getFaceValue())
				status = true;
			else if (hand[3].getFaceValue() == hand[4].getFaceValue())
				status = true;
		}
		
		return status;
	}
	
	public boolean isHighHand()
	{
		boolean status = false;
		
		if (isRoyalFlush())
			status = false;
		else if (isStraightFlush())
			status = false;
		else if (isFourOfAKind())
			status = false;
		else if (isThreeOfAKind())
			status = false;
		else if (isFullHouse())
			status = false;
		else if (isStraight())
			status = false;
		else if (isFlush())
			status = false;
		else if (isTwoPair())
			status = false;
		else if (isOnePair())
			status = false;
		else
			status = true;
		
		return status;
	}
	
	/*
	 * Returns a string with the full state of a hand.
	 */
	public String toString()
	{
		String string = "";
		
		for (PlayingCard card : hand)
			string += card.toString() + ": " + card.getFaceValue() + " ";
		
		return string;
	}
	
	private void testOverload(PlayingCard a, PlayingCard b, PlayingCard c, PlayingCard d, PlayingCard e)
	{
		hand[0] = a;
		hand[1] = b;
		hand[2] = c;
		hand[3] = d;
		hand[4] = e;
		this.sort();
		System.out.println("\n" + this.toString());
		
		System.out.println("isRoyalFlush: " + isRoyalFlush());
		System.out.println("isStraightFlush: " + isStraightFlush());
		System.out.println("isFourOfAKind: " + isFourOfAKind());
		System.out.println("isThreeOfAKind: " + isThreeOfAKind());
		System.out.println("isFullHouse: " + isFullHouse());
		System.out.println("isStraight: " + isStraight());
		System.out.println("isFlush: " + isFlush());
		System.out.println("isTwoPair: " + isTwoPair());
		System.out.println("isOnePair: " + isOnePair());
		System.out.println("isHighHand: " + isHighHand());
	}
	
	/*
	 * Check which set is in the hand, add all the card values to distinguish between better sets.
	 * It is required to additionally compute product of similar card types.
	 */
	public int getGameValue()
	{
		int gameValue;
		
		// Set appropriate poker set value.
		if (isRoyalFlush())
			gameValue = ROYAL_FLUSH_DEFAULT;
		else if (isStraightFlush())
			gameValue = STRAIGHT_FLUSH_DEFAULT;
		else if (isFourOfAKind())
		{
			gameValue = FOUR_KIND_DEFAULT;
			gameValue += computeProduct();
		}
		else if (isFullHouse())
		{
			gameValue = FULL_HOUSE_DEFAULT;
			gameValue += computeProduct();
		}
		else if (isFlush())
			gameValue = FLUSH_DEFAULT;
		else if (isStraight())
			gameValue = STRAIGHT_DEFAULT;
		else if (isThreeOfAKind())
		{
			gameValue = THREE_KIND_DEFAULT;
			gameValue += computeProduct();
		}
		else if (isTwoPair())
		{
			gameValue = TWO_PAIR_DEFAULT;
			gameValue += computeProduct();
	
		}
		else if (isOnePair())
		{
			gameValue = ONE_PAIR_DEFAULT;
			gameValue += computeProduct();
		}
		else
			gameValue = HIGH_HAND_DEFAULT;
		
		/*
		 *  Go through each card, add its gameValue to the sum.
		 *  This allows for distinguishing same sets with different cards.
		 */
		for (PlayingCard card : hand)
		{
			if (card != null)
				gameValue += card.getGameValue();
		}
		
		return gameValue;
	}
	
	/*
	 * A helper method to compute the product of cards of same type to fix the error of having stronger sets to appear lower
	 * EG. h1: AAA21, h2: AKKKQ
	 */
	private int computeProduct()
	{	
		int product = 1, i = 0;
		for (i = 0; i < HAND_CARDS-1; i++)
		{
			if ((hand[i] != null && hand[i+1] != null) && hand[i].getGameValue() == hand[i+1].getGameValue())
				product *= Math.pow(hand[i].getGameValue(), 2);
		}
		
		return product;
	}
	
	/*
	 * Computes probability of discarding a card, based on the available sets and if some of them need 1 card from completing the set.
	 * Returns an integer with range between 0 <= X <= 100, where 0 doesn't discard and 100 definitely discard.
	 */
	public int getDiscardProbability(int cardPosition)
	{
		int probability = 0;
		
		if (cardPosition > HAND_CARDS-1 && cardPosition < 0)
			probability = 0;
		else
		{
			if (isRoyalFlush())
				probability = 0;
			else if (isStraightFlush())
				probability = 0;
			else if (isFullHouse())
				probability = 0;
			else if (isFourOfAKind())
			{
				// IDENTIFY CARD THAT'S NOT IN A PAIR - useful for players
				if (!isInPair(cardPosition))
					probability = 50;
				else
					probability = 0;
			}
			else if (isFlush())
				probability = 0;
			else if (isBrokenFlush())
			{
				// CHECK IF OUR CARD BREAKS THE FLUSH
				if (isBrokenFlushCard(cardPosition))
					probability = (47 - (13 - 4)) / (13 - 4);
				else
					probability = 0;
			}
			else if (isBrokenStraight())
			{
				// CHECK IF OUR CARD BREAKS THE STRAIGHT
				if (isBrokenStraightCard(cardPosition))
					probability = (47 - 4) / 4;
				else
					probability = 0;
			}
			else if (isStraight())
				probability = 0;
			else if (isThreeOfAKind())
			{
				
				/*
				 * IMPROVE TO A FOUR OF A KIND OR A FULL HOUSE
				 * If the card isn't a pair then return probability to give it out
				 */
				if (isInPair(cardPosition))
					probability = 0;
				else
					probability = 90; // Nearly always improve, nothing to miss
				
			}
			else if (isTwoPair())
			{
				
				/*
				 * IMPROVE TO FULL HOUSE
				 */
				if (isInPair(cardPosition))
					probability = 0;
				else
					probability = 70; // Improve as we can get a better set
				
			}
			else if (isOnePair())
			{
				/*
				 * IMPROVE TO ANYTHING ABOVE, GO FOR MORE OF A KIND
				 */
				if (isInPair(cardPosition))
					probability = 0;
				else
					probability = 90; // Improve to get higher change of more pairs or more of a kind
			}
			else if (isHighHand())
				probability = 60; // We've nothing, discard or don't
		}
		
		
		return probability;
	}
	
	/*
	 * Go through the hand of cards to check if there is one card missing from making a Straight.
	 * Loop through the cards based on gameValues to see how many correct in order cards we have.
	 * Returns TRUE, only if the number of correct card positions is 3. 4 means it's a Straight and less than 3 means it's anything else.
	 */
	private boolean isBrokenStraight()
	{
		/*
		 * cardSequence keeps count of how many cards are in sequence eg. K J 10 9 8 means that there are 4 cards in sequence
		 * addNextGameValue keeps count of how much we need to add to the gameValue to make the sequence true, reset if next cards is not correct in sequence
		 */
		int cardSequence = 0, addNextGameValue = 1;
		boolean brokenStraight = false;
		
		
		for (int i = 0; i < HAND_CARDS - 1; i++)
		{
			cardSequence = 0;
			for (int j = 1; j < HAND_CARDS - i; j++)
			{
				if (hand[i].getGameValue() == hand[j].getGameValue() + addNextGameValue)
					cardSequence += 1;
				else 
					addNextGameValue = 1;
				
				addNextGameValue++;
				
			}
			
			// If 4 it's a Straight, if 3 or 2 it's a brokenStraight as the card sequence is broken.
			if (cardSequence == 3 || cardSequence == 2)
				brokenStraight = true;	
		}
		
		return brokenStraight;
	}
	
	/* 
	 * Method invoked only if isBrokenStraight() returns TRUE.
	 * Checks if the passed card breaks the straight so it can get replaced.
	 */
	private boolean isBrokenStraightCard(int cardPosition)
	{
		
		boolean status = false;
		
		if (isInPair(cardPosition))
			status = true;
		else if (isMakingGap(cardPosition))
			status = true;
		else
			status = false;

		return status;
	}
	
	/*
	 * Check if the given card makes a gap between a sequence, to be used only with Straight etc.
	 */
	private boolean isMakingGap(int cardPosition)
	{
		boolean status = false;
		
		// Check if cardPosition within a range
		if (cardPosition >= 0 && cardPosition < HAND_CARDS)
		{
			// Check all possible positions for non-breaking card. If they all fail it's a breaking card and makes a gap.
			if (cardPosition >= 0 && cardPosition < HAND_CARDS - 2 && hand[cardPosition].getGameValue() == hand[cardPosition+1].getGameValue() + 1 && hand[cardPosition+1].getGameValue() + 1 == hand[cardPosition+2].getGameValue() + 2)
				status = false;
			else if (cardPosition < HAND_CARDS - 1 && cardPosition >= 1 && hand[cardPosition].getGameValue() == hand[cardPosition+1].getGameValue() + 1 && hand[cardPosition+1].getGameValue() + 1 == hand[cardPosition-1].getGameValue() - 1)
				status = false;
			else if (cardPosition < HAND_CARDS && cardPosition >= 2 && hand[cardPosition].getGameValue() == hand[cardPosition-2].getGameValue() - 2 && hand[cardPosition-1].getGameValue() - 1 == hand[cardPosition].getGameValue())
				status = false;
			else if (cardPosition < HAND_CARDS - 2 && hand[cardPosition+1].getGameValue() + 1 == hand[cardPosition+2].getGameValue() + 2)
				status = false;
			else if (isPair(cardPosition+1, cardPosition+2))
				status = false;// prevent a case where non breaking and next two are a pair eg. Q J J 10 9
			else if (cardPosition >= 2 && hand[cardPosition].getGameValue() != hand[cardPosition-1].getGameValue() - 1 && hand[cardPosition].getGameValue() != hand[cardPosition-2].getGameValue() - 2)
				status = true;
			else
				status = true;
		}
		else
			status = false;
		
		return status;
	}
	
	/*
	 * Check if the card is engaged in a pair.
	 */
	private boolean isInPair(int cardPosition)
	{
		boolean status = false;
		
		if (cardPosition != 0 || cardPosition != HAND_CARDS - 1)
		{
			if (isPair(cardPosition, cardPosition+1) || isPair(cardPosition-1, cardPosition))
				status = true;
		}
		else if (cardPosition == 0 && isPair(cardPosition, cardPosition+1))
			status = true;
		else if (cardPosition == HAND_CARDS - 1 && isPair(cardPosition-1, cardPosition))
			status = true;
		else
			status = false; // The card is not associated with any pair
		
		return status;
	}
	
	private boolean isPair(int cardPosition1, int cardPosition2)
	{
		boolean pair = false;
		// THERE'S A PAIR
		if (cardPosition1 >= 0 && cardPosition2 >= 0 && cardPosition1 < HAND_CARDS && cardPosition2 < HAND_CARDS && hand[cardPosition1].getGameValue() == hand[cardPosition2].getGameValue())
			pair = true;
		else
			pair = false;
		
		return pair;
	}
	
	/*
	 * Check if there is only 1 missing suit
	 * Goes through all cards in hand and checks how many cards of suit there are, if there are 4 cards of same suit - then it's a broken Flush.
	 */
	private boolean isBrokenFlush()
	{
		boolean status = false;
		int suitHearts = 0, suitDiamonds = 0, suitClubs = 0, suitSpades = 0;
		
		for (PlayingCard card : hand)
		{
			if (card.getSuit() == PlayingCard.HEARTS)
				suitHearts++;
			else if (card.getSuit() == PlayingCard.DIAMONDS)
				suitDiamonds++;
			else if (card.getSuit() == PlayingCard.CLUBS)
				suitClubs++;
			else if (card.getSuit() == PlayingCard.SPADES)
				suitSpades++;
		}
		
		// 4 of same suit mean that we need 1 card to complete the Flush
		if (suitHearts == 4 || suitDiamonds == 4 || suitClubs == 4 || suitSpades == 4)
			status = true;
		else
			status = false;
		
		return status;
	}
	
	/* 
	 * Method invoked only if isBrokenFlush() returns TRUE.
	 * Checks if the passed card breaks the Flush so it can get replaced.
	 */
	private boolean isBrokenFlushCard(int cardPosition)
	{
		boolean status = false;
		int suitHearts = 0, suitDiamonds = 0, suitClubs = 0, suitSpades = 0;
		
		for (PlayingCard card : hand)
		{
			if (card.getSuit() == PlayingCard.HEARTS)
				suitHearts++;
			else if (card.getSuit() == PlayingCard.DIAMONDS)
				suitDiamonds++;
			else if (card.getSuit() == PlayingCard.CLUBS)
				suitClubs++;
			else if (card.getSuit() == PlayingCard.SPADES)
				suitSpades++;
		}

		// If the amount of cards of same suit is 4 and the cardPosition is not of the same suit then it breaks the straight
		if (suitHearts == 4 && hand[cardPosition].getSuit() != 'H')
			status = true;
		else if (suitDiamonds == 4 && hand[cardPosition].getSuit() != 'D')
			status = true;
		else if (suitClubs == 4 && hand[cardPosition].getSuit() != 'C')
			status = true;
		else if (suitSpades == 4 && hand[cardPosition].getSuit() != 'S')
			status = true;
		
		return status;
	}
	
	/*
	 * The main contains extensive testing of the cards in the hand, a full state of the hand is printed.
	 */
	public static void main(String[] args) {
		
		DeckOfCards deck = new DeckOfCards();
		deck.shuffle();
		HandOfCards hand = new HandOfCards(deck);
		
		System.out.println(hand.toString());
		
		System.out.println("\nRoyalFlush");
		hand.testOverload(new PlayingCard("A", 'H', 1, 14), new PlayingCard("K", 'H', 13, 13), new PlayingCard("Q", 'H', 12, 12), new PlayingCard("J", 'H', 11, 11), new PlayingCard("10", 'H', 10, 10));
		System.out.println("\nStraighFlush");
		hand.testOverload(new PlayingCard("10", 'H', 10, 10), new PlayingCard("9", 'H', 9, 9), new PlayingCard("8", 'H', 8, 8), new PlayingCard("7", 'H', 7, 7), new PlayingCard("6", 'H', 6, 6));
		System.out.println("\nFourOfAKind");
		hand.testOverload(new PlayingCard("10", 'C', 10, 10), new PlayingCard("10", 'H', 10, 10), new PlayingCard("10", 'D', 10, 10), new PlayingCard("10", 'S', 10, 10), new PlayingCard("6", 'C', 6, 6));
		System.out.println("\nFullHouse:");
		hand.testOverload(new PlayingCard("10", 'C', 10, 10), new PlayingCard("10", 'H', 10, 10), new PlayingCard("10", 'D', 10, 10), new PlayingCard("K", 'S', 13, 13), new PlayingCard("K", 'C', 13, 13));
		System.out.println("\nFlush");
		hand.testOverload(new PlayingCard("A", 'C', 1, 14), new PlayingCard("J", 'C', 11, 11), new PlayingCard("9", 'C', 9, 9), new PlayingCard("8", 'C', 8, 8), new PlayingCard("6", 'C', 6, 6));
		System.out.println("\nStraight:");
		hand.testOverload(new PlayingCard("10", 'C', 10, 10), new PlayingCard("9", 'H', 9, 9), new PlayingCard("8", 'C', 8, 8), new PlayingCard("7", 'H', 7, 7), new PlayingCard("6", 'C', 6, 6));
		System.out.println("\nThreeOfAKind");
		hand.testOverload(new PlayingCard("10", 'C', 10, 10), new PlayingCard("10", 'H', 10, 10), new PlayingCard("10", 'D', 10, 10), new PlayingCard("5", 'S', 5, 5), new PlayingCard("6", 'C', 6, 6));
		System.out.println("\nTwoPairs");
		hand.testOverload(new PlayingCard("Q", 'H', 12, 12), new PlayingCard("Q", 'D', 12, 12), new PlayingCard("7", 'C', 7, 7), new PlayingCard("7", 'H', 7, 7), new PlayingCard("3", 'H', 3, 3));
		System.out.println("\nOnePair");
		hand.testOverload(new PlayingCard("2", 'H', 2, 2), new PlayingCard("Q", 'D', 12, 12), new PlayingCard("A", 'C', 1, 14), new PlayingCard("7", 'H', 7, 7), new PlayingCard("A", 'S', 1, 14));
		System.out.println("\nHighHand");
		hand.testOverload(new PlayingCard("7", 'C', 7, 7), new PlayingCard("K", 'H', 13, 13), new PlayingCard("5", 'H', 5, 5), new PlayingCard("3", 'S', 3, 3), new PlayingCard("2", 'C', 2, 2));
		
		System.out.println();
		System.out.println("Game value tests:");
		DeckOfCards deck1 = new DeckOfCards();
		deck1.shuffle();
		HandOfCards hand1 = new HandOfCards(deck1);
		HandOfCards hand2 = new HandOfCards(deck1);
		hand1.testOverload(new PlayingCard("A", 'C', 1, 14), new PlayingCard("A", 'H', 1, 14), new PlayingCard("A", 'D', 1, 14), new PlayingCard("3", 'S', 3, 3), new PlayingCard("2", 'C', 2, 2));
		hand2.testOverload(new PlayingCard("K", 'C', 13, 13), new PlayingCard("K", 'H', 13, 13), new PlayingCard("K", 'D', 13, 13), new PlayingCard("A", 'S', 1, 14), new PlayingCard("Q", 'C', 12, 12));
		System.out.println(hand1);
		System.out.println(hand2);
		System.out.println("Hand1 game value: " + hand1.getGameValue());
		System.out.println("Hand2 game value: " + hand2.getGameValue());
		
		hand1.testOverload(new PlayingCard("9", 'C', 9, 9), new PlayingCard("9", 'H', 9, 9), new PlayingCard("2", 'D', 2, 2), new PlayingCard("3", 'S', 3, 3), new PlayingCard("4", 'C', 4, 4));
		hand2.testOverload(new PlayingCard("K", 'C', 13, 13), new PlayingCard("A", 'H', 1, 14), new PlayingCard("Q", 'D', 12, 12), new PlayingCard("6", 'S', 6, 6), new PlayingCard("6", 'C', 6, 6));
		System.out.println(hand1);
		System.out.println(hand2);
		System.out.println("Hand1 game value: " + hand1.getGameValue());
		System.out.println("Hand2 game value: " + hand2.getGameValue());

		hand1.testOverload(new PlayingCard("Q", 'C', 12, 12), new PlayingCard("Q", 'H', 12, 12), new PlayingCard("Q", 'D', 12, 12), new PlayingCard("7", 'S', 7, 7), new PlayingCard("7", 'C', 7, 7));
		hand2.testOverload(new PlayingCard("J", 'C', 11, 11), new PlayingCard("J", 'H', 11, 11), new PlayingCard("J", 'D', 11, 11), new PlayingCard("A", 'S', 1, 14), new PlayingCard("A", 'C', 1, 14));
		System.out.println(hand1);
		System.out.println(hand2);
		System.out.println("Hand1 game value: " + hand1.getGameValue());
		System.out.println("Hand2 game value: " + hand2.getGameValue());
		
		hand1.testOverload(new PlayingCard("A", 'C', 1, 14), new PlayingCard("A", 'H', 1, 14), new PlayingCard("A", 'D', 1, 14), new PlayingCard("A", 'S', 1, 14), new PlayingCard("7", 'C', 7, 7));
		hand2.testOverload(new PlayingCard("K", 'C', 13, 13), new PlayingCard("K", 'H', 13, 13), new PlayingCard("K", 'D', 13, 13), new PlayingCard("K", 'S', 13, 13), new PlayingCard("Q", 'C', 12, 12));
		System.out.println(hand1);
		System.out.println(hand2);
		System.out.println("Hand1 game value: " + hand1.getGameValue());
		System.out.println("Hand2 game value: " + hand2.getGameValue());
		
		hand1.testOverload(new PlayingCard("Q", 'C', 12, 12), new PlayingCard("J", 'H', 11, 11), new PlayingCard("J", 'D', 11, 11), new PlayingCard("3", 'S', 3, 3), new PlayingCard("2", 'C', 2, 2));
		hand2.testOverload(new PlayingCard("K", 'C', 13, 13), new PlayingCard("A", 'H', 1, 14), new PlayingCard("9", 'D', 9, 9), new PlayingCard("9", 'S', 9, 9), new PlayingCard("5", 'C', 5, 5));
		System.out.println(hand1);
		System.out.println(hand2);
		System.out.println("Hand1 game value: " + hand1.getGameValue());
		System.out.println("Hand2 game value: " + hand2.getGameValue());
		
		hand1.testOverload(new PlayingCard("Q", 'H', 12, 12), new PlayingCard("J", 'H', 11, 11), new PlayingCard("10", 'H', 10, 10), new PlayingCard("9", 'H', 9, 9), new PlayingCard("8", 'H', 8, 8));
		hand2.testOverload(new PlayingCard("K", 'C', 13, 13), new PlayingCard("Q", 'C', 12, 12), new PlayingCard("J", 'C', 11, 11), new PlayingCard("10", 'C', 10, 10), new PlayingCard("9", 'C', 9, 9));
		System.out.println(hand1);
		System.out.println(hand2);
		System.out.println("Hand1 game value: " + hand1.getGameValue());
		System.out.println("Hand2 game value: " + hand2.getGameValue());
		
		/*
		 * Tests for Assignment 5
		 */
		
		// Check the isBrokenStraight() method
		hand1.testOverload(new PlayingCard("K", 'C', 13, 13), new PlayingCard("Q", 'C', 12, 12), new PlayingCard("J", 'C', 11, 11), new PlayingCard("10", 'C', 10, 10), new PlayingCard("9", 'C', 9, 9));
		System.out.println("isBrokenStraight(): " + hand1.isBrokenStraight());
		hand1.testOverload(new PlayingCard("K", 'C', 13, 13), new PlayingCard("Q", 'C', 12, 12), new PlayingCard("J", 'C', 11, 11), new PlayingCard("10", 'C', 10, 10), new PlayingCard("2", 'C', 2, 2));
		System.out.println("isBrokenStraight(): " + hand1.isBrokenStraight());
		hand1.testOverload(new PlayingCard("J", 'C', 11, 11), new PlayingCard("Q", 'C', 12, 12), new PlayingCard("J", 'C', 11, 11), new PlayingCard("10", 'C', 10, 10), new PlayingCard("9", 'C', 9, 9));
		System.out.println("isBrokenStraight(): " + hand1.isBrokenStraight());
		//System.out.println("isBrokenStraightCard?: " + hand1.isBrokenStraightCard(0));
		
		hand1.testOverload(new PlayingCard("K", 'C', 13, 13), new PlayingCard("Q", 'C', 12, 12), new PlayingCard("Q", 'S', 12, 12), new PlayingCard("10", 'C', 10, 10), new PlayingCard("2", 'C', 2, 2));
		System.out.println("isBrokenStraight(): " + hand1.isBrokenStraight());
		
		
		hand1.testOverload(new PlayingCard("10", 'C', 10, 10), new PlayingCard("7", 'C', 7, 7), new PlayingCard("5", 'S', 5, 5), new PlayingCard("4", 'C', 4, 4), new PlayingCard("3", 'C', 3, 3));
		System.out.println("isBrokenStraight(): " + hand1.isBrokenStraight());
		System.out.println("isBrokenFlush(): " + hand1.isBrokenFlush());
		
		System.out.println("isBrokenStraightCard?: " + hand1.isBrokenStraightCard(0));
		// Check if correct probability returned for brokenStraight cards -- ALSO A FLUSH
		for (int i = 0; i < HAND_CARDS; i++)
		{
			System.out.println(hand1.getDiscardProbability(i));
		}
		
		
		// Check if it's a broken Flush, expected return TRUE
		hand2.testOverload(new PlayingCard("J", 'C', 11, 11), new PlayingCard("Q", 'C', 12, 12), new PlayingCard("3", 'C', 3, 3), new PlayingCard("10", 'C', 10, 10), new PlayingCard("9", 'H', 9, 9));
		System.out.println("isBrokenFlush(): " + hand2.isBrokenFlush());
		System.out.println("isBrokenFlushCard? :" + hand2.isBrokenFlushCard(3));
		
		// Check if correct probability returned for brokenFlush cards
		for (int i = 0; i < HAND_CARDS; i++)
			System.out.println(hand2.getDiscardProbability(i));
		
		// Full house
		System.out.print("\nFour of a kind:");
		hand2.testOverload(new PlayingCard("J", 'C', 11, 11), new PlayingCard("J", 'S', 11, 11), new PlayingCard("J", 'H', 11, 11), new PlayingCard("J", 'D', 11, 11), new PlayingCard("10", 'H', 10, 10));
				
		for (int i = 0; i < HAND_CARDS; i++)
			System.out.println(hand2.getDiscardProbability(i));
		
		// Full house
		System.out.print("\nFull house:");
		hand2.testOverload(new PlayingCard("J", 'C', 11, 11), new PlayingCard("J", 'S', 11, 11), new PlayingCard("J", 'H', 11, 11), new PlayingCard("10", 'C', 10, 10), new PlayingCard("10", 'H', 10, 10));
				
		for (int i = 0; i < HAND_CARDS; i++)
			System.out.println(hand2.getDiscardProbability(i));
		
		// Flush
		System.out.print("\nFlush:");
		hand2.testOverload(new PlayingCard("J", 'C', 11, 11), new PlayingCard("3", 'C', 3, 3), new PlayingCard("2", 'C', 2, 2), new PlayingCard("10", 'C', 10, 10), new PlayingCard("9", 'C', 9, 9));
				
		for (int i = 0; i < HAND_CARDS; i++)
			System.out.println(hand2.getDiscardProbability(i));
		
		// ThreeOfAKind
		System.out.print("\n Three of a kind:");
		hand2.testOverload(new PlayingCard("J", 'C', 11, 11), new PlayingCard("J", 'S', 11, 11), new PlayingCard("J", 'H', 11, 11), new PlayingCard("10", 'C', 10, 10), new PlayingCard("9", 'H', 9, 9));
		
		for (int i = 0; i < HAND_CARDS; i++)
			System.out.println(hand2.getDiscardProbability(i));
		
		// Two pair
		System.out.print("\nTwo Pair:");
		hand2.testOverload(new PlayingCard("J", 'C', 11, 11), new PlayingCard("J", 'S', 11, 11), new PlayingCard("2", 'H', 2, 2), new PlayingCard("10", 'C', 10, 10), new PlayingCard("10", 'H', 10, 10));
						
		for (int i = 0; i < HAND_CARDS; i++)
			System.out.println(hand2.getDiscardProbability(i));
		
		// One pair
		System.out.print("\nOne pair:");
		hand2.testOverload(new PlayingCard("J", 'C', 11, 11), new PlayingCard("J", 'S', 11, 11), new PlayingCard("2", 'H', 2, 2), new PlayingCard("A", 'C', 1, 14), new PlayingCard("10", 'H', 10, 10));
						
		for (int i = 0; i < HAND_CARDS; i++)
			System.out.println(hand2.getDiscardProbability(i));
		
		// High hand
		System.out.print("\nHigh hand:");
		hand2.testOverload(new PlayingCard("J", 'C', 11, 11), new PlayingCard("5", 'S', 5, 5), new PlayingCard("2", 'H', 2, 2), new PlayingCard("A", 'C', 1, 14), new PlayingCard("10", 'H', 10, 10));
						
		for (int i = 0; i < HAND_CARDS; i++)
			System.out.println(hand2.getDiscardProbability(i));
		
		// isBrokenStraight ONLY
		System.out.print("\nBroken straight:");
		hand2.testOverload(new PlayingCard("J", 'C', 11, 11), new PlayingCard("10", 'S', 10, 10), new PlayingCard("9", 'H', 9, 9), new PlayingCard("8", 'C', 8, 8), new PlayingCard("2", 'H', 2, 2));
		
		System.out.println("isBrokenStraight(): " + hand2.isBrokenStraight());
		System.out.println("isBrokenFlush(): " + hand2.isBrokenFlush());
		
		for (int i = 0; i < HAND_CARDS; i++)
			System.out.println(hand2.getDiscardProbability(i));
		
		
		
		hand1.testOverload(new PlayingCard("K", 'D', 13, 13), new PlayingCard("Q", 'H', 12, 12), new PlayingCard("J", 'S', 11, 11), new PlayingCard("10", 'C', 10, 10), new PlayingCard("5", 'C', 5, 5));
		System.out.println("isBrokenStraight(): " + hand1.isBrokenStraight());
		System.out.println("isBrokenFlush(): " + hand2.isBrokenFlush());
		for (int i = 0; i < HAND_CARDS; i++)
			System.out.println(hand1.getDiscardProbability(i));
		
		
		// TEST
		hand2.testOverload(new PlayingCard("9", 'C', 9, 9), new PlayingCard("4", 'S', 4, 4), new PlayingCard("4", 'H', 4, 4), new PlayingCard("3", 'C', 3, 3), new PlayingCard("3", 'H', 3, 3));
		for (int i = 0; i < HAND_CARDS; i++)
			System.out.println(hand2.getDiscardProbability(i));
		
	
	}
	
	
}
	
