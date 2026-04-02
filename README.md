# SC2002 Turn-Based Combat Arena

A CLI turn-based combat game built for SC2002 Object-Oriented Design & Programming.

Pick your class, grab some items, and fight through waves of enemies. Supports colour output on Mac/Linux terminals.

## How to Run

```bash
javac -sourcepath src -d out src/main/Game.java
java -cp out main.Game
```

## Gameplay

1. Choose difficulty (Easy / Medium / Hard)
2. Pick a class — Warrior or Wizard
3. Select 2 items from the shop
4. Fight enemies in turn-based combat
5. Defeat all waves to win

### Classes

| Class   | HP  | ATK | DEF | SPD | Special Skill          |
|---------|-----|-----|-----|-----|------------------------|
| Warrior | 260 | 40  | 20  | 30  | Shield Bash (stun, CD 3) |
| Wizard  | 200 | 50  | 10  | 20  | Arcane Blast (AoE, CD 3) |

### Items

| Item        | Effect                        |
|-------------|-------------------------------|
| Potion      | Heals 100 HP                  |
| Smoke Bomb  | Invulnerable for 2 turns      |
| Power Stone | Free skill activation (no CD) |

### Difficulty Levels

| Level  | Wave 1              | Wave 2 (Backup)      |
|--------|----------------------|----------------------|
| Easy   | 3 Goblins            | —                    |
| Medium | 1 Goblin + 1 Wolf    | 2 Wolves             |
| Hard   | 2 Goblins            | 1 Goblin + 2 Wolves  |

### Enemies

| Enemy  | HP | ATK | DEF | SPD |
|--------|----|-----|-----|-----|
| Goblin | 55 | 35  | 15  | 25  |
| Wolf   | 40 | 45  | 5   | 35  |

## Project Structure

```
src/
├── main/
│   └── Game.java              # entry point
├── engine/
│   ├── BattleEngine.java      # game loop, spawning, turn order
│   ├── GameSession.java       # session state tracking
│   ├── Level.java             # difficulty enum
│   ├── TurnOrderStrategy.java # strategy interface
│   └── SpeedBasedTurnOrder.java
├── ui/
│   ├── GameUI.java            # scanner input, delegates to renderer
│   └── GameRenderer.java      # ANSI output, HP bars, menus
├── domain/
│   ├── combatant/
│   │   ├── Combatant.java     # abstract base
│   │   ├── Player.java        # inventory, skills
│   │   ├── Warrior.java
│   │   ├── Wizard.java
│   │   ├── Enemy.java
│   │   ├── Goblin.java
│   │   └── Wolf.java
│   ├── action/
│   │   ├── Action.java        # interface
│   │   ├── BasicAttack.java
│   │   ├── Defend.java
│   │   ├── SpecialSkill.java  # abstract, handles cooldown
│   │   ├── ShieldBash.java
│   │   └── ArcaneBlast.java
│   ├── item/
│   │   ├── Item.java          # interface
│   │   ├── DefensiveItems.java
│   │   ├── MultiTargetItem.java
│   │   ├── SingleTargetItem.java
│   │   ├── Potion.java
│   │   ├── SmokeBomb.java
│   │   └── PowerStone.java
│   └── effect/
│       ├── StatusEffect.java  # interface
│       ├── DefendEffect.java
│       ├── StunEffect.java
│       └── InvulnerabilityEffect.java
```

## Design Notes

- **Turn order** is speed-based (highest SPD goes first each round)
- **All actions** take `List<Combatant>` as targets — single-target actions just use index 0
- **No game logic in the UI layer** — GameRenderer only builds strings, GameUI only handles I/O
- **Status effects** tick at the start of each round and auto-expire
- **Strategy pattern** for turn ordering so we can swap it out if needed

## Team

- P1 — Battle Engine + Turn Order
- P2 — Domain Classes (Combatant, Action, Effect, Item)
- P3 — Special Skills + Items
- P4 — CLI, Levels, Game Session
