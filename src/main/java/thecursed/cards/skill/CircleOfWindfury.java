package thecursed.cards.skill;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import thecursed.TheCursedMod;

public class CircleOfWindfury extends AbstractCircle {
    public static final String ID = "TheCursedMod:CircleOfWindfury";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/circleofwindfury.png";

    private static final CardStrings cardStrings;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public CircleOfWindfury() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), DESCRIPTION, EXTENDED_DESCRIPTION[0], RARITY, TARGET);
        this.isEthereal = true;
    }

    @Override
    public AbstractCard makeCopy() {
        return new CircleOfWindfury();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isEthereal = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.costForTurn == 0 || c.cost == -1 && c.energyOnUse == 0) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
            this.onActivateCircle(this.name);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }
}
