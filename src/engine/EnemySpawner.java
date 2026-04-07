package engine;

import java.util.ArrayList;
import java.util.List;

import domain.combatant.Enemy;
import domain.combatant.Goblin;
import domain.combatant.Wolf;
import ui.GameRenderer;

public class EnemySpawner {
    private char enemyLabel = 'A';
    private List<Enemy> enemies;

    public EnemySpawner(List<Enemy> enemies) {
        this.enemies = enemies;
    }
    
    private Goblin namedGoblin() {
        Goblin g = new Goblin();
        g.setName("Goblin " + enemyLabel++);
        return g;
    }

    private Wolf namedWolf() {
        Wolf w = new Wolf();
        w.setName("Wolf " + enemyLabel++);
        return w;
    }

    public void spawnInitialEnemies(Level level) {
        switch (level) {
            case Easy:
                enemies.add(namedGoblin());
                enemies.add(namedGoblin());
                enemies.add(namedGoblin());
                break;

            case Medium:
                enemies.add(namedGoblin());
                enemies.add(namedWolf());
                break;

            case Hard:
                enemies.add(namedGoblin());
                enemies.add(namedGoblin());
                break;
        }
    }

    public void spawnBackupEnemies(Level level) {
        List<Enemy> backup = new ArrayList<>();
        switch (level) {
            case Easy:
                break;

            case Medium:
                backup.add(namedWolf());
                backup.add(namedWolf());
                break;

            case Hard:
                backup.add(namedGoblin());
                backup.add(namedWolf());
                break;
        }
        enemies.addAll(backup);
        System.out.print(new GameRenderer().renderBackupSpawn(backup));
    }
}
