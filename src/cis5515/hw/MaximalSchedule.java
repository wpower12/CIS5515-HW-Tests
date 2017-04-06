/*
 * Implementation of Solution for Kleinberg,Tardos Problem 6.10
 * Expects a text file named "test_maxschedule.txt" in the directory.  
 * Format should be: First line = Number of minutes
 * Second line = Space seperated values for minutes for task A
 * Third line  = Space seperated values for minutes for task B
 * 
 * Outputs the value of the optimal.  Could be reworked to output the 
 * actual schedule. 
 */
package cis5515.hw;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author wkp3
 */
public class MaximalSchedule {
    
    private static int[] A, B, M; 
    private static String[] lasts;  // Holds last two characters of optimal solution up to that index
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader fin = new BufferedReader( new FileReader("test_maxschedule.txt"));
        int N = Integer.parseInt( fin.readLine() ); 
        M = new int[N];
        for( int i = 0; i < N; i++ ){
            M[i] = -1;
        }
        lasts = new String[N];
        A = parseLine( fin.readLine());
        B = parseLine( fin.readLine());
       
        findFirstThree();
        System.out.printf("Optimal Schedule Value: %d\n", findOptimalSchedule( N-1 ));
    }
    
    private static int[] parseLine( String s ){
        String[] split = s.split(" ");
        int[] ret = new int[split.length];
        for( int i = 0; i < split.length; i++ ){
            ret[i] = Integer.parseInt(split[i]);
        }
        return ret;
    }
    
    private static void findFirstThree(  ){
        // Optimal for First minute
        if( A[0] > B[0] ){
            M[0] = A[0];
            lasts[0] = "_A";
        } else {
            M[0] = B[0];
            lasts[0] = "_B";
        }
        // Second Minute
        if( A[0]+A[1] > B[0]+B[1] ){
            M[1] = A[0]+A[1];
            lasts[1] = "AA";
        } else {
            M[1] = B[0]+B[1];
            lasts[1] = "BB";
        }
        // Third Minute
        M[2] = Math.max( Math.max( A[0]+A[1]+A[2], B[0]+B[1]+B[2] ), 
                         Math.max(A[0]+B[2], B[0]+A[2]) );
        if( M[2] == A[0]+A[1]+A[2] ){
            lasts[2] = "AA";
        } 
        if( M[2] == B[0]+B[1]+B[2] ){
            lasts[2] = "BB";
        }
        if( M[2] == A[0]+B[2] ){
            lasts[2] = "xB";
        }
        if( M[2] == B[0]+A[2] ){
            lasts[2] = "xA";
        }
    }
    
    private static int findOptimalSchedule( int i ){   
        if( M[i] != -1 ){
            return M[i];
        }       
        findOptimalSchedule( i - 1);
        int tmp;
        // If there is a swap in the second to last place we have three cases.
        if( lasts[ i-1 ].compareTo("xA") == 0 ){
            tmp = Math.max( Math.max( A[i-1]+A[i] , B[i-2]+A[i]), B[i-2]+B[i-1]+B[i] );
            if( tmp ==  A[i-1]+A[i]){
                lasts[i] = "AA";
            }
            if( tmp ==  B[i-2]+A[i]){
                lasts[i] = "xA";
            }
            if( tmp ==  B[i-2]+B[i-1]+B[i]){
                lasts[i] = "BB";
            }
            M[i] = findOptimalSchedule(i-3)+tmp;
        }
        if( lasts[ i-1 ].compareTo("xB") == 0 ){
            tmp = Math.max( Math.max( B[i-1]+B[i] , A[i-2]+B[i]), A[i-2]+A[i-1]+A[i] );
            if( tmp ==  B[i-1]+B[i]){
                lasts[i] = "BB";
            }
            if( tmp ==  A[i-2]+B[i]){
                lasts[i] = "xB";
            }
            if( tmp ==  A[i-2]+A[i-1]+A[i]){
                lasts[i] = "AA";
            }
            M[i] = findOptimalSchedule(i-3)+tmp;
        }
        // If there isnt a swap we only have two cases.
        if( lasts[ i-1 ].compareTo("AA") == 0){
            M[i] = findOptimalSchedule(i-3) + Math.max(A[i-2]+A[i-1]+A[i], A[i-2]+B[i]);
            if( A[i-2]+A[i-1]+A[i] > A[i-2]+B[i] ){
                lasts[i] = "AA";
            } else {
                lasts[i] = "xB";
            }
        }
        if( lasts[ i-1 ].compareTo("BB") == 0){
            M[i] = findOptimalSchedule(i-3) + Math.max(B[i-2]+B[i-1]+B[i], B[i-2]+A[i]);
            if( B[i-2]+B[i-1]+B[i] > B[i-2]+A[i] ){
                lasts[i] = "BB";
            } else {
                lasts[i] = "xA";
            }
        }
        return M[i];
    }
}
