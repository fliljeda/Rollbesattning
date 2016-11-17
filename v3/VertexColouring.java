/* Author: Fredrik Liljedahl & Johan Pettersson
 * Reduction of graphcolouring to a rolecasting problem
 * to prove that the rolecasting problem is NP-hard
 * Loops are often 1-indexed because graph is given
 * in that format and it might be easier to read
 */

public class VertexColouring {
    private Kattio io;
    private int vertices;
    private int edges;
    private int colours;

    private int[] rolesWithScene;
    private int numberOfRoles;
    private int numberOfScenes;
    private int numberOfActors;
    private int[][] tuples;

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

        /*
            Read all edges and check if role is in a scene or not,
            if it isn't in a scene (have any edges) remove them.
         */
        rolesWithScene = new int[vertices + 1];
        tuples = new int[vertices + 1][vertices + 1];
        for (int i = 0; i < edges; i++) {

            //Check if this role is in a scene and increment numberOfRoles.
            int a = io.getInt();
            if (rolesWithScene[a] == 0) {
                numberOfRoles++;
            }

            //Check if this role is in a scene and increment numberOfRoles.
            int b = io.getInt();
            if (rolesWithScene[b] == 0) {
                numberOfRoles++;
            }

            // Count number of scenes and manipulate touples (adjacency matrix).
            if (tuples[a][b] == 0 && tuples[b][a] == 0) {
                numberOfScenes++;
                rolesWithScene[a]++;
                rolesWithScene[b]++;
                tuples[a][b] = 1;
            }
        }

        /*
            Remove all roles and edges that have fewer then colour edges and decrease number of roles and scenes.
         */
        for (int i = 0; i < rolesWithScene.length; i++) {
            if (rolesWithScene[i] < colours && rolesWithScene[i] > 0) {
                for (int j = 0; j < rolesWithScene.length; j++) {
                    if (rolesWithScene[j] < colours && tuples[i][j] != 0) {
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

    /* Diva rules can be fixed by adding 3 roles
     * and two scenes which makes it always true.
     */
    private void ensureDivaRules() {

        numberOfRoles += 3;
        numberOfScenes += 2;

        // 2 more actors to act as divas
        if (colours < numberOfRoles) {
            numberOfActors = colours + 2; // (third role can be anyone)
        } else {
            numberOfActors = numberOfRoles + 2;
        }

        System.out.println(numberOfRoles);
        System.out.println(numberOfScenes);
        System.out.println(numberOfActors);

        System.out.println("1 1"); // Role 1 must be actor 1 (diva)
        System.out.println("1 2"); // Role 2 must be actor 2 (diva)
        System.out.println("1 3"); // Roll 3 (can be anyone except divas but set to 3)
    }

    private void printRoles() {

        /*
            Build the role string. Starting at role 3. 1 and 2 are divas.
         */
        StringBuilder roleString = new StringBuilder(2 * numberOfActors);
        roleString.append(numberOfActors - 2); // all except 1, 2
        for (int i = 3; i <= numberOfActors; i++) { //from 3->|{actors}|
            roleString.append(' ');
            roleString.append(i);
        }

        /*
            Print the role string
         */
        for (int i = 4; i <= numberOfRoles; i++) { //every role
            System.out.println(roleString);
        }
    }

    private void ensureDivaRules2() {
        System.out.println("2 1 3"); // diva 1 is casted
        System.out.println("2 2 3"); // diva 2 is casted
    }

    /* Printing the edges that are given in the graph. Here we 
     * let an edge describe a scene with 2 roles. 
     * Important here is that we have to offset the roles given 
     * in the edges because we added 3 extra roles which is not
     * part of the real graph of which we are trying to colour.
     */
    private void printScenes() {
        // Scene 1 and 2 have already been printed
        for (int i = 0; i <= vertices; i++) {
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
