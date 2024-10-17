/**
 * Class Item - an item in the adventure game. 
 *
 * An "Item" represents an object in the game which the players can interact with
 * by picking up, dropping it or giving it to a character. Each item has a name,
 * weight and the permission of whether it is pickable or not.
 * 
 */
public class Item
{
    private String name;
    private int weight;
    private boolean permission;
    
    /**
     * Constructor for the objects of the class Item
     * Creates an item with a name, weight and permission 
     * as the parameters. 
     */
    public Item(String name, int weight, boolean permission)
    {
        this.name = name;
        this.weight = weight;
        this.permission = permission;
    }
    
    /**
     * returns the description of the item
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * returns the weight of the item
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * returns a boolean on whether the item is
     * pickable or not
     */
    public boolean getPermission()
    {
        return permission;
    }
}
