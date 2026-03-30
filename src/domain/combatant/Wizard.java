package domain.combatant;

import java.util.List;

import domain.action.ArcaneBlast;

public class Wizard extends Player {

    private int attackBonus = 0;

    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
        this.specialSkill = new ArcaneBlast();
    }

    @Override
    public void SpecialSkill(List<Combatant> targets) {
        specialSkill.execute(this, targets);
    }

    public void addAttackBonus(int amount) {
        this.attackBonus += amount;
    }

    public int getAtk(){
        return super.getAtk()+this.attackBonus;
    }

}