package thecursed.cards.skill;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thecursed.TheCursedMod;
import thecursed.enums.AbstractCardEnum;

public class ParallelWorld extends CustomCard {
    public static final String ID = "TheCursedMod:ParallelWorld";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/parallelworld.png";

    private static final CardStrings cardStrings;

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    public ParallelWorld() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.THE_CURSED_PURPLE, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        p.hand.group.stream()
                .filter(c -> !c.equals(this))
                .forEach(c -> AbstractDungeon.actionManager.addToBottom(
                        new MakeTempCardInDrawPileAction(
                                c.makeStatEquivalentCopy(), 1, true, false,
                                false, c.current_x, c.current_y + 300)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ParallelWorld();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}