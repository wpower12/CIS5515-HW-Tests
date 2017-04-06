/*
 * Implementation of Solution for Kleinberg,Tardos Problem 6.1
 * Expects a text file named "test_maxweight.txt" in the directory. 
 * First line is an interger, the number of nodes
 * then each line after is a node weight value, in path order.  
 */
package cis5515.hw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author wkp3
 */
public class MaxWeightIndependantSet {
    
    public static int[] M, path;
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // Read 'graph' into array
        BufferedReader fin = new BufferedReader( new FileReader("test_maxweight.txt"));
        int N = Integer.parseInt( fin.readLine() );       
        path = new int[N];
        M = new int[N];
        for( int i = 0; i < N; i++ ){
            M[i] = -1;
        }  
        int i = 0;
        String line;
        while( (line = fin.readLine()) != null){
            path[i] = Integer.parseInt(line);
            i++;
        }
        printPath();
        M[0] = path[0];
        M[1] = Math.max(path[0], path[1]);        
        System.out.printf("Max value: %d\n", findMaxSet(path.length-1));
        printSet();
    }
    
    public static void printPath(){
        System.out.printf("Provided Path: ");
        for(int i = 0; i < path.length; i++){
            System.out.printf("(%d)%s", path[i], i == path.length-1 ? "":"--");
        }
        System.out.printf("\n");
    }
    
    public static int findMaxSet( int i ){
        if( M[i] != -1 ){
            return M[i];
        } else {
            if(path[i] + findMaxSet(i-2) > findMaxSet(i-1)){
                M[i] = path[i] + findMaxSet(i-2);
            } else {
                M[i] = findMaxSet(i-1);
            }
            return M[i];
        }
    }
    
    public static void printSet(){
        System.out.printf("Actual set: { ");
        int i = M.length-1;
        while( i > 0 ){
            if( M[i] > M[i-1] ){
                System.out.printf("%d ", path[i]);
                i = i - 2;
            } else {
                i = i - 1;
            }
        }
        if( i == 0 ){
            System.out.printf("%d ", path[i]); // Could miss the 0th element. if i == -1, the 1st element was included.
        }
        System.out.printf("}\n");
    }
}
