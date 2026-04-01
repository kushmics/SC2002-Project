package effects;

import combatants.Combatant;

public interface StatusEffect {
    void onApply(Combatant target);
    void tick(Combatant target);
    void onRemove(Combatant target);
    boolean isExpired();
    String getName();
    boolean preventsAction();
}
