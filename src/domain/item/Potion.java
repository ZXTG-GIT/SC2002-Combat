package domain.item;

import domain.combatant.Combatant;
import ui.GameRenderer;

public class Potion implements DefensiveItems {

    @Override
    public String getName(){
        return "Potion";
    }

    @Override
    public void use(Combatant user) {
        int before = user.getHp();
        user.heal(100);
        int after = user.getHp();

        System.out.println(GameRenderer.heal("Potion used! HP " + before + " \u2192 " + after));
    }
}
