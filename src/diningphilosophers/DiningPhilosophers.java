package diningphilosophers;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Furton
 */
public class DiningPhilosophers {
    
    public static void main(String[] args) {
        //variable declarations
        Scanner user_input = new Scanner(System.in);
        int philosopher_count=1, hungry_philosophers=1, choice, tmp, cont=1;
        int i = 0;
        boolean flag = true;
        
        while(flag){
            System.out.print("Total number of philosophers: ");
            philosopher_count = user_input.nextInt();

            if(philosopher_count < 5){
                System.out.print("**ERROR: Minimum total number of philosophers is 5.\n\n");
                continue;
            }
            flag = false;
        }
        
        //initializing philosophers array
        Philosopher[] philosophers = new Philosopher[philosopher_count];

        for(; i < philosopher_count; i++){
            Philosopher p = new Philosopher(i);
            philosophers[i] = p;
        }
        
        flag = true;
        while(flag){
            System.out.print("Total number of hungry philosophers: ");
            hungry_philosophers = user_input.nextInt();

            if(hungry_philosophers < 1 || hungry_philosophers > philosopher_count){
                System.out.println("**ERROR: Invalid number of hungry philosopher/s.");
            }
            else
                flag = false;
        }
            
        //initializing hungry philosopher
        int[] hungryPhilosophers = new int[hungry_philosophers];

        i = 0;
        flag = true;
        System.out.println("");
        while(flag){
            while( i< hungry_philosophers){
                System.out.print("Hungry philosopher position: ");
                tmp = user_input.nextInt();

                if(tmp > philosopher_count || tmp < 0){
                    System.out.println("**ERROR: Invalid hungry philosopher position.");
                    continue;
                }
                
                if(search(hungryPhilosophers, tmp)){
                    System.out.println("**ERROR: This philosopher is already hungry.");
                    continue;
                }
                else{
                   //sets the isHungry property of the philosopher
                    philosophers[tmp].setIsHungry(true);
                    hungryPhilosophers[i] = tmp; 
                    i++;
                }
            }
            flag = false;
        }
            
        flag = true;
        while(flag){
            System.out.println("\n\n-----------------");
            System.out.println("Choose one scenario: ");
            System.out.println("[1] One can eat at a time");
            System.out.println("[2] Two can eat at a time");
            System.out.println("Enter choice: ");
            choice = user_input.nextInt();

            switch(choice){
                case 1:
                    System.out.println("\n\n-----------------");
                    OneCanEat(philosophers, hungryPhilosophers);
                    flag = false;
                    break;
                case 2:
                    System.out.println("\n\n-----------------");
                    Arrays.sort(hungryPhilosophers);
                    TwoCanEat(philosophers, hungryPhilosophers);
                    flag = false;
                    break;
                default:
                    System.out.println("**ERROR: Invalid input.");
            }
        }
    }
    
    public static void OneCanEat(Philosopher[] p, int[] hp){  
        for(int i = 0; i < hp.length; i++){
            p[hp[i]].IsEating(true);
            System.out.print(" is eating...\n");
            waiting(p);
            System.out.println("");
        }
        
        System.out.println("\n\nAll hungry philosophers are now full.");
    }
    
    public static void TwoCanEat(Philosopher[]p, int[] hp){
        
        if(hp.length == 2 && hp[1]-hp[0]==1){
            System.out.println("Deadlock");
            return;
        }
        
        if(hp.length == 1){
            System.out.println("");
            p[hp[0]].IsEating(true);
            System.out.println(" is now eating...");
            System.out.println("All hungry philosophers are now full.");
        }
        
        else{
            int[][] pairs = getcombinations(2, hp);
            int rows = pairs.length;
            int ctr = hp.length;

            while(ctr!=0){
                for(int i = 0; i < rows; i++){
                    //if two philosophers are not adjacent then they can eat
                    if(checkConsecutives(pairs[i][0], pairs[i][1])){
                        if(!p[pairs[i][0]].getHasEaten() && !p[pairs[i][1]].getHasEaten()){
                            p[pairs[i][0]].IsEating(true);
                            System.out.print(" and ");
                            p[pairs[i][1]].IsEating(true);
                            System.out.print(" are eating...");
                            System.out.println("");
                            waiting(p);
                            ctr = ctr - 2;
                        }

                        if(ctr==1){
                            //get the index of the lone philosopher who has not eaten
                            ctr = getIndex(p);
                            System.out.println("");
                            p[ctr].IsEating(true);
                            System.out.println(" is now eating...");
                            ctr = 0;
                        }
                    }
                }
                if(ctr==0)
                    break;
            }
            System.out.println("All hungry philosophers are now full.");
        }
    }
    
    public static boolean search(int[] hp, int key){
        
        if(hp.length == 0){
            return false;
        }
        
        else{
            for(int i =0; i<hp.length; i++){
                if(hp[i] == key)
                    return true;
            }
            
            return false;
        }
    }
    
    //checks whether two numbers are consecutive
    public static boolean checkConsecutives(int a, int b){
        if((a - b == -1) || (b - a == 1) ||
            (a + b == -1) || (b + a == 1) ||
            (a - b == 1) || (b - a == -1) ||
            (a + b == 1) || (b + a == -1) ||
             a == b){
                return false;
            }
        else
            return true;
    }
    
    //prints the philosopher number who is/are waiting to eat
    public static void waiting(Philosopher[] p){
        int i = 0;
        
        while( i < p.length) {
            if (!p[i].getHasEaten() && p[i].getIsHungry()) {
                p[i].IsEating(false);
            }
             i++;
        }
        
        //adds delay on printing
        try {
            //1000 milliseconds is one second.
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
    
    //returns the index of the lone philosopher who has not eaten
    //in case the number of the hungry philosophers is odd
    public static int getIndex(Philosopher[] p){
        int i = 0;
        for(; i < p.length; i++){
            if(!p[i].getHasEaten() && p[i].getIsHungry())
                break;
        }
        return i;
    }
    
    public static int[][] getcombinations(int k, int[] set) {
    int c = (int) binomial(set.length, k);
    int[][] res = new int[c][Math.max(0, k)];
    int[] ind = k < 0 ? null : new int[k];
    
    for (int i = 0; i < k; ++i){
        ind[i] = i;
    }
    
    for (int i = 0; i < c; ++i) {
        for (int j = 0; j < k; ++j) {
            res[i][j] = set[ind[j]];
        }
        int x = ind.length - 1;
        boolean loop;
        do {
            loop = false;
            ind[x] = ind[x] + 1;
            if (ind[x] > set.length - (k - x)) {
                --x;
                loop = x >= 0;
            } else {
                for (int x1 = x + 1; x1 < ind.length; ++x1) {
                    ind[x1] = ind[x1 - 1] + 1;
                }
            }
        } while (loop);
    }
    return res;
}
    
    private static long binomial(int n, int k) {
        int c = 1;
        
        if (k < 0 || k > n)
            return 0;
        
        if (k > n - k) {
            k = n - k;
        }
        
        for (int i = 1; i < k+1; ++i) {
            c = c * (n - (k - i));
            c = c / i;
        }
        
        return c;
    }
}
