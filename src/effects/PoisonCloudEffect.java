package effects;

import combatants.Combatant;

public class PoisonCloudEffect implements StatusEffect {
    private int duration;
    private static final int POISON_DAMAGE = 15;

    public PoisonCloudEffect(int duration) {
        this.duration = duration;
    }
    
    @Override
    public void onApply(Combatant target) {
        System.out.println(target.getName() + " is engulfed in a Poison Cloud! (Takes " + POISON_DAMAGE + " damage per turn for " + duration + " turns)");
    }
    
    @Override
    public void tick(Combatant target) {
        if (duration > 0) {
            System.out.println(target.getName() + " takes " + POISON_DAMAGE + " poison damage from the Poison Cloud!");
            target.takeDamage(POISON_DAMAGE);
        }
        duration--;
    }
    
    @Override
    public void onRemove(Combatant target) {
        System.out.println(target.getName() + "'s Poison Cloud effect dissipates.");
    }
    
    @Override
    public boolean isExpired() {
        return duration <= 0;
    }
    
    @Override
    public String getName() {
        return "Poison Cloud";
    }
    
    @Override
    public boolean preventsAction() {
        return false;
    }
}
