package engine;

import combatants.Enemy;
import combatants.Goblin;
import combatants.Wolf;
import java.util.ArrayList;
import java.util.List;

public class Level {
  public enum Difficulty {
    EASY, MEDIUM, HARD 
  }

  private Difficulty difficulty;
  private List<Enemy> initialSpawn;
  private List<Enemy> backupSpawn;
  private boolean backupSpawnTriggered;

  public Level(Difficulty difficulty) {
    this.difficulty = difficulty;
    this.initialSpawn = new ArrayList<>();
    this.backupSpawn = new ArrayList<>();
    this.backupSpawnTriggered = false;
    setupSpawns();
  }

  private void setupSpawn() {
    switch (difficulty) {
      case EASY:
        initialSpawn.add(new Goblin("Goblin A"));
        initialSpawn.add(new Goblin("Goblin B"));
        initialSpawn.add(new Goblin("Goblin C"));
        break;
      case MEDIUM:
        initialSpawn.add(new Goblin("Goblin"));
        initialSpawn.add(new Wolf("Wolf"));

        backupSpawn.add(new Wolf("Wolf A"));
        backupSpawn.add(new Wolf("Wolf B"));
        break;
      case HARD:
        initialSpawn.add(new Goblin("Goblin A"));
        initialSpawn.add(new Goblin("Goblin B"));

        backupSpawn.add(new Goblin("Goblin"));
        backupSpawn.add(new Wolf("Wolf A"));
        backupSpawn.add(new Wolf("Wolf B"));
        break;
    }
  }

  public List<Enemy> getInitialSpawns() {
    return initialSpawn;
  }

  public List<Enemy> triggerBackupSpawns() {
    if (!backupSpawnTriggered && !backupSpawn.isEmpty()) {
      backupSpawnTriggered = true;
      return backupSpawn;
    }
    return new ArrayList<>();
  }

  public boolean hasBackupSpawns() {
    return !backupSpawn.isEmpty() && !backupSpawnTriggered;
  }
}
        
