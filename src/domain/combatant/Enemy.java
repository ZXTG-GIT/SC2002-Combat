package domain.combatant;

import engine.BattleEngine;

public abstract class Enemy extends Combatant {

    public Enemy(String name, int hp, int atk, int def, int spd) {
        super(name, hp, atk, def, spd);
    }

    @Override
    public void takeTurn(BattleEngine engine) {

        Combatant player = engine.getPlayer();

        if (player.isInvulnerable()) {
            System.out.println(name + " deals 0 damage (Smoke Bomb)");
            return;
        }

        int dmg = calculateDamage(player);
        player.takeDamage(dmg);

        System.out.println(name + " attacks player for " + dmg);
    }
}
