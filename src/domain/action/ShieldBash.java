package domain.action;

import java.util.List;

import domain.combatant.Combatant;
import domain.combatant.Player;
import domain.effect.StunEffect;

public class ShieldBash extends SpecialSkill {

    public ShieldBash() {
        super(3);
    }

    @Override
    public void activate(Player player, List<Combatant> targets) {
        Combatant target = targets.get(0);
        player.basicAttack(targets);
        target.addEffect(new StunEffect());
        System.out.println(player.getName() + " uses Shield Bash on " + target.getName() + "!");
    }
}