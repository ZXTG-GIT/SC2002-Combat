package domain.item;

import domain.combatant.Combatant;
import domain.effect.InvulnerabilityEffect;

public class SmokeBomb implements DefensiveItems {

    @Override
    public void use(Combatant user) {

        user.addEffect(new InvulnerabilityEffect());

        System.out.println("Smoke Bomb used: Enemy damage = 0 for 2 turns");
    }
}
