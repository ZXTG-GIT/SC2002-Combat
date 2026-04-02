package domain.effect;

import domain.combatant.Combatant;
import ui.GameRenderer;

public class StunEffect implements StatusEffect {

    private int duration = 2;

    @Override
    public void apply(Combatant user) {
        System.out.println(GameRenderer.dmg(user.getName() + " is STUNNED!"));
    }

    @Override
    public void tick() {
        duration--;
    }

    @Override
    public boolean isExpired() {
        return duration <= 0;
    }

    @Override
    public String getName() {
        return "Stun";
    }

    @Override
    public void statusExpired(Combatant user) {
        System.out.println(GameRenderer.info(user.getName() + " is no longer stunned"));
    }
}
