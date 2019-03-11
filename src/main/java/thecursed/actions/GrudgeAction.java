package thecursed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thecursed.cards.skill.*;

import java.util.ArrayList;

public class GrudgeAction extends AbstractGameAction {
    private ArrayList<AbstractCard> ritualPool;

    public GrudgeAction() {
        this.ritualPool = new ArrayList<>();
        // this.ritualPool.add(new AntiAging()); - don't include HEALING card
        this.ritualPool.add(new AshesToAshes());
        this.ritualPool.add(new BloodBarrier());
        this.ritualPool.add(new DarkBarrier());
        this.ritualPool.add(new CleanUpWorkshop());
        this.ritualPool.add(new DoubleShield());
        this.ritualPool.add(new Evocation());
        this.ritualPool.add(new FleshFromBone());
        // this.ritualPool.add(new ForbiddenAlchemy()); - don't include HEALING card
        this.ritualPool.add(new Obscuration());
        this.ritualPool.add(new RapidMovement());
        this.ritualPool.add(new ReleasedKnowledge());
        this.ritualPool.add(new ReleasedStrength());
        this.ritualPool.add(new SmokeScreen());
    }

    @Override
    public void update() {
        AbstractCard candidateRitualCard =
                this.ritualPool
                        .get(AbstractDungeon.cardRandomRng.random(this.ritualPool.size() - 1))
                        .makeCopy();
        candidateRitualCard.setCostForTurn(0);
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(candidateRitualCard));
        this.isDone = true;
    }
}