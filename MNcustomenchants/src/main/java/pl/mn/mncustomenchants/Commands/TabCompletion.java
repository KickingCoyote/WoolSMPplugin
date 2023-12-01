package pl.mn.mncustomenchants.Commands;

//import com.sun.org.apache.bcel.internal.util.Args;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.mn.mncustomenchants.CustomEnchantments.CustomEnchantments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TabCompletion implements TabCompleter {


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {



        if(args.length == 1){
            List<String> e = new ArrayList<>(CustomEnchantments.enchantmentArgs);
            Collections.sort(e);
            return e;
        }

        if(args.length == 2){
            List<String> lvls = new ArrayList<String>();
            Integer maxLvl = CustomEnchantments.valueOf(args[1]).getMaxLevel();
            for (int i = 0; i <= maxLvl; i++){

                lvls.add(String.valueOf(i));
            }
            return lvls;
        }

        return null;
    }
}