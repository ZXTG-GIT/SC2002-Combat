package domain.combatant;

import domain.action.ShieldBash;

public class Warrior extends Player {

    public Warrior() {
        super("Warrior", 260, 40, 20, 30);
        this.specialSkill = new ShieldBash();
    }

    @Override
    public void SpecialSkill(Combatant target) {
        specialSkill.execute(this, target);
    }


}