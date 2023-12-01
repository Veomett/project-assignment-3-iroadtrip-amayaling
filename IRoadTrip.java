import java.util.*; 
import java.io.*; 

public class IRoadTrip {

    public static void readFiles() throws Exception{

        HashMap<String, String> hash = new HashMap<>(); 
        try{
           // String fileString = "";
            File borders = new File ("borders.txt"); 
            Scanner fileScanner = new Scanner(borders); // to read the file 
           
            while (fileScanner.hasNextLine()){
                // need to parse this in order to get the bordering countries 
                String fileString = fileScanner.nextLine();
                String [] mainBorder = fileString.split("="); // splitting at the = to get the main countries 
                String addString = "";
// have to do the entire length not minus 1 bc it cuts off the last thing 
                for (int i = 0; i < 1; i++){
                    // trying to manipulate the strings in order to add to hash map
                    addString+= mainBorder[i] + ",";
                    String [] ch = addString.split(",");
                    System.out.println(ch[i]);
                    // String borderingCountries = mainBorder[i + 1]; // should get everything after the = 
                    // System.out.println(borderingCountries.);
                    // hash.put(mainBorder[i], borderingCountries);
               }
               //System.out.println(addString);


              
               
            }

            // for (int j = 0; j < hash.size(); j++){
            //     System.out.println(hash.get(j));
            // }
            
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
        // Replace with your code
        System.out.println("IRoadTrip - skeleton");
    }


    public static void main(String[] args) throws Exception {
        try{
            readFiles(); 
        }   
        catch (IOException e){
            e.printStackTrace(); 
        }
        //IRoadTrip a3 = new IRoadTrip(args);

        //a3.acceptUserInput();
    }

}

