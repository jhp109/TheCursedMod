package thecursed.cards.skill;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import thecursed.TheCursedMod;

public class CircleOfCorruption extends AbstractCircle {
    public static final String ID = "TheCursedMod:CircleOfCorruption";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/circleofcorruption.png";

    private static final CardStrings cardStrings;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int THORN_AMT = 5;
    private static final int THORN_UPGRADE_BONUS = 3;

    public CircleOfCorruption() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), DESCRIPTION, EXTENDED_DESCRIPTION[0], RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = THORN_AMT;
    }

    @Override
    public AbstractCard makeCopy() {
        return new CircleOfCorruption();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(THORN_UPGRADE_BONUS);
        }
    }

    @Override
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (c.type == CardType.SKILL) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageRandomEnemyAction(
                            new DamageInfo(AbstractDungeon.player, this.magicNumber, DamageInfo.DamageType.THORNS),
                            AbstractGameAction.AttackEffect.FIRE));
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
