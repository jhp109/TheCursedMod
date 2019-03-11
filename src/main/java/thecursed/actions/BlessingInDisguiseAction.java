package thecursed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.List;
import java.util.stream.Collectors;

public class BlessingInDisguiseAction extends AbstractGameAction {
    public BlessingInDisguiseAction(AbstractCreature owner, int amount) {
        this.actionType = ActionType.WAIT;
        this.target = owner;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_FAST;
        this.startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            List<AbstractCard> cursesInHand =
                    AbstractDungeon.player.hand.group.stream()
                            .filter(c -> c.type == AbstractCard.CardType.CURSE)
                            .collect(Collectors.toList());

            cursesInHand.forEach(
                    c -> AbstractDungeon.actionManager.addToTop(
                            new GainBlockAction(this.target, this.target, this.amount, true)));
            cursesInHand.forEach(
                    c -> AbstractDungeon.actionManager.addToTop(
                            new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand, true)));
        }

        this.tickDuration();
    }
}