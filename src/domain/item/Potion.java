package domain.item;

import domain.combatant.Combatant;

public class Potion implements Item {

    @Override
    public void use(Combatant user) {
        user.heal(100);
        System.out.println("Potion used: +100 HP");
    }
}
