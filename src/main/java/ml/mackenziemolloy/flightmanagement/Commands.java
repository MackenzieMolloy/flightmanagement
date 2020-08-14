package ml.mackenziemolloy.flightmanagement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class Commands implements CommandExecutor {

    // Makes main class getters/setters accessible
    private final Main main;

    // Registers the primary /fly command - /flight is registered as an alias in the plugin.yml
    public Commands(final Main main) {
        this.main = main;
        main.getCommand("fly").setExecutor(this);
    }


    // On Command Event
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Checks if the person who sent the command, is a player
        if(sender instanceof Player) {

            // Casts the command sender to a player
            Player commandSender = (Player) sender;

            // If no arguments are supplied with the sent command
            if (args.length <= 0) {
                List<String> invalidArgsRaw = main.getSettings().getProperty(Config.MESSAGES_HELP);
                StringBuilder msg = new StringBuilder("\n");

                for (String s : invalidArgsRaw) {
                    msg.append(s).append("\n");
                }

                String invalidArgs = ChatColor.translateAlternateColorCodes('&', msg.toString());
                commandSender.sendMessage(invalidArgs);
                return true;
            }

            // If the command sender has the permission node "flight.use" or "flight.check"
            else if (commandSender.hasPermission("flight.use") || commandSender.hasPermission("flight.check")) {

                // If the 1st supplied argument is "check" (Ignoring case) and command sender has the permission node "flight.check"
                if(args[0].toLowerCase().equals("check") && commandSender.hasPermission("flight.check")) {

                    // If arguments length is longer than 1
                    if (args.length >= 2) {

                        // Fetch player information
                        Player otherPlayer = Bukkit.getPlayer(args[1]);

                        // If the provided player couldn't be fetched
                        if (otherPlayer == null) {

                            // Converts the config message, to a formated chat message
                            String playerNotFoundRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NOT_FOUND).replace("{username}", args[1]);
                            String playerNotFound = ChatColor.translateAlternateColorCodes('&', playerNotFoundRaw);
                                
                            // Sends the converted message to the player
                            commandSender.sendMessage(playerNotFound);
                        }

                        // If the provided player was fetched successfully
                        else {

                            // Converts the config message, to a formated chat message
                            String flightStateRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_STATE).replace("{state}", String.valueOf(otherPlayer.getAllowFlight())).replace("{player}", args[1]);
                            String flightState = ChatColor.translateAlternateColorCodes('&', flightStateRaw);
                                
                            // Sends the converted message to the player
                            commandSender.sendMessage(flightState);

                        }

                    }

                    // If argument length was not longer than 1 (No playername provided)
                    else {

                        // Converts the config message, to a formated chat message
                        String playerNameRequiredRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NAME_REQUIRED);
                        String playerNameRequired = ChatColor.translateAlternateColorCodes('&', playerNameRequiredRaw);
                            
                        // Sends the converted message to the player
                        commandSender.sendMessage(playerNameRequired);
                    }

                }

                // If the 1st supplied argument is "toggle" (Ignoring case) and the command sender has the permission node "flight.use"
                else if (args[0].toLowerCase().equals("toggle") && commandSender.hasPermission("flight.use")) {

                    // If the amount of arguments supplied with the command is equal to 1 (No player username provided) or the command sender doesn't have the permission node "flight.others"
                    if (args.length == 1 || !commandSender.hasPermission("flight.others")) {

                        // If the command sender's flight is disabled
                        if (!commandSender.getAllowFlight()) {

                            // Enables command sender's flight
                            commandSender.setAllowFlight(true);
                            
                            // Converts the config message, to a formated chat message
                            String flightEnabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED);
                            String flightEnabled = ChatColor.translateAlternateColorCodes('&', flightEnabledRaw);

                             // Sends the converted message to the player
                            commandSender.sendMessage(flightEnabled);
                        }

                        // If the command sender's flight is enabled
                        else {

                            // Disables command sender's flight
                            commandSender.setAllowFlight(false);
                            
                            // Converts the config message, to a formated chat message
                            String flightDisabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED);
                            String flightDisabled = ChatColor.translateAlternateColorCodes('&', flightDisabledRaw);
                            
                            // Sends the converted message to the player
                            commandSender.sendMessage(flightDisabled);
                        }

                    }

                    // If the amount of arguments supplied with the command is not equal to 1 (Player username provided)
                    else {

                        // Fetches the provided player username
                        Player otherPlayer = Bukkit.getPlayer(args[1]);

                        // If the provided player couldn't be fetched
                        if (otherPlayer == null) {

                            // Converts the config message, to a formated chat message
                            String playerNotFoundRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NOT_FOUND).replace("{username}", args[1]);
                            String playerNotFound = ChatColor.translateAlternateColorCodes('&', playerNotFoundRaw);
                            
                            // Sends the converted message to the player
                            commandSender.sendMessage(playerNotFound);
                        }

                        // If the player is exempt from flight toggle
                        else if(otherPlayer.hasPermission("flight.toggle-exempt")) {

                            // Converts the config message, to a formated chat message
                            String exemptUserRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_EXEMPT).replace("{player}", args[1]);
                            String exemptUser = ChatColor.translateAlternateColorCodes('&', exemptUserRaw);

                            // Sends the converted message to the player
                            commandSender.sendMessage(exemptUser);

                        }

                        // If the provided player was fetched successfully
                        else {

                            // If the fetched player's flight is disabled
                            if (!otherPlayer.getAllowFlight()) {

                                // Enables provided player's flight
                                otherPlayer.setAllowFlight(true);

                                // If the amount of supplied arguments is greater than 1 (Slient possiblity)
                                if(args.length >= 3) {

                                    // If the 3rd argument is "-s" (For silent flight toggle)
                                    if(args[2].toLowerCase().equals("-s")) {
                                        // Don't notify player
                                        return true;
                                    }

                                    // If the 2nd argument isn't "-s" (Not silent)
                                    else {

                                        // Notify target player
                                        // Converts the config message, to a formated chat message
                                        String flightEnabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_EXTERNAL).replace("{other}", commandSender.getName());
                                        String flightEnabledExternal = ChatColor.translateAlternateColorCodes('&', flightEnabledExternalRaw);
                                        
                                        // Sends the converted message to the player
                                        otherPlayer.sendMessage(flightEnabledExternal);
                                    }
                                }

                                // If there is no 3rd argument (not silent)
                                else {

                                    // Notify target Player
                                    // Converts the config message, to a formated chat message
                                    String flightEnabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_EXTERNAL).replace("{other}", commandSender.getName());
                                    String flightEnabledExternal = ChatColor.translateAlternateColorCodes('&', flightEnabledExternalRaw);
                                    
                                    // Sends the converted message to the player
                                    otherPlayer.sendMessage(flightEnabledExternal);

                                }

                                // Notify command sender
                                // Converts the config message, to a formated chat message
                                String flightEnabledOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_OTHER).replace("{player}", otherPlayer.getName());
                                String flightEnabledOther = ChatColor.translateAlternateColorCodes('&', flightEnabledOtherRaw);
                                
                                // Sends the converted message to the player
                                commandSender.sendMessage(flightEnabledOther);
                            }

                            // If the fetched player's flight is enabled
                            else {

                                // Disables provided player's flight
                                otherPlayer.setAllowFlight(false);

                                if(args.length >= 3) {

                                    // If the 3nd argument is "-s" (For silent flight toggle)
                                    if(args[2].toLowerCase().equals("-s")) {
                                        // Don't notify player
                                        return true;
                                    }

                                    else {

                                        // Notify target player
                                        // Converts the config message, to a formated chat message
                                        String flightDisabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_EXTERNAL).replace("{other}", commandSender.getName());
                                        String flightDisabledExternal = ChatColor.translateAlternateColorCodes('&', flightDisabledExternalRaw);

                                        // Sends the converted message to the player
                                        otherPlayer.sendMessage(flightDisabledExternal);
                                    }
                                }

                                // If there is no 3rd argument
                                else {

                                    // Notify target player
                                    // Converts the config message, to a formated chat message
                                    String flightDisabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_EXTERNAL).replace("{other}", commandSender.getName());
                                    String flightDisabledExternal = ChatColor.translateAlternateColorCodes('&', flightDisabledExternalRaw);

                                    // Sends the converted message to the player
                                    otherPlayer.sendMessage(flightDisabledExternal);

                                }

                                // Notify command sender
                                // Converts the config message, to a formated chat message
                                String flightDisabledOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_OTHER).replace("{player}", otherPlayer.getName());
                                String flightDisabledOther = ChatColor.translateAlternateColorCodes('&', flightDisabledOtherRaw);
                                
                                // Sends the converted message to the player
                                commandSender.sendMessage(flightDisabledOther);
                            }
                        }

                    }
                }

                // If the 1st supplied argument is "on" (Ignoring case) and the command sender has the permission node "flight.use"
                else if (args[0].toLowerCase().equals("on") && commandSender.hasPermission("flight.use")) {

                    // If the command sender has flight enabled
                    if (commandSender.getAllowFlight()) {
                        
                        // Converts the config message, to a formated chat message
                        String flightAlreadyEnabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ALREADY_ENABLED);
                        String flightAlreadyEnabled = ChatColor.translateAlternateColorCodes('&', flightAlreadyEnabledRaw);
                        
                        // Sends the converted message to the player
                        commandSender.sendMessage(flightAlreadyEnabled);
                    }

                    // If the command sender has flight disabled
                    else {
                        // Enables the command sender's flight
                        commandSender.setAllowFlight(true);

                        // Converts the config message, to a formated chat message
                        String flightEnabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED);
                        String flightEnabled = ChatColor.translateAlternateColorCodes('&', flightEnabledRaw);

                        // Sends the converted message to the player
                        commandSender.sendMessage(flightEnabled);
                    }
                }

                // If the 1st supplied argument is "off" (Ignoring case) and the command sender has the permission node "flight.use"
                else if (args[0].toLowerCase().equals("off") && commandSender.hasPermission("flight.use")) {

                    // If the command sender has flight disabled
                    if (!commandSender.getAllowFlight()) {
                        
                        // Converts the config message, to a formated chat message
                        String flightAlreadyDisabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ALREADY_DISABLED);
                        String flightAlreadyDisabled = ChatColor.translateAlternateColorCodes('&', flightAlreadyDisabledRaw);

                        // Sends the converted message to the player
                        commandSender.sendMessage(flightAlreadyDisabled);
                    }

                    // If the command sender has flight enabled
                    else {
                        // Disables the command sender's flight
                        commandSender.setAllowFlight(false);

                        // Converts the config message, to a formated chat message
                        String flightDisabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED);
                        String flightDisabled = ChatColor.translateAlternateColorCodes('&', flightDisabledRaw);

                        // Sends the converted message to the player
                        commandSender.sendMessage(flightDisabled);
                    }
                }

                // If the 1st supplied argument is "reload" or "rl" (Ignoring case) and the command sender has the permission node "flight.reload"
                else if((args[0].toLowerCase().equals("reload") || args[0].toLowerCase().equals("rl")) && commandSender.hasPermission("flight.reload")) {
                        
                    // Fetches updated configurate (updates the cached config)
                    main.getSettings().reload();

                    // Converts the config message, to a formated chat message
                    String configReloadedRaw = main.getSettings().getProperty(Config.MESSAGES_CONFIG_RELOADED);
                    String configReloaded = ChatColor.translateAlternateColorCodes('&', configReloadedRaw);

                    // Sends the converted message to the player
                    commandSender.sendMessage(configReloaded);

                }

                // If the 1st supplied argument is invalid
                else {

                    // Converts the config message, to a formated chat message
                    String unknownCommandRaw = main.getSettings().getProperty(Config.MESSAGES_UNKNOWN_COMMAND);
                    String unknownCommand = ChatColor.translateAlternateColorCodes('&', unknownCommandRaw);

                    // Sends the converted message to the player
                    commandSender.sendMessage(unknownCommand);

                }
            }

            // If command sender doesn't have the permission node "flight.use"
            else {

                // Converts the config message, to a formated chat message
                String noPermissionRaw = main.getSettings().getProperty(Config.MESSAGES_NO_PERMISSION);
                String noPermission = ChatColor.translateAlternateColorCodes('&', noPermissionRaw);

                // Sends the converted message to the player
                commandSender.sendMessage(noPermission);
            }
        }

        // If command sender is console
        else {

            // Casts console to console command sender
            ConsoleCommandSender console = getServer().getConsoleSender();

            // If there were no supplied arguments
            if (args.length <= 0) {
                
                // Processes the confgured help message to 1 string/chat message
                List<String> invalidArgsRaw = main.getSettings().getProperty(Config.MESSAGES_HELP);
                StringBuilder msg = new StringBuilder("\n");

                for (String s : invalidArgsRaw) {
                    msg.append(s).append("\n");
                }

                String invalidArgs = ChatColor.translateAlternateColorCodes('&', msg.toString());
                console.sendMessage(invalidArgs);
                return true;
            }

            // If the 1st supplied argument is "check" (Ignoring case)
            if(args[0].toLowerCase().equals("check")) {

                // If arguments length is longer than 1
                if (args.length >= 2) {

                    // Fetch player information
                    Player otherPlayer = Bukkit.getPlayer(args[1]);

                    // If the provided player couldn't be fetched
                    if (otherPlayer == null) {

                        String playerNotFoundRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NOT_FOUND).replace("{username}", args[1]);
                        String playerNotFound = ChatColor.translateAlternateColorCodes('&', playerNotFoundRaw);

                        console.sendMessage(playerNotFound);
                    }

                    // If the provided player was fetched successfully
                    else {

                        String flightStateRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_STATE).replace("{state}", String.valueOf(otherPlayer.getAllowFlight())).replace("{player}", args[1]);
                        String flightState = ChatColor.translateAlternateColorCodes('&', flightStateRaw);

                        console.sendMessage(flightState);
                    }
                }

                else {

                    String playerNameRequiredRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NAME_REQUIRED);
                    String playerNameRequired = ChatColor.translateAlternateColorCodes('&', playerNameRequiredRaw);

                    console.sendMessage(playerNameRequired);

                }
            }

            // If the 1st supplied argument is "toggle" (Ignoring case)
            else if (args[0].toLowerCase().equals("toggle")) {

                // If the amount of arguments supplied with the command is great than or equal to 2 (player provided)
                if (args.length >= 2) {

                    Player otherPlayer = Bukkit.getPlayer(args[1]);
                    String serverNameRaw = main.getSettings().getProperty(Config.MESSAGES_SERVER_NAME);
                    String serverName = ChatColor.translateAlternateColorCodes('&', serverNameRaw);

                    // If the provided player couldn't be fetched
                    if (otherPlayer == null) {

                        String playerNotFoundRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NOT_FOUND).replace("{username}", args[1]);
                        String playerNotFound = ChatColor.translateAlternateColorCodes('&', playerNotFoundRaw);

                        console.sendMessage(playerNotFound);
                    }

                    // If the player is exempt from flight toggle
                    else if(otherPlayer.hasPermission("flight.toggle-exempt")) {

                        String exemptUserRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_EXEMPT).replace("{player}", args[1]);
                        String exemptUser = ChatColor.translateAlternateColorCodes('&', exemptUserRaw);

                        console.sendMessage(exemptUser);

                    }

                    // If the provided player was fetched successfully
                    else {

                        // If the fetched player's flight is disabled
                        if (!otherPlayer.getAllowFlight()) {

                            // Enables provided player's flight
                            otherPlayer.setAllowFlight(true);

                            // If the amount of supplied arguments is greater than 1
                            if(args.length >= 3) {

                                // If the 3rd argument is "-s" (For silent flight toggle)
                                if(args[2].toLowerCase().equals("-s")) {
                                    // Don't notify player
                                    return true;
                                }

                                // If the 2nd argument isn't "-s"
                                else {

                                    // Notify target player
                                    String flightEnabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_EXTERNAL).replace("{other}", serverName);
                                    String flightEnabledExternal = ChatColor.translateAlternateColorCodes('&', flightEnabledExternalRaw);

                                    otherPlayer.sendMessage(flightEnabledExternal);
                                }
                            }

                            // If there is no 3rd argument
                            else {

                                // Notify target Player
                                String flightEnabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_EXTERNAL).replace("{other}", serverName);
                                String flightEnabledExternal = ChatColor.translateAlternateColorCodes('&', flightEnabledExternalRaw);

                                otherPlayer.sendMessage(flightEnabledExternal);

                            }

                            // Notify command sender
                            String flightEnabledOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_OTHER).replace("{player}", otherPlayer.getName());
                            String flightEnabledOther = ChatColor.translateAlternateColorCodes('&', flightEnabledOtherRaw);

                            console.sendMessage(flightEnabledOther);
                        }

                        // If the fetched player's flight is enabled
                        else {

                            // Disables provided player's flight
                            otherPlayer.setAllowFlight(false);

                            if(args.length >= 3) {

                                // If the 3nd argument is "-s" (For silent flight toggle)
                                if(args[2].toLowerCase().equals("-s")) {
                                    // Don't notify player
                                    return true;
                                }
                                else {

                                    // Notify target player
                                    String flightDisabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_EXTERNAL).replace("{other}", serverName);
                                    String flightDisabledExternal = ChatColor.translateAlternateColorCodes('&', flightDisabledExternalRaw);

                                    otherPlayer.sendMessage(flightDisabledExternal);
                                }
                            }

                            // If there is no 3rd argument
                            else {

                                // Notify target player
                                String flightDisabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_EXTERNAL).replace("{other}", serverName);
                                String flightDisabledExternal = ChatColor.translateAlternateColorCodes('&', flightDisabledExternalRaw);

                                otherPlayer.sendMessage(flightDisabledExternal);

                            }

                            // Notify command sender
                            String flightDisabledOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_OTHER).replace("{player}", otherPlayer.getName());
                            String flightDisabledOther = ChatColor.translateAlternateColorCodes('&', flightDisabledOtherRaw);

                            console.sendMessage(flightDisabledOther);
                        }
                    }

                }

                else {

                    String playerNameRequiredRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NAME_REQUIRED);
                    String playerNameRequired = ChatColor.translateAlternateColorCodes('&', playerNameRequiredRaw);

                    console.sendMessage(playerNameRequired);

                }

            }

            else if(args[0].toLowerCase().equals("reload") || args[0].toLowerCase().equals("rl")) {

                main.getSettings().reload();

                String configReloadedRaw = main.getSettings().getProperty(Config.MESSAGES_CONFIG_RELOADED);
                String configReloaded = ChatColor.translateAlternateColorCodes('&', configReloadedRaw);

                console.sendMessage(configReloaded);

            }

            else {

                String unknownCommandRaw = main.getSettings().getProperty(Config.MESSAGES_UNKNOWN_COMMAND);
                String unknownCommand = ChatColor.translateAlternateColorCodes('&', unknownCommandRaw);

                console.sendMessage(unknownCommand);

            }

        }

        return true;
    }
}
