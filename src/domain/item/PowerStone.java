package domain.item;

import domain.combatant.*;

import java.util.List;

import domain.action.SpecialSkill;

public class PowerStone implements MultiTargetItem {

    @Override
    public String getName(){
        return "PowerStone";
    }

    @Override
    public void use(Combatant user, List<Combatant> targets) {

        Player player = (Player) user;

        System.out.println("PowerStone used! Free skill activation!");

        SpecialSkill skill = player.getSpecialSkill();

        if (skill.isMultiTarget()) {
            for (Combatant target : targets) {
                skill.activate(player, target);
            }
        } else {
            skill.activate(player, targets.get(0));
        }
    }
}
