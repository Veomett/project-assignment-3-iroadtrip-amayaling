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

                        // have to hard code to get certain values 
                        if (stateName.containsValue("YEM")){
                            stateName.put("Yemen", "YEM");
                        }
                        else if (stateName.containsValue("ITA")){
                            stateName.put("Italy", "ITA");
                        }
                        else if (stateName.containsValue("USA")){
                            stateName.put("United States", "USA"); 
                        }
                        idToName.put(stateLineArr[1], stateLineArr[2]);

                        if (idToName.containsKey("YEM")){
                            idToName.put("YEM", "Yemen"); 
                        }
                        else if (idToName.containsKey("ITA")){
                            idToName.put("ITA", "Italy"); 
                        }
                        else if (idToName.containsKey("USA")){
                            stateName.put("USA", "United States"); 
                        }
                        

                    }
                    
                }
                File borders = new File (args[0]); 
                Scanner bordersScanner = new Scanner(borders); // to read the file 
               // think i found a loophole 
                int count = 0; 
                while (bordersScanner.hasNext()){
                    String line = bordersScanner.nextLine(); // getting the lines from the string
                    String [] splitLine = line.split("="); // getting the countries 

                    String country = splitLine[0].trim();

                    String borderCountries = splitLine[1].trim(); 

                    
                    bordersHash.put(country, borderCountries);
                    

                    
                } 
                //System.out.println(bordersHash);

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

                //System.out.println(graph.get("USA"));



                //System.out.println(stateName);
            }


        catch (IOException err){
            err.printStackTrace(); 

        }
    }


    public int getDistance (String country1, String country2) {
        // use borders txt
        // the keys are separated by ;

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

         if (country1.equals(country2)){
            return 0; 
         }
        // if the countries do not share a border, return -1
        return -1;
    }

    

    public List<String> findPath (String country1, String country2) {
// all of the data structures I think I will need for this 
        PriorityQueue<Node> pq = new PriorityQueue<>(); 
        ArrayList<String> visited = new ArrayList<>(); // gonna store visited/known countries in here 
        HashMap<String, Integer> distance = new HashMap<String, Integer>(); 
        HashMap<String, String> prev = new HashMap<String, String>();
        // list to use to print the travel path 
        List<String> path = new ArrayList<>();

        country1 = stateName.get(country1); // need the acronym
        country2 = stateName.get(country2);

        distance.put(country1, 0);
        pq.add(new Node(country1, 0)); // priority 

        // giving all of the countries the max value, subject to change 
        for (String country: graph.keySet()){
            if (!country.equals(country1)){
                distance.put(country, Integer.MAX_VALUE); 
               
            }
        }

        while (!pq.isEmpty()){
            Node curr = pq.poll(); // the most important thing in the PQ
            String currentCountry = curr.node; 
        
            if (visited.contains(currentCountry)){
                continue; 
            }
            visited.add(currentCountry); // dont go back to this 

            if (graph.containsKey(currentCountry)){
                Map<String, Integer> neighbors = graph.get(currentCountry);
                //System.out.println(neighbors);
                // looping over the hash of neighbors to get the new distance 
                for (String neighbor : neighbors.keySet()) {
                    if (!visited.contains(neighbor)) {
                        int newDistance = graph.get(neighbor).get(currentCountry);
                        
                        if (newDistance < distance.get(neighbor)) {
                            distance.put(neighbor, newDistance);
                            prev.put(neighbor, currentCountry);
                            pq.add(new Node(neighbor, newDistance));
                        }
                    }

                }
            }


        }

        String current = country2;
        while (prev.containsKey(current)) {
            path.add(current);
            current = prev.get(current);
        }
        path.add(country1);
        // have to reverse the path 
        Collections.reverse(path);

    
        return path;
    }

   // this is for the algorithm 
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

    System.out.println("Enter the name of the first country (type EXIT to quit): ");
    String country1 = input.next();
    
    
    while (again) {
        if (country1.equals(quit)) {
            System.exit(0);
        }
        if (!bordersHash.containsKey(country1) || !stateName.containsKey(country1)){
            System.out.println("Invalid input. Try again: ");
                country1 = input.next(); 
                //country1 = stateName.get(country1); 
                again = true; 
            }
            
            System.out.println("Enter the name of the second country (type EXIT to quit): ");
            String country2 = input.next();
           
            if (country2.equals(quit)) {
                System.exit(0);
            }

            if (!bordersHash.containsKey(country2) || !stateName.containsKey(country2)){
                System.out.println("Invalid input. Try again");
                country2 = input.next(); 
                
                
            } else {
                System.out.println(country1 + " --> " + country2 + " (" + getDistance(country1, country2) + " km)");
                again = false;
            } 
        }   

       
    }

    public void printPath(List<String> path) {
        if (path.isEmpty()) {
            System.out.println("No path found.");
            return;
        }
        

        System.out.println("Route from " + idToName.get(path.get(0)) + " to " + idToName.get(path.get(path.size() -1)) + ": ");
        for (int i = 0; i < path.size() - 1; i++) {
            int distance = graph.get(path.get(i)).get(path.get(i + 1));
            System.out.print("* " + idToName.get(path.get(i)) + " -> " + idToName.get(path.get((i + 1))));
            System.out.println("(" + distance + " km)");
        }
        System.out.println(path.get(path.size() - 1));
}


    public static void main(String[] args) throws Exception {
       

       // IRoadTrip input = new IRoadTrip(); 
        //input.acceptUserInput(); 
        IRoadTrip a3 = new IRoadTrip(args);
        a3.acceptUserInput();

        // to print the path 
        List<String> path = a3.findPath("Yemen", "Jordan");
        a3.printPath(path);
        


        //a3.acceptUserInput();
    }

}

