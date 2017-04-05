package poker;

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
            return true;
        }
        return false;
    }
    
}
