package domain.action;

import domain.combatant.Combatant;
import domain.combatant.Player;
import domain.effect.StunEffect;

public class ShieldBash extends SpecialSkill {

    public ShieldBash() {
        super(3);
    }

    @Override
    public void activate(Player player, Combatant target) {
        player.basicAttack(target);
        target.addEffect(new StunEffect());
        System.out.println(player.getName() + " uses Shield Bash on " + target.getName() + "!");
    }
}