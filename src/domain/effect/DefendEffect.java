package domain.effect;

import domain.combatant.Combatant;

public class DefendEffect implements StatusEffect {

    private int duration = 2;

    @Override
    public void apply(Combatant user) {
        user.setDef(user.getDef()+10);
        System.out.println(user.getName() + " defends! +10 defense for 2 turns");
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
        return "DefenseEffect";
    }
}
