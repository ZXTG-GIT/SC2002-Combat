package domain.combatant;

import domain.action.BasicAttack;
import domain.effect.DefendEffect;
import domain.effect.StatusEffect;
import domain.item.DefensiveItems;
import domain.item.Item;
import domain.item.MultiTargetItem;
import domain.item.OffensiveItems;
import domain.item.SingleTargetItem;

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

    public int calculateReceivingDamage(Combatant user, Combatant target){
        return Math.max(0, user.getAtk()-target.getDef());
    }

    public void takeDamage(int dmg) {
        hp = Math.max(0, hp - dmg);
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, this.hp + amount);
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

    public void defend(){
        this.addEffect(new DefendEffect());
    }

    public void useDefensiveItem(DefensiveItems item) {
        item.use(this);
    }

    public void useOffensiveItem(OffensiveItems item, Combatant target) {
        item.use(this, target);
    }

    public void useItem(Item item, List<Combatant> targets) {
        if (item instanceof MultiTargetItem) {
            ((MultiTargetItem) item).use(this, targets);
        } else if (item instanceof DefensiveItems) {
            ((DefensiveItems) item).use(this);
        }
    }
    public void basicAttack(Combatant target){
        this.BaseAttack.execute(this, target);
    }

}
