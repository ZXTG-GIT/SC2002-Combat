package domain.action;

import java.util.List;

import domain.combatant.Combatant;
import domain.combatant.Player;
import domain.effect.StunEffect;
import ui.GameRenderer;

public class ShieldBash extends SpecialSkill {

    public ShieldBash() {
        super(3);
    }

    @Override
    public void activate(Player player, List<Combatant> targets) {
        Combatant target = targets.get(0);
        System.out.println(GameRenderer.dmg(player.getName() + " uses Shield Bash on " + target.getName() + "!"));
        
        int dmg = player.calculateReceivingDamage(player, target);
        target.takeDamage(dmg);
        System.out.println(GameRenderer.dmg(player.getName() + " hits " + target.getName() + " for " + dmg + " damage"));
        
        target.addEffect(new StunEffect());
    }
}