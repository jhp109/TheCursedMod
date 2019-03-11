package thecursed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class RavingStaffHitAction extends AbstractGameAction {
    private DamageInfo info;
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;
    private int numTimes;

    public RavingStaffHitAction(AbstractCreature target, DamageInfo info, int numTimes) {
        this.info = info;
        this.target = target;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = AttackEffect.BLUNT_LIGHT;
        this.duration = DURATION;
        this.numTimes = numTimes;
    }

    public void update() {
        if (this.target == null) {
            this.isDone = true;
        } else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else {
            if (this.target.currentHealth > 0) {
                this.target.damageFlash = true;
                this.target.damageFlashFrames = 4;
                AbstractDungeon.effectList.add(
                        new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
                this.info.applyPowers(this.info.owner, this.target);
                this.target.damage(this.info);
                if (this.numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    --this.numTimes;
                    AbstractDungeon.actionManager.addToTop(
                            new RavingStaffHitAction(
                                    AbstractDungeon.getMonsters().getRandomMonster(
                                            null, true, AbstractDungeon.cardRandomRng),
                                    this.info, this.numTimes));
                }

                AbstractDungeon.actionManager.addToTop(new WaitAction(POST_ATTACK_WAIT_DUR));
            }

            this.isDone = true;
        }
    }
}
