package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Frostsplinter extends MeleeWeapon {

    {
        image = ItemSpriteSheet.EGO_WEB_FROST_SPLINTER;
        hitSound = Assets.Sounds.HIT_CRUSH;
        hitSoundPitch = 1.1f;

        tier = 3;
        grade = "He";
        DLY = 1.5f; //0.67x speed
        RCH = 2;    //extra reach

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
