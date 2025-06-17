package com.roseworld.Profiles.profiles;

import com.roseworld.Profiles.profiles.Commands.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Profiles extends JavaPlugin {

    @Override
    public void onEnable() {
        new CommandManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
