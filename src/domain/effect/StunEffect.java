package domain.effect;

import domain.combatant.Combatant;

public class StunEffect implements StatusEffect {

    private int duration = 2;

    @Override
    public void apply(Combatant target) {
        System.out.println(target + " is stunned!");
    }

    @Override
    public void tick() {
        duration--;
    }

    @Override
    public boolean isExpired() {
        return duration <= 0;
    }
}
