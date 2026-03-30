package ui;

import domain.combatant.*;
import domain.item.*;
import engine.BattleEngine;
import engine.Level;

import java.util.List;

public class GameUI {
    private GameRenderer renderer = new GameRenderer();

    public void showLoadingScreen() {};

    public Level chooseDifficulty() { return null; };

    public Player chooseCharacterClass() { return null; };

    public List<Item> chooseItems() { return null; };

    public void showRoundState(Player player, List<Enemy> enemies, int round) {};

    public int getPlayerAction(Player player, List<Enemy> enemies) { return 0; };

    public Enemy chooseTarget(List<Enemy> enemies) { return null; };

    public Item chooseInventory(Player player) { return null; };

    public void showTargetMenu(List<Enemy> enemies) {};

    public void showInventory(Player player) {};

    public void showActionMenu(Player player) {};

    public void showEndOfRound(Player player, int round) {};

    public void showBattleResult(BattleEngine session) {};

    public void waitForEnter() {};
}
