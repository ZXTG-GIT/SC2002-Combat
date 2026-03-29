package domain.item;

import domain.combatant.Combatant;

public interface SingleTargetItem extends Item {
    void use(Combatant user, Combatant target);
}