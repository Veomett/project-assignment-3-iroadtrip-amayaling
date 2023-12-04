import java.util.*; 
import java.io.*; 

public class IRoadTrip {

    public static void readFiles() throws Exception{

        HashMap<String, String> bordersHash = new HashMap<String, String>(); // for borders txt
        HashMap<String, String> capDistHash = new HashMap<String, String>(); // for capdist 
        HashMap<String, String> stateName = new HashMap<String, String>(); // for statename tsv 

        try{
           // String fileString = "";
            File borders = new File ("borders.txt"); 
            Scanner bordersScanner = new Scanner(borders); // to read the file 
           // think i found a loophole 
            int count = 0; 
            while (bordersScanner.hasNext()){
                String line = bordersScanner.nextLine(); // getting the lines from the string
                String [] splitLine = line.split("="); // getting the countries 

                String country = splitLine[0].trim();
                String borderCountries = splitLine[1].trim(); 

                if (borderCountries == ""){
                    borderCountries = null;
                }
                // if borders nothign do we ignore it? dont know yet (can easily change it)
                if (borderCountries != null){
                    bordersHash.put(country, borderCountries);
                }
                
                
            }
            //System.out.println(bordersHash); // testing that it works


            // works 
            File statenames = new File("state_name.tsv");
            Scanner stateScanner = new Scanner(statenames);
            while(stateScanner.hasNext()){
                String stateLine = stateScanner.nextLine();
                String [] stateLineArr = stateLine.split("\t");
                // only adding states that exist currently  
                if (stateLineArr[4].contains("2020")){
                    // gets state ID & associated country name
                    stateName.put(stateLineArr[1], stateLineArr[2]); 

                }
                
            }
           //System.out.println(stateName.get()); 

/*
            this has to be re worked
            basically, need to make a linked list map in order to get duplicate keys 
*/
            File capDist = new File("capdist.csv"); // doing the next file

            Scanner capDistScanner = new Scanner(capDist);
            while (capDistScanner.hasNext()){
                String cdLine = capDistScanner.nextLine(); // seeing what it looks like first 
                String [] cdSplit = cdLine.split(",");
                //System.out.println(cdSplit); 
            }

            
        }
        catch (IOException err){
            err.printStackTrace(); 

        }

    }


    public IRoadTrip (String [] args) {
        // Replace with your code
    }


    public int getDistance (String country1, String country2) {
        // Replace with your code
        return -1;
    }


    public List<String> findPath (String country1, String country2) {
        // Replace with your code
        return null;
    }


    public void acceptUserInput() {

        boolean valid = true; 
        boolean again = true; 
        Scanner input = new Scanner(System.in); 

        while (again){
            System.out.println("Enter a country: ")
            String country1 = input.next(); 
           /*

           check for validity 
           if country 1 equals the values or keys of any of the hash maps, 
           should check to make sure it greater than 3 in length 
           if len == 3, check with the state names 
           else check in borders 

           if its valid, get country 2 and check
           if its not vlaid, ask again 
           do this for both 
           */

        }
       
    }


    public static void main(String[] args) throws Exception {
        try{
            readFiles(); 
        }   
        catch (IOException e){
            e.printStackTrace(); 
        }

        IRoadTrip input = new IRoadTrip(); 
        input.acceptUserInput(); 
        //IRoadTrip a3 = new IRoadTrip(args);

        //a3.acceptUserInput();
    }

}

