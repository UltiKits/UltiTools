package com.ultikits.ultitools.checker.prochecker;

import org.bukkit.Bukkit;

public class DependencyChecker {

    private DependencyChecker() {
    }

    public static boolean isUltiCoreUpToDate(){
        int UltiCoreVersionRequired = 111;
        int UltiCoreVersionCurrent = Integer.parseInt(Bukkit.getPluginManager().getPlugin("UltiCore").getDescription().getVersion().replaceAll("\\.", ""));
        return UltiCoreVersionCurrent >= UltiCoreVersionRequired;
    }
}
