package pl.mn.mncustomenchants.Spells.SpellBases;

import pl.mn.mncustomenchants.Spells.Spell;
import pl.mn.mncustomenchants.Spells.SpellManager;

import javax.swing.*;
import java.lang.reflect.Type;

public class ChainCastSpell extends Spell {



    Spell spell;
    int castPeriod;
    int amount;

    public ChainCastSpell(Spell spell, int castPeriod, int amount){
        this.spell = spell;
        this.castPeriod = castPeriod;
        this.amount = amount;

    }


    int t = 0;

    @Override
    public void onTick() {

        if (t + castPeriod > castPeriod * amount){
            onEnd();
            return;
        }

        t++;
        if (t%castPeriod == 0){
            SpellManager.castSpell(spell);
        }


    }

    @Override
    public void onCast() {
        SpellManager.castSpell(spell);
    }

    @Override
    public void onHit() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void onEnd() {
        SpellManager.removeSpell(this);
    }
}
