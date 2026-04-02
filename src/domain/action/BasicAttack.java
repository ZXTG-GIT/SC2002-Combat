package domain.action;

import java.util.List;

import domain.combatant.Combatant;
import ui.GameRenderer;

public class BasicAttack implements Action {
    @Override
    public void execute(Combatant user, List<Combatant> targets) {
        Combatant target = targets.get(0);

        if (target.isInvulnerable()) {
            System.out.println(GameRenderer.warn(user.getName() + "'s attack blocked by Smoke Bomb! (0 damage)"));
            return;
        }

        int dmg = user.calculateReceivingDamage(user, target);
        target.takeDamage(dmg);
        System.out.println(GameRenderer.dmg(user.getName() + " attacks " + target.getName() + " for " + dmg + " damage"));
    }
}