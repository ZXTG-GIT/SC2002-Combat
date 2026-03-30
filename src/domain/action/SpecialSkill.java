package domain.action;

import java.util.List;

import domain.combatant.Combatant;
import domain.combatant.Player;

public abstract class SpecialSkill implements Action {

    private int cooldown = 0;
    private int baseCooldown;

    public SpecialSkill(int baseCooldown) {
        this.baseCooldown = baseCooldown;
    }

    public int getBaseCooldown(){
        return this.baseCooldown;
    }

    public int getCurrentCooldown(){
        return this.cooldown;
    }

    public boolean canUseSkill() {
        return cooldown == 0;
    }

    public void setCooldown(int cd) {
        this.cooldown = cd;
    }

    public void reduceCooldown() {
        if (cooldown > 0) cooldown--;
    }

    public boolean isMultiTarget() {
        return false; 
    }

    @Override
    public void execute(Combatant user, List<Combatant> targets) {
        Player player = (Player) user;
        if (!canUseSkill()) {
            System.out.println("Skill on cooldown! " + cooldown + " turns remaining");
            return;
        }
        activate(player, targets);
        setCooldown(this.getBaseCooldown());
    }

    public abstract void activate(Player player, List<Combatant> targets);
}