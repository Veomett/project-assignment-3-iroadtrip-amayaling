import java.util.*; 
import java.io.*; 

public class IRoadTrip {
    // need to be able to access every hashmap 
    public HashMap<String, String> bordersHash = new HashMap<String, String>(); // for borders txt
    
    public HashMap<String, String> capDistHash = new HashMap<String, String>(); // for capdist 

    public HashMap<String, String> stateName = new HashMap<String, String>(); // for statename tsv 

    public HashMap<String, String> idToName = new HashMap<String, String>(); // this will help with dijkstras alg 
    public Map<String, HashMap<String, Integer>> graph = new HashMap<>(); // getting ALL of the state IDS & their corresponding countries and sitances 

    public IRoadTrip (String [] args) throws Exception{

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

                        // if (borderCountries == ""){
                        //     borderCountries = null;
                        // }
                        // // if borders nothign do we ignore it? dont know yet (can easily change it)
                        // if (borderCountries != null){
                        bordersHash.put(country, borderCountries);
                        
                        
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

                        // need to ignore this
                        if (cdLine.contains("kmdist")){
                            continue;
                        }
                        
                        // for large graph array 
                        String c1 = cdSplit[1]; // country 1
                        String c2 = cdSplit[3]; // country 3

                        int d = Integer.parseInt(distance.replaceAll("[\\D]", "")); // distance for inner map

                        if (graph.get(c1) == null){
                            HashMap<String, Integer> g = new HashMap<>();
                            g.put(c2, d); 
                            graph.put(c1, g);
                        }
                        else{
                            HashMap<String, Integer> g = graph.get(c1);
                           // g = graph.get(c1);
                            g.put(c2, d);
                        }

                       
                    } 

                    System.out.println(graph.get("USA").get("CAN"));
                    
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
                            stateName.put(stateLineArr[2], stateLineArr[1]); 
                            idToName.put(stateLineArr[1], stateLineArr[2]);



                        }
                        
                    }
                    //System.out.println(stateName);
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
        String acc1 = stateName.get(country1);
        String [] pathArr = pathStr.split(";");


        for (int i = 0; i < pathArr.length; i++){
            if (pathArr[i].contains(country2)){
                // gets alias name
                String acc2 = stateName.get(country2); 
                String acronymCountry = acc1 + ", " + acc2;
                
                String dist = capDistHash.get(acronymCountry);
                // parsing the string to get the km distance 
                int distance = Integer.parseInt(dist);
                return distance;
            }
        }
        // if the countries do not share a border, return -1
        return -1;
    }

    

    public List<String> findPath (String country1, String country2) {
        String c1Acronym = stateName.get(country1);
        String c2Acronym = stateName.get(country2); 

        // 
// since my capdist file is stored like this thats how i have to get it 
        String acronym = c1Acronym + ", " + c2Acronym; // this is how I will get the distance from each country 
        // need to loop through each border country and see if any match up
        // if they match, loop through that countries borders and if target is there, stop
        //else, find shortest path from next country

        // use borders txt
        // if country 1 and 2 do not border each other, find the countries that overlap in order to do it 

        //System.out.println(capDistHash.get(c));


        PriorityQueue<Node> pq = new PriorityQueue<>(); 

        ArrayList<String> visited = new ArrayList<>(); // gonna store visited countries in here 

        HashMap<String, Integer> distance = new HashMap<String, Integer>(); 
        HashMap<String, String> prev = new HashMap<String, String>();

        distance.put(country1, 0);

        for (String country : bordersHash.keySet()){
            if (!country.equals(country1)){
                distance.put(country, Integer.MAX_VALUE); 
            }
        }

        pq.add(new Node(country1, 0)); // have to make source 0

        while (!pq.isEmpty()){
            Node current = pq.poll();
            String currentNode = current.node;

            if (bordersHash.containsKey(currentNode)){
                String [] neighbors = bordersHash.get(currentNode).split(";");
                for (int n = 0; n < neighbors.length; n++){
                    String cc = neighbors[n]; // neighboring countries 

                    String ac1 = stateName.get(currentNode); 
                    String ac2 = stateName.get(cc);
                    String fullAcc = ac1 + ", " + ac2; 
                    int d = Integer.parseInt(capDistHash.get(acronym)); 
                }
            }

        }

        
        


        // use dijkstras alg 
        // Replace with your code
        return null;
    }

    private class Node implements Comparable<Node> {
            String node;
            int distance;

            public Node(String node, int distance) {
                this.node = node;
                this.distance = distance;
            }

            public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
            }
        }


    public void acceptUserInput() {
    boolean again = true;
    Scanner input = new Scanner(System.in);
    String quit = "EXIT";

    while (again) {
        System.out.println("Enter the name of the first country (type EXIT to quit): ");
        String country1 = input.next();
        
        if (country1.equalsIgnoreCase(quit)) {
            System.exit(0);
        }

        if (bordersHash.containsKey(country1)) {
            System.out.println("Enter the name of the second country (type EXIT to quit): ");
            String country2 = input.next();

            if (country2.equalsIgnoreCase(quit)) {
                System.exit(0);
            }

            if (bordersHash.containsKey(country2)) {
                System.out.println(country1 + " --> " + country2 + " (" + getDistance(country1, country2) + " km)");
                again = false;
            } else {
                System.out.println("Invalid. Please try again: ");
            } 
        }
        else {
            System.out.println("Invalid. Please try again: ");
            }

        
    }        

        // System.out.println(country1 + " --> " + country2 + " (" + getDistance(country1, country2) + " km)");
        // //path = findPath(country1, country2); // will print this accordingly 
        // again = false;
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
        //a3.findPath("France", "Spain");


        //a3.acceptUserInput();
    }

}

