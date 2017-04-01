package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A deck of card class which will create a 52 instances of a deck of card
 * It also have method called shuffle which shuffles the deck of card
 * A method called reset which will reset the deck of card by creating a new deck and shuffle the deck
 * A method dealNext which will be used to deal a card or return null if there is no card left to be dealt
 * A method returnCard which will be used to add the discarded cards  
 * @author adam
 *
 */

public class DeckOfCards {
	private static List<PlayingCard> deck;
	private static final int MAXLENGTH = 52*52;
	private static final int SUITSIZE = 4;
	private static final int CARDSIZE= 13;
	private static final int DECKSIZE = 52;
	private  int dealCard = 0;
	private static final int ACE_FACE_VALUE = 1;
	private static final int ACE_GAMEVALUE= 14;
	
	/**
	 * A constructor that creates 52 instances of a playing cards
	 */
	public DeckOfCards(){
		reset();
	}
	
	/**
	 * A method that will create a new set of deck of cards and shuffle the deck.
	 */
	public synchronized void reset(){
		String [] TYPEOFCARD = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
		char [] SUIT= {'C', 'D', 'H', 'S'};
		
		deck = Collections.synchronizedList(new ArrayList<PlayingCard>());
		for(int suits = 0; suits < SUITSIZE; suits++){
			for(int cardType = 0; cardType < CARDSIZE; cardType++){
				if(TYPEOFCARD[cardType] == "A"){
					deck.add(new PlayingCard(TYPEOFCARD[cardType], SUIT[suits], ACE_FACE_VALUE, ACE_GAMEVALUE));	 // exception for an Ace card
				}
				else{
					deck.add(new PlayingCard(TYPEOFCARD[cardType], SUIT[suits], cardType + 1, cardType + 1));
				}
			}
		}
		shuffle();
		dealCard = 0;
		
	}
	
	/**
	 * A method that shuffles the deck of cards 2704
	 */
	public synchronized void shuffle(){ 
		dealCard = 0;
		Random random = new Random(); 
		
		for(int i = 0; i < MAXLENGTH; i++){
			int rand1 = random.nextInt(52);
			int rand2 = random.nextInt(52);
			
			PlayingCard temp  = deck.get(rand1);
			deck.set(rand1, deck.get(rand2));
			deck.set(rand2, temp);
		}
	}
	
	/**
	 * A method that allows the player to deal a card by removing the first element in the deck
	 * @return the card that has been dealt or return null if there no card left to be dealt
	 */
	public synchronized PlayingCard dealNext(){
		
		if(dealCard == DECKSIZE){
			System.out.println("Deck is empty!\n");
			dealCard = 0;
			return null;
		}
		dealCard++;
		
		return deck.remove(0);
	}
	
	
	
	/**
	 * A method that add the dealt card to the back of the deck and it will not be dealt again unless the deck is reset again
	 * @param discarded
	 */
	public synchronized void  returnCard(PlayingCard discarded){
		if(discarded != null){
			deck.add(discarded);
		}
	}

	public static void main(String[] args) {
		DeckOfCards deckOfCard  = new DeckOfCards();
		
			// test to print out the drawn deck of cards
		
			System.out.println("Draw deck of Card");
			for(PlayingCard card: deck){
				System.out.print(card.toString() + " ");
			}
			System.out.println("\n");
			
			
			// test to shuffle the deck of card
			
			System.out.println("Shuffled deck of cards");
			deckOfCard.shuffle();
			for(PlayingCard card: deck){
				System.out.print(card.toString() + " ");	
			}
			System.out.println("\n");
			
			
			// test to deal 5 cards in the deck
			
			System.out.println("Deal 5 cards in the deck");
			for(int i = 0 ; i < 5; i++){
				PlayingCard discardCard = deckOfCard.dealNext();
				if(discardCard == null){
				}
				else{
					deckOfCard.returnCard(discardCard);
					System.out.print("Discarded card [" + discardCard + "] = " +  "  ");
					for(PlayingCard card: deck){
						System.out.print(card.toString() + " ");	
					}
					System.out.println("\n");
				}
			}
			

			// test to reset the deck and create a new deck
			
			System.out.println("Reset and create a new shuffled deck of cards");
			deckOfCard.reset();
			for(PlayingCard card: deck){
				System.out.print(card.toString() + " ");
			}
			System.out.println("\n");
			
			// test to deal all the cards in the deck
			
			System.out.println("\n\n");
			System.out.println("Deal all cards in the deck");
			for(int i = 0 ; i < 53; i++){
				PlayingCard discard_card = deckOfCard.dealNext();
				if(discard_card == null){
				}
				else{
					deckOfCard.returnCard(discard_card);
					System.out.print("Discarded card [" + discard_card + "] = " +  "  ");
						for(PlayingCard card: deck){
							System.out.print(card.toString() + " ");	
						}
						System.out.println("\n");
				}
			}
		}	
}
