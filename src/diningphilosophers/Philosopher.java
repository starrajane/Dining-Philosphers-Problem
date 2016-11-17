package diningphilosophers;

/**
 *
 * @author Furton
 */
public class Philosopher {
    public int philosopherNo;
    private boolean isHungry;
    private boolean hasEaten;
    public boolean hasRight;
    public boolean hasLeft;
    
    public Philosopher(int a){
        this.philosopherNo = a;
        this.isHungry = false;
        this.hasEaten = false;
    }
    
    public void setIsHungry(boolean hungry){
        this.isHungry = hungry;
    }
    
    public void setHasEaten(boolean eaten){
        this.hasEaten = eaten;
    }
    
    public void setHasLeft(boolean left){
        this.hasLeft = left;
    }
    
    public void setHasRight(boolean right){
        this.hasRight = right;
    }
    
    public void IsEating(boolean eat){
        if(eat){
            System.out.print("Philosopher " + this.philosopherNo);
            this.isHungry = false;
            this.hasEaten = true;
       }    
       else{
           System.out.println("Philosopher " + this.philosopherNo + " is waiting...");
       }
    }
    
    public boolean getIsHungry(){
        return this.isHungry; 
    }
    
    public boolean getHasEaten(){
        return this.hasEaten; 
    }
}
