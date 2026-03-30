package main;

import engine.*;
import ui.*;
import domain.item.*;
import domain.combatant.*;
import java.util.*;

public class Game {
    public static void main(String[] args) {
        GameUI ui = new GameUI();
        ui.showLoadingScreen();

        while (true) {
            Level level = ui.chooseDifficulty();
            Player player = ui.chooseCharacterClass();
            List<Item> items = ui.chooseItems();
            for (Item item: items) {
                player.addItem(item);
            }

            BattleEngine engine = new BattleEngine(player, level, ui);
            engine.startBattle();
            ui.showBattleResult(engine);
        }
    }
}
