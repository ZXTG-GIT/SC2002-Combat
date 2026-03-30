package engine;

import domain.combatant.*;
import java.util.List;

public interface TurnOrderStrategy {
    void setTurnOrder(List<Combatant> combatants);
}