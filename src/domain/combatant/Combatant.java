package domain.combatant;

import domain.action.BasicAttack;
import domain.effect.StatusEffect;
import engine.BattleEngine;

import java.util.*;

public abstract class Combatant {

    private String name;
    private int hp, maxHp, attack, defense, speed;
    private List<StatusEffect> effects = new ArrayList<>();
    private BasicAttack BaseAttack;

    public Combatant(String name, int hp, int atk, int def, int spd) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = atk;
        this.defense = def;
        this.speed = spd;
        this.BaseAttack = new BasicAttack();
    }

    public String getName() { return name; }

    public boolean isAlive() { return hp > 0; }

    public int getSpeed() { return speed; }

    public int getHp() { return hp; }

    public int getAtk() { return attack; }

    public int getDef() { return defense; }

    public void setDef(int def) {  this.defense = def; }

    public int calculateReceivingDamage(int dmg){
        return dmg-this.getDef();
    }

    public void takeDamage(int dmg) {
        hp = Math.max(0, hp - dmg);
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }

    public void addEffect(StatusEffect effect) {
        effects.add(effect);
        effect.apply(this);
    }

    public void updateEffects() { //Loops through the effects in the array for 1 tick
        Iterator<StatusEffect> it = effects.iterator();
        while (it.hasNext()) {
            StatusEffect e = it.next();
            if (e.isExpired()) {
                e.statusExpired(this);
                it.remove();}
            else{
                e.tick();
            }
        }
    }

    public boolean isStunned() {
        return effects.stream().anyMatch(e -> e.getName().equals("Stun"));
    }

    public boolean isInvulnerable() {
        return effects.stream().anyMatch(e -> e.getName().equals("Invulnerable"));
    }

    public void Defend(Combatant target){
        this.BaseAttack.execute(this, target);
    }

    public void Item(Combatant target){
        this.BaseAttack.execute(this, target);
    }

    public void basicAttack(Combatant target){
        this.BaseAttack.execute(this, target);
    }

    public abstract void SpecialSkill(Combatant target);

    public abstract void takeTurn(BattleEngine engine);
}
