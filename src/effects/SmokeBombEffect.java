package effects;

import combatants.Combatant;

public class SmokeBombEffect implements StatusEffect {
    private int duration;
    
    public SmokeBombEffect(int duration) {
        this.duration = duration;
    }

    @Override
    public void onApply(Combatant target) {}

    @Override
    public void tick(Combatant target) {}

    public void onRemove(Combatant target) {}

    @Override
    public boolean isExpired() {
        return duration <= 0;
    }

    @Override
    public String getName() {
        return "Smoke Bomb Effect";
    }

    @Override
    public boolean preventsAction() {
        return false;
    }
}