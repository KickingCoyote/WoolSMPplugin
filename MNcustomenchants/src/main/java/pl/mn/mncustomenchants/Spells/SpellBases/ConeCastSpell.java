package pl.mn.mncustomenchants.Spells.SpellBases;

import pl.mn.mncustomenchants.Spells.Spell;
import pl.mn.mncustomenchants.Spells.SpellManager;

public class ConeCastSpell extends Spell {


    Spell spell;
    int coneAngle;
    int spellAmount;

    public ConeCastSpell(Spell spell, int coneAngle, int spellAmount){
        this.spell = spell;
        this.coneAngle = coneAngle;
        this.spellAmount = spellAmount;
    }


    @Override
    public void onTick() {

    }

    @Override
    public void onCast() {

        

        spell.castLocation.setDirection(spell.castLocation.getDirection().rotateAroundY(-coneAngle / 2 * Math.PI / 180));

        for (int i = 0; i < coneAngle; i += coneAngle / spellAmount){

            spell.castLocation.setDirection(spell.castLocation.getDirection().rotateAroundY((double) (coneAngle / spellAmount) * Math.PI / 180));

            SpellManager.castSpell(spell);

        }


        onEnd();
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
