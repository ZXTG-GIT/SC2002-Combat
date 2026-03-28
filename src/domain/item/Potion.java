package domain.item;

import domain.combatant.Combatant;

public class Potion implements Item {

    @Override
    public void use(Combatant user) {
        int before = user.getHp();
        user.heal(100);
        int after = user.getHp();

        System.out.println("Potion used: HP " + before + " → " + after);
    }
}
