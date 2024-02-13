package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Batons extends MeleeWeapon {

    {
        image = ItemSpriteSheet.EGO_WEB_BATONS;
        hitSound = Assets.Sounds.HIT_CRUSH;
        hitSoundPitch = 1.1f;

        tier = 1;

        bones = false;
    }

    @Override
    public int proc( Char attacker, Char defender, int damage)
    {
        attacker.heal( 25 );
        return super.proc( attacker, defender, damage );
    }


    @Override
    public int min(int lvl) {
        return  9*(tier+4) + //base
                lvl*(tier+1);    //level scaling
    }
    @Override
    public int max(int lvl) {
        return  25*(tier+4) +    //45 base, up from 30
                lvl*(tier+1);   //scaling unchanged
    }

}
