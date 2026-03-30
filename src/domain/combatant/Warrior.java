package domain.combatant;

import java.util.List;

import domain.action.ShieldBash;

public class Warrior extends Player {

    public Warrior() {
        super("Warrior", 260, 40, 20, 30);
        this.specialSkill = new ShieldBash();
    }

    @Override
    public void SpecialSkill(List<Combatant> targets) {
        specialSkill.execute(this, targets);
    }


}