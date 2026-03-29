package domain.combatant;

public class Enemy extends Combatant {

    public Enemy(String name, int hp, int atk, int def, int spd) {
        super(name, hp, atk, def, spd);
    }

    public void act(Combatant target) {
        if (target.isInvulnerable()) {
            System.out.println(this.getName() + " deals 0 damage (Smoke Bomb)");
            return;
        }
        basicAttack(target);
    }

}