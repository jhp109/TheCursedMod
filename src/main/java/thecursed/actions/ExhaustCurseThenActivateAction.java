package thecursed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.List;

public class ExhaustCurseThenActivateAction extends AbstractGameAction {
    public static final String UI_NAME = "TheCursedMod:ExhaustCurseThenActivateAction";
    public static final UIStrings uiStrings;
    public static final String[] TEXT;
    private final List<AbstractGameAction> targetActions;
    private final String cardName;
    private AbstractPlayer p;

    public ExhaustCurseThenActivateAction(AbstractGameAction targetAction, String cardName) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.targetActions = new ArrayList<>();
        this.targetActions.add(targetAction);
        this.cardName = cardName;
    }

    public ExhaustCurseThenActivateAction(ArrayList<AbstractGameAction> targetActions, String cardName) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.targetActions = targetActions;
        this.cardName = cardName;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
            } else if (this.p.hand.size() == 1) {
                AbstractCard onlyCard = this.p.hand.getBottomCard();
                exhaustCardAndActivateActionsIfItIsCurse(onlyCard);
                this.tickDuration();
            } else {
                AbstractDungeon.handCardSelectScreen.open(
                        TEXT[0] + this.cardName + TEXT[1], 1, false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractDungeon.handCardSelectScreen.selectedCards.group
                        .forEach(this::exhaustCardAndActivateActionsIfItIsCurse);

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();

                this.isDone = true;
            }

            this.tickDuration();
        }
    }

    private void exhaustCardAndActivateActionsIfItIsCurse(AbstractCard card) {
        if (card.type == AbstractCard.CardType.CURSE) {
            targetActions.forEach(AbstractDungeon.actionManager::addToTop);
        }
        this.p.hand.moveToExhaustPile(card);
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(UI_NAME);
        TEXT = uiStrings.TEXT;
    }
}