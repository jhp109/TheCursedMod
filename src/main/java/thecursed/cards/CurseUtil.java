package thecursed.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import thecursed.cards.curse.Dregs;
import thecursed.relics.FourLeafCloverCharm;

import java.util.ArrayList;

public class CurseUtil {
    // As MakeTempCard... actions copy the card object, just define a static Dregs here for common use.
    public static final AbstractCard DREGS = new Dregs();

    public static int getNumCurse(ArrayList<AbstractCard> cards) {
        return (int) cards.stream()
                .filter(card -> card.type == AbstractCard.CardType.CURSE)
                .count();
    }

    public static boolean hasCurse(ArrayList<AbstractCard> cards) {
        return getNumCurse(cards) > 0;
    }

    public static AbstractCard getRandomCurseCard() {
        if (AbstractDungeon.player.hasRelic(FourLeafCloverCharm.ID)) {
            AbstractDungeon.player.getRelic(FourLeafCloverCharm.ID).flash();
            return DREGS;
        } else {
            return CardLibrary.getCurse();
        }
    }
}
