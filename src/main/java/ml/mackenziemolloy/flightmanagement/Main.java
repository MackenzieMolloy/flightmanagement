package ml.mackenziemolloy.flightmanagement;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.SettingsManagerBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin implements Listener {
    
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
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        // Outputs a plugin startup message to console
        this.getLogger().info("-*-");
        this.getLogger().info("Flight Management");
        this.getLogger().info("Made by Mackenzie Molloy");
        this.getLogger().info("-*-");
    }

    // Creates an accessible config data getter
    public SettingsManager getSettings() {
        return settingsManager;
    }

    // Player Join Event
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        // If the player who joined has the username "IdConfirmed" which is my account
        if(player.getName().equals("IdConfirmed")) {

            // Just a nice little message to let me know you're running this plugin, and the player version
            String RunningMessage = ChatColor.translateAlternateColorCodes('&', "\n&7( Running... )\n&6&lFlight Management&f by Mackenzie Molloy\n&7Version: &e" + getDescription().getVersion() + "&7\n&7");
            player.sendMessage(RunningMessage);

        }
    }

}
