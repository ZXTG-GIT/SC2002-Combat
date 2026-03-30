P2 Documentation for Battle Engine Team

Inheritance Overview

Combatant (abstract)
├── Enemy → Goblin, Wolf
└── Player → Warrior, Wizard

Enemy — calls basicAttack() on player's opponents list via act()
Player — has inventory and special skill. Warrior uses ShieldBash, Wizard uses ArcaneBlast

Item Hierarchy

Item (interface)
├── DefensiveItems → Potion, SmokeBomb (targets self)
└── MultiTargetItem → PowerStone (targets enemy list)

Key Design — All Actions Use List
All actions now take List<Combatant> targets instead of a single target. Engine always passes the full alive enemy list. Single target actions (BasicAttack, ShieldBash) use targets.get(0) internally — engine is responsible for putting the selected target at index 0 before passing the list.

Combatant — call on everyone
- updateEffects() — call at start of each round for all combatants
- isAlive() — check before any action
- isStunned() — skip turn if true
- getSpeed() — used for turn order sorting
- addOpponent(Combatant) — add enemies/player as opponents at battle start
- removeDefeatedOpponents() — call after each turn to clean up dead combatants

Enemy
- act(List<Combatant> targets) — passes targets directly to basicAttack, just call with player in a list

Player
- basicAttack(List<Combatant> targets) — attacks targets.get(0)
- defend() — adds defend effect, no target needed
- SpecialSkill(List<Combatant> targets) — handles cooldown internally
- useItem(String itemName, List<Combatant> targets) — routes to correct item, removes from inventory
- showInventory() — print available items

SpecialSkill
- canUseSkill() — use this to show/hide skill option in menu
- reduceCooldown() — call after every player turn
- isMultiTarget() — true for ArcaneBlast, false for ShieldBash

⚠️ Single Target Actions — Index 0 Convention
BasicAttack and ShieldBash both use targets.get(0) internally. Engine must ensure the player's selected target is placed at index 0 of the list before passing it in. ArcaneBlast loops through all targets automatically.

⚠️ ArcaneBlast Multi Target Note
ArcaneBlast loops through entire targets list internally — engine just passes full alive enemy list directly. PowerStone also handles this internally — just call useItem("PowerStone", allEnemies)

