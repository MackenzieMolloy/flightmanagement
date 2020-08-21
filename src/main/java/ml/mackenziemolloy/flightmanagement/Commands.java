//
//  > Plugin Recode - commands.java
//
// CommandSender share player, and consoleCommandExecutor
// > Add tests for commands affecting the commandSender if it's a player
// Create a function for managing flight
// > Pass "Command Executor", "Target Player", "execution: on/off/toggle", "visibility: silent/not"
//

package ml.mackenziemolloy.flightmanagement;
import ml.mackenziemolloy.flightmanagement.FlightManage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static ml.mackenziemolloy.flightmanagement.FlightManage.*;
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

            // If the 1st supplied argument is "check" (Ignoring case)
            if(args[0].toLowerCase().equals("check")) {

                // If the command sender has the permission node "flight.check"
                if(commandSender.hasPermission("flight.check")) {

                    // If arguments length is longer than 1
                    if (args.length >= 2) {

                        // Fetch player information
                        Player otherPlayer = Bukkit.getPlayer(args[1]);

                        // If the provided player couldn't be fetched
                        if (otherPlayer == null) {

                            // Converts the config message, to a formatted chat message
                            String playerNotFoundRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NOT_FOUND).replace("{username}", args[1]);
                            String playerNotFound = ChatColor.translateAlternateColorCodes('&', playerNotFoundRaw);

                            // Sends the converted message to the player
                            commandSender.sendMessage(playerNotFound);
                        }

                        // If the provided player was fetched successfully
                        else {

                            // Converts the config message, to a formatted chat message
                            String flightStateRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_STATE).replace("{state}", String.valueOf(otherPlayer.getAllowFlight())).replace("{player}", args[1]);
                            String flightState = ChatColor.translateAlternateColorCodes('&', flightStateRaw);

                            // Sends the converted message to the player
                            commandSender.sendMessage(flightState);

                        }

                    }

                    // If argument length was not longer than 1 (No player-name provided)
                    else {

                        // Converts the config message, to a formated chat message
                        String playerNameRequiredRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NAME_REQUIRED);
                        String playerNameRequired = ChatColor.translateAlternateColorCodes('&', playerNameRequiredRaw);

                        // Sends the converted message to the player
                        commandSender.sendMessage(playerNameRequired);
                    }
                }

                // If the command sender doesn't have the permission node "flight.check"
                else {

                    // Converts the config message, to a formatted chat message
                    String noPermissionRaw = main.getSettings().getProperty(Config.MESSAGES_NO_PERMISSION);
                    String noPermission = ChatColor.translateAlternateColorCodes('&', noPermissionRaw);

                    // Sends the converted message to the player
                    commandSender.sendMessage(noPermission);

                }
            }

            // If the 1st supplied argument is "toggle" (Ignoring case)
            else if (args[0].toLowerCase().equals("toggle")) {

                // If the command sender has the permission node "flight.use"
                if(commandSender.hasPermission("flight.use")) {

                    // If the amount of arguments supplied with the command is equal to 1 (No player username provided) or the command sender doesn't have the permission node "flight.others"
                    if (args.length == 1 || !commandSender.hasPermission("flight.others")) {

                        new FlightManage(main, commandSender, commandSender, "toggle", false);

                    }

                    // If the amount of arguments supplied with the command is not equal to 1 (Player username provided)
                    else {

                        // Fetches the provided player username
                        Player otherPlayer = Bukkit.getPlayer(args[1]);

                        // If the provided player couldn't be fetched
                        if (otherPlayer == null) {

                            // Converts the config message, to a formatted chat message
                            String playerNotFoundRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NOT_FOUND).replace("{username}", args[1]);
                            String playerNotFound = ChatColor.translateAlternateColorCodes('&', playerNotFoundRaw);

                            // Sends the converted message to the player
                            commandSender.sendMessage(playerNotFound);
                        }

                        // If the player is exempt from flight toggle
                        else if (otherPlayer.hasPermission("flight.toggle-exempt")) {

                            // Converts the config message, to a formatted chat message
                            String exemptUserRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_EXEMPT).replace("{player}", args[1]);
                            String exemptUser = ChatColor.translateAlternateColorCodes('&', exemptUserRaw);

                            // Sends the converted message to the player
                            commandSender.sendMessage(exemptUser);

                        }

                        // If the provided player was fetched successfully
                        else {

                            if(args.length >= 3) {
                                if(args[2].toLowerCase().equals("-s")) {

                                    new FlightManage(main, commandSender, otherPlayer, "toggle", true);

                                } else {

                                    new FlightManage(main, commandSender, otherPlayer, "toggle", false);

                                }
                            }

                            else {

                                new FlightManage(main, commandSender, otherPlayer, "toggle", false);

                            }
                            
                        }

                    }
                }

                // If the command sender doesn't have the permission node "flight.use"
                else {

                    // Converts the config message, to a formatted chat message
                    String noPermissionRaw = main.getSettings().getProperty(Config.MESSAGES_NO_PERMISSION);
                    String noPermission = ChatColor.translateAlternateColorCodes('&', noPermissionRaw);

                    // Sends the converted message to the player
                    commandSender.sendMessage(noPermission);

                }

            }

            // If the 1st supplied argument is "on" (Ignoring case)
            else if (args[0].toLowerCase().equals("on")) {

                // If the command sender has the permission node "flight.use"
                if(commandSender.hasPermission("flight.use")) {

                    if(args.length >= 2 && commandSender.hasPermission("flight.others")) {

                        Player otherPlayer = Bukkit.getPlayer(args[1]);

                        // If the provided player couldn't be fetched
                        if (otherPlayer == null) {

                            // Converts the config message, to a formatted chat message
                            String playerNotFoundRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NOT_FOUND).replace("{username}", args[1]);
                            String playerNotFound = ChatColor.translateAlternateColorCodes('&', playerNotFoundRaw);

                            // Sends the converted message to the console
                            commandSender.sendMessage(playerNotFound);
                        }

                        // If the provided player was fetched successfully
                        else {

                            // If the amount of supplied arguments is greater than 1 (Silent possibility)
                            if (args.length >= 3) {

                                // If the 3rd argument is "-s" (For silent flight toggle)
                                if (args[2].toLowerCase().equals("-s")) {
                                    // Don't notify target player

                                    // If the fetched player's flight is enabled
                                    if(otherPlayer.getAllowFlight()) {

                                        // Converts the config message, to a formatted chat message
                                        String flightEnabledAlreadyOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ALREADY_ENABLED_OTHER).replace("{player}", otherPlayer.getName());
                                        String flightEnabledAlreadyOther = ChatColor.translateAlternateColorCodes('&', flightEnabledAlreadyOtherRaw);

                                        // Sends the converted message to the player
                                        commandSender.sendMessage(flightEnabledAlreadyOther);

                                    }

                                    // If the fetched player's flight is disabled
                                    else {

                                        // Converts the config message, to a formatted chat message
                                        String flightEnabledOtherSilentRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_OTHER_SILENT).replace("{player}", otherPlayer.getName());
                                        String flightEnabledOtherSilent = ChatColor.translateAlternateColorCodes('&', flightEnabledOtherSilentRaw);

                                        // Sends the converted message to the command sender
                                        commandSender.sendMessage(flightEnabledOtherSilent);
                                        otherPlayer.setAllowFlight(true);

                                    }
                                    return true;
                                }

                                // If the 2nd argument isn't "-s" (Not silent)
                                else {

                                    // Notify target player
                                    // Converts the config message, to a formatted chat message
                                    String flightEnabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_EXTERNAL).replace("{other}", commandSender.getName());
                                    String flightEnabledExternal = ChatColor.translateAlternateColorCodes('&', flightEnabledExternalRaw);

                                    // Sends the converted message to the player
                                    otherPlayer.sendMessage(flightEnabledExternal);
                                }
                            }

                            // If there is no 3rd argument (not silent)
                            else {

                                if(!otherPlayer.getAllowFlight()) {
                                    // Notify target Player
                                    // Converts the config message, to a formatted chat message
                                    String flightEnabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_EXTERNAL).replace("{other}", commandSender.getName());
                                    String flightEnabledExternal = ChatColor.translateAlternateColorCodes('&', flightEnabledExternalRaw);

                                    // Sends the converted message to the player
                                    otherPlayer.sendMessage(flightEnabledExternal);
                                }

                            }

                            // If the fetched player's flight is enabled
                            if(otherPlayer.getAllowFlight()) {
                                // Notify command sender
                                // Converts the config message, to a formatted chat message
                                String flightEnabledAlreadyOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ALREADY_ENABLED_OTHER).replace("{player}", otherPlayer.getName());
                                String flightEnabledAlreadyOther = ChatColor.translateAlternateColorCodes('&', flightEnabledAlreadyOtherRaw);

                                // Sends the converted message to the player
                                commandSender.sendMessage(flightEnabledAlreadyOther);
                            }

                            // If the fetched player's flight is disabled
                            else {
                                // Notify command sender
                                // Converts the config message, to a formatted chat message
                                String flightEnabledOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_OTHER).replace("{player}", otherPlayer.getName());
                                String flightEnabledOther = ChatColor.translateAlternateColorCodes('&', flightEnabledOtherRaw);

                                // Sends the converted message to the player
                                commandSender.sendMessage(flightEnabledOther);
                                otherPlayer.setAllowFlight(true);
                            }

                        }

                    }

                    else {
                        // If the command sender has flight enabled
                        if (commandSender.getAllowFlight()) {

                            // Converts the config message, to a formatted chat message
                            String flightAlreadyEnabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ALREADY_ENABLED);
                            String flightAlreadyEnabled = ChatColor.translateAlternateColorCodes('&', flightAlreadyEnabledRaw);

                            // Sends the converted message to the player
                            commandSender.sendMessage(flightAlreadyEnabled);
                        }

                        // If the command sender has flight disabled
                        else {
                            // Enables the command sender's flight
                            commandSender.setAllowFlight(true);

                            // Converts the config message, to a formatted chat message
                            String flightEnabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED);
                            String flightEnabled = ChatColor.translateAlternateColorCodes('&', flightEnabledRaw);

                            // Sends the converted message to the player
                            commandSender.sendMessage(flightEnabled);
                        }
                    }
                }

                // If the command sender doesn't have the permission node "flight.use"
                else {

                    // Converts the config message, to a formatted chat message
                    String noPermissionRaw = main.getSettings().getProperty(Config.MESSAGES_NO_PERMISSION);
                    String noPermission = ChatColor.translateAlternateColorCodes('&', noPermissionRaw);

                    // Sends the converted message to the player
                    commandSender.sendMessage(noPermission);

                }

            }

            // If the 1st supplied argument is "off" (Ignoring case)
            else if (args[0].toLowerCase().equals("off")) {

                // If the command sender has the permission node "flight.use"
                if(commandSender.hasPermission("flight.use")) {

                    if(args.length >= 2 && commandSender.hasPermission("flight.others")) {

                        Player otherPlayer = Bukkit.getPlayer(args[1]);

                        // If the provided player couldn't be fetched
                        if (otherPlayer == null) {

                            // Converts the config message, to a formatted chat message
                            String playerNotFoundRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NOT_FOUND).replace("{username}", args[1]);
                            String playerNotFound = ChatColor.translateAlternateColorCodes('&', playerNotFoundRaw);

                            // Sends the converted message to the console
                            commandSender.sendMessage(playerNotFound);
                        }

                        // If the provided player was fetched successfully
                        else {

                            // If the amount of supplied arguments is greater than 1 (Silent possibility)
                            if (args.length >= 3) {

                                // If the 3rd argument is "-s" (For silent flight toggle)
                                if (args[2].toLowerCase().equals("-s")) {
                                    // Don't notify target player

                                    // If the fetched player's flight is enabled
                                    if(!otherPlayer.getAllowFlight()) {

                                        // Converts the config message, to a formatted chat message
                                        String flightDisabledAlreadyOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ALREADY_DISABLED_OTHER).replace("{player}", otherPlayer.getName());
                                        String flightDisabledAlreadyOther = ChatColor.translateAlternateColorCodes('&', flightDisabledAlreadyOtherRaw);

                                        // Sends the converted message to the player
                                        commandSender.sendMessage(flightDisabledAlreadyOther);

                                    }

                                    // If the fetched player's flight is disabled
                                    else {

                                        // Converts the config message, to a formatted chat message
                                        String flightDisabledOtherSilentRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_OTHER_SILENT).replace("{player}", otherPlayer.getName());
                                        String flightDisabledOtherSilent = ChatColor.translateAlternateColorCodes('&', flightDisabledOtherSilentRaw);

                                        // Sends the converted message to the command sender
                                        commandSender.sendMessage(flightDisabledOtherSilent);
                                        otherPlayer.setAllowFlight(false);

                                    }
                                    return true;
                                }

                                // If the 2nd argument isn't "-s" (Not silent)
                                else {

                                    // Notify target player
                                    // Converts the config message, to a formatted chat message
                                    String flightDisabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_EXTERNAL).replace("{other}", commandSender.getName());
                                    String flightDisabledExternal = ChatColor.translateAlternateColorCodes('&', flightDisabledExternalRaw);

                                    // Sends the converted message to the player
                                    otherPlayer.sendMessage(flightDisabledExternal);
                                }
                            }

                            // If there is no 3rd argument (not silent)
                            else {

                                if(otherPlayer.getAllowFlight()) {
                                    // Notify target Player
                                    // Converts the config message, to a formatted chat message
                                    String flightDisabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_EXTERNAL).replace("{other}", commandSender.getName());
                                    String flightDisabledExternal = ChatColor.translateAlternateColorCodes('&', flightDisabledExternalRaw);

                                    // Sends the converted message to the player
                                    otherPlayer.sendMessage(flightDisabledExternal);
                                }

                            }

                            // If the fetched player's flight is enabled
                            if(!otherPlayer.getAllowFlight()) {
                                // Notify command sender
                                // Converts the config message, to a formatted chat message
                                String flightDisabledAlreadyOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ALREADY_DISABLED_OTHER).replace("{player}", otherPlayer.getName());
                                String flightDisabledAlreadyOther = ChatColor.translateAlternateColorCodes('&', flightDisabledAlreadyOtherRaw);

                                // Sends the converted message to the player
                                commandSender.sendMessage(flightDisabledAlreadyOther);
                            }

                            // If the fetched player's flight is disabled
                            else {
                                // Notify command sender
                                // Converts the config message, to a formatted chat message
                                String flightDisabledOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_OTHER).replace("{player}", otherPlayer.getName());
                                String flightDisabledOther = ChatColor.translateAlternateColorCodes('&', flightDisabledOtherRaw);

                                // Sends the converted message to the player
                                commandSender.sendMessage(flightDisabledOther);
                                otherPlayer.setAllowFlight(false);
                            }

                        }

                    }


                    else {

                        // If the command sender has flight disabled
                        if (!commandSender.getAllowFlight()) {

                            // Converts the config message, to a formatted chat message
                            String flightAlreadyDisabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ALREADY_DISABLED);
                            String flightAlreadyDisabled = ChatColor.translateAlternateColorCodes('&', flightAlreadyDisabledRaw);

                            // Sends the converted message to the player
                            commandSender.sendMessage(flightAlreadyDisabled);
                        }

                        // If the command sender has flight enabled
                        else {
                            // Disables the command sender's flight
                            commandSender.setAllowFlight(false);

                            // Converts the config message, to a formatted chat message
                            String flightDisabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED);
                            String flightDisabled = ChatColor.translateAlternateColorCodes('&', flightDisabledRaw);

                            // Sends the converted message to the player
                            commandSender.sendMessage(flightDisabled);
                        }
                    }

                }

                // If the command sender doesn't have the permission node "flight.use"
                else {

                    // Converts the config message, to a formatted chat message
                    String noPermissionRaw = main.getSettings().getProperty(Config.MESSAGES_NO_PERMISSION);
                    String noPermission = ChatColor.translateAlternateColorCodes('&', noPermissionRaw);

                    // Sends the converted message to the player
                    commandSender.sendMessage(noPermission);

                }
            }

            // If the 1st supplied argument is "reload" or "rl" (Ignoring case)
            else if((args[0].toLowerCase().equals("reload") || args[0].toLowerCase().equals("rl"))) {

                // If command sender has the permission node "flight.check"
                if(commandSender.hasPermission("flight.reload")) {

                    // Fetches updated configuration (updates the cached config)
                    main.getSettings().reload();

                    // Converts the config message, to a formatted chat message
                    String configReloadedRaw = main.getSettings().getProperty(Config.MESSAGES_CONFIG_RELOADED);
                    String configReloaded = ChatColor.translateAlternateColorCodes('&', configReloadedRaw);

                    // Sends the converted message to the player
                    commandSender.sendMessage(configReloaded);
                }

                // If command sender doesn't have the permission node "flight.check"
                else {

                    // Converts the config message, to a formatted chat message
                    String noPermissionRaw = main.getSettings().getProperty(Config.MESSAGES_NO_PERMISSION);
                    String noPermission = ChatColor.translateAlternateColorCodes('&', noPermissionRaw);

                    // Sends the converted message to the player
                    commandSender.sendMessage(noPermission);
                }

            }

            // If the 1st supplied argument is invalid
            else {

                // Converts the config message, to a formatted chat message
                String unknownCommandRaw = main.getSettings().getProperty(Config.MESSAGES_UNKNOWN_COMMAND);
                String unknownCommand = ChatColor.translateAlternateColorCodes('&', unknownCommandRaw);

                // Sends the converted message to the player
                commandSender.sendMessage(unknownCommand);

            }

        }

        // If command sender is console
        else {

            // Casts console to console command sender
            ConsoleCommandSender console = getServer().getConsoleSender();

            // If there were no supplied arguments
            if (args.length <= 0) {
                
                // Processes the configured help message to 1 string/chat message
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

                // If arguments length is equal to or longer than 2
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

                        // Converts the config message, to a formatted chat message
                        String flightStateRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_STATE).replace("{state}", String.valueOf(otherPlayer.getAllowFlight())).replace("{player}", args[1]);
                        String flightState = ChatColor.translateAlternateColorCodes('&', flightStateRaw);

                        // Sends the converted message to the console
                        console.sendMessage(flightState);
                    }
                }

                // If arguments length is smaller than 2
                else {

                    // Converts the config message, to a formatted chat message
                    String playerNameRequiredRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NAME_REQUIRED);
                    String playerNameRequired = ChatColor.translateAlternateColorCodes('&', playerNameRequiredRaw);

                    // Sends the converted message to the console
                    console.sendMessage(playerNameRequired);

                }
            }

            // If the 1st supplied argument is "toggle" (Ignoring case)
            else if (args[0].toLowerCase().equals("toggle")) {

                // If the amount of arguments supplied with the command is great than or equal to 2 (player provided)
                if (args.length >= 2) {

                    Player otherPlayer = Bukkit.getPlayer(args[1]);

                    // Converts the config message, to a formatted chat message
                    String serverNameRaw = main.getSettings().getProperty(Config.MESSAGES_SERVER_NAME);
                    String serverName = ChatColor.translateAlternateColorCodes('&', serverNameRaw);

                    // If the provided player couldn't be fetched
                    if (otherPlayer == null) {

                        // Converts the config message, to a formatted chat message
                        String playerNotFoundRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NOT_FOUND).replace("{username}", args[1]);
                        String playerNotFound = ChatColor.translateAlternateColorCodes('&', playerNotFoundRaw);

                        // Sends the converted message to the console
                        console.sendMessage(playerNotFound);
                    }

                    // If the player is exempt from flight toggle
                    else if(otherPlayer.hasPermission("flight.toggle-exempt")) {

                        // Converts the config message, to a formatted chat message
                        String exemptUserRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_EXEMPT).replace("{player}", args[1]);
                        String exemptUser = ChatColor.translateAlternateColorCodes('&', exemptUserRaw);

                        // Sends the converted message to the console
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
                                    // Don't notify target player

                                    // Notify console
                                    // Converts the config message, to a formatted chat message
                                    String flightEnabledOtherSilentRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_OTHER_SILENT).replace("{player}", otherPlayer.getName());
                                    String flightEnabledOtherSilent = ChatColor.translateAlternateColorCodes('&', flightEnabledOtherSilentRaw);

                                    // Sends the converted message to the console
                                    console.sendMessage(flightEnabledOtherSilent);
                                    return true;
                                }

                                // If the 2nd argument isn't "-s"
                                else {

                                    // Notify target player
                                    // Converts the config message, to a formatted chat message
                                    String flightEnabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_EXTERNAL).replace("{other}", serverName);
                                    String flightEnabledExternal = ChatColor.translateAlternateColorCodes('&', flightEnabledExternalRaw);

                                    // Sends the converted message to the target player
                                    otherPlayer.sendMessage(flightEnabledExternal);
                                }
                            }

                            // If there is no 3rd argument
                            else {

                                // Notify target Player
                                // Converts the config message, to a formatted chat message
                                String flightEnabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_EXTERNAL).replace("{other}", serverName);
                                String flightEnabledExternal = ChatColor.translateAlternateColorCodes('&', flightEnabledExternalRaw);

                                // Sends the converted message to the target player
                                otherPlayer.sendMessage(flightEnabledExternal);

                            }

                            // Notify command sender
                            // Converts the config message, to a formatted chat message
                            String flightEnabledOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_OTHER).replace("{player}", otherPlayer.getName());
                            String flightEnabledOther = ChatColor.translateAlternateColorCodes('&', flightEnabledOtherRaw);

                            // Sends the converted message to the command sender
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

                                    // Notify console
                                    // Converts the config message, to a formatted chat message
                                    String flightDisabledOtherSilentRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_OTHER_SILENT).replace("{player}", otherPlayer.getName());
                                    String flightDisabledOtherSilent = ChatColor.translateAlternateColorCodes('&', flightDisabledOtherSilentRaw);

                                    // Sends the converted message to the console
                                    console.sendMessage(flightDisabledOtherSilent);
                                    return true;
                                }
                                else {

                                    // Notify target player
                                    // Converts the config message, to a formatted chat message
                                    String flightDisabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_EXTERNAL).replace("{other}", serverName);
                                    String flightDisabledExternal = ChatColor.translateAlternateColorCodes('&', flightDisabledExternalRaw);

                                    // Sends the converted message to the target player
                                    otherPlayer.sendMessage(flightDisabledExternal);
                                }
                            }

                            // If there is no 3rd argument
                            else {

                                // Notify target player
                                // Converts the config message, to a formatted chat message
                                String flightDisabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_EXTERNAL).replace("{other}", serverName);
                                String flightDisabledExternal = ChatColor.translateAlternateColorCodes('&', flightDisabledExternalRaw);

                                // Sends the converted message to the target player
                                otherPlayer.sendMessage(flightDisabledExternal);

                            }

                            // Notify command sender
                            // Converts the config message, to a formatted chat message
                            String flightDisabledOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_OTHER).replace("{player}", otherPlayer.getName());
                            String flightDisabledOther = ChatColor.translateAlternateColorCodes('&', flightDisabledOtherRaw);

                            // Sends the converted message to the console
                            console.sendMessage(flightDisabledOther);
                        }
                    }

                }

                // If the 2nd supplied argument doesn't exist (Not player name provided)
                else {

                    // Converts the config message, to a formatted chat message
                    String playerNameRequiredRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NAME_REQUIRED);
                    String playerNameRequired = ChatColor.translateAlternateColorCodes('&', playerNameRequiredRaw);

                    // Sends the converted message to the console
                    console.sendMessage(playerNameRequired);

                }

            }

            // if the 1st supplied argument is "reload" or "rl" (Ignoring case)
            else if(args[0].toLowerCase().equals("reload") || args[0].toLowerCase().equals("rl")) {

                // Reloads updates cached configuration
                main.getSettings().reload();

                // Converts the config message, to a formatted chat message
                String configReloadedRaw = main.getSettings().getProperty(Config.MESSAGES_CONFIG_RELOADED);
                String configReloaded = ChatColor.translateAlternateColorCodes('&', configReloadedRaw);

                // Sends the converted message to the console
                console.sendMessage(configReloaded);

            }

            // If the 1st supplied argument is invalid
            else {

                // Converts the config message, to a formatted chat message
                String unknownCommandRaw = main.getSettings().getProperty(Config.MESSAGES_UNKNOWN_COMMAND);
                String unknownCommand = ChatColor.translateAlternateColorCodes('&', unknownCommandRaw);

                // Sends the converted message to the console
                console.sendMessage(unknownCommand);

            }

        }

        return true;
    }
}
