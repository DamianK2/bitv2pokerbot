package poker;

/**
 * Holds an information about a single card.
 * @param cardType
 * @param cardSuit
 * @param faceValue
 * @param gameValue
 */
public class PlayingCard {

	static public final char HEARTS = 'H';
	static public final char SPADES = 'S';
	static public final char DIAMONDS = 'D';
	static public final char CLUBS = 'C';
	
	private String cardType;
	private char cardSuit;
	private int faceValue;
	private int gameValue;
	
	public PlayingCard(String cardType, char cardSuit, int faceValue, int gameValue)
	{
		this.cardType = cardType;
		this.cardSuit = cardSuit;
		this.faceValue = faceValue;
		this.gameValue = gameValue;
	}
	
	public char getSuit()
	{
		return this.cardSuit;
	}
	
	public int getFaceValue()
	{
		return this.faceValue;
	}
	
	public int getGameValue()
	{
		return this.gameValue;
	}
	
	public String toString() 
	{
		String card = "";
		switch(this.cardSuit) {
			case PlayingCard.SPADES:
				card = this.cardType + (char)'\u2660';
				break;
			case PlayingCard.HEARTS:
				card = this.cardType + (char)'\u2665';
				break;
			case PlayingCard.DIAMONDS:
				card = this.cardType + (char)'\u2666';
				break;
			case PlayingCard.CLUBS:
				card = this.cardType + (char)'\u2663';
				break;
		}
		
		return card;
	}
	
	public static void main(String[] args) {
		
		// Create arrays for testing
		String[] cardTypes = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
		char[] cardSuits = {PlayingCard.HEARTS, PlayingCard.SPADES, PlayingCard.DIAMONDS, PlayingCard.CLUBS};
		int[] faceValues = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 1};
		int[] gameValues = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
		PlayingCard[] cardSet = new PlayingCard[52];
		
		// Go through data arrays to initialise 52 cards. Select a suit and generate cards for each suit.
		int setIndex = 0;
		for (char suit : cardSuits)
		{
			for (int i = 0; i < 13; i++)
			{
				cardSet[setIndex] = new PlayingCard(cardTypes[i], suit, faceValues[i], gameValues[i]);
				setIndex++;	
			}
		}
		
		for (PlayingCard card : cardSet)
		{
			System.out.println(card.toString());
			//System.out.println( " --FaceValue: " + card.getFaceValue() + " --HandValue: " + card.getGameValue());
		}
		
		
	}
}