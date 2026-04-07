package engine;

// tracks player + level and some stats
// BattleEngine handles the actual game loop and spawning
public class GameSession {
    private int totalRounds = 1;
    private int enemiesDefeated = 0;
    private boolean playerWon = false;
    private boolean backupSpawned = false;

    public int getTotalRounds() { return totalRounds; }

    public int getEnemiesDefeated() { return enemiesDefeated; }

    public boolean isPlayerWon() { return playerWon; }

    public boolean isBackupSpawned() { return backupSpawned; }

    // called by BattleEngine each round
    public void incrementRound() {
        totalRounds++;
    }

    public void recordEnemyDefeated() {
        enemiesDefeated++;
    }

    public void markBackupSpawned() {
        backupSpawned = true;
    }

    public void setPlayerWon(boolean won) {
        playerWon = won;
    }
}
