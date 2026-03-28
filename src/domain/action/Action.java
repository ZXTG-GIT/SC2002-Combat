package domain.action;

import domain.combatant.Combatant;

public interface Action {
    void execute(Combatant user, Combatant target);
}