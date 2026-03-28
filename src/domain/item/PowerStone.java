package domain.item;

import domain.combatant.Combatant;

public class PowerStone implements Item {

    @Override
    public void use(Combatant user) {
        System.out.println("PowerStone used (not fully implemented)");
    }
}
