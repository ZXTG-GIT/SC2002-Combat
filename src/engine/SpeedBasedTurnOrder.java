package engine;

import domain.combatant.*;

import java.util.Comparator;
import java.util.List;

public class SpeedBasedTurnOrder implements TurnOrderStrategy {
    public void setTurnOrder(List<Combatant> combatants) {
        combatants.sort(
                Comparator.comparing(Combatant::getSpeed).reversed());
    };
}