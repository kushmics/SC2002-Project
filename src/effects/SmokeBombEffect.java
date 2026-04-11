package effects;

import combatants.Combatant;

public class SmokeBombEffect implements StatusEffect {
    private int duration;
    
    public SmokeBombEffect(int duration) {
        this.duration = duration;
    }

    @Override
    public void onApply(Combatant target) {
        target.setInvulnerable(true);
        System.out.println(target.getName() + " is now enveloped in a Smoke Bomb (Invulnerable)!");
    }

    @Override
    public void tick(Combatant target) {
        duration--;
    }

    @Override
    public void onRemove(Combatant target) {
        target.setInvulnerable(false);
        System.out.println(target.getName() + "'s Smoke Bomb effect wears off.");
    }

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