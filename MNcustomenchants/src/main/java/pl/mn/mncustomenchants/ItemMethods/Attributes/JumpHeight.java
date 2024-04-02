package pl.mn.mncustomenchants.ItemMethods.Attributes;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;
import pl.mn.mncustomenchants.ItemMethods.AttributeType;
import pl.mn.mncustomenchants.ItemMethods.ItemUtils;
import pl.mn.mncustomenchants.main;

public class JumpHeight implements Listener {

    public JumpHeight(){
        Bukkit.getPluginManager().registerEvents(this, main.getInstance());
    }


    @EventHandler
    public void playerJump(PlayerJumpEvent event){

        double jumpHeight = ItemUtils.getEntityAttribute(event.getPlayer(), AttributeType.JUMP_HEIGHT);



        //event.getPlayer().setVelocity(new Vector(0, 64, 0));



    }

}
