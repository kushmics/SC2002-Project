package effects;

import combatants.Combatant;

public class Stun implements StatusEffect {
    private int duration;
    
    public Stun(int duration) {
        this.duration = duration;
    }

    @Override
    public void onApply(Combatant target) {
        System.out.println(target.getName() + " is STUNNED!");
    }

    @Override
    public void tick(Combatant target) {
        duration--;
    }
    
    @Override
    public void onRemove(Combatant target) {
        System.out.println(target.getName() + " is no longer stunned.");
    }

    @Override
    public boolean isExpired() {
        return duration <= 0;
    }

    @Override
    public String getName() {
        return "Stun";
    }

    @Override
    public boolean preventsAction() {
        return true;
    }
}