package domain.effect;

import domain.combatant.Combatant;

public interface StatusEffect {
    void apply(Combatant user);
    void statusExpired(Combatant user); // Used to do stuff after tick
    void tick();
    boolean isExpired();
    String getName();
}
