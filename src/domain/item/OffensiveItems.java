package domain.item;

import domain.combatant.Combatant;

public interface OffensiveItems extends Item {
    void use(Combatant user, Combatant target);
}