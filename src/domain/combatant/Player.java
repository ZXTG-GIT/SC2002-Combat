package domain.combatant;

import domain.action.SpecialSkill;

public abstract class Player extends Combatant {

    protected SpecialSkill specialSkill;
    protected int skillCooldown = 0;

    public Player(String name, int hp, int atk, int def, int spd) {
        super(name, hp, atk, def, spd);
    }

    public boolean canUseSkill() {
        return skillCooldown == 0;
    }

    public void setCooldown(int cd) {
        this.skillCooldown = cd;
    }

    public void reduceCooldown() {
        if (skillCooldown > 0) skillCooldown--;
    }

    public SpecialSkill getSpecialSkill() {
        return specialSkill;
    }
}
