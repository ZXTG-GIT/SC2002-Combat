package ui;

import domain.combatant.Combatant;
import domain.combatant.Enemy;
import domain.combatant.Player;
import engine.BattleEngine;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class GameRenderer {

    private static final Logger logger = Logger.getLogger(GameRenderer.class.getName());

    public String renderLoadingScreen() {
        logger.fine("rendering loading screen");
        String s = "";
        s += "\n========================================\n";
        s += "         SC2002 COMBAT ARENA\n";
        s += "========================================\n";
        s += "       Turn-Based Combat Game\n";
        s += "----------------------------------------\n";
        s += "Press Enter to start...";
        return s;
    }

    public String renderDifficultyMenu() {
        logger.fine("rendering difficulty menu");
        String s = "";
        s += "----------------------------------------\n";
        s += "Select Difficulty:\n";
        s += "  1) EASY   - 3 Goblins\n";
        s += "  2) MEDIUM - 1 Goblin + 1 Wolf, backup: 2 Wolves\n";
        s += "  3) HARD   - 2 Goblins, backup: 1 Goblin + 2 Wolves\n";
        s += "----------------------------------------\n";
        s += "Choose: ";
        return s;
    }

    public String renderClassMenu() {
        logger.fine("rendering class menu");
        String s = "";
        s += "----------------------------------------\n";
        s += "Select Class:\n";
        s += "  1) Warrior  (HP 260 | ATK 40 | DEF 20 | SPD 30)\n";
        s += "  2) Wizard   (HP 200 | ATK 50 | DEF 10 | SPD 20)\n";
        s += "----------------------------------------\n";
        s += "Choose: ";
        return s;
    }

    public String renderItemsMenu() {
        logger.fine("rendering items menu");
        String s = "";
        s += "----------------------------------------\n";
        s += "Select 2 Items:\n";
        s += "  1) Potion     - Heals 100 HP\n";
        s += "  2) Smoke Bomb - Invulnerable for 2 turns\n";
        s += "  3) Power Stone - Free skill activation\n";
        s += "----------------------------------------\n";
        s += "Choose item (enter number): ";
        return s;
    }

    public String renderInventory(Player player) {
        logger.fine("rendering inventory");
        String s = "Inventory:\n";
        java.util.List<domain.item.Item> inv = player.getInventory();
        for (int i = 0; i < inv.size(); i++) {
            s += "  " + (i + 1) + ") " + inv.get(i).getName() + "\n";
        }
        s += "Choose item: ";
        return s;
    }

    public String renderRoundState(Player player, List<Enemy> enemies, int round) {
        logger.fine("rendering round " + round);
        String s = "";
        s += "\n========================================\n";
        s += "  ROUND " + round + "\n";
        s += "========================================\n";

        // player stats
        s += "  YOU: " + formatStats(player) + "\n";
        s += "----------------------------------------\n";

        // enemy list
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if (e.isAlive()) {
                s += "  [" + (i + 1) + "] " + formatStats(e) + "\n";
            } else {
                s += "  [" + (i + 1) + "] " + e.getName() + "  (DEAD)\n";
            }
        }
        s += "----------------------------------------\n";
        return s;
    }

    public String renderActionMenu(Player player) {
        logger.fine("rendering action menu");
        String s = "Actions:\n";
        s += "  1) Basic Attack\n";
        s += "  2) Defend\n";
        s += "  3) Special Skill";
        if (player.getSpecialSkill() != null && !player.getSpecialSkill().canUseSkill()) {
            s += " (CD: " + player.getSpecialSkill().getCurrentCooldown() + ")";
        }
        s += "\n";
        s += "  4) Use Item\n";
        s += "Choose action: ";
        return s;
    }

    public String renderTargetMenu(List<Enemy> enemies) {
        logger.fine("rendering target menu");
        String s = "Select target:\n";
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if (e.isAlive()) {
                s += "  " + (i + 1) + ") " + e.getName() + " [HP " + e.getHp() + "]\n";
            }
        }
        s += "Choose: ";
        return s;
    }

    public String renderEndOfRound(Player player, int round) {
        logger.fine("rendering end of round");
        String s = "";
        s += "----------------------------------------\n";
        s += "  Round " + round + " complete!\n";
        s += "  " + player.getName() + " HP: " + player.getHp() + "\n";
        s += "----------------------------------------\n";
        return s;
    }

    public String renderBattleResult(BattleEngine session) {
        logger.fine("rendering battle result");
        String s = "\n========================================\n";

        if (session.isPlayerWon()) {
            s += "  VICTORY!\n";
        } else {
            s += "  DEFEAT\n";
        }

        s += "========================================\n";
        s += "  Difficulty : " + session.getLevel() + "\n";
        s += "  Rounds     : " + session.getTotalRounds() + "\n";
        s += "  Defeated   : " + session.getEnemiesDefeated() + " enemies\n";
        s += "  Player HP  : " + session.getPlayer().getHp() + "\n";
        s += "========================================\n";
        return s;
    }

    public String renderBackupSpawn(List<Enemy> backupEnemies) {
        logger.fine("backup wave spawned");
        String s = "\n----------------------------------------\n";
        s += "  BACKUP WAVE INCOMING!\n";
        s += "  ";
        for (int i = 0; i < backupEnemies.size(); i++) {
            if (i > 0) s += ", ";
            s += backupEnemies.get(i).getName();
        }
        s += " appeared!\n";
        s += "----------------------------------------\n";
        return s;
    }

    // helper to format a combatant's stats into one line
    private String formatStats(Combatant c) {
        return c.getName() + "  HP:" + c.getHp()
            + "  ATK:" + c.getAtk()
            + "  DEF:" + c.getDef()
            + "  SPD:" + c.getSpeed();
    }
}
