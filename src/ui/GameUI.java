package ui;

import domain.combatant.Enemy;
import domain.combatant.Player;
import engine.GameSession;
import engine.Level;

import java.util.List;

public interface GameUI {

    void showLoadingScreen();

    Level.Difficulty chooseDifficulty();

    int chooseCharacterClass();

    void showRoundState(Player player, List<Enemy> enemies, int round);

    int getPlayerAction(Player player, List<Enemy> enemies);

    int chooseTarget(List<Enemy> enemies);

    void showMessage(String message);

    void showEndOfRound(Player player, int round);

    void showBattleResult(GameSession session);

    void waitForEnter();
}
