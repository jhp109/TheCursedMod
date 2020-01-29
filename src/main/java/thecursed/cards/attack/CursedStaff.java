package thecursed.cards.attack;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import thecursed.TheCursedMod;
import thecursed.cards.CurseUtil;
import thecursed.cards.curse.Dregs;
import thecursed.enums.AbstractCardEnum;

public class CursedStaff extends CustomCard {
    public static final String ID = "TheCursedMod:CursedStaff";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/cursedstaff.png";

    private static final CardStrings cardStrings;

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final int COST = 2;
    private static final int DAMAGE = 24;
    private static final int UPGRADE_BONUS = 6;

    public CursedStaff() {
        super(ID, NAME, TheCursedMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.THE_CURSED_PURPLE, RARITY, TARGET);

        this.baseDamage = this.damage = DAMAGE;
        this.cardsToPreview = new Dregs();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p != null && m != null) {
            AbstractDungeon.actionManager.addToBottom(
                    new VFXAction(new SmallLaserEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.5F));
            CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM_SHORT");
        }
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(CurseUtil.DREGS, 2));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CursedStaff();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_BONUS);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
    }
}