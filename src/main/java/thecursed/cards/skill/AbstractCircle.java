package thecursed.cards.skill;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import thecursed.cards.TheCursedCardTags;
import thecursed.enums.AbstractCardEnum;
import thecursed.relics.DemonicMark;
import thecursed.relics.MagicCandle;
import thecursed.relics.PinkPellets;

import static com.badlogic.gdx.math.MathUtils.random;

public abstract class AbstractCircle extends CustomCard {
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = -2;

    public AbstractCircle(
            String id, String name, String imagePath, String description, String cantUseMessage, CardRarity rarity,
            CardTarget target) {
        super(id, name, imagePath, COST, description, TYPE, AbstractCardEnum.THE_CURSED_PURPLE, rarity, target);
        this.cantUseMessage = cantUseMessage;
        this.tags.add(TheCursedCardTags.CIRCLE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    protected void onActivateCircle(String name) {
        AbstractPlayer p = AbstractDungeon.player;
        CardCrawlGame.sound.playV("BUFF_3", 0.8F);
        AbstractDungeon.effectsQueue.add(
                new TextAboveCreatureEffect(
                        p.hb.cX - p.animX, p.hb.cY + random(180) - 90, name, Color.PURPLE));

        if (p.hasRelic(DemonicMark.ID)) {
            ((DemonicMark) p.getRelic(DemonicMark.ID)).increaseCountAndMaybeActivate();
        }
        if (p.hasRelic(MagicCandle.ID)) {
            ((MagicCandle) p.getRelic(MagicCandle.ID)).increaseCountAndMaybeActivate();
        }
        if (p.hasRelic(PinkPellets.ID)) {
            ((PinkPellets) p.getRelic(PinkPellets.ID)).increaseCountAndMaybeActivate();
        }
    }
}
