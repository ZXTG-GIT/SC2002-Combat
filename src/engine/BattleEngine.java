package engine;

import domain.combatant.*;
import domain.item.*;
import ui.GameUI;
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
            
            if (enemies.size() == 0 && !hasBackupSpawned) {
                spawnBackupEnemies();
                hasBackupSpawned = true;
            }

            for (Combatant combatant : combatants) {
                combatant.updateEffects();
            }
            if (player.getHp() <= 0) {
                isPlayerWon = false;
                return;
            }

            for (Combatant combatant : combatants) {
                if (combatant instanceof Player) {
                    executePlayerAction();

                    if (player.getHp() <= 0) {
                        isPlayerWon = false;
                        return;
                    }
                } else if (combatant instanceof Enemy) {
                    combatant.basicAttack(List.of(player));

                    if (combatant.getHp() <= 0) {
                        enemies.remove(enemies.indexOf(combatant));
                        combatants.remove(enemies.indexOf(combatant));
                        enemiesDefeated += 1;
                    }
                }
            }

            if (enemies.size() == 0 && hasBackupSpawned) {
                isPlayerWon = true;
                return;
            }

            ui.showEndOfRound(player, round);
        }
    }

    private void spawnInitialEnemies() {
        switch (level) {
            case Level.Easy:
                enemies.add(new Goblin());
                enemies.add(new Goblin());
                enemies.add(new Goblin());
                break;

            case Level.Medium:
                enemies.add(new Goblin());
                enemies.add(new Wolf());
                break;

            case Level.Hard:
                enemies.add(new Goblin());
                enemies.add(new Goblin());
                break;
        }
    }

    private void spawnBackupEnemies() {
        switch (level) {
            case Level.Easy:
                break;

            case Level.Medium:
                enemies.add(new Wolf());
                enemies.add(new Wolf());
                break;

            case Level.Hard:
                enemies.add(new Goblin());
                enemies.add(new Wolf());
                break;
        }
    }

    private void executePlayerAction() {
        ui.showActionMenu(player);
        int action = ui.getPlayerAction(player, enemies);
        switch (action) {
            case 1:
                ui.showTargetMenu(enemies);
                Enemy enemy = ui.chooseTarget(enemies);
                player.basicAttack(List.of(enemy));
                break;

            case 2:
                player.defend(List.copyOf(enemies));
                break;

            case 3:
                ui.showInventory(player);
                Item item = ui.chooseInventory(player);
                if (player.getSpecialSkill().isMultiTarget()) {
                    player.useItem(item, List.copyOf(enemies));
                } else {
                    ui.showTargetMenu(enemies);
                    enemy = ui.chooseTarget(enemies);
                    player.useItem(item, List.of(enemy));
                }
                return;

            case 4:
                if (player.getSpecialSkill().isMultiTarget()) {
                    player.SpecialSkill(List.copyOf(enemies));
                } else {
                    ui.showTargetMenu(enemies);
                    enemy = ui.chooseTarget(enemies);
                    player.SpecialSkill(List.of(enemy));
                }
                return;
        }
    }
}