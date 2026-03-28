package domain.item;

import domain.combatant.Combatant;

public interface Item {
    void use(Combatant user);
}
