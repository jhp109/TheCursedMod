package thecursed.cards.skill;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import thecursed.TheCursedMod;

public class CircleOfAbyss extends AbstractCircle {
    public static final String ID = "TheCursedMod:CircleOfAbyss";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/circleofabyss.png";

    private static final CardStrings cardStrings;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int BLOCK = 2;
    private static final int UPGRADE_BLOCK_AMOUNT = 1;
    private static final int ENERGY = 1;
    private static final int UPGRADED_ENERGY = 2;

    public CircleOfAbyss() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), DESCRIPTION, EXTENDED_DESCRIPTION[0], RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = BLOCK;
    }

    @Override
    public AbstractCard makeCopy() {
        return new CircleOfAbyss();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_BLOCK_AMOUNT);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.magicNumber, true));
        this.onActivateCircle(this.name);
    }

    @Override
    public void triggerOnExhaust() {
        int gainAmount = this.upgraded ? UPGRADED_ENERGY : ENERGY;
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(gainAmount));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}
