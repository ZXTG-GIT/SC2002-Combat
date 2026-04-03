package engine;

import domain.combatant.*;
import domain.item.*;
import ui.GameUI;
import ui.GameRenderer;
import java.util.*;

public class BattleEngine {
    private List<Enemy> enemies = new ArrayList<>();
    private SpeedBasedTurnOrder strategy = new SpeedBasedTurnOrder();
    private boolean isPlayerWon = false;
    private boolean hasBackupSpawned = false;
    private int enemiesDefeated = 0;
    private int round = 1;
    private Player player;
    private Level level;
    private GameUI ui;
    private char enemyLabel = 'A';

    public BattleEngine(Player player, Level level, GameUI ui) {
        this.player = player;
        this.level = level;
        this.ui = ui;
    }

    public boolean isPlayerWon() {
        return isPlayerWon;
    }

    public int getEnemiesDefeated() {
        return enemiesDefeated;
    }

    public int getTotalRounds() {
        return round;
    }

    public String getLevel() {
        return level.name();
    }

    public Player getPlayer() {
        return player;
    }

    public void startBattle() {
        spawnInitialEnemies();

        while (true) {
            ui.showRoundState(player, enemies, round);

            List<Combatant> combatants = new ArrayList<>(enemies);
            combatants.add(player);
            strategy.setTurnOrder(combatants);

            if (player.getHp() <= 0) {
                isPlayerWon = false;
                return;
            }

            // execute turns in speed order
            for (Combatant combatant : combatants) {
                if (!combatant.isAlive()) continue;

                combatant.updateEffects();

                if (combatant instanceof Player) {
                    if (combatant.isStunned()) {
                        System.out.println(GameRenderer.dmg(player.getName() + " is STUNNED and cannot act!"));
                        continue;
                    }
                    executePlayerAction();
                    removeDeadEnemies();

                    if (player.getHp() <= 0) {
                        isPlayerWon = false;
                        return;
                    }
                } else if (combatant instanceof Enemy) {
                    if (combatant.isStunned()) {
                        System.out.println(GameRenderer.dmg(combatant.getName() + " is STUNNED and cannot act!"));
                        continue;
                    }
                    combatant.basicAttack(List.of(player));

                    if (player.getHp() <= 0) {
                        isPlayerWon = false;
                        return;
                    }
                }
            }

            removeDeadEnemies();

            // check for backup spawn
            if (enemies.isEmpty() && !hasBackupSpawned) {
                spawnBackupEnemies();
                hasBackupSpawned = true;
                if (enemies.isEmpty()) {
                    isPlayerWon = true;
                    return;
                }
            }

            // check win after backup already spawned
            if (enemies.isEmpty() && hasBackupSpawned) {
                isPlayerWon = true;
                return;
            }

            // reduce special skill cooldown at end of round
            player.getSpecialSkill().reduceCooldown();

            ui.showEndOfRound(player, round);
            round++;
        }
    }

    private void removeDeadEnemies() {
        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy e = it.next();
            if (!e.isAlive()) {
                it.remove();
                enemiesDefeated++;
            }
        }
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

    private void spawnInitialEnemies() {
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

    private void spawnBackupEnemies() {
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
        if (!backup.isEmpty()) {
            enemies.addAll(backup);
            System.out.print(new GameRenderer().renderBackupSpawn(backup));
        }
    }

    private void executePlayerAction() {
        ui.showActionMenu(player);
        int action = ui.getPlayerAction(player, enemies);
        switch (action) {
            case 1: {
                ui.showTargetMenu(enemies);
                Enemy enemy = ui.chooseTarget(enemies);
                player.basicAttack(List.of(enemy));
                break;
            }

            case 2:
                player.defend(List.copyOf(enemies));
                break;

            case 3: {
                if (player.getSpecialSkill().isMultiTarget()) {
                    player.SpecialSkill(List.copyOf(enemies));
                } else {
                    ui.showTargetMenu(enemies);
                    Enemy enemy = ui.chooseTarget(enemies);
                    player.SpecialSkill(List.of(enemy));
                }
                break;
            }

            case 4: {
                ui.showInventory(player);
                Item item = ui.chooseInventory(player);
                if (item instanceof MultiTargetItem) {
                    player.useItem(item, List.copyOf(enemies));
                } else {
                    player.useItem(item, List.of(player));
                }
                break;
            }
        }
    }
}
