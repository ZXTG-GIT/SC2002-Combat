package domain.effect;

import domain.combatant.Combatant;

public interface StatusEffect {
    void apply(Combatant user);
    void tick();
    boolean isExpired();
    String getName();
}
