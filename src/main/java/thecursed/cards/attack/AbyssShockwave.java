package thecursed.cards.attack;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import thecursed.TheCursedMod;
import thecursed.cards.CurseUtil;
import thecursed.enums.AbstractCardEnum;

public class AbyssShockwave extends CustomCard {
    public static final String ID = "TheCursedMod:AbyssShockwave";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/abyssshockwave.png";

    private static final CardStrings cardStrings;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 2;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_DAMAGE_AMOUNT = 3;

    public AbyssShockwave() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.THE_CURSED_PURPLE, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int numTrigger = 1;
        numTrigger += CurseUtil.hasCurse(p.hand.group) ? 1 : 0;

        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_SLOW_2"));
        AbstractGameEffect effect = new ShockWaveEffect(
                p.hb.cX, p.hb.cY, Color.ROYAL, ShockWaveEffect.ShockWaveType.ADDITIVE);
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, effect, 0.50F));

        for (int i = 0; i < numTrigger; i++) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAllEnemiesAction(
                            p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new AbyssShockwave();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE_AMOUNT);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}
