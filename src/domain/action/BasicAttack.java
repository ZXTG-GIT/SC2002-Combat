package domain.action;

import java.util.List;

import domain.combatant.Combatant;



public class BasicAttack implements Action {
    @Override
    public void execute(Combatant user, List<Combatant> targets) {
        Combatant target = targets.get(0);

        if (target.isInvulnerable()) {
            System.out.println(user.getName() + " deals 0 damage (Smoke Bomb)");
            return;
        }

        int dmg = user.calculateReceivingDamage(user, target);
        target.takeDamage(dmg);
        System.out.println(user.getName() + " attacks " + target.getName() + " for " + dmg);
        System.out.println(target.getName() + " HP: " + target.getHp());
    }
}