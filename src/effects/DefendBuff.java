package effects;

import combatants.Combatant;

public class DefendBuff implements StatusEffect {
    private int duration;
    private static final int DEFENSE_BOOST = 10;
    
    public DefendBuff(int duration) {
        this.duration = duration;
    }