package com.ultikits.ultitools.commands;

import com.ultikits.abstracts.AbstractTabExecutor;
import com.ultikits.ultitools.beans.TpBean;
import com.ultikits.ultitools.ultitools.UltiTools;
import com.ultikits.utils.MessagesUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.ultikits.utils.MessagesUtils.warning;

public class TpaHereCommands extends AbstractTabExecutor {
    @Override
    protected boolean onPlayerCommand(@NotNull Command command, @NotNull String[] strings, @NotNull Player player) {
        if (strings.length == 1){
            switch (strings[0]) {
                case "accept":
                    Player teleporter = TpBean.tphereTemp.get(player);
                    if (teleporter == null) {
                        player.sendMessage(MessagesUtils.warning(UltiTools.languageUtils.getString("tpa_no_request")));
                        return true;
                    }
                    TpBean.tphereTemp.put(player, null);
                    TpBean.tphereTimer.put(player, 0);
                    player.teleport(teleporter.getLocation());
                    teleporter.sendMessage(MessagesUtils.info(UltiTools.languageUtils.getString("tpa_teleport_success")));
                    return true;
                case "reject":
                    Player teleporter2 = TpBean.tphereTemp.get(player);
                    teleporter2.sendMessage(MessagesUtils.warning(UltiTools.languageUtils.getString("tpa_teleport_rejected")));
                    player.sendMessage(MessagesUtils.info(UltiTools.languageUtils.getString("tpa_rejected")));
                    TpBean.tphereTemp.put(player, null);
                    TpBean.tphereTimer.put(player, 0);
                    return true;
                default:
                    Player target = Bukkit.getPlayerExact(strings[0]);
                    if (target == null) {
                        player.sendMessage(warning(UltiTools.languageUtils.getString("tpa_player_not_found")));
                        return true;
                    }
                    TpBean.tphereTemp.put(target, player);
                    TpBean.tphereTimer.put(target, 20);
                    target.sendMessage(MessagesUtils.info(String.format(UltiTools.languageUtils.getString("tpahere_enquire"), player.getName())));
                    target.sendMessage(MessagesUtils.info(UltiTools.languageUtils.getString("tpahere_accept_tip")));
                    target.sendMessage(MessagesUtils.info(UltiTools.languageUtils.getString("tpahere_reject_tip")));
                    return true;
            }
        }
        return false;
    }

    @Nullable
    @Override
    protected List<String> onPlayerTabComplete(@NotNull Command command, @NotNull String[] strings, @NotNull Player player) {
        return getTpTabList(strings);
    }

    @Nullable
    public static List<String> getTpTabList(@NotNull String[] strings) {
        List<String> tabCommands = new ArrayList<>();
        if (strings.length == 1) {
            tabCommands.add("accept");
            tabCommands.add("reject");
            for (Player player1 : Bukkit.getOnlinePlayers()) {
                tabCommands.add(player1.getName());
            }
            return tabCommands;
        }
        return null;
    }
}
