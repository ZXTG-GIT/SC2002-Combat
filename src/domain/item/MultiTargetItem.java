package domain.item;

import domain.combatant.Combatant;
import java.util.List;

public interface MultiTargetItem extends Item {
    void use(Combatant user, List<Combatant> targets);
}