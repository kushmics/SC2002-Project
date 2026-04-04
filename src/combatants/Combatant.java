package combatants;

import java.util.ArrayList;
import java.util.List;
import effects.StatusEffect;

public abstract class Combatant {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int attack;
    protected int defense;
    protected int speed;
    protected List<StatusEffect> activeEffects;
    protected boolean invulnerable;

    public Combatant(String name, int maxHp, int attack, int defense, int speed) {
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.activeEffects = new ArrayList<>();
        this.invulnerable = false;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public void setAttack(int attack) { this.attack = attack; }
    public int getDefense() { return defense; }
    public void setDefense(int defense) { this.defense = defense; }
    public int getSpeed() { return speed; }
    public boolean isInvulnerable() { return invulnerable; }
    public void setInvulnerable(boolean invulnerable) { this.invulnerable = invulnerable; }

    public void takeDamage(int damage) {
        if (invulnerable) {
            System.out.println(name + " takes 0 damage (Invulnerable).");
            return;
        }
        this.hp -= damage;
        if (this.hp < 0) this.hp = 0;
        System.out.println(name + " takes " + damage + " damage! HP is now " + this.hp + "/" + this.maxHp + ".");
    }

        public void heal(int amount) {
        this.hp += amount;
        if (this.hp > this.maxHp) this.hp = this.maxHp;
        System.out.println(name + " heals for " + amount + " HP! HP is now " + this.hp + "/" + this.maxHp + ".");
    }

        public boolean isAlive() {
        return this.hp > 0;
    }

        public void addStatusEffect(StatusEffect effect) {
        activeEffects.add(effect);
        effect.onApply(this);
    }

    public void removeStatusEffect(StatusEffect effect) {
        effect.onRemove(this);
        activeEffects.remove(effect);
    }

        public void tickEffects() {
        List<StatusEffect> toRemove = new ArrayList<>();
        for (StatusEffect effect : activeEffects) {
            effect.tick(this);
            if (effect.isExpired()) {
                toRemove.add(effect);
            }
        }
        for (StatusEffect effect : toRemove) {
            removeStatusEffect(effect);
        }
    }

    public boolean canAct() {
        for (StatusEffect effect : activeEffects) {
            if (effect.preventsAction()) {
                return false;
            }
        }
        return true;
    }
}