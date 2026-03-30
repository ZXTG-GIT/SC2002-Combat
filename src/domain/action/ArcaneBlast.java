package domain.action;

import java.util.List;

import domain.combatant.Combatant;
import domain.combatant.Player;
import domain.combatant.Wizard;

public class ArcaneBlast extends SpecialSkill {

    public ArcaneBlast() {
        super(3);
    }

    @Override
    public boolean isMultiTarget() {
        return true;
    }

    @Override
    public void activate(Player player, List<Combatant> targets) {
        for (Combatant target : targets) {
            player.basicAttack(targets);
            System.out.println(player.getName() + " hits " + target.getName() + " with Arcane Blast!");

            if (!target.isAlive()) {
                ((Wizard) player).addAttackBonus(10);
                System.out.println(player.getName() + " ATK increased by 10!");
            }
        }
    }
}

