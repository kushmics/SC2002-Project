package effects;

import combatants.Combatant;

public class PoisonCloudEffect implements StatusEffect {
    private int duration;
    private static final int POISON_DAMAGE = 15;

    public PoisonCloudEffect(int duration) {
        this.duration = duration;
    }
    
}
