package thecursed.cards.skill;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import thecursed.TheCursedMod;

public class CircleOfFocus extends AbstractCircle {
    public static final String ID = "TheCursedMod:CircleOfFocus";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/circleoffocus.png";

    private static final CardStrings cardStrings;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST_THRESHOLD = 3;
    private static final int UPGRADE_COST_THRESHOLD_AMT = -1;
    private static final int GAIN_ENERGY = 1;

    public CircleOfFocus() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), DESCRIPTION, EXTENDED_DESCRIPTION[0], RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = COST_THRESHOLD;
    }

    @Override
    public AbstractCard makeCopy() {
        return new CircleOfFocus();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_COST_THRESHOLD_AMT);
        }
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.costForTurn >= this.magicNumber || c.cost == -1 && c.energyOnUse >= this.magicNumber) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(GAIN_ENERGY));
            this.onActivateCircle(this.name);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }
}
