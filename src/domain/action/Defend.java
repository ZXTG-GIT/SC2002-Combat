package domain.action;

import domain.combatant.Combatant;
import domain.effect.DefendEffect;



public class Defend implements Action {
    @Override
    public void execute(Combatant user, Combatant target){
        user.addEffect(new DefendEffect());
    }
}