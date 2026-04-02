package ui;

import domain.combatant.*;
import domain.item.*;
import engine.BattleEngine;
import engine.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameUI {
    private GameRenderer renderer = new GameRenderer();
    private Scanner scanner = new Scanner(System.in);

    public void showLoadingScreen() {
        System.out.print(renderer.renderLoadingScreen());
        scanner.nextLine();
    }

    public Level chooseDifficulty() {
        System.out.print(renderer.renderDifficultyMenu());
        while (true) {
            int choice = readInt();
            switch (choice) {
                case 1: return Level.Easy;
                case 2: return Level.Medium;
                case 3: return Level.Hard;
                default: System.out.print("Invalid. Choose (1-3): ");
            }
        }
    }

    public Player chooseCharacterClass() {
        System.out.print(renderer.renderClassMenu());
        while (true) {
            int choice = readInt();
            switch (choice) {
                case 1: return new Warrior();
                case 2: return new Wizard();
                default: System.out.print("Invalid. Choose (1-2): ");
            }
        }
    }

    public List<Item> chooseItems() {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            System.out.print(renderer.renderItemsMenu());
            if (i > 0) System.out.print("(Item " + (i + 1) + " of 2): ");
            while (true) {
                int choice = readInt();
                switch (choice) {
                    case 1: items.add(new Potion()); break;
                    case 2: items.add(new SmokeBomb()); break;
                    case 3: items.add(new PowerStone()); break;
                    default: System.out.print("Invalid. Choose (1-3): "); continue;
                }
                break;
            }
        }
        return items;
    }

    public void showRoundState(Player player, List<Enemy> enemies, int round) {
        System.out.print(renderer.renderRoundState(player, enemies, round));
    }

    public void showActionMenu(Player player) {
        System.out.print(renderer.renderActionMenu(player));
    }

    public int getPlayerAction(Player player, List<Enemy> enemies) {
        while (true) {
            int choice = readInt();
            if (choice >= 1 && choice <= 4) {
                if (choice == 3 && !player.getSpecialSkill().canUseSkill()) {
                    System.out.print("Skill on cooldown! Choose again: ");
                    continue;
                }
                if (choice == 4 && player.getInventory().isEmpty()) {
                    System.out.print("No items left! Choose again: ");
                    continue;
                }
                return choice;
            }
            System.out.print("Invalid. Choose (1-4): ");
        }
    }

    public void showTargetMenu(List<Enemy> enemies) {
        System.out.print(renderer.renderTargetMenu(enemies));
    }

    public Enemy chooseTarget(List<Enemy> enemies) {
        while (true) {
            int choice = readInt();
            if (choice >= 1 && choice <= enemies.size()) {
                Enemy target = enemies.get(choice - 1);
                if (target.isAlive()) return target;
                System.out.print("Target is dead. Choose again: ");
            } else {
                System.out.print("Invalid. Choose target: ");
            }
        }
    }

    public void showInventory(Player player) {
        System.out.print(renderer.renderInventory(player));
    }

    public Item chooseInventory(Player player) {
        List<Item> inv = player.getInventory();
        while (true) {
            int choice = readInt();
            if (choice >= 1 && choice <= inv.size()) {
                return inv.get(choice - 1);
            }
            System.out.print("Invalid. Choose item: ");
        }
    }

    public void showEndOfRound(Player player, int round) {
        System.out.print(renderer.renderEndOfRound(player, round));
    }

    public void showBattleResult(BattleEngine session) {
        System.out.print(renderer.renderBattleResult(session));
    }

    public void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    private int readInt() {
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Enter a number: ");
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }
}
