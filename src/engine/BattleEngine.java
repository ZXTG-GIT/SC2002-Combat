package engine;

import domain.combatant.*;
import java.util.*;

public class BattleEngine {

    private TurnOrderStrategy strategy;
    private Player player;
    private List<Enemy> enemies;

    public BattleEngine(TurnOrderStrategy strategy) {
        this.strategy = strategy;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Combatant getFirstAliveEnemy() {
        return enemies.stream().filter(Enemy::isAlive).findFirst().orElse(null);
    }

    public void startBattle(Player player, List<Enemy> enemies) {
        this.player = player;
        this.enemies = enemies;

        int round = 1;

        while (player.isAlive() && enemies.stream().anyMatch(Enemy::isAlive)) {

            System.out.println("\n=== Round " + round + " ===");

            player.processEffects();
            enemies.forEach(Enemy::processEffects);

            List<Combatant> order = strategy.determineOrder(player, enemies);

            for (Combatant c : order) {
                if (!c.isAlive()) continue;
              
                if (c.isStunned()) {
                    System.out.println(c.getName() + " is stunned and skips turn");
                    continue;
                }

                c.takeTurn(this);
                
                if (c instanceof Player) 
                    ((Player) c).reduceCooldown();
                
                if (!player.isAlive() || enemies.stream().noneMatch(Enemy::isAlive))
                    break;
            }

            System.out.println("Player HP: " + player.getHp());
            round++;
        }

        System.out.println(player.isAlive() ? "Victory!" : "Defeat!");
    }
}
