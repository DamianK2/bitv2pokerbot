package poker;
/**
 *
 * @author Damian Kluziak
 * This class is the player's hand. It takes 5 instances from the deck and places them in an array which is effectively the player's hand.
 * It also checks different combinations of cards to determine what kind of Poker hand the player has.
 *
 */
public class HandOfCards {
	// create a constant to use in loops and creating array
	public static final int SIZE = 5;
	private static final int LOOP_TWICE = 2, LOOP_THRICE = 3, LOOP_FOUR_TIMES = 4, LOOP_FIVE_TIMES = 5, ROYAL_FLUSH_DEFAULT = 900000000, STRAIGHT_FLUSH_DEFAULT = 800000000, FOUR_OF_KIND_DEFAULT = 700000000, FULL_HOUSE_DEFAULT = 600000000, FLUSH_DEFAULT = 500000000, STRAIGHT_DEFAULT = 400000000, THREE_OF_KIND_DEFAULT = 300000000, TWO_PAIR_DEFAULT = 200000000, ONE_PAIR_DEFAULT = 100000000, HIGH_HAND_DEFAULT = 10000000;
	private PlayingCard hand[] = new PlayingCard[SIZE];	// create player's hand
	private DeckOfCards deck;							// could be used later for a reference to the deck object we created
	private int discardProbability, brokenCardPos;

	// fetches 5 cards from the deck upon creation of HandOfCards object and places them into an array, then sorts that array
	HandOfCards(DeckOfCards deckRef) {
		deck = deckRef;
		for(int i = 0; i < SIZE; i++) {
			hand[i] = deck.dealNext();
		}
		this.sort();
	}

	// sorts the hand in descending order
	private void sort() {
		PlayingCard temp;
		boolean swapped = true;
		int j = 0;
		while (swapped) {
			swapped = false;
			j++;
			for(int i = 0; i < hand.length - j; i++) {
				if(hand[i].getGameValue() < hand[i+1].getGameValue()) {
					temp = hand[i];
					hand[i] = hand[i+1];
					hand[i+1] = temp;
					swapped = true;
				}
			}
		}
	}

	// returns the player's hand
	public String toString() {
		String cardsInHand;
		cardsInHand = "Your hand: " + hand[0].toString() + ", " + hand[1].toString() + ", "  + hand[2].toString() + ", "  + hand[3].toString() + ", "  + hand[4].toString();
		return cardsInHand;
	}

	// this method is here only for testing purposes, it will serve no purpose later on and therefore will probably be removed
	private void handOverloadTesting(PlayingCard card1, PlayingCard card2, PlayingCard card3, PlayingCard card4, PlayingCard card5) {
		hand[0] = card1;
		hand[1] = card2;
		hand[2] = card3;
		hand[3] = card4;
		hand[4] = card5;
	}

	// returns the deck object that we have created and stored in a local variable
	public DeckOfCards getDeck() {
		return deck;
	}

	// if the suits are all the same and the cards are sequential from Ace down to 10 then it is a royal flush
	public boolean isRoyalFlush() {
		boolean check = false;
		if(hand[0].getSuit() == hand[1].getSuit() && hand[1].getSuit() == hand[2].getSuit() && hand[2].getSuit() == hand[3].getSuit() && hand[3].getSuit() == hand[4].getSuit()) {
			if(hand[0].getGameValue() == 14 && hand[1].getGameValue() == 13 && hand[2].getGameValue() == 12 && hand[3].getGameValue() == 11 && hand[4].getGameValue() == 10)
				check = true;
		}
		return check;
	}

	// if we have 4 of the same cards then it is a four of a kind
	public boolean isFourOfAKind() {
		boolean check = false;
		if((hand[0].getGameValue() == hand[1].getGameValue() && hand[1].getGameValue() == hand[2].getGameValue() && hand[2].getGameValue() == hand[3].getGameValue()) || (hand[1].getGameValue() == hand[2].getGameValue() && hand[2].getGameValue() == hand[3].getGameValue() && hand[3].getGameValue() == hand[4].getGameValue()))
			check = true;
		return check;
	}

	// if we have cards in the format of 3/2 or 2/3 where the '2' and '3' symbolize the same cards then it is a full house e.g. AH, AC, 8C, 8S, 8C
	public boolean isFullHouse() {
		boolean check = false;
		if((hand[0].getGameValue() == hand[1].getGameValue() && hand[2].getGameValue() == hand[3].getGameValue() && hand[3].getGameValue() == hand[4].getGameValue()) || (hand[0].getGameValue() == hand[1].getGameValue() && hand[1].getGameValue() == hand[2].getGameValue() && hand[3].getGameValue() == hand[4].getGameValue()))
			check = true;
		return check;
	}

	// if it is not a royal flush or a straight flush but all suits are the same but not sequential then it is a flush
	public boolean isFlush() {
		boolean check = false;
		if(this.isRoyalFlush())
			check = false;
		else if(this.isStraightFlush())
			check = false;
		else if(hand[0].getSuit() == hand[1].getSuit() && hand[1].getSuit() == hand[2].getSuit() && hand[2].getSuit() == hand[3].getSuit() && hand[3].getSuit() == hand[4].getSuit())
			check = true;
		return check;
	}

	// if the hand is not a full house, four of a kind, three of a kind or two pairs then it may be one pair
	public boolean isOnePair() {
		boolean check = false;
		if(this.isFullHouse())
			check = false;
		else if(this.isFourOfAKind())
			check = false;
		else if(this.isThreeOfAKind())
			check = false;
		else if(this.isTwoPair())
			check = false;
		else if(hand[0].getGameValue() == hand[1].getGameValue() || hand[1].getGameValue() == hand[2].getGameValue() || hand[2].getGameValue() == hand[3].getGameValue() || hand[3].getGameValue() == hand[4].getGameValue())
			check = true;
		return check;
	}

	// if it is not a royal flush and all suits are the same and sequential then it is a straight flush
	public boolean isStraightFlush() {
		boolean check = false;
		if(this.isRoyalFlush())
			check = false;
		else if(hand[0].getSuit() == hand[1].getSuit() && hand[1].getSuit() == hand[2].getSuit() && hand[2].getSuit() == hand[3].getSuit() && hand[3].getSuit() == hand[4].getSuit()) {
			if (hand[0].getGameValue() == hand[1].getGameValue() + 1 && hand[1].getGameValue() == hand[2].getGameValue() + 1 && hand[2].getGameValue() == hand[3].getGameValue() + 1 && hand[3].getGameValue() == hand[4].getGameValue() + 1)
				check = true;
		}
		return check;
	}

	// if three cards are the same but not a full house or a four of a kind then they are three of a kind
	public boolean isThreeOfAKind() {
		boolean check = false;
		if(this.isFullHouse())
			check = false;
		else if(this.isFourOfAKind())
			check = false;
		else if((hand[0].getGameValue() == hand[1].getGameValue() && hand[1].getGameValue() == hand[2].getGameValue()) || (hand[1].getGameValue() == hand[2].getGameValue() && hand[2].getGameValue() == hand[3].getGameValue()) || (hand[2].getGameValue() == hand[3].getGameValue() && hand[3].getGameValue() == hand[4].getGameValue()))
			check = true;
		return check;
	}

	// if the cards are sequential but not a royal flush or a straight flush(not the same suits) then it is a straight
	public boolean isStraight() {
		boolean check = false;
		if(this.isRoyalFlush())
			check = false;
		else if(this.isStraightFlush())
			check = false;
		else if(hand[0].getGameValue() == hand[1].getGameValue() + 1 && hand[1].getGameValue() == hand[2].getGameValue() + 1 && hand[2].getGameValue() == hand[3].getGameValue() + 1 && hand[3].getGameValue() == hand[4].getGameValue() + 1)
			check = true;
		return check;
	}

	// checks if there are 2 pairs in the hand, but first checks if it is not a full house, four of a kind or three of a kind
	// because they have a greater value
	public boolean isTwoPair() {
		boolean check = false;
		if(this.isFullHouse())
			check = false;
		else if(this.isFourOfAKind())
			check = false;
		else if(this.isThreeOfAKind())
			check = false;
		else if((hand[0].getGameValue() == hand[1].getGameValue() && hand[2].getGameValue() == hand[3].getGameValue()) || (hand[1].getGameValue() == hand[2].getGameValue() && hand[3].getGameValue() == hand[4].getGameValue()) || (hand[0].getGameValue() == hand[1].getGameValue() && hand[3].getGameValue() == hand[4].getGameValue()))
			check = true;
		return check;
	}

	// if no other method return true then it is a high hand
	public boolean isHighHand() {
		boolean check = false;
		if(this.isFlush())
			check = false;
		else if(this.isFourOfAKind())
			check = false;
		else if(this.isFullHouse())
			check = false;
		else if(this.isOnePair())
			check = false;
		else if(this.isRoyalFlush())
			check = false;
		else if(this.isStraight())
			check = false;
		else if(this.isStraightFlush())
			check = false;
		else if(this.isThreeOfAKind())
			check = false;
		else if(this.isTwoPair())
			check = false;
		else
			check = true;
		return check;
	}

	// gets the value of a hand based on the default values + whatever is in the hand from the specified poker hand
	// e.g. AJJ87 is a one pair, so it gets the default value for one pair + the two JJ added to that
	public int getGameValue() {
		int gameValue = 0;
		if(this.isFlush())
			gameValue = HandOfCards.FLUSH_DEFAULT + this.countCardValues(HandOfCards.LOOP_FIVE_TIMES);
		else if(this.isFourOfAKind())
			gameValue = HandOfCards.FOUR_OF_KIND_DEFAULT + this.countCardValues(HandOfCards.LOOP_FOUR_TIMES);
		else if(this.isFullHouse())
			gameValue = HandOfCards.FULL_HOUSE_DEFAULT + this.countCardValues(HandOfCards.LOOP_FIVE_TIMES);
		else if(this.isOnePair())
			gameValue = HandOfCards.ONE_PAIR_DEFAULT + this.countCardValues(HandOfCards.LOOP_TWICE);
		else if(this.isRoyalFlush())
			gameValue = HandOfCards.ROYAL_FLUSH_DEFAULT + this.countCardValues(HandOfCards.LOOP_FIVE_TIMES);
		else if(this.isStraight())
			gameValue = HandOfCards.STRAIGHT_DEFAULT + this.countCardValues(HandOfCards.LOOP_FIVE_TIMES);
		else if(this.isStraightFlush())
			gameValue = HandOfCards.STRAIGHT_FLUSH_DEFAULT + this.countCardValues(HandOfCards.LOOP_FIVE_TIMES);
		else if(this.isThreeOfAKind())
			gameValue = HandOfCards.THREE_OF_KIND_DEFAULT + this.countCardValues(HandOfCards.LOOP_THRICE);
		else if(this.isTwoPair())
			gameValue = HandOfCards.TWO_PAIR_DEFAULT + this.countCardValues(HandOfCards.LOOP_FOUR_TIMES);
		else if(this.isHighHand())
			gameValue = HandOfCards.HIGH_HAND_DEFAULT + this.countCardValues(HandOfCards.LOOP_FIVE_TIMES);
		return gameValue;
	}

	// this method is used to count the values of the cards in the hand, it loops 5 times to get the hand value or goes into the equivalent if statement if number is < 5 to break ties in hands e.g. AAAA7 will win over KKKKQ(4 times, so goes into if statement)
	private int countCardValues(int timesToLoop) {
		int i, sum = 0;
		if(hand[0] != null && hand[1] != null && hand[2] != null && hand[3] != null && hand[4] != null) {
			// if this method gets a 2 this means it a one pair, it can be in 4 places so we need to check where it is and add that to the sum
			if(timesToLoop == 2) {
				if(hand[0].getGameValue() == hand[1].getGameValue())
					sum += hand[0].getGameValue() + hand[1].getGameValue();
				else if(hand[1].getGameValue() == hand[2].getGameValue())
					sum += hand[1].getGameValue() + hand[2].getGameValue();
				else if(hand[2].getGameValue() == hand[3].getGameValue())
					sum += hand[2].getGameValue() + hand[3].getGameValue();
				else
					sum += hand[3].getGameValue() + hand[4].getGameValue();
			}
			// if this method gets a 3 this means it a three of a kind, it can be in 3 places so we need to check where it is and add that to the sum
			else if(timesToLoop == 3) {
				if(hand[0].getGameValue() == hand[1].getGameValue() && hand[1].getGameValue() == hand[2].getGameValue())
					sum += hand[0].getGameValue() + hand[1].getGameValue() + hand[2].getGameValue();
				else if(hand[1].getGameValue() == hand[2].getGameValue() && hand[2].getGameValue() == hand[3].getGameValue())
					sum += hand[1].getGameValue() + hand[2].getGameValue() + hand[3].getGameValue();
				else
					sum += hand[2].getGameValue() + hand[3].getGameValue() + hand[4].getGameValue();
			}
			// if this method gets a 4 this means it a two pair OR a four of a kind, it can be in a few places so we need to check where it is and add that to the sum
			else if(timesToLoop == 4) {
				if(hand[0].getGameValue() == hand[1].getGameValue() && hand[2].getGameValue() == hand[3].getGameValue())
					sum += hand[0].getGameValue() + hand[1].getGameValue() + hand[2].getGameValue() + hand[3].getGameValue();
				else if(hand[1].getGameValue() == hand[2].getGameValue() && hand[3].getGameValue() == hand[4].getGameValue())
					sum += hand[1].getGameValue() + hand[2].getGameValue() + hand[3].getGameValue() + hand[4].getGameValue();
				else if(hand[0].getGameValue() == hand[1].getGameValue() && hand[3].getGameValue() == hand[4].getGameValue())
					sum += hand[0].getGameValue() + hand[1].getGameValue() + hand[3].getGameValue() + hand[4].getGameValue();
				else if(hand[0].getGameValue() == hand[1].getGameValue() && hand[1].getGameValue() == hand[2].getGameValue() && hand[2].getGameValue() == hand[3].getGameValue())
					sum += hand[0].getGameValue() + hand[1].getGameValue() + hand[2].getGameValue() + hand[3].getGameValue();
				else
					sum += hand[1].getGameValue() + hand[2].getGameValue() + hand[3].getGameValue() + hand[4].getGameValue();
			}
			else {
				for(i = 0; i < timesToLoop; i++) {
					sum += hand[i].getGameValue();		// it adds each card to the sum
				}
			}
		}
		return sum;
	}
	
	/*We got the odds for different probabilities of outcomes after discarding a card improving a hand the user currently holds from 'https://rip94550.wordpress.com/2011/03/21/draw-poker-%E2%80%93-improving-the-hand/'
	 * We converted the odds to percentage using this website: 'http://www.calculatorsoup.com/calculators/games/odds.php'
	 * For some methods By default discardProbability is zero. In some methods value returned is 100 as for example in high hand if no 4 flush or broken
	 * straight are there, discarding the weakest card is best option.*/
	

	public int getDiscardProbability(int cardPosition) {
		discardProbability = 0;							// 0 by default
		if(cardPosition >= 0 && cardPosition < HandOfCards.SIZE) {
			if(this.isRoyalFlush() || this.isStraightFlush() || this.isFourOfAKind() || this.isFullHouse());
			else if(this.isBrokenFlush()) {
				if(cardPosition == brokenCardPos) {
					discardProbability = 20;
				}
				// otherwise discardProbability is 0 by default
			}
			else if(this.isBrokenStraight()){
				if(this.isBrokenCardStraight(cardPosition))
					discardProbability = 19;
			}
			else if(this.isThreeOfAKind()) {
				// this allows us to improve to a four of a kind or a full house
				if(!this.checkForThreeCards(cardPosition))
					discardProbability = 10;				// high probability due to the card not being in the three of a kind
			}
			else if(this.isTwoPair()) {
				// this allows us to improve to a full house
				if(!this.checkForPair(cardPosition))
					discardProbability = 9;				// moderate probability due to the card not being in two pair
			}
			else if(this.isOnePair()) {
				if(!this.checkForPair(cardPosition))
					discardProbability = 29;				// high probability because we can get a three of a kind if we discard 1 card, which will bring us to the three of a kind probability calculator
			}
			else if(this.isHighHand()) {
				// the last card is always the worst so it is a definite discard, lowers down with the card number
				if(cardPosition == 4)
					discardProbability = 100;
				else if(cardPosition == 3)
					discardProbability = 0;
				else if(cardPosition == 2)
					discardProbability = 0;
				else if(cardPosition == 1)
					discardProbability = 0;
				else if(cardPosition == 0)
					discardProbability = 0;
			}
		}
		return discardProbability;
	}

	// if it is not a flush then it is a broken flush due to our previous checks in getDiscardProbabilty
	// ONLY WORKS IF ONE CARD HAS A DIFFERENT SUIT, IF TWO OR MORE THEN DOESNT WORK
	private boolean isBrokenFlush() {
		boolean check = false;
		if(this.isFlush())
			check = false;
		else if(this.isStraight() || this.isThreeOfAKind() || this.isTwoPair() || this.isOnePair())
			check = false;
		else if(hand[0].getSuit() == hand[1].getSuit() && hand[1].getSuit() == hand[2].getSuit() && hand[2].getSuit() == hand[3].getSuit() && hand[3].getSuit() != hand[4].getSuit()) {
			check = true;
			brokenCardPos = 4;		//4, the position of the broken card
		}
		else if(hand[0].getSuit() == hand[1].getSuit() && hand[1].getSuit() == hand[2].getSuit() && hand[2].getSuit() != hand[3].getSuit() && hand[2].getSuit() == hand[4].getSuit()) {
			check = true;
			brokenCardPos = 3;		//3, the position of the broken card
		}
		else if(hand[0].getSuit() == hand[1].getSuit() && hand[1].getSuit() != hand[2].getSuit() && hand[1].getSuit() == hand[3].getSuit() && hand[3].getSuit() == hand[4].getSuit()) {
			check = true;
			brokenCardPos = 2;		//2, the position of the broken card
		}
		else if(hand[0].getSuit() != hand[1].getSuit() && hand[0].getSuit() == hand[2].getSuit() && hand[2].getSuit() == hand[3].getSuit() && hand[3].getSuit() == hand[4].getSuit()) {
			check = true;
			brokenCardPos = 1;		//1, the position of the broken card
		}
		else if(hand[0].getSuit() != hand[1].getSuit() && hand[1].getSuit() == hand[2].getSuit() && hand[2].getSuit() == hand[3].getSuit() && hand[3].getSuit() == hand[4].getSuit()) {
			check = true;
			brokenCardPos = 0;		//0, the position of the broken card
		}
		return check;
	}

	// if it is not a straight then it is a broken straight due to our previous checks in getDiscardProbabilty
	private boolean isBrokenStraight() {
		boolean check = false;;
		if(this.isStraight())
			check = false;
		else if(this.isThreeOfAKind() || this.isTwoPair() || this.isOnePair())
			check = false;
		else
			check = true;
		return check;
	}

	// checks which position the broken card is in
	private boolean isBrokenCardStraight(int cardPos) {
		boolean check = false;
		if (cardPos >= 0 && cardPos < HandOfCards.SIZE - 2 && hand[cardPos].getGameValue() == hand[cardPos+1].getGameValue() + 1 && hand[cardPos+1].getGameValue() + 1 == hand[cardPos+2].getGameValue() + 2)
			check = false;
		else if (cardPos < HandOfCards.SIZE - 1 && cardPos >= 1 && hand[cardPos].getGameValue() == hand[cardPos+1].getGameValue() + 1 && hand[cardPos+1].getGameValue() + 1 == hand[cardPos-1].getGameValue() - 1)
			check = false;
		else if (cardPos < HandOfCards.SIZE && cardPos >= 2 && hand[cardPos].getGameValue() == hand[cardPos-2].getGameValue() -2 && hand[cardPos-1].getGameValue() - 1 == hand[cardPos].getGameValue())
			check = false;
		else if (cardPos < HandOfCards.SIZE - 2 && hand[cardPos+1].getGameValue() + 1 == hand[cardPos+2].getGameValue() + 2)
			check = false;
		else
			check = true;
		return check;
	}

	/* method to check cards 2 to the right, 2 to the left and 1 on each side of the given position
	 * returns true if it found the same cards using the given criteria
	 */
	private boolean checkForThreeCards(int cardPos) {
		boolean check = false;			// if all checks fail, the default is false
		if((cardPos < HandOfCards.SIZE - 2 && hand[cardPos].getGameValue() == hand[cardPos+1].getGameValue() && hand[cardPos].getGameValue() == hand[cardPos+2].getGameValue()) ||
				(cardPos > HandOfCards.SIZE - 4 && hand[cardPos].getGameValue() == hand[cardPos-1].getGameValue() && hand[cardPos].getGameValue() == hand[cardPos-2].getGameValue()) ||
				(cardPos < HandOfCards.SIZE - 1 && cardPos > HandOfCards.SIZE - 5 && hand[cardPos].getGameValue() == hand[cardPos+1].getGameValue() && hand[cardPos].getGameValue() == hand[cardPos-1].getGameValue()))
			check = true;
		return check;
	}

	// method to check if a pair is present, if the given position is part of the pair then the probability of discarding it is 0(given in getDiscardedProbability)
	private boolean checkForPair(int cardPos) {
		boolean check = false;
		if(cardPos < HandOfCards.SIZE - 1 && hand[cardPos].getGameValue() == hand[cardPos+1].getGameValue() ||
				(cardPos > HandOfCards.SIZE - 5 && hand[cardPos].getGameValue() == hand[cardPos-1].getGameValue()))
			check = true;
		return check;
	}

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

	public PlayingCard getCardAt(int i)
	{
		return hand[i];
	}

	public static void main(String[] args) {
		DeckOfCards deck = new DeckOfCards();
		deck.shuffle();
		HandOfCards handOfCards = new HandOfCards(deck);
		HandOfCards handOfCards2 = new HandOfCards(deck);
		/**
		 * TESTING CODE FOR ASSIGNMENT 3
		 *
		 //create 5 cards for testing purposes
		 PlayingCard card1 = new PlayingCard("A", PlayingCard.HEARTS, 1, 14);
		 PlayingCard card2 = new PlayingCard("K", PlayingCard.HEARTS, 13, 13);
		 PlayingCard card3 = new PlayingCard("Q", PlayingCard.HEARTS, 12, 13);
		 PlayingCard card4 = new PlayingCard("J", PlayingCard.HEARTS, 11, 13);
		 PlayingCard card5 = new PlayingCard("10", PlayingCard.HEARTS, 10, 10);
		 // overload the cards from the deck for testing purposes
		 handOfCards.handOverloadTesting(card1, card2, card3, card4, card5);
		 // check if the overloading was successful
		 System.out.println(handOfCards.toString() + "\n");
		 // print out the methods to see if they work correctly with the overloaded cards
		 System.out.println("Royal flush: " + handOfCards.isRoyalFlush() + "\n");
		 System.out.println("Straight Flush: " + handOfCards.isStraightFlush() + "\n");
		 System.out.println("Four of a kind: " + handOfCards.isFourOfAKind() + "\n");
		 System.out.println("Full house: " + handOfCards.isFullHouse() + "\n");
		 System.out.println("Flush: " + handOfCards.isFlush() + "\n");
		 System.out.println("Straight: " + handOfCards.isStraight() + "\n");
		 System.out.println("Three of a kind: " + handOfCards.isThreeOfAKind() + "\n");
		 System.out.println("Two pair: " + handOfCards.isTwoPair() + "\n");
		 System.out.println("One pair: " + handOfCards.isOnePair() + "\n");
		 System.out.println("High hand: " + handOfCards.isHighHand() + "\n");
		 */

		/**
		 * TESTING CODE FOR ASSIGNMENT 4
		 *
		 // you may only change the game value of the cards to save time in testing the class
		 // example FULL HAND in both cases - AAA77 = 14+14+14+7+7 = 56 and KKKQQ = 13+13+13+12+12 = 60 ~~~ so hand 2 is better
		 // create 10 cards for testing purposes
		 PlayingCard card1 = new PlayingCard("A", PlayingCard.HEARTS, 1, 14);
		 PlayingCard card2 = new PlayingCard("A", PlayingCard.CLUBS, 1, 14);
		 PlayingCard card3 = new PlayingCard("A", PlayingCard.SPADES, 1, 14);
		 PlayingCard card4 = new PlayingCard("7", PlayingCard.DIAMONDS, 7, 7);
		 PlayingCard card5 = new PlayingCard("7", PlayingCard.HEARTS, 7, 7);

		 PlayingCard card6 = new PlayingCard("K", PlayingCard.HEARTS, 13, 13);
		 PlayingCard card7 = new PlayingCard("K", PlayingCard.CLUBS, 13, 13);
		 PlayingCard card8 = new PlayingCard("K", PlayingCard.SPADES, 13, 13);
		 PlayingCard card9 = new PlayingCard("Q", PlayingCard.DIAMONDS, 12, 12);
		 PlayingCard card10 = new PlayingCard("Q", PlayingCard.HEARTS, 12, 12);
		 // overload the cards from the deck for testing purposes
		 handOfCards.handOverloadTesting(card1, card2, card3, card4, card5);
		 handOfCards2.handOverloadTesting(card6, card7, card8, card9, card10);
		 // check if the overloading was successful
		 System.out.println(handOfCards.toString() + "\n" + handOfCards2.toString() + "\n");
		 if(handOfCards.getGameValue() > handOfCards2.getGameValue())
		 System.out.println("Hand 1 is better.");
		 else if(handOfCards.getGameValue() < handOfCards2.getGameValue())
		 System.out.println("Hand 2 is better.");
		 else
		 System.out.println("Both hands are the same in value.");
		 */

		/*
		 * =====================
		 * FLUSH PROBABILTY TEST
		 * =====================
		 */
		System.out.println("=============" + "\n" + "FLUSH TESTING" + "\n" + "=============");
		// create 10 cards for testing purposes
		PlayingCard card1 = new PlayingCard("J", PlayingCard.HEARTS, 10, 10);
		PlayingCard card2 = new PlayingCard("9", PlayingCard.HEARTS, 9, 9);
		PlayingCard card3 = new PlayingCard("6", PlayingCard.HEARTS, 6, 6);
		PlayingCard card4 = new PlayingCard("4", PlayingCard.HEARTS, 4, 4);
		PlayingCard card5 = new PlayingCard("2", PlayingCard.CLUBS, 2, 2);
		// overload the cards from the deck for testing purposes
		handOfCards.handOverloadTesting(card1, card2, card3, card4, card5);
		// check if the overloading was successful
		System.out.println(handOfCards.toString() + "\n");
		// get the discard probability
		for(int i = 0; i < HandOfCards.SIZE; i++)
			System.out.println(handOfCards.getDiscardProbability(i));

		// create 10 cards for testing purposes
		PlayingCard card11 = new PlayingCard("J", PlayingCard.HEARTS, 10, 10);
		PlayingCard card21 = new PlayingCard("9", PlayingCard.HEARTS, 9, 9);
		PlayingCard card31 = new PlayingCard("6", PlayingCard.HEARTS, 6, 6);
		PlayingCard card41 = new PlayingCard("4", PlayingCard.CLUBS, 4, 4);
		PlayingCard card51 = new PlayingCard("2", PlayingCard.HEARTS, 2, 2);
		// overload the cards from the deck for testing purposes
		handOfCards.handOverloadTesting(card11, card21, card31, card41, card51);
		// check if the overloading was successful
		System.out.println(handOfCards.toString() + "\n");
		// get the discard probability
		for(int i = 0; i < HandOfCards.SIZE; i++)
			System.out.println(handOfCards.getDiscardProbability(i));

		// create 10 cards for testing purposes
		PlayingCard card12 = new PlayingCard("J", PlayingCard.HEARTS, 10, 10);
		PlayingCard card22 = new PlayingCard("9", PlayingCard.HEARTS, 9, 9);
		PlayingCard card32 = new PlayingCard("6", PlayingCard.CLUBS, 6, 6);
		PlayingCard card42 = new PlayingCard("4", PlayingCard.HEARTS, 4, 4);
		PlayingCard card52 = new PlayingCard("2", PlayingCard.HEARTS, 2, 2);
		// overload the cards from the deck for testing purposes
		handOfCards.handOverloadTesting(card12, card22, card32, card42, card52);
		// check if the overloading was successful
		System.out.println(handOfCards.toString() + "\n");
		// get the discard probability
		for(int i = 0; i < HandOfCards.SIZE; i++)
			System.out.println(handOfCards.getDiscardProbability(i));

		// create 10 cards for testing purposes
		PlayingCard card13 = new PlayingCard("J", PlayingCard.HEARTS, 10, 10);
		PlayingCard card23 = new PlayingCard("9", PlayingCard.CLUBS, 9, 9);
		PlayingCard card33 = new PlayingCard("6", PlayingCard.HEARTS, 6, 6);
		PlayingCard card43 = new PlayingCard("4", PlayingCard.HEARTS, 4, 4);
		PlayingCard card53 = new PlayingCard("2", PlayingCard.HEARTS, 2, 2);
		// overload the cards from the deck for testing purposes
		handOfCards.handOverloadTesting(card13, card23, card33, card43, card53);
		// check if the overloading was successful
		System.out.println(handOfCards.toString() + "\n");
		// get the discard probability
		for(int i = 0; i < HandOfCards.SIZE; i++)
			System.out.println(handOfCards.getDiscardProbability(i));

		// create 10 cards for testing purposes
		PlayingCard card14 = new PlayingCard("J", PlayingCard.CLUBS, 10, 10);
		PlayingCard card24 = new PlayingCard("9", PlayingCard.HEARTS, 9, 9);
		PlayingCard card34 = new PlayingCard("6", PlayingCard.HEARTS, 6, 6);
		PlayingCard card44 = new PlayingCard("4", PlayingCard.HEARTS, 4, 4);
		PlayingCard card54 = new PlayingCard("2", PlayingCard.HEARTS, 2, 2);
		// overload the cards from the deck for testing purposes
		handOfCards.handOverloadTesting(card14, card24, card34, card44, card54);
		// check if the overloading was successful
		System.out.println(handOfCards.toString() + "\n");
		// get the discard probability
		for(int i = 0; i < HandOfCards.SIZE; i++)
			System.out.println(handOfCards.getDiscardProbability(i));

		/*
		 * ===============================
		 * THREE OF A KIND PROBABILTY TEST
		 * ===============================
		 */
		System.out.println("\n" + "========================" + "\n" + "THREE OF A KIND TESTING" + "\n" + "========================");
		// create 10 cards for testing purposes
		PlayingCard card15 = new PlayingCard("10", PlayingCard.CLUBS, 10, 10);
		PlayingCard card25 = new PlayingCard("10", PlayingCard.HEARTS, 10, 10);
		PlayingCard card35 = new PlayingCard("10", PlayingCard.SPADES, 10, 10);
		PlayingCard card45 = new PlayingCard("9", PlayingCard.HEARTS, 9, 9);
		PlayingCard card55 = new PlayingCard("8", PlayingCard.HEARTS, 8, 8);
		// overload the cards from the deck for testing purposes
		handOfCards.handOverloadTesting(card15, card25, card35, card45, card55);
		// check if the overloading was successful
		System.out.println(handOfCards.toString() + "\n");
		// get the discard probability
		for(int i = 0; i < HandOfCards.SIZE; i++)
			System.out.println(handOfCards.getDiscardProbability(i));

		// create 10 cards for testing purposes
		PlayingCard card16 = new PlayingCard("J", PlayingCard.CLUBS, 11, 11);
		PlayingCard card26 = new PlayingCard("8", PlayingCard.HEARTS, 8, 8);
		PlayingCard card36 = new PlayingCard("8", PlayingCard.CLUBS, 8, 8);
		PlayingCard card46 = new PlayingCard("8", PlayingCard.SPADES, 8, 8);
		PlayingCard card56 = new PlayingCard("7", PlayingCard.HEARTS, 7, 7);
		// overload the cards from the deck for testing purposes
		handOfCards.handOverloadTesting(card16, card26, card36, card46, card56);
		// check if the overloading was successful
		System.out.println(handOfCards.toString() + "\n");
		// get the discard probability
		for(int i = 0; i < HandOfCards.SIZE; i++)
			System.out.println(handOfCards.getDiscardProbability(i));

		// create 10 cards for testing purposes
		PlayingCard card17 = new PlayingCard("K", PlayingCard.CLUBS, 13, 13);
		PlayingCard card27 = new PlayingCard("Q", PlayingCard.HEARTS, 12, 12);
		PlayingCard card37 = new PlayingCard("J", PlayingCard.HEARTS, 11, 11);
		PlayingCard card47 = new PlayingCard("J", PlayingCard.CLUBS, 11, 11);
		PlayingCard card57 = new PlayingCard("J", PlayingCard.SPADES, 11, 11);
		// overload the cards from the deck for testing purposes
		handOfCards.handOverloadTesting(card17, card27, card37, card47, card57);
		// check if the overloading was successful
		System.out.println(handOfCards.toString() + "\n");
		// get the discard probability
		for(int i = 0; i < HandOfCards.SIZE; i++)
			System.out.println(handOfCards.getDiscardProbability(i));

		/*
		 * ========================
		 * TWO PAIR PROBABILTY TEST
		 * ========================
		 */
		System.out.println("\n" + "================" + "\n" + "TWO PAIR TESTING" + "\n" + "================");
		// create 10 cards for testing purposes
		PlayingCard card19 = new PlayingCard("K", PlayingCard.CLUBS, 13, 13);
		PlayingCard card29 = new PlayingCard("K", PlayingCard.HEARTS, 13, 13);
		PlayingCard card39 = new PlayingCard("Q", PlayingCard.HEARTS, 12, 12);
		PlayingCard card49 = new PlayingCard("Q", PlayingCard.CLUBS, 12, 12);
		PlayingCard card59 = new PlayingCard("J", PlayingCard.SPADES, 11, 11);
		// overload the cards from the deck for testing purposes
		handOfCards.handOverloadTesting(card19, card29, card39, card49, card59);
		// check if the overloading was successful
		System.out.println(handOfCards.toString() + "\n");
		// get the discard probability
		for(int i = 0; i < HandOfCards.SIZE; i++)
			System.out.println(handOfCards.getDiscardProbability(i));

		// create 10 cards for testing purposes
		PlayingCard card201 = new PlayingCard("K", PlayingCard.CLUBS, 13, 13);
		PlayingCard card202 = new PlayingCard("K", PlayingCard.HEARTS, 13, 13);
		PlayingCard card203 = new PlayingCard("Q", PlayingCard.HEARTS, 12, 12);
		PlayingCard card204 = new PlayingCard("J", PlayingCard.CLUBS, 11, 11);
		PlayingCard card205 = new PlayingCard("J", PlayingCard.SPADES, 11, 11);
		// overload the cards from the deck for testing purposes
		handOfCards.handOverloadTesting(card201, card202, card203, card204, card205);
		// check if the overloading was successful
		System.out.println(handOfCards.toString() + "\n");
		// get the discard probability
		for(int i = 0; i < HandOfCards.SIZE; i++)
			System.out.println(handOfCards.getDiscardProbability(i));

		// create 10 cards for testing purposes
		PlayingCard card206 = new PlayingCard("K", PlayingCard.CLUBS, 13, 13);
		PlayingCard card207 = new PlayingCard("Q", PlayingCard.HEARTS, 12, 12);
		PlayingCard card208 = new PlayingCard("Q", PlayingCard.HEARTS, 12, 12);
		PlayingCard card209 = new PlayingCard("J", PlayingCard.CLUBS, 11, 11);
		PlayingCard card210 = new PlayingCard("J", PlayingCard.SPADES, 11, 11);
		// overload the cards from the deck for testing purposes
		handOfCards.handOverloadTesting(card206, card207, card208, card209, card210);
		// check if the overloading was successful
		System.out.println(handOfCards.toString() + "\n");
		// get the discard probability
		for(int i = 0; i < HandOfCards.SIZE; i++)
			System.out.println(handOfCards.getDiscardProbability(i));

		// ONE PAIR USES SAME METHOD AS TWO PAIR SO IT WORKS
		// HIGH HAND DOES NOT NEED TESTING AS YOU CAN SEE THE CODE WORKS JUST BY LOOKING AT IT IN THE METHOD

		/*
		 * ========================
		 * STRAIGHT PROBABILTY TEST
		 * ========================
		 */
		System.out.println("\n" + "================" + "\n" + "STRAIGHT TESTING" + "\n" + "================");
		// create 10 cards for testing purposes
		PlayingCard card211 = new PlayingCard("10", PlayingCard.CLUBS, 10, 10);
		PlayingCard card212 = new PlayingCard("7", PlayingCard.HEARTS, 7, 7);
		PlayingCard card213 = new PlayingCard("5", PlayingCard.HEARTS, 5, 5);
		PlayingCard card214 = new PlayingCard("4", PlayingCard.CLUBS, 4, 4);
		PlayingCard card215 = new PlayingCard("3", PlayingCard.SPADES, 3, 3);
		// overload the cards from the deck for testing purposes
		handOfCards.handOverloadTesting(card211, card212, card213, card214, card215);
		// check if the overloading was successful
		System.out.println(handOfCards.toString() + "\n");
		// get the discard probability
		for(int i = 0; i < HandOfCards.SIZE; i++)
			System.out.println(handOfCards.getDiscardProbability(i));

		// create 10 cards for testing purposes
		PlayingCard card216 = new PlayingCard("K", PlayingCard.CLUBS, 13, 13);
		PlayingCard card217 = new PlayingCard("Q", PlayingCard.HEARTS, 12, 12);
		PlayingCard card218 = new PlayingCard("J", PlayingCard.HEARTS, 11, 11);
		PlayingCard card219 = new PlayingCard("10", PlayingCard.CLUBS, 10, 10);
		PlayingCard card220 = new PlayingCard("5", PlayingCard.SPADES, 5, 5);
		// overload the cards from the deck for testing purposes
		handOfCards.handOverloadTesting(card216, card217, card218, card219, card220);
		// check if the overloading was successful
		System.out.println(handOfCards.toString() + "\n");
		// get the discard probability
		for(int i = 0; i < HandOfCards.SIZE; i++)
			System.out.println(handOfCards.getDiscardProbability(i));

		//TAKES THIS AS A ONE PAIR
		// create 10 cards for testing purposes
		PlayingCard card221 = new PlayingCard("A", PlayingCard.SPADES, 14, 11);
		PlayingCard card222 = new PlayingCard("K", PlayingCard.HEARTS, 13, 9);
		PlayingCard card223 = new PlayingCard("K", PlayingCard.HEARTS, 13, 8);
		PlayingCard card224 = new PlayingCard("Q", PlayingCard.CLUBS, 12, 7);
		PlayingCard card225 = new PlayingCard("J", PlayingCard.CLUBS, 11, 6);
		// overload the cards from the deck for testing purposes
		handOfCards.handOverloadTesting(card221, card222, card223, card224, card225);
		// check if the overloading was successful
		System.out.println(handOfCards.toString() + "\n\n" + "This is taken as a one pair instead of a straight." + "\n");
		// get the discard probability
		for(int i = 0; i < HandOfCards.SIZE; i++)
			System.out.println(handOfCards.getDiscardProbability(i));

	}

}