package pl.mn.mncustomenchants.Spells;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import pl.mn.mncustomenchants.main;

import java.util.ArrayList;
import java.util.List;

public class SpellManager {

    public static List<Spell> activeSpells = new ArrayList<>();

    private static boolean isTicking = false;

    private static BukkitTask tick;


    public static void tick(){

        tick = Bukkit.getScheduler().runTaskTimer(main.getInstance(), new Runnable() {
            @Override
            public void run() {

                for (Spell spell : new ArrayList<>(activeSpells)){
                    spell.onTick();
                }

            }

        }, 0 , 2);


    }

    public static void castSpell(Spell spell){
        spell.onCast();

        activeSpells.add(spell);

        if(!isTicking){
            isTicking = true;
            tick();
        }

    }

    public static void removeSpell(Spell spell){

        activeSpells.remove(spell);

        if (activeSpells.isEmpty()){
            isTicking = false;
            tick.cancel();
        }

    }

    public static void CancelSpell(Spell spell){
        spell.cancel();

        removeSpell(spell);
    }



}
