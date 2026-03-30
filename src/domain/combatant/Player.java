package domain.combatant;

import domain.action.SpecialSkill;
import domain.item.*;

import java.util.*;

public abstract class Player extends Combatant {

    protected SpecialSkill specialSkill;


    protected static List<Item> sharedInventory = new ArrayList<>();

    public Player(String name, int hp, int atk, int def, int spd) {
        super(name, hp, atk, def, spd);
    }

    public SpecialSkill getSpecialSkill(){
        return this.specialSkill;
    }


    public void addItem(Item item) {
        sharedInventory.add(item);
    }


    public void useItem(String itemName, List<Combatant> targets) {

        for (int i = 0; i < sharedInventory.size(); i++) {
            Item item = sharedInventory.get(i);

            if (item.getName().equals(itemName)) {


                if (item instanceof MultiTargetItem) {
                    ((MultiTargetItem) item).use(this, targets);
                } else if (item instanceof DefensiveItems) {
                    ((DefensiveItems) item).use(this);
                } else {
                    item.use(this); // fallback
                }


                sharedInventory.remove(i);

                System.out.println(itemName + " used!");
                return;
            }
        }

        System.out.println("No " + itemName + " left!");
    }


    public void showInventory() {
        Map<String, Integer> countMap = new LinkedHashMap<>();

        for (Item item : sharedInventory) {
            String name = item.getName();
            countMap.put(name, countMap.getOrDefault(name, 0) + 1);
        }

        System.out.println("Shared Inventory:");
        for (String key : countMap.keySet()) {
            System.out.println(key + " x" + countMap.get(key));
        }
    }

    public abstract void SpecialSkill(Combatant target);
}
