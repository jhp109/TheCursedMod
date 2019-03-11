package thecursed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class HarvestSoulAction extends AbstractGameAction {
    private int healAmount;
    private DamageInfo info;

    public HarvestSoulAction(AbstractCreature target, DamageInfo info, int healAmount) {
        this.info = info;
        this.setValues(target, info);
        this.healAmount = healAmount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1F;
    }

    @Override
    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.NONE));
            this.target.damage(this.info);
            if ((((AbstractMonster) this.target).isDying || this.target.currentHealth <= 0)
                    && !this.target.halfDead && !this.target.hasPower("Minion")) {
                AbstractDungeon.player.heal(this.healAmount);
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }
}
