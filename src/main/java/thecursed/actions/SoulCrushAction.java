package thecursed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.List;
import java.util.stream.Collectors;

public class SoulCrushAction extends AbstractGameAction {
    private DamageInfo info;

    public SoulCrushAction(AbstractCreature target, DamageInfo info) {
        this.actionType = ActionType.WAIT;
        this.info = info;
        this.setValues(target, info);
        this.duration = Settings.ACTION_DUR_FAST;
        this.attackEffect = AttackEffect.FIRE;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            List<AbstractCard> cursesInHand =
                    AbstractDungeon.player.hand.group.stream()
                            .filter(c -> c.type == AbstractCard.CardType.CURSE)
                            .collect(Collectors.toList());

            AbstractDungeon.actionManager.addToTop(new DamageAction(this.target, this.info, AttackEffect.BLUNT_HEAVY));
            cursesInHand.forEach(
                    c -> AbstractDungeon.actionManager.addToTop(
                            new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand, true)));
        }

        this.tickDuration();
    }
}