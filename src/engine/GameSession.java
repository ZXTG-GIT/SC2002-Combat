package engine;

import domain.combatant.Player;

import java.util.logging.Logger;

// tracks player + level and some stats
// BattleEngine handles the actual game loop and spawning
public class GameSession {

    private static final Logger logger = Logger.getLogger(GameSession.class.getName());

    private Player player;
    private Level level;
    private int totalRounds;
    private int enemiesDefeated;
    private boolean playerWon;
    private boolean backupSpawned;

    public GameSession(Player player, Level level) {
        this.player = player;
        this.level = level;
        this.totalRounds = 0;
        this.enemiesDefeated = 0;
        this.playerWon = false;
        this.backupSpawned = false;
        logger.fine("New session: " + player.getName() + " on " + level.name());
    }

    public Player getPlayer() { return player; }

    public Level getLevel() { return level; }

    public int getTotalRounds() { return totalRounds; }

    public int getEnemiesDefeated() { return enemiesDefeated; }

    public boolean isPlayerWon() { return playerWon; }

    public boolean isBackupSpawned() { return backupSpawned; }

    // called by BattleEngine each round
    public void incrementRound() {
        totalRounds++;
        logger.fine("Round: " + totalRounds);
    }

    public void recordEnemyDefeated() {
        enemiesDefeated++;
    }

    public void markBackupSpawned() {
        backupSpawned = true;
        logger.fine("Backup wave spawned");
    }

    public void setPlayerWon(boolean won) {
        playerWon = won;
        if (won) {
            logger.fine("Player won!");
        } else {
            logger.fine("Player lost");
        }
    }

    public boolean isOver() {
        return playerWon || !player.isAlive();
    }
}
