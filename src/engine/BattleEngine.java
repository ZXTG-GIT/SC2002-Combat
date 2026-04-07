package engine;

import domain.combatant.*;
import domain.item.*;
import ui.GameUI;
import ui.GameRenderer;
import java.util.*;

public class BattleEngine {
    private List<Enemy> enemies = new ArrayList<Enemy>();
    private SpeedBasedTurnOrder strategy = new SpeedBasedTurnOrder();
    private GameSession session = new GameSession();
    private EnemySpawner spawner = new EnemySpawner(enemies);
    private Player player;
    private Level level;
    private GameUI ui;

    public BattleEngine(Player player, Level level, GameUI ui) {
        this.player = player;
        this.level = level;
        this.ui = ui;
    }

    public GameSession getSession() {
        return session;
    }

    public String getLevel() {
        return level.name();
    }

    public Player getPlayer() {
        return player;
    }

    public void startBattle() {
        spawner.spawnInitialEnemies(level);

        while (true) {
            List<Combatant> combatants = new ArrayList<>(enemies);
            combatants.add(player);
            strategy.setTurnOrder(combatants);

            // execute turns in speed order
            for (Combatant combatant : combatants) {
                if (!combatant.isAlive())
                    continue;
                combatant.updateEffects();
                if (combatant.isStunned()) {
                    System.out.println(GameRenderer.dmg(combatant.getName() + " is STUNNED and cannot act!"));
                    continue;
                }

                if (combatant instanceof Player) {
                    ui.showRoundState(player, enemies, session.getTotalRounds());
                    executePlayerAction();
                } else if (combatant instanceof Enemy) {
                    combatant.basicAttack(List.of(player));
                }

                if (player.getHp() <= 0) {
                    session.setPlayerWon(false);
                    return;
                }
            }

            removeDeadEnemies();

            if (enemies.isEmpty()) {
                // check for backup spawn
                if (session.isBackupSpawned() || level == Level.Easy) {
                    session.setPlayerWon(true);
                    return;
                } else {
                    spawner.spawnBackupEnemies(level);
                    session.markBackupSpawned();
                }
            }

            // reduce special skill cooldown at end of round
            if (!player.isUsedPowerStone()) {
                player.getSpecialSkill().reduceCooldown();
            }
            player.setUsedPowerStone(false);

            ui.showEndOfRound(player, session.getTotalRounds());
            session.incrementRound();
        }
    }

    private void removeDeadEnemies() {
        enemies.removeIf(enemy -> {
            session.recordEnemyDefeated();
            return !enemy.isAlive();
        });
    }

    private void executePlayerAction() {
        ui.showActionMenu(player);
        int action = ui.getPlayerAction(player, enemies);
        switch (action) {
            case 1: {
                executeBasicAttack();
                break;
            }
            case 2:
                executeDefend();
                break;
            case 3: {
                executeSpecialSkill();
                break;
            }
            case 4: {
                executeItem();
                break;
            }
        }
    }

    private void executeBasicAttack() {
        ui.showTargetMenu(enemies);
        Enemy enemy = ui.chooseTarget(enemies);
        player.basicAttack(List.of(enemy));
    }

    private void executeDefend() {
        player.defend(List.copyOf(enemies));
    }

    private void executeSpecialSkill() {
        if (player.getSpecialSkill().isMultiTarget()) {
            player.SpecialSkill(List.copyOf(enemies));
        } else {
            ui.showTargetMenu(enemies);
            Enemy enemy = ui.chooseTarget(enemies);
            player.SpecialSkill(List.of(enemy));
        }
    }

    private void executeItem() {
        ui.showInventory(player);
        Item item = ui.chooseInventory(player);
        if (item instanceof PowerStone) {
            player.setUsedPowerStone(true);
        }
        if (item instanceof MultiTargetItem) {
            if (player.getSpecialSkill().isMultiTarget()) {
                player.useItem(item, List.copyOf(enemies));
            } else {
                ui.showTargetMenu(enemies);
                Enemy enemy = ui.chooseTarget(enemies);
                player.useItem(item, List.of(enemy));
            }
        } else {
            player.useItem(item, List.of(player));
        }
    }
}
