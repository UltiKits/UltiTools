package com.ultikits.ultitools.commands;

import com.ultikits.abstracts.AbstractPlayerCommandExecutor;
import com.ultikits.abstracts.AbstractTabExecutor;
import com.ultikits.ultitools.config.ConfigController;
import com.ultikits.ultitools.enums.ConfigsEnum;
import com.ultikits.ultitools.ultitools.UltiTools;
import com.ultikits.ultitools.views.CreateKitsView;
import com.ultikits.ultitools.views.KitsView;
import com.ultikits.utils.MessagesUtils;
import org.bukkit.command.Command;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KitsCommands extends AbstractTabExecutor {

    @Override
    protected boolean onPlayerCommand(@NotNull Command command, @NotNull String[] strings, @NotNull Player player) {
        switch (strings.length){
            case 0:
                Inventory inventory = KitsView.setUp(player);
                player.openInventory(inventory);
                return true;
            case 2:
                if (!strings[0].equals("edit")){
                    return false;
                }
                if (player.hasPermission("ultikits.tools.admin")){
                    Map<String, Object> map = ConfigController.getConfigSection("kits");
                    if (map.get(strings[1]) == null){
                        player.sendMessage(MessagesUtils.warning(UltiTools.languageUtils.getString("kits_no_such_kit")));
                        return true;
                    }
                    Inventory inventory1 = CreateKitsView.setUp(strings[1]);
                    player.openInventory(inventory1);
                    return true;
                }
            default:
                return false;
        }
    }

    @Nullable
    @Override
    protected List<String> onPlayerTabComplete(@NotNull Command command, @NotNull String[] strings, @NotNull Player player) {
        if (!player.isOp()){
            return null;
        }
        if (strings.length == 1) {
            List<String> tabCommands = new ArrayList<>();
            tabCommands.add("edit");
            return tabCommands;
        } else if (strings.length == 2) {
            File file = new File(ConfigsEnum.KIT.toString());
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            return new ArrayList<>(config.getKeys(false));
        }
        return null;
    }
}
