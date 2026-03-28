package domain.effect;

import domain.combatant.Combatant;

public interface StatusEffect {
    void apply(Combatant target);
    void tick();
    boolean isExpired();
}
