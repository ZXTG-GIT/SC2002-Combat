package domain.item;

import domain.combatant.*;
import domain.action.SpecialSkill;

public class PowerStone implements OffensiveItems {

    @Override
    public void use(Combatant user, Combatant target) {

        Player player = (Player) user;

        System.out.println("PowerStone used! Free skill activation!");

        SpecialSkill skill = player.getSpecialSkill();

        skill.useWithoutCooldown(player, target);
    }
}
