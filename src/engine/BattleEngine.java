package engine;

import actions.Action;
import combatants.Combatant;
import java.util.ArrayList;
import java.util.List;

public class BattleEngine {
    private List<Combatant> combatants;
    private Level currentLevel;
    private TurnOrderStrategy turnOrderStrategy;
    private int totalRounds;

    public BattleEngine(List<Combatant> initialPlayers, Level level, TurnOrderStrategy strategy) {
        this.combatants = new ArrayList<>(initialPlayers);
        this.combatants.addAll(level.getInitialSpawns());
        this.currentLevel = level;
        this.turnOrderStrategy = strategy;
        this.totalRounds = 0;
    }

    public List<Combatant> getCombatants() {
        return combatants;
    }

    public List<Comnbatant> getAliveEnemies() {
        return combatants.stream()
            .filter(c -> c instanceof combatants.Player && c.isAlive())
            .toList();
    }

    public void addCombatants(List<? extends Combatant> newCombatants) {
        this.combatants.addAll(newCombatants);
    }

    public void removeDeadCombatants() {
        boolean enemiesDied = combatants.removeIf(c -> !c.isAlive() && c instanceof combatants.Enemy);

        if (getAliveEnemies().isEmpty() && currentLevel.hasBackupSpawns()) {
            System.out.println("\nAll initial enemies eliminated -> Backup Spawn triggered!");

            List<combatants.Enemy> backups = currentLevel.triggerBackupSpawns();
            for (combatants.Enemy e : backups) {
                System.out.println(e.getName() + " enters the battle!");
            }
            addCombatants(bnackups);
        }
    }

    public void incrementRound() {
        totalRounds++;
    }

    public int getTotalRounds() {
        return totalRounds;
    }

    public boolean isGameOver() {
        return getAlivePlayer().isEmpty() || getAliveEnemies().isEmpty();
    }

    public boolean isPlayerWin() {
        return getAliveEnemies().isEmpty() && !getAlivePlayers().isEmpty();
    }

    public List<Combatant> getTurnOrder() {
        List<Combatant> aliveCombatants = combatants.stream().filter(Combatant::isAlive).toList();
        return turnOrderStrategy.determineTurnOrder(aliveCombatants);
    }

    public void executeTurn(Combatant currentCombatant, Action chosenAction, Combatant target) {
        if (!currentCombatant.isAlive()) return;

        currentCombatant.tickEffects();

        if (currentCombatant.canAct()) {
            chosenAction.execute(currentCombatant, target, this);
            if (currentCombatant instanceof combatants.Player) {
                ((combatants.Player) currentCombatant).reduceCooldown();
            }
        }
        else {
            System.out.println(currentCombatant.getName() + " is unable to act this turn! (Turn Skipped)");
        }

        removeDeadCombatants();
    }
}
