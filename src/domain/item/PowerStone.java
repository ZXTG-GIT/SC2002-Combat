package domain.item;

import domain.combatant.*;
import domain.action.SpecialSkill;

public class PowerStone implements Item {

    @Override
    public void use(Combatant user) {

        if (!(user instanceof Player)) {
            System.out.println("Only player can use PowerStone");
            return;
        }

        Player player = (Player) user;

        SpecialSkill skill = player.getSpecialSkill();

        if (skill == null) {
            System.out.println("No skill available");
            return;
        }

        System.out.println("PowerStone used! Free skill activation!");

        skill.useWithoutCooldown(player);
    }
}
