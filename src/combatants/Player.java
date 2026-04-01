package combatants;

import java.util.List;
import java.util.ArrayList;
import items.Item;
import actions.Action;

public abstract class Player extends Combatant {
    protected int specialSkillCooldown;
    protected int currentCooldown;
    protected List<Item> inventory;

    public Player(String name, int maxHp, int attack, int defense, int speed, int cooldown) {
        super(name, maxHp, attack, defense, speed);
        this.specialSkillCooldown = cooldown;
        this.currentCooldown = 0;
        this.inventory = new ArrayList<>();
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }
    
    public void clearInventory() {
        inventory.clear();
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public void reduceCooldown() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
    }

    public void setCooldown(int cd) {
        this.currentCooldown = cd;
    }
    
    public int getMaxCooldown() {
        return specialSkillCooldown;
    }

    public boolean canUseSpecialSkill() {
        return currentCooldown == 0;
    }

    public abstract Action getSpecialSkill();
}
