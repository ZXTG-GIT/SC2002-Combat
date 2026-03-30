package domain.combatant;
import java.util.*;

public class Enemy extends Combatant {

    public Enemy(String name, int hp, int atk, int def, int spd) {
        super(name, hp, atk, def, spd);
    }

    public void act(List<Combatant> targets) {
        basicAttack(targets);
    }

}
