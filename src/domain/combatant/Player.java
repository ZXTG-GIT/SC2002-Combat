package domain.combatant;

import domain.action.SpecialSkill;

public abstract class Player extends Combatant {

    protected SpecialSkill specialSkill;

    public Player(String name, int hp, int atk, int def, int spd) {
        super(name, hp, atk, def, spd);
    }

    public SpecialSkill getSpecialSkill() {
        return specialSkill;
    }
}
