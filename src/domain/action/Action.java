package domain.action;

import java.util.List;

import domain.combatant.Combatant;

public interface Action {
    void execute(Combatant user, List<Combatant> targets);
}