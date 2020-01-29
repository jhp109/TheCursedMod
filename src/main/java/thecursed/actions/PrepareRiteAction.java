package thecursed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import thecursed.cards.CurseUtil;

import java.util.ArrayList;

public class PrepareRiteAction extends AbstractGameAction {
    public static final String UI_NAME = "TheCursedMod:PrepareRiteAction";
    public static final UIStrings uiStrings;
    public static final String[] TEXT;
    private ArrayList<AbstractCard> cannotTransform = new ArrayList();

    public PrepareRiteAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, -1);
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (p.hand.isEmpty() || p.hand.group.size() == CurseUtil.getNumCurse(p.hand.group)) {
                this.isDone = true;
            } else {
                p.hand.group.stream().filter(card -> CurseUtil.isCurse(card)).forEach(cannotTransform::add);
                p.hand.group.removeAll(cannotTransform);

                AbstractDungeon.handCardSelectScreen.open(
                        TEXT[0], 99, true, true, true);
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractDungeon.handCardSelectScreen.selectedCards.group.forEach(c -> p.hand.moveToExhaustPile(c));
                AbstractDungeon.actionManager.addToBottom(
                        new MakeTempCardInHandAction(
                                CurseUtil.DREGS, AbstractDungeon.handCardSelectScreen.selectedCards.group.size()));

                cannotTransform.forEach(p.hand::addToTop);
                p.hand.refreshHandLayout();

                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();

                this.isDone = true;
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(UI_NAME);
        TEXT = uiStrings.TEXT;
    }
}