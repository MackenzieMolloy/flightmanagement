package ml.mackenziemolloy.flightmanagement;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {
    
    // Generates, and sets-up the config.yml file utilizing the config class
    private final SettingsManager settingsManager = SettingsManagerBuilder
            .withYamlFile(new File(getDataFolder(),"config.yml"))
            .configurationData(Config.class)
            .useDefaultMigrationService()
            .create();

    // On server startup
    @Override
    public void onEnable() {
        // Registers the command class
        new Commands(this);

        // Outputs a plugin startup message to console
        this.getLogger().info("-*-");
        this.getLogger().info("Flight Management");
        this.getLogger().info("Made by Mackenzie Molloy");
        this.getLogger().info("-*-");
    }

    // Creates an accessable config data getter
    public SettingsManager getSettings() {
        return settingsManager;
    }

}
