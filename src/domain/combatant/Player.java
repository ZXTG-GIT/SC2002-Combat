package domain.combatant;

import domain.action.SpecialSkill;
import domain.item.*;

import java.util.*;

public abstract class Player extends Combatant {

    protected SpecialSkill specialSkill;


    protected List<Item> inventory = new ArrayList<>();

    public Player(String name, int hp, int atk, int def, int spd) {
        super(name, hp, atk, def, spd);
    }

    public SpecialSkill getSpecialSkill(){
        return this.specialSkill;
    }


    public void addItem(Item item) {
        inventory.add(item);
    }


    public void useItem(String itemName, List<Combatant> targets) {

        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);

            if (item.getName().equals(itemName)) {


                if (item instanceof MultiTargetItem) {
                    ((MultiTargetItem) item).use(this, targets);
                } else if (item instanceof DefensiveItems) {
                    ((DefensiveItems) item).use(this);
                }

                inventory.remove(i);
                return;
            }
        }

        System.out.println("No " + itemName + " left!");
    }


    public void showInventory() {
        Map<String, Integer> countMap = new LinkedHashMap<>();

        for (Item item : inventory) {
            String name = item.getName();
            countMap.put(name, countMap.getOrDefault(name, 0) + 1);
        }

        System.out.println("Inventory:");
        for (String key : countMap.keySet()) {
            System.out.println(key + " x" + countMap.get(key));
        }
    }

    public abstract void SpecialSkill(Combatant target);
}
