package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Dummyrabbit extends MeleeWeapon {

    {
        image = ItemSpriteSheet.EGO_WEB_DUMMY_RABBIT;
        hitSound = Assets.Sounds.HIT_CRUSH;
        hitSoundPitch = 1.1f;

        tier = 2;
        grade = "Teth";

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
