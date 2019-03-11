package thecursed.cards.attack;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thecursed.TheCursedMod;
import thecursed.enums.AbstractCardEnum;

public class Wield extends CustomCard {
    public static final String ID = "TheCursedMod:Wield";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/wield.png";

    private static final CardStrings cardStrings;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int DAMAGE = 7;
    private static final int UPGRADE_DAMAGE_AMOUNT = 3;
    private static final int COST = 1;

    public Wield() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.THE_CURSED_PURPLE, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, getNumDraw(p)));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int count = this.getNumDraw(AbstractDungeon.player);

        int pluralOffset = count > 1 ? 1 : 0;
        this.rawDescription =
                DESCRIPTION + EXTENDED_DESCRIPTION[0] + count + EXTENDED_DESCRIPTION[1 + pluralOffset];

        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        this.initializeDescription();
    }

    private int getNumDraw(AbstractPlayer p) {
        return (int) p.hand.group.stream().filter(c -> c.cost == -2).count();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Wield();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE_AMOUNT);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }
}
