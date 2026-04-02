package domain.item;

import domain.combatant.Combatant;
import domain.effect.InvulnerabilityEffect;
import ui.GameRenderer;

public class SmokeBomb implements DefensiveItems {

    @Override
    public String getName(){
        return "SmokeBomb";
    }

    @Override
    public void use(Combatant user) {
        user.addEffect(new InvulnerabilityEffect());
        System.out.println(GameRenderer.warn("Smoke Bomb used! Invulnerable for 2 turns"));
    }
}
