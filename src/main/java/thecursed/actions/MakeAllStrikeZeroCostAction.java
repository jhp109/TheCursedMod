package thecursed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class MakeAllStrikeZeroCostAction extends AbstractGameAction {
    private final ArrayList<AbstractCard> cardsInHand;

    public MakeAllStrikeZeroCostAction(ArrayList<AbstractCard> cardsInHand) {
        this.cardsInHand = cardsInHand;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        cardsInHand.stream()
                .filter(c -> c.hasTag(AbstractCard.CardTags.STRIKE))
                .forEach(c -> c.setCostForTurn(0));
        this.isDone = true;
    }
}
