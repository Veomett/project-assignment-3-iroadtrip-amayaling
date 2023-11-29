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
                String [] borderArr = fileString.split(";", 0); // splitting at the ; to get the bordering countries 

                for (int i = 0; i < borderArr.length - 1; i++){
                    if (i == 0){
                        hash.put(borderArr[i], borderArr[i+1]);
                        // that is the first country 
                        // need to add that to a hashtable 

                    }

               }
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

