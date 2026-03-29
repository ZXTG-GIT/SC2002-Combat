package domain.item;

import domain.combatant.Combatant;

public interface DefensiveItems extends Item {
    void use(Combatant user);
}