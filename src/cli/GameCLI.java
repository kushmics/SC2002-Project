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
                playGame();
            } else {
                running = false;
                System.out.println("Goodbye!");
            }
        }
    }
