package thecursed.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;


public class BleedPower extends AbstractTheCursedPower {
    public static final String POWER_ID = "TheCursedMod:BleedPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    public static PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final String IMG = "bleed.png";

    private AbstractCreature source;

    public BleedPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(IMG);
        this.owner = owner;
        this.source = source;
        this.amount = amount;
        this.isTurnBased = true;

        this.name = NAME;
        this.ID = POWER_ID;
        this.type = POWER_TYPE;

        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.type == DamageInfo.DamageType.THORNS || info.type == DamageInfo.DamageType.HP_LOSS
                || info.owner != this.owner) {
            return;
        }

        activateBleed(1);
    }

    @Override
    public void atEndOfRound() {
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
        }
    }

    public void activateBleed(int times) {
        DamageInfo bleedDamageInfo = new DamageInfo(this.source, this.amount, DamageInfo.DamageType.HP_LOSS);
        for (int i = 0; i < times; i++) {
            this.flash();
            DamageAction action =
                    new DamageAction(this.owner, bleedDamageInfo, AbstractGameAction.AttackEffect.POISON);
            AbstractDungeon.actionManager.addToTop(action);
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
