package domain.combatant;

import domain.action.ArcaneBlast;

public class Wizard extends Player {

    private int attackBonus = 0;

    public Wizard() {
        super("Wizard", 200, 50, 10, 20);
        this.specialSkill = new ArcaneBlast();
    }

    @Override
    public void SpecialSkill(Combatant target) {
        specialSkill.execute(this, target);
    }

    public void addAttackBonus(int amount) {
        this.attackBonus += amount;
    }

    public int getAtk(){
        return super.getAtk()+this.attackBonus;
    }

}