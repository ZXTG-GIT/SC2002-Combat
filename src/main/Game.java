package main;

import engine.*;
import domain.combatant.*;
import java.util.*;

public class Game {
    public static void main(String[] args) {

        Player player = new Warrior();

        List<Enemy> enemies = new ArrayList<>();
        enemies.add(new Goblin());
        enemies.add(new Goblin());
        enemies.add(new Goblin());

        TurnOrderStrategy strategy = new SpeedBasedTurnOrder();
        BattleEngine engine = new BattleEngine(strategy);

        engine.startBattle(player, enemies);
    }
}
