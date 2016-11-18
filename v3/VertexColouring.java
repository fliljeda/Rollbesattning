/* Author: Fredrik Liljedahl & Johan Pettersson
 * Reduction of graphcolouring to a rolecasting problem
 * to prove that the rolecasting problem is NP-hard
 * Loops are often 1-indexed because graph is given
 * in that format and it might be easier to read
 */

public class VertexColouring {

    private Kattio io;
    private int vertices; // number of vertices in read graph
    private int edges; // number of edges in read graph
    private int colours; // number of colours in read graph

    private int[] rolesWithScene; // number of edges per node
    private int numberOfRoles;
    private int numberOfScenes;
    private int numberOfActors;
    private int[][] tuples; // adjacency matrix

    public VertexColouring() {
        io = new Kattio(System.in, System.out);
        readGraphFormat();
        ensureDivaRules();
        printRoles();
        ensureDivaRules2();
        printScenes();
    }

    private void readGraphFormat() {

        //Roles (nodes)
        vertices = io.getInt();

        //Scenes (edges)
        edges = io.getInt();

        //Actors (colours)
        colours = io.getInt();

        // Read all edges and check if role is in a scene or not
        rolesWithScene = new int[vertices + 1];
        tuples = new int[vertices + 1][vertices + 1];
        for (int i = 0; i < edges; i++) {

            // Check if this role have been counted already. If not, increment numberOfRoles.
            int a = io.getInt();
            if (rolesWithScene[a] == 0) {
                numberOfRoles++;
            }

            // Check if this role have been counted already. If not, increment numberOfRoles.
            int b = io.getInt();
            if (rolesWithScene[b] == 0) {
                numberOfRoles++;
            }

            // Count number of scenes and manipulate adjacency matrix.
            if (tuples[a][b] == 0 && tuples[b][a] == 0) {
                numberOfScenes++;
                rolesWithScene[a]++;
                rolesWithScene[b]++;
                tuples[a][b] = 1;
            }
        }

        /*
            (Remove all edges and decrease roles) that have fewer then # colour edges.
         */
        for (int i = 0; i < rolesWithScene.length; i++) {  // edge vector
            if (rolesWithScene[i] < colours && rolesWithScene[i] > 0) { // check if colours are less then number of edges for role
                for (int j = 0; j < rolesWithScene.length; j++) { // for all neighbours
                    if (rolesWithScene[j] < colours && tuples[i][j] != 0) { // check if colours are less then number of edges for neighbours role
                        tuples[i][j] = 0;
                        rolesWithScene[i]--;
                        rolesWithScene[j]--;
                        numberOfScenes--;
                        if (rolesWithScene[i] == 0) {
                            numberOfRoles--;
                        }
                        if (rolesWithScene[j] == 0) {
                            numberOfRoles--;
                        }
                    }
                }
            }
        }
    }

    private void ensureDivaRules() {

        numberOfRoles += 3;
        numberOfScenes += 2;

        if (colours < numberOfRoles) { // if we have fewer actors then nodes proceed as normal
            numberOfActors = colours + 2;
        } else {
            numberOfActors = numberOfRoles + 2;
        }

        System.out.println(numberOfRoles);
        System.out.println(numberOfScenes);
        System.out.println(numberOfActors);

        System.out.println("1 1"); // Role 1 must be actor 1 (diva)
        System.out.println("1 2"); // Role 2 must be actor 2 (diva)
        System.out.println("1 3"); // Roll 3 (can be anyone except divas, but set to 3)
    }

    private void printRoles() {

        // Build the role string. Starting at role 3. 1 and 2 are divas.
        StringBuilder roleString = new StringBuilder(2 * numberOfActors);
        roleString.append(numberOfActors - 2); // all except 1, 2
        for (int i = 3; i <= numberOfActors; i++) { //from 3->|{actors}|
            roleString.append(' ');
            roleString.append(i);
        }

        // Print the role string
        for (int i = 4; i <= numberOfRoles; i++) {
            System.out.println(roleString);
        }
    }

    private void ensureDivaRules2() {
        System.out.println("2 1 3"); // diva 1 is casted
        System.out.println("2 2 3"); // diva 2 is casted
    }

    // Print all edges in adjacency matrix.
    private void printScenes() {
        for (int i = 0; i <= vertices; i++) { // Scene 1 and 2 have already been printed
            for (int j = 0; j <= vertices; j++) {
                if (tuples[i][j] != 0) {
                    System.out.println("2 " + (i + 3) + " " + (j + 3));
                }
            }
        }
    }

    public static void main(String[] args) {
        new VertexColouring();
    }
}
