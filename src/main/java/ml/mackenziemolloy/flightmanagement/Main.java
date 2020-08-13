package ml.mackenziemolloy.flightmanagement;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    private final SettingsManager settingsManager = SettingsManagerBuilder
            .withYamlFile(new File(getDataFolder(),"config.yml"))
            .configurationData(Config.class)
            .useDefaultMigrationService()
            .create();

    @Override
    public void onEnable() {
        new Commands(this);

        this.getLogger().info("-*-");
        this.getLogger().info("Flight Management");
        this.getLogger().info("Made by Mackenzie Molloy");
        this.getLogger().info("-*-");
    }

    public SettingsManager getSettings() {
        return settingsManager;
    }

}
