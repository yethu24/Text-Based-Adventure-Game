//Ye Thurein Win, k23086194
import java.util.ArrayList;

/**
 * Class Character - Characters inside the game.
 * Contains a name, ArrayList of Items Needed to progress through the game,
 * Inventory ArrayList capable of receiving items from the player.
 * Can move on itsown.
 */
public class Character
{
    private String name;
    private ArrayList<Item> itemsNeeded; //Item needed to progress through the game, which can be checked against its inventory.
    private ArrayList<Item> inventory; //Inventory to store items given by the player. 
    private Room[] roomsAllowed; //Array of rooms the character is allowed to be in.
    private Room currentRoom; //keeps track of the current room.
    
    /**
     * Constructor with name, itemsNeeded and roomsAllowed as parameter
     * creates an inventory for the character as well.
     */
    public Character(String name, ArrayList<Item> itemsNeeded, Room[] roomsAllowed)
    {
        this.name = name;
        this.itemsNeeded = itemsNeeded;
        this.roomsAllowed = roomsAllowed;
        this.inventory = new ArrayList<>();
    }
    
    /**
     * returns the name.
     */
    public String getName() 
    {
        return name;
    }
    
    /**
     * returns the ArrayList of items needed.
     */
    public ArrayList<Item> getItemsNeeded()
    {
        return itemsNeeded;
    }
    
    /**
     * return the Array of rooms the character can be in.
     */
    public Room[] getRooms()
    {
        return roomsAllowed;
    }
    
    /**
     * Sets the current of the character by taking a parameter.
     */
    public void setCurrentRoom(Room room) {
        currentRoom = room;
    }
    
    /**
     * returns the current room of the character.
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
    
    /**
     * Adds item into the character's inventory.
     */
    public void addItem(Item item) {
        inventory.add(item);
    }
    
    /**
     * returns the Inventory of the character.
     */
    public ArrayList<Item> getInventory()
    {
        return inventory;
    }
}
