package poker;
import java.util.Arrays;
import java.util.Random;


public class ComputerPlayer extends PokerPlayer {

	private String behaviourType;
	private static final int NO_OF_NAMES = 20, NO_OF_BEHAVIOURS = 3, RISKY_MIN_PROBABILITY = 10, NORMAL_MIN_PROBABILITY = 50, SAFE_MIN_PROBABILITY = 80;
	
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
    	String [] behaviours = {"safe", "normal", "risky"};
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
				if (this.checkBotDiscard() < hand.getDiscardProbability(i))
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
    
    private int checkBotDiscard() {
    	Random rand = new Random();
    	int probability = 0;
    	
    	if(this.behaviourType == "safe") 
    		probability = rand.nextInt(PokerPlayer.MAX_PROBABILITY - ComputerPlayer.SAFE_MIN_PROBABILITY + 1) + ComputerPlayer.SAFE_MIN_PROBABILITY;
    	else if(this.behaviourType == "normal")
    		probability = rand.nextInt(PokerPlayer.MAX_PROBABILITY - ComputerPlayer.NORMAL_MIN_PROBABILITY + 1) + ComputerPlayer.NORMAL_MIN_PROBABILITY;
    	else if(this.behaviourType == "risky")
    		probability = rand.nextInt(PokerPlayer.MAX_PROBABILITY - ComputerPlayer.RISKY_MIN_PROBABILITY + 1) + ComputerPlayer.RISKY_MIN_PROBABILITY;
    	
    	return probability;
    }
    
    public int askFold(int currentBet) {
    	if(this.behaviourType == "safe"){
    		if(this.hand.getGameValue() <= HandOfCards.ROYAL_FLUSH_DEFAULT && this.hand.getGameValue() >= HandOfCards.STRAIGHT_DEFAULT && this.coins != 0){
    			if(currentBet <= 12 && currentBet >= 8){		//After 2 rounds of seeing/raising decide to fold.
    				return PokerPlayer.TRUE;
    			}
    			else return PokerPlayer.FALSE;
    		}
    		else if(this.hand.getGameValue() <= HandOfCards.THREE_OF_KIND_DEFAULT && this.hand.getGameValue() >= HandOfCards.ONE_PAIR_DEFAULT && this.coins != 0){
    			if(currentBet <= 8 && currentBet >= 4){		//After 1 round of seeing/raising decide to fold.
    				return PokerPlayer.TRUE;
    			}
    			else return PokerPlayer.FALSE;
    		}
    		else{			//If player has only a high hand, fold after one round.
    			if(currentBet < 5){
    				return PokerPlayer.FALSE;
    			}
    			else return PokerPlayer.TRUE;
    		}
    	}
    	else if(this.behaviourType == "normal"){
    		if(this.hand.getGameValue() <= HandOfCards.ROYAL_FLUSH_DEFAULT && this.hand.getGameValue() >= HandOfCards.STRAIGHT_DEFAULT && this.coins != 0){
    			if(currentBet <= 16 && currentBet >= 12){		//After 3 rounds of seeing/raising decide to fold.
    				return PokerPlayer.TRUE;
    			}
    			else return PokerPlayer.FALSE;
    		}
    		else if(this.hand.getGameValue() <= HandOfCards.THREE_OF_KIND_DEFAULT && this.hand.getGameValue() >= HandOfCards.ONE_PAIR_DEFAULT && this.coins != 0){
    			if(currentBet <= 12 && currentBet >= 8){		//After 2 round of seeing/raising decide to fold.
    				return PokerPlayer.TRUE;
    			}
    			else return PokerPlayer.FALSE;
    		}
    		else{			//If player has only a high hand, fold after 1 round.
    			if(currentBet < 5){
    				return PokerPlayer.FALSE;
    			}
    			else return PokerPlayer.TRUE;
    		}
    	}
    	else if(this.behaviourType == "risky"){
    		if(this.hand.getGameValue() <= HandOfCards.ROYAL_FLUSH_DEFAULT && this.hand.getGameValue() >= HandOfCards.STRAIGHT_DEFAULT && this.coins != 0){
    			if(this.coins > currentBet/4){		//Risky player has a good hand, he won't fold unless he has no coins.
    				return PokerPlayer.FALSE;
    			}
    			else return PokerPlayer.TRUE;
    			}
    		else if(this.hand.getGameValue() <= HandOfCards.THREE_OF_KIND_DEFAULT && this.hand.getGameValue() >= HandOfCards.ONE_PAIR_DEFAULT && this.coins != 0){
    			if(currentBet <= 16 && currentBet >= 12){		//After 3 rounds of seeing/raising decide to fold.
    				return PokerPlayer.TRUE;
    			}
    			else return PokerPlayer.FALSE;
    		}
    		else{			//If player has only a high hand, fold after two rounds.
    			if(currentBet <= 12 && currentBet >= 8){		//After 2 round of seeing/raising decide to fold.
    				return PokerPlayer.TRUE;
    			}
    			else return PokerPlayer.FALSE;
    		}
    	}
    	return PokerPlayer.TRUE;
    }
 
    public int askOpenBet(int currentBet) {
    	if(this.behaviourType == "safe"){
    		if(this.hand.getGameValue() <= HandOfCards.ROYAL_FLUSH_DEFAULT && this.hand.getGameValue() >= HandOfCards.STRAIGHT_DEFAULT && this.coins != 0){
    			return PokerPlayer.TRUE;	//Even when playing safe with a good had player will be willing to open
    		}
    		else if(this.hand.getGameValue() <= HandOfCards.THREE_OF_KIND_DEFAULT && this.hand.getGameValue() >= HandOfCards.ONE_PAIR_DEFAULT && this.coins != 0){
    			return PokerPlayer.TRUE;	//Even when playing safe with a good had player will be willing to open
    		}
    		else{			//If player has only a high hand, don't open.
    			return PokerPlayer.FALSE;
    		}
    	}
    	else if(this.behaviourType == "normal"){
    		if(this.hand.getGameValue() <= HandOfCards.ROYAL_FLUSH_DEFAULT && this.hand.getGameValue() >= HandOfCards.STRAIGHT_DEFAULT && this.coins != 0){
    			return PokerPlayer.TRUE;
    		}
    		else if(this.hand.getGameValue() <= HandOfCards.THREE_OF_KIND_DEFAULT && this.hand.getGameValue() >= HandOfCards.ONE_PAIR_DEFAULT && this.coins != 0){
    			return PokerPlayer.TRUE;
    		}
    		else{			//open once every two times of only high hand available.
    			Random rand = new Random(); 
    			int x = rand.nextInt(2);
    			if(x == 0){
    				return PokerPlayer.TRUE;
    			}
    			else return PokerPlayer.FALSE;
    		}
    	}
    	else if(this.behaviourType == "risky"){		//always open
    		return PokerPlayer.TRUE;
    	}
    	return PokerPlayer.TRUE;
	}
    
    public int askRaiseBet(int currentBet) {
    	int noOfRaises = 0;
    	if(this.behaviourType == "safe"){
    		if(this.hand.getGameValue() <= HandOfCards.ROYAL_FLUSH_DEFAULT && this.hand.getGameValue() >= HandOfCards.STRAIGHT_DEFAULT && this.coins != 0){
    			if(noOfRaises >= 2){
    				return PokerPlayer.FALSE;
    			}
    				else{
    					noOfRaises++;
    					return PokerPlayer.TRUE;    			
    				}
    		}
    		else if(this.hand.getGameValue() <= HandOfCards.THREE_OF_KIND_DEFAULT && this.hand.getGameValue() >= HandOfCards.ONE_PAIR_DEFAULT && this.coins != 0){
    			if(noOfRaises >= 1){
    				return PokerPlayer.FALSE;
    			}
    			else {
    				noOfRaises++;
    				return PokerPlayer.TRUE;
    			}
    		}
    		else{			//If player has only a high hand, don't raise.
    			return PokerPlayer.FALSE;
    		}
    	}
    	else if(this.behaviourType == "normal"){
    		if(this.hand.getGameValue() <= HandOfCards.ROYAL_FLUSH_DEFAULT && this.hand.getGameValue() >= HandOfCards.STRAIGHT_DEFAULT && this.coins != 0){
    			if(noOfRaises >= 3){
    				return PokerPlayer.FALSE;
    			}
    			else {
    				noOfRaises++;
    				return PokerPlayer.TRUE;
    			}
    		}
    		else if(this.hand.getGameValue() <= HandOfCards.THREE_OF_KIND_DEFAULT && this.hand.getGameValue() >= HandOfCards.ONE_PAIR_DEFAULT && this.coins != 0){
    			if(noOfRaises >= 2){
    				return PokerPlayer.FALSE;
    			}
    			else {
    				noOfRaises++;
    				return PokerPlayer.TRUE;
    			}
    		}
    		else{			//if only high hand do not raise.
    			return PokerPlayer.FALSE;
    		}
    	}
    	else if(this.behaviourType == "risky"){		
    		if(this.hand.getGameValue() <= HandOfCards.ROYAL_FLUSH_DEFAULT && this.hand.getGameValue() >= HandOfCards.STRAIGHT_DEFAULT && this.coins != 0){
    			if(noOfRaises >= 4){
    				return PokerPlayer.FALSE;
    			}
    			else {
    				noOfRaises++;
    				return PokerPlayer.TRUE;
    			}
    		}
    		else if(this.hand.getGameValue() <= HandOfCards.THREE_OF_KIND_DEFAULT && this.hand.getGameValue() >= HandOfCards.ONE_PAIR_DEFAULT && this.coins != 0){
    			if(noOfRaises >= 3){
    				return PokerPlayer.FALSE;
    			}
    			else {
    				noOfRaises++;
    				return PokerPlayer.TRUE;
    			}
    		}
    		else{			//raise once every two times of only high hand available(extreme bluff).
    			Random rand = new Random(); 
    			int x = rand.nextInt(2);
    			if(x == 0){
    				noOfRaises++;
    				return PokerPlayer.TRUE;
    			}
    			else return PokerPlayer.FALSE;
    		}
	}
    	noOfRaises++;
    	return PokerPlayer.TRUE;
	}

    public static void main(String[] args) {
        DeckOfCards deck = new DeckOfCards();
        ComputerPlayer p1 = new ComputerPlayer(deck);
        System.out.println(p1.getName());
        
        if(p1.behaviourType == "risky") {
        	System.out.println("risky");
        	System.out.println("Probabilty returned for risky: " + p1.checkBotDiscard());
        	System.out.println(p1.hand.toString());
        	p1.discard();
        	System.out.println(p1.hand.toString());
        }	
        else if(p1.behaviourType == "normal") {
        	System.out.println("normal");
        	System.out.println("Probabilty returned for normal: " + p1.checkBotDiscard());
        	System.out.println(p1.hand.toString());
        	p1.discard();
        	System.out.println(p1.hand.toString());
        }	
        else if(p1.behaviourType == "safe") {
        	System.out.println("safe");
        	System.out.println("Probabilty returned for safe: " + p1.checkBotDiscard());
        	System.out.println(p1.hand.toString());
        	p1.discard();
        	System.out.println(p1.hand.toString());
        }
        
        
        
    }
}
