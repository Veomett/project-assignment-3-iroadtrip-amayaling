import java.util.*; 
import java.io.*; 

public class IRoadTrip {
    // need to be able to access every hashmap 
    public HashMap<String, String> bordersHash = new HashMap<String, String>(); // for borders txt
    public HashMap<String, String> capDistHash = new HashMap<String, String>(); // for capdist 
    public HashMap<String, String> stateName = new HashMap<String, String>(); // for statename tsv 

    public IRoadTrip (String [] args) throws Exception{
        // public HashMap<String, String> bordersHash = new HashMap<String, String>(); // for borders txt
        // public HashMap<String, String> capDistHash = new HashMap<String, String>(); // for capdist 
        // public HashMap<String, String> stateName = new HashMap<String, String>(); // for statename tsv 

        try{
            for (int i = 0; i < args.length; i++){
               if (i == 0){

                    File borders = new File (args[0]); 
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
                        
                }
                else if (i == 1){
                    File capDist = new File(args[1]); // doing the next file

                    Scanner capDistScanner = new Scanner(capDist);
                    while (capDistScanner.hasNext()){
                        String cdLine = capDistScanner.nextLine(); // seeing what it looks like first 
                        String [] cdSplit = cdLine.split(",");
                        // when i call states, i need to parse the keys to get the proper countries and then test them against state names 
                        String stateIds = cdSplit[1] + ", " + cdSplit[3]; // getting the countries and their bordering countries into one string so i can have multiple keys with the same name
                        String distance = cdSplit[4].trim(); // in KM
                        capDistHash.put(stateIds, distance); 
                    } 
                    // String cc = "USA, CAN";
                    // System.out.println(capDistHash.get(cc)); 
                    
                }
                else{

                    // works 
                    // might swap the ids and names 
                    File statenames = new File(args[2]);
                    Scanner stateScanner = new Scanner(statenames);
                    while(stateScanner.hasNext()){
                        String stateLine = stateScanner.nextLine();
                        String [] stateLineArr = stateLine.split("\t");
                        // only adding states that exist currently  
                        if (stateLineArr[4].contains("2020")){
                            // gets state ID & associated country name
                            // get country name first and then get the acronym
                            // test value against capdist in order to get the distance 
                            stateName.put(stateLineArr[1], stateLineArr[2]); 

                        }
                        
                    }
                }
            }
        }

        catch (IOException err){
            err.printStackTrace(); 

        }
    }


    public int getDistance (String country1, String country2) {
        // use borders txt
        // the keys are separated by :

        String pathStr = bordersHash.get(country1);
        String [] pathArr = pathStr.split(";");
        System.out.println("arr size: " + pathArr.length);


        for (int i = 0; i < pathArr.length; i++){
            if (pathArr[i].contains(country2)){
                //System.out.println(pathArr[i].trim());
                String dist = pathArr[i].trim();
                // parsing the string to get the km distance 
                int distance = Integer.parseInt(dist.replaceAll("[\\D]", ""));
                return distance;
            }
        }
        // if the countries do not share a border, return -1 km
        return -1;
    }


    public List<String> findPath (String country1, String country2) {

        // use dijkstras alg 
        // Replace with your code
        return null;
    }


    public void acceptUserInput() {
// once both countries are validated
// send to get distance. if there is no border, return -1
// call find path


     //   boolean valid = true; 
        boolean again = true; 
        Scanner input = new Scanner(System.in); 

        while (again){
            System.out.println("Enter the first country: ");
            String country1 = input.next(); 
            if (country1.length() > 3){
                if (!bordersHash.containsKey(country1)){
                    System.out.println("Invalid input. Pleaese try again: ");
                //country1 = input.next(); 
                    continue;
                }
            }else if (country1.length() == 3){
                // getting state ID 
                country1 = country1.toUpperCase(); 
                if (!stateName.containsKey(country1))
                    country1 = stateName.get(country1); 

            }
           
            System.out.println("Enter second country: ");
            String country2 = input.next(); 
            if (!bordersHash.containsKey(country2)){
                System.out.println("Invalid input. Pleaese try again: ");
                //country2 = input.next(); 
                again = true;
                continue;
            }
            else{
                System.out.println(country1 + " -> " + country2 + " = " + getDistance(country1, country2) + " km");
                again = false;
            }
            
        }
           /*

        //    check for validity 
        //    if country 1 equals the values or keys of any of the hash maps, 
        //    should check to make sure it greater than 3 in length 
        //    if len == 3, check with the state names 
        //    else check in borders 

        //    if its valid, get country 2 and check
        //    if its not vlaid, ask again 
        //    do this for both 
        //    */

        // }
       
    }


    public static void main(String[] args) throws Exception {
       

       // IRoadTrip input = new IRoadTrip(); 
        //input.acceptUserInput(); 
        IRoadTrip a3 = new IRoadTrip(args);
        a3.acceptUserInput();


        //a3.acceptUserInput();
    }

}

