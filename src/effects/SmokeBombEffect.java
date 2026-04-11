package effects;

import combatants.Combatant;

public class SmokeBombEffect implements StatusEffect {
    private int duration;
    
    public SmokeBombEffect(int duration) {
        this.duration = duration;
    }
}