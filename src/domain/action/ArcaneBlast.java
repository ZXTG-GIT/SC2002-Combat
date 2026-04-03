package domain.action;

import java.util.List;

import domain.combatant.Combatant;
import domain.combatant.Player;
import domain.combatant.Wizard;
import ui.GameRenderer;

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
            int dmg = player.calculateReceivingDamage(player, target);
            target.takeDamage(dmg);
            System.out.println(GameRenderer.dmg(player.getName() + " hits " + target.getName() + " with Arcane Blast!"));

            if (!target.isAlive()) {
                ((Wizard) player).addAttackBonus(10);
                System.out.println(GameRenderer.info(player.getName() + " ATK increased by 10!"));
            }
        }
    }
}

