//Ye Thurein Win, k23086194
import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items;              // stores the items inside the room.

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<Item>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     Items: 
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + "\n" + getRoomItem();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
    
    

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Adds items into the room. 
     */
    public void addItem(Item item) {
        items.add(item);
    }
    
    /**
     * Returns the description of the item in the room if there is any.
     * If not, returns an empty string. 
     */
    public String getRoomItem() 
    {
        if (!items.isEmpty()){
            String output = "Items: \n";
            for (int i = 0; i < items.size(); i++) {
                output += items.get(i).getName() + "  Weight:" + items.get(i).getWeight() +
                "  Pickable:" + items.get(i).getPermission() + "\n";
            }
            return output;
        }
        else {
            String output = "";
            return output;  
        }  
    }
    
    /**
     * Returns the item in the room with the matching name
     */
    public Item getItem(String itemName)
    {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(itemName)){
                return items.get(i);
            }
        }
        return null;
    }
    
    /**
     * Removes item from the room
     */
    public void removeItem(Item item)
    {
        items.remove(item);
    }
}

