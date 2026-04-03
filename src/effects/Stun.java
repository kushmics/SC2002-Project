package effects;

import combatants.Combatant;

public class Stun implements StatusEffect {
    private int duration;
    
    public Stun(int duration) {
        this.duration = duration;
    }