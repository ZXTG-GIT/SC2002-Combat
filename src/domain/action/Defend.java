package domain.action;

import java.util.List;

import domain.combatant.Combatant;
import domain.effect.DefendEffect;



public class Defend implements Action {
    @Override
    public void execute(Combatant user, List<Combatant> targets){
        user.addEffect(new DefendEffect());
    }
}