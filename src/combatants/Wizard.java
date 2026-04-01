package combatants;

import actions.Action;
import actions.ArcaneBlastAction;

public class Wizard extends Player {
    public Wizard(String name) {
        super(name, 200, 50, 10, 20, 3);
    }

    @Override
    public Action getSpecialSkill() {
        return new ArcaneBlastAction();
    }
}
