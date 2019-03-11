package thecursed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class ReduceCostInHandAction extends AbstractGameAction {
    private final ArrayList<AbstractCard> cardsInHand;

    public ReduceCostInHandAction(ArrayList<AbstractCard> cardsInHand) {
        this.cardsInHand = cardsInHand;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        cardsInHand.forEach(c -> c.modifyCostForTurn(-1));
        this.isDone = true;
    }
}
