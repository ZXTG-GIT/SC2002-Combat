package ui;

import domain.combatant.Combatant;
import domain.combatant.Enemy;
import domain.combatant.Player;
import domain.item.Item;
import engine.BattleEngine;
import java.util.List;
import java.util.logging.Logger;

public class GameRenderer {

    private static final Logger logger = Logger.getLogger(GameRenderer.class.getName());

    // ANSI escape codes
    private static final String RST    = "\033[0m";
    private static final String BOLD   = "\033[1m";
    private static final String RED    = "\033[31m";
    private static final String GRN    = "\033[32m";
    private static final String YEL    = "\033[33m";
    private static final String CYN    = "\033[36m";
    private static final String B_RED  = "\033[91m";
    private static final String B_GRN  = "\033[92m";
    private static final String B_YEL  = "\033[93m";
    private static final String B_BLU  = "\033[94m";
    private static final String B_CYN  = "\033[96m";
    private static final String WHT    = "\033[97m";
    private static final String BLK    = "\033[30m";
    private static final String BG_RED = "\033[41m";
    private static final String BG_BLU = "\033[44m";
    private static final String BG_YEL = "\033[43m";

    // combat log formatters — domain classes call these
    public static String dmg(String msg)  { return "  " + RED + ">> " + msg + RST; }
    public static String heal(String msg) { return "  " + GRN + ">> " + msg + RST; }
    public static String info(String msg) { return "  " + CYN + ">> " + msg + RST; }
    public static String warn(String msg) { return "  " + YEL + ">> " + msg + RST; }

    public static void resetColor() { System.out.print(RST); }

    // ── HP bar (20 chars wide) ─────────────────────────────
    private String hpBar(int hp, int maxHp) {
        int filled = (hp > 0) ? Math.max(1, (int) Math.round(20.0 * hp / maxHp)) : 0;
        String col = hpColor(hp, maxHp);
        StringBuilder b = new StringBuilder(col + "[");
        for (int i = 0; i < 20; i++) b.append(i < filled ? "\u2588" : "\u2591");
        b.append("]" + RST + " " + hp + "/" + maxHp + " HP");
        return b.toString();
    }

    private String hpColor(int hp, int maxHp) {
        double pct = (double) hp / maxHp;
        if (pct > 0.5)  return GRN;
        if (pct > 0.25) return YEL;
        return RED;
    }

    // ── status badges ──────────────────────────────────────
    private String badges(Combatant c) {
        StringBuilder b = new StringBuilder();
        if (c.isStunned())      b.append("  " + BG_RED + WHT + BOLD + " STUNNED " + RST);
        if (c.isDefending())    b.append("  " + BG_BLU + WHT + BOLD + " DEFENDING " + RST);
        if (c.isInvulnerable()) b.append("  " + BG_YEL + BLK + BOLD + " SMOKE BOMB " + RST);
        return b.toString();
    }

    // ── box helper for victory/defeat ──────────────────────
    private String boxLine(String text) {
        StringBuilder c = new StringBuilder("  " + text);
        while (c.length() < 38) c.append(' ');
        return "\u2551" + c.toString() + "\u2551";
    }

    // ════════════════════════════════════════════════════════
    // SCREENS
    // ════════════════════════════════════════════════════════

    public String renderLoadingScreen() {
        logger.fine("rendering loading screen");
        return "\n"
            + BOLD + B_CYN
            + "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\n"
            + "        SC2002 COMBAT ARENA\n"
            + "\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550"
            + RST + "\n"
            + "       Turn-Based Combat Game\n"
            + "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n"
            + "Press Enter to start...";
    }

    public String renderDifficultyMenu() {
        logger.fine("rendering difficulty menu");
        return "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n"
            + "Select Difficulty:\n"
            + "  " + B_BLU + "[1]" + RST + " EASY   \u2014 3 Goblins\n"
            + "  " + B_BLU + "[2]" + RST + " MEDIUM \u2014 1 Goblin + 1 Wolf, backup: 2 Wolves\n"
            + "  " + B_BLU + "[3]" + RST + " HARD   \u2014 2 Goblins, backup: 1 Goblin + 2 Wolves\n"
            + "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n"
            + "Choose: ";
    }

    public String renderClassMenu() {
        logger.fine("rendering class menu");
        return "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n"
            + "Select Class:\n"
            + "  " + B_BLU + "[1]" + RST + " Warrior  (HP 260 | ATK 40 | DEF 20 | SPD 30)\n"
            + "  " + B_BLU + "[2]" + RST + " Wizard   (HP 200 | ATK 50 | DEF 10 | SPD 20)\n"
            + "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n"
            + "Choose: ";
    }

    public String renderItemsMenu() {
        logger.fine("rendering items menu");
        return "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n"
            + "Select Item:\n"
            + "  " + B_BLU + "[1]" + RST + " " + YEL + "Potion" + RST + "      \u2014 Heals 100 HP\n"
            + "  " + B_BLU + "[2]" + RST + " " + YEL + "Smoke Bomb" + RST + "  \u2014 Invulnerable for 2 turns\n"
            + "  " + B_BLU + "[3]" + RST + " " + YEL + "Power Stone" + RST + " \u2014 Free skill activation\n"
            + "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n"
            + "Choose item: ";
    }

    public String renderInventory(Player player) {
        logger.fine("rendering inventory");
        StringBuilder s = new StringBuilder("Inventory:\n");
        List<Item> inv = player.getInventory();
        for (int i = 0; i < inv.size(); i++) {
            s.append("  " + B_BLU + "[" + (i + 1) + "]" + RST + " "
                + YEL + inv.get(i).getName() + RST + "\n");
        }
        s.append("Choose item: ");
        return s.toString();
    }

    public String renderRoundState(Player player, List<Enemy> enemies, int round) {
        logger.fine("rendering round " + round);
        StringBuilder s = new StringBuilder("\n");

        s.append(BOLD + B_CYN);
        s.append("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\n");
        s.append("          ROUND " + round + "\n");
        s.append("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        s.append(RST + "\n\n");

        s.append("YOU: " + BOLD + player.getName() + RST + "    ");
        s.append(hpBar(player.getHp(), player.getMaxHp()) + "\n");

        s.append("ATK:" + player.getAtk() + "  DEF:" + player.getDef()
            + "  SPD:" + player.getSpeed());
        s.append(badges(player));
        if (player.getSpecialSkill() != null && !player.getSpecialSkill().canUseSkill()) {
            s.append("  " + YEL + "[CD:" + player.getSpecialSkill().getCurrentCooldown() + "]" + RST);
        }
        s.append("\n");

        List<Item> inv = player.getInventory();
        if (!inv.isEmpty()) {
            s.append("Items: ");
            for (int i = 0; i < inv.size(); i++) {
                if (i > 0) s.append(" | ");
                s.append(YEL + inv.get(i).getName() + RST);
            }
            s.append("\n");
        }

        s.append("\n\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n");
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            s.append(B_BLU + "[" + (i + 1) + "]" + RST + " ");
            if (e.isAlive()) {
                s.append(e.getName() + "    " + hpBar(e.getHp(), e.getMaxHp()));
                s.append(badges(e));
            } else {
                s.append(e.getName() + "    " + RED + "(DEAD)" + RST);
            }
            s.append("\n");
        }
        s.append("\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n");
        return s.toString();
    }

    public String renderActionMenu(Player player) {
        logger.fine("rendering action menu");
        StringBuilder s = new StringBuilder();
        s.append("  " + B_BLU + "[1]" + RST + " Basic Attack\n");
        s.append("  " + B_BLU + "[2]" + RST + " Defend (+10 DEF, 2 rounds)\n");
        s.append("  " + B_BLU + "[3]" + RST + " Special Skill  ");
        if (player.getSpecialSkill() != null) {
            if (player.getSpecialSkill().canUseSkill()) {
                s.append(GRN + "<< READY >>" + RST);
            } else {
                s.append(YEL + "(CD: " + player.getSpecialSkill().getCurrentCooldown() + ")" + RST);
            }
        }
        s.append("\n");
        s.append("  " + B_BLU + "[4]" + RST + " Use Item (" + player.getInventory().size() + " remaining)\n");
        s.append("Choose action: ");
        return s.toString();
    }

    public String renderTargetMenu(List<Enemy> enemies) {
        logger.fine("rendering target menu");
        StringBuilder s = new StringBuilder("Select target:\n");
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if (e.isAlive()) {
                s.append("  " + B_BLU + "[" + (i + 1) + "]" + RST + " "
                    + e.getName() + "  [HP " + e.getHp() + "]\n");
            }
        }
        s.append("Choose: ");
        return s.toString();
    }

    public String renderEndOfRound(Player player, int round) {
        logger.fine("rendering end of round");
        return "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n"
            + "  " + CYN + "Round " + round + " complete!" + RST + "\n"
            + "  " + player.getName() + " HP: " + player.getHp() + "/" + player.getMaxHp() + "\n"
            + "\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n";
    }

    public String renderBattleResult(BattleEngine session) {
        logger.fine("rendering battle result");
        StringBuilder s = new StringBuilder("\n");
        Player p = session.getPlayer();

        if (session.isPlayerWon()) {
            s.append(B_GRN + BOLD);
            s.append("\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557\n");
            s.append("\u2551         VICTORY!                     \u2551\n");
            s.append("\u2551  All enemies defeated!               \u2551\n");
            s.append(boxLine("Remaining HP : " + p.getHp() + "/" + p.getMaxHp()) + "\n");
            s.append(boxLine("Total Rounds : " + session.getTotalRounds()) + "\n");
            s.append("\u255a\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255d\n");
            s.append(RST);
        } else {
            s.append(B_RED + BOLD);
            s.append("\u2554\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2557\n");
            s.append("\u2551         DEFEATED.                    \u2551\n");
            s.append("\u2551  Don't give up, try again!           \u2551\n");
            s.append(boxLine("Rounds Survived : " + session.getTotalRounds()) + "\n");
            s.append("\u255a\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u255d\n");
            s.append(RST);
        }
        return s.toString();
    }

    public String renderBackupSpawn(List<Enemy> backupEnemies) {
        logger.fine("backup wave spawned");
        StringBuilder s = new StringBuilder("\n" + BOLD + B_YEL);
        s.append("\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\n");
        s.append("  BACKUP WAVE INCOMING!\n  ");
        for (int i = 0; i < backupEnemies.size(); i++) {
            if (i > 0) s.append(", ");
            s.append(backupEnemies.get(i).getName());
        }
        s.append(" appeared!\n");
        s.append("\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500");
        s.append(RST + "\n");
        return s.toString();
    }
}
