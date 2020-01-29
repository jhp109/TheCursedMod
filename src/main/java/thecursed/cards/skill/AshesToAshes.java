package thecursed.cards.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
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

public class AshesToAshes extends CustomCard {
    public static final String ID = "TheCursedMod:AshesToAshes";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/ashestoashes.png";

    private static final CardStrings cardStrings;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 1;
    private static final int DRAW = 4;
    private static final int UPGRADED_DRAW_AMOUNT = 1;

    public AshesToAshes() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.THE_CURSED_PURPLE, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = DRAW;
        this.tags.add(TheCursedCardTags.RITE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractGameAction> targetActions = new ArrayList<>();
        targetActions.add(new DrawCardAction(p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ExhaustCurseThenActivateAction(targetActions, NAME));
    }

    @Override
    public AbstractCard makeCopy() {
        return new AshesToAshes();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADED_DRAW_AMOUNT);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
