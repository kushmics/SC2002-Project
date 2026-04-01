package combatants;

import actions.Action;
import actions.ShieldBashAction;

public class Warrior extends Player {
    public Warrior(String name) {
        super(name, 260, 40, 20, 30, 3);
    }

    @Override
    public Action getSpecialSkill() {
        return new ShieldBashAction();
    }
}
