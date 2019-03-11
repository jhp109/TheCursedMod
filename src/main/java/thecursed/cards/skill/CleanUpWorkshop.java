package thecursed.cards.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thecursed.TheCursedMod;
import thecursed.actions.ExhaustCurseThenActivateAction;
import thecursed.cards.TheCursedCardTags;
import thecursed.enums.AbstractCardEnum;

import java.util.ArrayList;

public class CleanUpWorkshop extends CustomCard {
    public static final String ID = "TheCursedMod:CleanUpWorkshop";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "cards/cleanupworkshop.png";

    private static final CardStrings cardStrings;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 0;
    private static final int BLOCK = 3;
    private static final int UPGRADE_BLOCK_AMOUNT = 1;
    private static final int CARD_DRAW = 1;
    private static final int UPGRADE_CARD_DRAW_AMOUNT = 1;


    public CleanUpWorkshop() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.THE_CURSED_PURPLE, RARITY, TARGET);
        this.baseBlock = this.block = BLOCK;
        this.baseMagicNumber = this.magicNumber = CARD_DRAW;
        this.tags.add(TheCursedCardTags.RITUAL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractGameAction> targetActions = new ArrayList<>();
        targetActions.add(new GainBlockAction(p, p, this.block));
        targetActions.add(new DrawCardAction(p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ExhaustCurseThenActivateAction(targetActions, NAME));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CleanUpWorkshop();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK_AMOUNT);
            this.upgradeMagicNumber(UPGRADE_CARD_DRAW_AMOUNT);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }
}
