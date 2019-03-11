package thecursed.cards.attack;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thecursed.TheCursedMod;
import thecursed.cards.CurseUtil;
import thecursed.enums.AbstractCardEnum;

public class ManaBullet extends CustomCard {
    public static final String ID = "TheCursedMod:ManaBullet";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/manabullet.png";

    private static final CardStrings cardStrings;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_DAMAGE_BONUS = 1;

    public ManaBullet() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.THE_CURSED_PURPLE, RARITY, TARGET);

        this.baseDamage = this.damage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int numHit = this.getNumHit(p);
        for (int i = 0; i < numHit; i++) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                            AbstractGameAction.AttackEffect.BLUNT_LIGHT, true));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int count = this.getNumHit(AbstractDungeon.player);

        String baseDescription = this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        int baseIndex = 3 * (this.upgraded ? 1 : 0);
        int pluralOffset = count > 1 ? 1 : 0;
        this.rawDescription =
                baseDescription + EXTENDED_DESCRIPTION[baseIndex] + count
                        + EXTENDED_DESCRIPTION[baseIndex + 1 + pluralOffset];

        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = this.upgraded ? UPGRADE_DESCRIPTION : DESCRIPTION;
        this.initializeDescription();
    }

    private int getNumHit(AbstractPlayer p) {
        if (this.upgraded) {
            return (int) p.hand.group.stream().filter(c -> c.cost == -2).count();
        } else {
            return CurseUtil.getNumCurse(p.hand.group);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ManaBullet();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE_BONUS);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
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