/* Author: Fredrik Liljedahl & Johan Pettersson
 * Reduction of graphcolouring to a rolecasting problem
 * to prove that the rolecasting problem is NP-hard
 * Loops are often 1-indexed because graph is given
 * in that format and it might be easier to read
 */
import java.util.*;
import java.io.*;


public class VertexColouring{
    private Kattio io;
    private int vertices;
    private int edges;
    private int colours;

    public VertexColouring(){
        io = new Kattio(System.in, System.out);
        readGraphFormat();
        //TODO Make sure independant vertices are left out
        ensureDivaRules();
        printRoles();
        ensureDivaRules2();
        readAndPrintScenes();
    }
    
    /* Simply reads the initial values of the graph */
    private void readGraphFormat(){
        //Roles
        vertices = io.getInt();
        
        //Scenes (with one relation each)
        edges = io.getInt();
        
        //Actors
        colours = io.getInt();
    }
    
    /* Divas rules can be fixed by simply adding 3 roles
     * and two scenes which are merely there to trick the
     * casting algorithm to put the divas there, therefore
     * satisfying both giving divas work and not letting them
     * act in the same scene
     */
    private void ensureDivaRules(){
        //3 more roles
        vertices+=3;
        //2 scenes with 1 relation each
        edges +=2;   
        //2 more actors to act as divas 
        colours +=2; //(third role can be anyone)
        
        System.out.println(vertices);
        System.out.println(edges);
        System.out.println(colours);
        
        //Manually print divas roles which is 1 and 2
        //Let 3 be the role that plays against the divas
        System.out.println("1 1");
        System.out.println("1 2");
        System.out.println("1 3");
        
        //More work needs to be done after this to ensure
        //that we also have 2 scenes to fill these actors int
    }
    
    /* Print the roles. Since we are trying to solve a graph colouring
     * problem we can let the roles be played by any actor as every
     * vertex has the possibility of being coloured with any colour
     * Notice that the roles 1,2 and 3 is already printed
     * and the rest of the roles needs to offset by 3.
     */
    private void printRoles(){
        
        //Since all roles will contain the same information we can
        //use one string object.
        StringBuilder roleString = new StringBuilder(2*colours);
        
        //Roles can be played by all actors except 1 and 2
        roleString.append(colours - 2); // all except 1,2
        for(int i = 3; i <= colours; i++){ //from 3->|{actors}|
            roleString.append(' ');
            roleString.append(i);
        }

        //offset 1,2,3
        for(int i = 4; i <= vertices; i++){ //every role 
            System.out.println(roleString);
        }
    }
    
    /* Follow up from the previous function since we needed to
     * print the rest of the roles before we contrinued with 
     * the scenes that will ensure diva rules are contained
     */
    private void ensureDivaRules2(){
        System.out.println("2 1 3"); // diva 1 is casted
        System.out.println("2 2 3"); // diva 2 is casted
    }
    
    /* Printing the edges that are given in the graph. Here we 
     * let an edge describe a scene with 2 roles. 
     * Important here is that we have to offset the roles given 
     * in the edges because we added 3 extra roles which is not
     * part of the real graph of which we are trying to colour.
     */
    private void readAndPrintScenes(){
        // Scene 1 and 2 have already been printed
        for(int i = 3; i <= edges; i++){
            // Offset by 3 because role 1,2 and 3 are already casted
            // and isn't part of colouring graph
            //       #roles   #first role + 3         #second role + 3
            System.out.println("2 " + (io.getInt()+3) + " " + (io.getInt()+3));
        }
    }

    public static void main(String[] args){
        new VertexColouring();
    }
}
