import java.util.*; 
import java.io.*; 

public class IRoadTrip {

    public static void readFiles() throws Exception{

        HashMap<String, String> bordersHash = new HashMap<String, String>(); // for borders txt
        try{
           // String fileString = "";
            File borders = new File ("borders.txt"); 
            Scanner fileScanner = new Scanner(borders); // to read the file 
           // think i found a loophole 
            int count = 0; 
            while (fileScanner.hasNext()){
                String line = fileScanner.nextLine(); // getting the lines from the string
                String [] splitLine = line.split("="); // getting the countries 

                if (splitLine.length == 2){
                    String country = splitLine[0].trim();
                    String borderCountries = splitLine[1].trim(); 

                    bordersHash.put(country, borderCountries);
                }
            }
            System.out.println(bordersHash.get("Yemen")); // testing that it works 

            
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

