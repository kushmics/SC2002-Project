package cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import combatants.*;
import engine.*;
import actions.*;
import items.*;

public class GameCLI {
    private Scanner scanner;

    public GameCLI() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("=== Turn-Based Combat Arena ===");
            System.out.println("1. Start Game");
            System.out.println("2. Exit");
            int choice = getIntInput(1, 2);
            if (choice == 1) {
                running = playGame(null);
            } else {
                running = false;
                System.out.println("Goodbye!");
            }
        }
    }

    private boolean playGame(GameSettings preset) {
        GameSettings settings = (preset != null) ? preset : captureSettings();

        Player player = buildPlayer(settings);
        Level level = new Level(settings.difficulty);

        List<Combatant> initialPlayers = new ArrayList<>();
        initialPlayers.add(player);

        BattleEngine engine = new BattleEngine(initialPlayers, level, new SpeedBasedTurnOrder());

        System.out.println("\n--- BATTLE START ---");
        while (!engine.isGameOver()) {
            engine.incrementRound();
            System.out.println("\n=== ROUND " + engine.getTotalRounds() + " ===");
            List<Combatant> turnOrder = engine.getTurnOrder();

            for (Combatant current : turnOrder) {
                if (engine.isGameOver()) break;
                if (!current.isAlive()) continue;

                System.out.println("\n[" + current.getName() + "'s Turn] HP: " + current.getHp() + "/" + current.getMaxHp() + " SPD: " + current.getSpeed());
                
                if (current instanceof Player) {
                    Player p = (Player) current;
                    Action action = chooseActionForPlayer(p, engine);
                    
                    Combatant target = null;
                    if (action instanceof ExecuteSpecialSkillAction) {
                        if (p.getSpecialSkill().requiresTarget()) {
                            target = chooseEnemyTarget(engine);
                        }
                    } else if (action.requiresTarget()) {
                        target = chooseEnemyTarget(engine);
                    }

                    engine.executeTurn(p, action, target);
                } else if (current instanceof Enemy) {
                    Action action = new BasicAttack();
                    Combatant target = engine.getAlivePlayers().get(0);
                    engine.executeTurn(current, action, target);
                }
            }
            
            printRoundSummary(engine, player);
        }

        if (engine.isPlayerWin()) {
            System.out.println("\n--- Player Victory ---");
            System.out.println("Congratulations, you have defeated all your enemies.");
            System.out.println("Remaining HP: " + player.getHp() + " | Total Rounds: " + engine.getTotalRounds());
        } else {
            System.out.println("\n--- Player Defeat ---");
            System.out.println("Defeated. Don't give up, try again!");
            System.out.println("Enemies remaining: " + engine.getAliveEnemies().size() + " | Total Rounds Survived: " + engine.getTotalRounds());
        }
    }

    private Player choosePlayer() {
        System.out.println("\n--- Choose Class ---");
        System.out.println("1. Warrior (HP: 260, ATK: 40, DEF: 20, SPD: 30) - Skill: Shield Bash (Damage + Stun 2 turns)");
        System.out.println("2. Wizard (HP: 200, ATK: 50, DEF: 10, SPD: 20) - Skill: Arcane Blast (AoE Damage + ATK Boost per kill)");
        int choice = getIntInput(1, 2);
        if (choice == 1) return new Warrior("Warrior");
        else return new Wizard("Wizard");
    }

    private void chooseItems(Player player) {
        System.out.println("\n--- Choose Items (Pick 2, duplicates allowed) ---");
        System.out.println("1. Potion (Heal 100 HP)");
        System.out.println("2. Power Stone (Trigger special skill without cooldown)");
        System.out.println("3. Smoke Bomb (Enemy attacks do 0 damage this and next turn)");
        System.out.println("4. Poison Cloud (Deal 15 poison damage per turn for 3 turns)");

        for (int i = 0; i < 2; i++) {
            System.out.print("Select item " + (i + 1) + ": ");
            int choice = getIntInput(1, 4);
            switch (choice) {
                case 1: player.addItem(new Potion()); break;
                case 2: player.addItem(new PowerStone()); break;
                case 3: player.addItem(new SmokeBomb()); break;
                case 4: player.addItem(new PoisonCloud()); break;
            }
        }
    }

     private Level chooseLevel() {
        System.out.println("\n--- Choose Difficulty ---");
        System.out.println("1. Easy (3 Goblins)");
        System.out.println("2. Medium (1 Goblin, 1 Wolf -> Backup: 2 Wolves)");
        System.out.println("3. Hard (2 Goblins -> Backup: 1 Goblin, 2 Wolves)");
        System.out.println("\nEnemy Stats:");
        System.out.println("Goblin - HP: 55, ATK: 35, DEF: 15, SPD: 25");
        System.out.println("Wolf   - HP: 40, ATK: 45, DEF: 5,  SPD: 35");

        int choice = getIntInput(1, 3);
        if (choice == 1) return new Level(Level.Difficulty.EASY);
        if (choice == 2) return new Level(Level.Difficulty.MEDIUM);
        return new Level(Level.Difficulty.HARD);
    }
    
    private Action chooseActionForPlayer(Player p, BattleEngine engine) {
        System.out.println("\nSelect Action:");
        System.out.println("1. Basic Attack");
        System.out.println("2. Defend");
        System.out.println("3. Use Item");
        System.out.println("4. Special Skill (Cooldown: " + p.getCurrentCooldown() + ")");
        
        while (true) {
            int choice = getIntInput(1, 4);
            if (choice == 1) return new BasicAttack();
            if (choice == 2) return new DefendAction();
            if (choice == 3) {
                if (p.getInventory().isEmpty()) {
                    System.out.println("No items left! Choose another action.");
                    continue;
                }
                System.out.println("Select Item:");
                List<Item> inv = p.getInventory();
                for (int i = 0; i < inv.size(); i++) {
                    System.out.println((i + 1) + ". " + inv.get(i).getName());
                }
                System.out.println((inv.size() + 1) + ". Cancel");
                int itemChoice = getIntInput(1, inv.size() + 1);
                if (itemChoice <= inv.size()) {
                    return new UseItemAction(inv.get(itemChoice - 1));
                }
                System.out.println("\nSelect Action (1-4):");
            } else if (choice == 4) {
                if (p.canUseSpecialSkill()) {
                    return new ExecuteSpecialSkillAction();
                } else {
                    System.out.println("Special skill is on cooldown! Choose another action.");
                }
            }
        }
    }

    private Enemy chooseEnemyTarget(BattleEngine engine) {
        List<Combatant> enemies = engine.getAliveEnemies();
        if (enemies.isEmpty()) return null;
        if (enemies.size() == 1) return (Enemy) enemies.get(0);

        System.out.println("Select Target:");
        for (int i = 0; i < enemies.size(); i++) {
            System.out.println((i + 1) + ". " + enemies.get(i).getName() + " (HP: " + enemies.get(i).getHp() + ")");
        }
        int choice = getIntInput(1, enemies.size());
        return (Enemy) enemies.get(choice - 1);
    }
    
    private void printRoundSummary(BattleEngine engine, Player p) {
        System.out.println("\n--- End of Round " + engine.getTotalRounds() + " ---");
        for (Combatant c : engine.getAlivePlayers()) {
            System.out.print(c.getName() + " HP: " + c.getHp() + "/" + c.getMaxHp() + " | ");
        }

        for (Combatant c : engine.getCombatants()) {
            if (c instanceof Enemy) {
                if (c.isAlive()) {
                    System.out.print(c.getName() + " HP: " + c.getHp() + " | ");
                } else {
                    System.out.print(c.getName() + " HP: \u2717 | ");  // ✗
                }
            }
        }
        
        // Count items
        int potions = 0, bombs = 0, stones = 0, clouds = 0;
        for (Item i : p.getInventory()) {
            if (i instanceof Potion) potions++;
            if (i instanceof SmokeBomb) bombs++;
            if (i instanceof PowerStone) stones++;
            if (i instanceof PoisonCloud) clouds++;
        }
        System.out.println("\nPotions: " + potions + " | Smoke Bombs: " + bombs + " | Power Stones: " + stones + " | Poison Clouds: " + clouds);
        System.out.println("Special Skill Cooldown: " + p.getCurrentCooldown() + " rounds");
    }

    private int getIntInput(int min, int max) {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) return value;
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Starting SC2002 Combat Arena...");
        new GameCLI().start();
    }
}
