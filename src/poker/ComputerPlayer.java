package poker;
import java.util.Arrays;
import java.util.Random;


public class ComputerPlayer extends PokerPlayer {

	private String behaviourType;
	private static final int NO_OF_NAMES = 20, NO_OF_BEHAVIOURS = 3;
	
	public ComputerPlayer(DeckOfCards deck) {
        super(deck);
        this.name = this.getRandomName();
        this.behaviourType = this.getRandomBehaviour();
        this.isHuman = false;
    }

    private String getRandomName() {
        int position = this.generateRandomNumber(ComputerPlayer.NO_OF_NAMES);
        String[] names = {"Brittanie", "Jim", "Lilli", "Daysi", "Elouise", "Horace", "Corazon", "Agustin", "Magan", "Vivan", "Retta", "Normand", "Rory", "Erick", "Antonio", "Gisele", "Parker", "Marla", "Cassie", "Aurelia"};
        return names[position];
    }
    
    private String getRandomBehaviour() {
    	int position = this.generateRandomNumber(ComputerPlayer.NO_OF_BEHAVIOURS);
    	String [] behaviours = {"risky", "normal", "no risk"};
    	return behaviours[position];
    }
    
    private int generateRandomNumber(int range) {
    	Random rand = new Random();
    	int number = rand.nextInt(range);
    	return number;
    }
    
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
				if (this.checkBotDiscard(hand.getDiscardProbability(i)))
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
    
    private boolean checkBotDiscard(int discardProbabilty) {
    	// TO DO check behaviour and return value based on it
    	return false;
    }

    public static void main(String[] args) {
        DeckOfCards deck = new DeckOfCards();
        ComputerPlayer p1 = new ComputerPlayer(deck);
        System.out.println(p1.getName());
        if(p1.behaviourType == "risky") 
        	System.out.println("risky");
        else if(p1.behaviourType == "normal")
        	System.out.println("normal");
        else if(p1.behaviourType == "no risk") {
        	System.out.println("not risky");
        }
        
    }
}
