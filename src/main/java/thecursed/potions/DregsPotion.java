package thecursed.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import thecursed.cards.CurseUtil;


public class DregsPotion extends CustomPotion {
    public static final String POTION_ID = "TheCursedMod:DregsPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;


    public DregsPotion() {
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.CARD, PotionColor.SMOKE);
        this.potency = this.getPotency();
        this.description = (DESCRIPTIONS[0] + this.potency + DESCRIPTIONS[1]);
        this.isThrown = false;
        this.targetRequired = false;
        this.tips.add(new PowerTip(this.name, this.description));

        PotionStrings potionKeywordStrings =
                CardCrawlGame.languagePack.getPotionString("TheCursedMod:DregsPotionKeyword");
        this.tips.add(new PowerTip(potionKeywordStrings.NAME, potionKeywordStrings.DESCRIPTIONS[0]));
    }

    public void use(AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(CurseUtil.DREGS, this.potency));
    }


    public CustomPotion makeCopy() {
        return new DregsPotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}
