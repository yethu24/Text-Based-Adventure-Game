//Ye Thurein Win, k23086194
import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;

public class Game 
{
    //default initialization of the variable
    private Parser parser;
    private Room currentRoom;
    private Player player;
    
    private Room lobby, mapChamber, southEastCorner, southHall, northHall, southEastChamber, 
    northEastChamber, northWestChamber, weaponChamber, tpChamber, minotaurChamber, northEastCorner;
    
    private Item sword, map, skull, offering, bread;
    
    private Character warrior, wizard, minotaur;
    
    private ArrayList<Character> characters;
    
    private Stack<Room> visitedRooms; //stack to record all the rooms visited.
    
    private boolean win;
    
    private boolean finished = false;
    
    /**
     * Create the game and initialise the player and its internal map.
     */
    public Game() 
    {
        createRooms();
        createItems();
        createCharacters();
        parser = new Parser();
        player = new Player();
        visitedRooms = new Stack<>();
        win = false;
    }

    /**
     * Create all the rooms and link their exits together.
     */    
    private void createRooms()
    {
        // create the rooms
        lobby = new Room("in the entrance lobby of the Labyrinth ");
        mapChamber = new Room("in the Map Chamber");
        southEastCorner = new Room("in the South East Corner");
        southHall = new Room("in the South Hall");
        northHall = new Room("in the North Hall");
        southEastChamber = new Room("in the South East Chamber");
        northEastChamber = new Room("in the North East Chamber");
        northWestChamber = new Room("in the North West Chamber");
        weaponChamber = new Room("in the sword Chamber");
        tpChamber = new Room("in the Magical Teleportation Chamber");
        minotaurChamber = new Room("in the Minotaur Chamber");
        northEastCorner = new Room("in the North East Corner");
        
        // initialise room exits
        lobby.setExit("north", southHall);
        lobby.setExit("east", southEastCorner);
        lobby.setExit("west", mapChamber);
        
        southHall.setExit("south", lobby);
        southHall.setExit("north", northHall);
        southHall.setExit("east", southEastChamber);
        
        southEastCorner.setExit("west", lobby);
        southEastCorner.setExit("north", southEastChamber);
        
        mapChamber.setExit("east", lobby);
        
        northHall.setExit("south", southHall);
        northHall.setExit("east", northEastChamber);
        northHall.setExit("west", northWestChamber);
        
        southEastChamber.setExit("west", southHall);
        southEastChamber.setExit("south", southEastCorner);
        
        northEastChamber.setExit("west", northHall);
        northEastChamber.setExit("north", northEastCorner);
        
        northWestChamber.setExit("east", northHall);
        northWestChamber.setExit("north", weaponChamber);
        northWestChamber.setExit("south", tpChamber);
        
        weaponChamber.setExit("south", northWestChamber);
        
        tpChamber.setExit("north", northWestChamber);
        
        northEastCorner.setExit("south", northEastChamber);
        northEastCorner.setExit("west", minotaurChamber);
        
        minotaurChamber.setExit("east", northEastCorner);
        
        currentRoom = lobby;  // start game in the lobby
    }
    
    /**
     * Creates all the items and adds them into the rooms.
     */
    private void createItems()
    {
        // create the items
        sword = new Item("sword", 10, true);
        map = new Item("map", 5, true);
        skull = new Item("skull", 0, false);
        offering = new Item("offering", 10, true);
        bread = new Item("bread", 10, true);
        
        // adds items in the rooms
        mapChamber.addItem(map);
        southHall.addItem(skull);
        southHall.addItem(offering);
        northHall.addItem(bread);
    }
    
    /**
     * Creates the characters and sets the rooms they are allowed in
     * and the items they need.
     */
    private void createCharacters()
    {
        Room[] wizardRooms = {southEastChamber, southEastCorner};
        Room[] minotaurRooms = {minotaurChamber, northEastCorner};
        Room[] warriorRooms = {northWestChamber, weaponChamber};
        
        ArrayList<Item> wizardItem = new ArrayList<>();
        wizardItem.add(offering);
        
        ArrayList<Item> minotaurItem = new ArrayList<>();
        
        ArrayList<Item> warriorItem = new ArrayList<>();
        warriorItem.add(bread);
        warriorItem.add(sword);
        
        wizard = new Character("wizard", wizardItem, wizardRooms);
        minotaur = new Character("minotaur", minotaurItem, minotaurRooms);
        warrior = new Character("warrior", warriorItem, warriorRooms);
        
        characters = new ArrayList<>();
        characters.add(wizard);
        characters.add(minotaur);
        characters.add(warrior);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        if (win) {
            System.out.println("You have defeated the minotaur and won. Thank you for playing. ");
        }
        else {
        System.out.println("Thank you for playing.  Good bye.");
        }
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Labyrinth.");
        System.out.println("You are an ancient Greek Warrior on a mission to defeat the mythical beast Minotaur. ");
        System.out.println("Find a map to avoid getting lost inside the Labyrinth. Certain items and characters ");
        System.out.println("you will find along the way will be the key to finding the beast and defeating it. ");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...\n");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("inventory")) {
            player.printInventory();            
        }
        else if (commandWord.equals("get")) {
            getItem(command);
        }
        else if (commandWord.equals("drop")) {
            dropItem(command);
        }
        else if (commandWord.equals("back")) {
            back(command);
        }
        else if (commandWord.equals("map")) {
            map(command);
        }
        else if (commandWord.equals("give")) {
            give(command);
        }
        else if (commandWord.equals("kill")) {
            wantToQuit = kill(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?\n");
            return;
        }
        
        if(command.hasThirdWord()) {
            System.out.println("Can't have a third word with this command\n");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!\n");
        }
        else if(nextRoom == tpChamber) //Checks if the next Room is the same as the teleportation room.
        {
            teleport();
        }
        else {
            visitedRooms.push(currentRoom); //pushes the previous room into the stack.
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            moveCharacters();
            checkCharacterLocation();
        }
    }
    
    /**
     * Try to pick up one item.
     * Verifies if the item exists in the room, can be picked up and
     * doesn't exceed the Maximum weight limit.
     * If so the item is picked up. If not an error message is displayed.
     */
    private void getItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to get...
            System.out.println("Get what?\n");
            return;
        }
        
        // error message if there is a third word.
        if(command.hasThirdWord()) {
            System.out.println("Can't have a third word with this command\n");
            return;
        }
        
        String item = command.getSecondWord();
        
        Item newItem = currentRoom.getItem(item);

        if (newItem == null) {
            System.out.println("The item does not exist.");
            //error message if the item doesn't exist in the room.
        }
        else {
            if(!newItem.getPermission()){
                System.out.println("Oops! This item cannot be picked up.\n");
                //error mesage if the item cannot be picked up.
            }
            else{
                //checks if picking up the item will exceed the total max weight.
                if(newItem.getWeight() + player.checkWeight() <= player.getMaxWeight()){
                player.addItem(newItem);
                currentRoom.removeItem(newItem);
                System.out.println("Picked up: " + newItem.getName() + "\n");
                }
                else {
                //error message if the total weight is exceeeded.
                System.out.println("Picking up the item will exceed the weight limit");
                System.out.println("Please drop some item.\n");
                }
            }
        }
    }
    
    /**
     * Try to drop one item. If the item is in your inventory, the
     * item is dropped, otherwise print an error message.
     */
    private void dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
        }
        
        // error message if there is a third word.
        if(command.hasThirdWord()) {
            System.out.println("Can't have a third word with this command\n");
            return;
        }
        
        String item = command.getSecondWord();
        
        
        Item newItem = player.getItem(item);

        if (newItem == null) {
            System.out.println("You don't have that item.");
            //error message if the item is not in the inventory.
        }
        else {
            currentRoom.addItem(newItem);
            player.removeItem(newItem);
            System.out.println("Dropped: " + newItem.getName() + "\n");
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?\n");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Goes back to the latest previous room visited which was 
     * recorded in a stack.
     */
    private void back(Command command)
    {
        if(command.hasSecondWord()) {
            //this command word can't have a second word.
            System.out.println("Back what?\n");
        }
        else {
            if (visitedRooms.empty()){
                //if the stack is empty, there is no more rooms left for back command.
                System.out.println("No rewinds left.\n");
            }
            else {
                //pops the last room visited from the stack and sets it as current room.
                currentRoom = visitedRooms.pop();
                System.out.println(currentRoom.getLongDescription());
                moveCharacters();
                checkCharacterLocation();
            }
            }
        }
        
    /**
     * Displays a map but the item 'map' is needed inside the inventory
     * in order to use this function.
     */
    private void map(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Map what?\n");
            return;
        }
        
        if (player.checkMap()){
            System.out.println("|---------------------------------------------------------|");
            System.out.println("|                 |                   |                   |");
            System.out.println("|                 |                   |                   |");
            System.out.println("|                 |                                       |");
            System.out.println("|      Weapon     |     Minotaur           North East     |");
            System.out.println("|      Chamber    |      Chamb               Corner       |");
            System.out.println("|                 |                   |                   |");
            System.out.println("|                 |                   |                   |");
            System.out.println("|------     ------|-------------------|-------      ------|");
            System.out.println("|                 |                   |                   |");
            System.out.println("|                                                         |");
            System.out.println("|   North West            North            North East     |");
            System.out.println("|     Chamber              Hall             Chamber       |");
            System.out.println("|                                                         |");
            System.out.println("|                 |                   |                   |");
            System.out.println("|------     ------|-------     -------|-------------------|");
            System.out.println("|                 |                   |                   |");
            System.out.println("|                 |                   |                   |");
            System.out.println("|     Magical     |       South             South East    |");
            System.out.println("|  Teleportation  |        Hall             Chamber       |");
            System.out.println("|    Chamber      |                                       |");
            System.out.println("|                 |                   |                   |");
            System.out.println("|-----------------|-------     -------|-------     -------|");
            System.out.println("|                 |                   |                   |");
            System.out.println("|                 |                   |                   |");
            System.out.println("|                                                         |");
            System.out.println("|       Map               Lobby            South East     |");
            System.out.println("|     Chamber                                Corner       |");
            System.out.println("|                 |                   |                   |");
            System.out.println("|                 |                   |                   |");
            System.out.println("|---------------------------------------------------------|");
        }
        else {
            System.out.println("You need map in your inventory to use this function.\n");
        }    
        
        
    }
    
    /**
     * Tries to give an item to a character. Only succesful if
     * the character is in the same room as the player, the character
     * needs the item and the player has the item.
     */
    private void give(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to give...
            System.out.println("Give who?\n");
            return;
        }
        
        if(!command.hasThirdWord()) {
            // if there is no third word, we don't know who to give the item to...
            System.out.println("Give this character what?\n");
            return;
        }
        
        if(command.hasFourthWord()) {
            // This command doesn't need a fourth word
            System.out.println("Can't have a fourth word in this command\n");
            return;
        }
        
        Character targetCharacter = null;
        
        //Find the target character
        for (Character character : characters) {
        if (character.getName().equals(command.getSecondWord())) {
            targetCharacter = character;
            break;
            }
        }
        
        
        if (targetCharacter == null) {
            System.out.println("Character does not exist.\n");
            return;
        }
        
        // Check if the target character is in the same room
        if(targetCharacter.getCurrentRoom() != currentRoom) {
            System.out.println(targetCharacter.getName() + " is not in the same room.\n");
            return;
        }
        
        
        //Check if the player has the item 
        Item targetItem = player.getItem(command.getThirdWord());
        
        if(targetItem == null) {
            System.out.println("You don't have this item.\n");
            return;
        }
        
        if(targetCharacter.getItemsNeeded().contains(targetItem)){
            targetCharacter.addItem(targetItem);
            player.removeItem(targetItem);
            
            System.out.println("You gave " + targetItem.getName() + " to " + targetCharacter.getName() + ".\n");
            checkWizard();
            checkWarrior();
        }
        else {
            System.out.println(targetCharacter.getName() + " does not need this item.\n");
        }
    
    }
    
    /**
     * Attempts to kill the minotaur
     * Returns true if the minotaur is in the same room,
     * Strength attribute is true and the player has a sword
     * in the inventory.
     */
    private boolean kill (Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to kill...
            System.out.println("kill what?\n");
            return false;
        }
        
        if(command.hasThirdWord()) {
            System.out.println("Can't have a third word with this command\n");
            return false;
        }
        
        String boss = command.getSecondWord();
        
        if (!boss.equals(minotaur.getName())){
            System.out.println("You can't kill this!\n");
            return false;
        }
        
        if (boss.equals(minotaur.getName())){
            if (currentRoom != minotaur.getCurrentRoom()){
                System.out.println("The minotaur is not in the room.\n");
                return false;
            }
            if (!player.getStrength()){
                System.out.println("You need super strength from the wizard to kill the minotaur.\n");
                return false;
            }
            if (!player.getInventory().contains(sword)){
                System.out.println("You need a sword from the warrior to kill the minotaur.\n");
                return false;
            }
            
            win = true;
            
            return true;
        }
        return false;
    }
    
    /**
     * Check if the offering have been given to the wizard. if so, super strength will be granted.
     */
    private void checkWizard() {
        if (!player.getStrength()){
            if (wizard.getInventory().contains(offering)){
                player.setStrength();
                System.out.println("You have gained super strength.\n");
            }
        }
    }
    
    /**
     * Check if the sword has been obtained from the warrior.
     */
    private void checkWarrior() {
        if (!player.getHasSword()){
            if (warrior.getInventory().contains(bread)){
                player.addItem(sword);
                player.setHasSword();
                System.out.println("You have obtained a sword.\n");
            }
        }
    }
    
    /**
     * Iterates through the array of character and
     * for each of the character, sets the current room to a random room
     * from the character's allowed rooms.
     */
    private void moveCharacters() {
        for (Character character : characters) {
            Room[] allowedRooms = character.getRooms();
            Random random = new Random();
            int i = random.nextInt(allowedRooms.length);
            Room randomRoom = allowedRooms[i];
            character.setCurrentRoom(randomRoom);
        }
    }
    
    /**
     * For each of the character, compares the character's current to 
     * the player's current room and notifies the player if 
     * they are in the same room. 
     */
    private void checkCharacterLocation() {
        for (Character character: characters) {
            if (character.getCurrentRoom() == currentRoom) {
                System.out.println(character.getName() + " is in the same room as you.\n");
            }
        }
    }
    
    /**
     * Teleports the player into a random room that have been generated.
     */
    private void teleport() {
        // List of rooms excluding the teleportation room
        Room[] destinationRooms = {
            lobby, mapChamber, southEastCorner, southHall, northHall,
            southEastChamber, northEastChamber, northWestChamber, 
            weaponChamber, minotaurChamber, northEastCorner
        };
        
        // Randomly select a destination room
        Random random = new Random();
        int i = random.nextInt(destinationRooms.length);
        Room randomRoom = destinationRooms[i];
        
        visitedRooms.push(currentRoom);
        currentRoom = randomRoom;
        System.out.println("You went into the teleportation room.");
        System.out.println("You have been teleported to a random room:\n"
                            + currentRoom.getLongDescription());
        moveCharacters();
        checkCharacterLocation();
    }
}
