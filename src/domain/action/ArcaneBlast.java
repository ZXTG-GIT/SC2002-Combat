package domain.action;

import domain.combatant.Combatant;
import domain.combatant.Player;
import domain.combatant.Wizard;

public class ArcaneBlast extends SpecialSkill {

    public ArcaneBlast() {
        super(3);
    }

    @Override
    public void activate(Player player, Combatant target) {
        player.basicAttack(target);
        System.out.println(player.getName() + " hits " + target.getName() + " with Arcane Blast!");

        if (!target.isAlive()) {
            ((Wizard) player).addAttackBonus(10);
            System.out.println(player.getName() + " ATK increased by 10!");
        }
    }
}

