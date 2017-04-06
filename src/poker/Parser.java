package poker;

import java.util.Scanner;

/**
 * Created by Adam Freeman on 05/04/2017.
 */
public class Parser {

    Parser(){

    }

    public boolean convertResponse(String response){
        String checkYes = "Yes";
        String checkY = "Y";
        String checkNo =  "No";
        String checkN = "N";

        if(checkYes.equalsIgnoreCase(response) || checkY.equalsIgnoreCase(response) ){
            return  true;
        }
        else if(checkNo.equalsIgnoreCase(response) || checkN.equalsIgnoreCase(response)){
            return false;
        }
        else
        	return false;
    }

    public int[] convertDiscards(String response){
        String [] strArray = response.split("\\s*(\\s|=>|,)\\s*");
        int discard[] = new int[3];

        for (int i = 0; i < discard.length; i++){

            if(i < strArray.length)
                discard[i] = Integer.parseInt(strArray[i]);
            else
                discard[i] = -1;

        }
        return discard;
    }

    public static void main(String[] args){
        Parser parser = new Parser();
        Scanner input = new Scanner(System.in);

        System.out.println("Enter discard answer Y/N");
        String response = input.nextLine();
        System.out.println(parser.convertResponse(response));

        System.out.println("Enter discard position");
        String discard = input.nextLine();
        int discards[] = parser.convertDiscards(discard);
        for(int i = 0; i < discards.length; i++)
            System.out.println(discards[i]);





    }
}
