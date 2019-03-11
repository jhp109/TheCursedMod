package thecursed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class IncreasePlayersMaxHPAction extends AbstractGameAction {
    private final int increaseHpAmount;

    public IncreasePlayersMaxHPAction(int maxHPAmount) {
        this.increaseHpAmount = maxHPAmount;
    }

    @Override
    public void update() {
        AbstractDungeon.player.increaseMaxHp(this.increaseHpAmount, true);
        this.isDone = true;
    }
}
