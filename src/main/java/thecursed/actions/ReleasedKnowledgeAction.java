package thecursed.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thecursed.cards.skill.*;

import java.util.ArrayList;

public class ReleasedKnowledgeAction extends AbstractGameAction {
    private ArrayList<AbstractCircle> circlePool;

    public ReleasedKnowledgeAction() {
        this.circlePool = new ArrayList<>();
        this.circlePool.add(new CircleOfAbyss());
        this.circlePool.add(new CircleOfAmplification());
        // this.circlePool.add(new CircleOfBlood()); - don't include HEALING card
        this.circlePool.add(new CircleOfCorruption());
        this.circlePool.add(new CircleOfFlame());
        this.circlePool.add(new CircleOfFocus());
        this.circlePool.add(new CircleOfWindfury());
    }

    @Override
    public void update() {
        AbstractCard candidateCircleCard =
                this.circlePool
                        .get(AbstractDungeon.cardRandomRng.random(this.circlePool.size() - 1))
                        .makeCopy();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(candidateCircleCard));
        this.isDone = true;
    }
}