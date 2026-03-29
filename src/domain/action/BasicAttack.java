package domain.action;

import domain.combatant.Combatant;



public class BasicAttack implements Action {
    @Override
    public void execute(Combatant user, Combatant target) {
        int dmg = user.calculateReceivingDamage(user, target);
        target.takeDamage(dmg);
        System.out.println(user.getName() + " attacks " + target.getName() + " for " + dmg);
        System.out.println(target.getName() + " HP: " + target.getHp());
    }
}