package domain.effect;

import domain.combatant.Combatant;
import ui.GameRenderer;

public class DefendEffect implements StatusEffect {

    private int duration = 1;

    @Override
    public void apply(Combatant user) {
        user.setDef(user.getDef()+10);
        System.out.println(GameRenderer.info(user.getName() + " defends! +10 DEF for 2 rounds"));
    }

    @Override
    public void tick() {
        duration--;
    }

    @Override
    public boolean isExpired() {
        return duration <= 0;
    }

    @Override
    public String getName() {
        return "Defend";
    }

    @Override
    public void statusExpired(Combatant user){
        user.setDef(user.getDef()-10);
        System.out.println(GameRenderer.info(user.getName() + "'s defense boost expired"));
    }
}
