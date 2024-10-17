//Ye Thurein Win, k23086194
import java.util.ArrayList;

/**
 * Class Player - the player in the adventure game that the user plays as. 
 * Contains an ArrayList called inventory.
 * When dropping or picking up items Player's inventory is the one 
 * being interacted with.
 */
public class Player
{
    private ArrayList<Item> inventory; //Inventory of the player.
    private final int maxWeight = 15; //Maximum weight of the items that the player can carry.
    private Boolean strength; //Variable that tells whether the player has gotten the sword from the warrior.
    private Boolean hasSword; //Variable that tells whether the player has gained Strength from the wizard.
    
    /**
     * Constructor for the class Player
     * Creates a player that contains an arraylist holding 
     * objects of class Item. 
     */
    public Player()
    {
        inventory = new ArrayList<Item>();
        strength = false;
        hasSword = false;
    }
    
    /**
     * Prints out the items that are inside the player's inventory.
     */
    public void printInventory() 
    {
        String output = "";
        for (int i = 0; i < inventory.size(); i++) {
            output += inventory.get(i).getName() + "  Weight:" + inventory.get(i).getWeight() + "\n";
        }
        System.out.println("You are carrying:");
        System.out.println(output);
    }
    
    /**
     * Adds items into the player's inventory.
     */
    public void addItem(Item item)
    {
        inventory.add(item);
    }
    
    /**
     * Returns the ArrayList for inventory.
     */
    public ArrayList<Item> getInventory()
    {
        return inventory;
    }
    
    /**
     * Returns the item from the inventory with the matching name.
     */  
    public Item getItem(String itemName)
    {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equals(itemName)){
                return inventory.get(i);
            }
        }
        return null;
    }
    
    /**
     * Removes item from the inventory.
     */
    public void removeItem(Item item)
    {
        inventory.remove(item);
    }
    
    /**
     * Returns the maximum weight allowed.
     */
    public int getMaxWeight()
    {
        return maxWeight;
    }
    
    /**
     * Checks the total weight of the items in the inventory. 
     */
    public int checkWeight()
    {
        int weight = 0;
        for (int i = 0; i < inventory.size(); i++) {
            weight += inventory.get(i).getWeight();
        }
        return weight;
    }
    
    /**
     * returns true if the player has a map in the inventory
     */
    public boolean checkMap()
    {
        boolean hasMap = false;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equals("map")){
                hasMap = true;
            }
        }
        return hasMap;
    }
    
    /**
     * returns strength
     */
    public boolean getStrength()
    {
        return strength;
    }
    
    /**
     * sets strength to true
     */
    public void setStrength()
    {
        strength = true;
    }
    
    /**
     * returns hasSword
     */
    public boolean getHasSword() 
    {
        return hasSword;
    }
    
    /**
     * Sets hasSword to true
     */
    public void setHasSword()
    {
        hasSword = true;
    }
}
