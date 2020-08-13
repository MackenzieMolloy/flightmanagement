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

    private final Main main;

    public Commands(final Main main) {
        this.main = main;
        main.getCommand("fly").setExecutor((CommandExecutor) this);
    }


    // On Command Event
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Checks if the person who sent the command, is a player
        if(sender instanceof Player) {

            // Casts the command sender to a player
            Player commandSender = (Player) sender;

            commandSender.setFoodLevel(10);

            // If no arguments are supplied with the sent command
            if (args.length <= 0) {
                List<String> invalidArgsRaw = main.getSettings().getProperty(Config.MESSAGES_HELP);
                String msg = "\n";

                for(int i = 0; i < invalidArgsRaw.size(); i++) {
                    msg = msg + invalidArgsRaw.get(i) + "\n";
                }

                String invalidArgs = ChatColor.translateAlternateColorCodes('&', msg);
                commandSender.sendMessage(invalidArgs);
                return true;
            }

            // If the command sender has the permission node "flight.use"
            else if (commandSender.hasPermission("flight.use") || commandSender.hasPermission("flight.check")) {

                // If the 1st supplied argument is "check" (Ignoring case)
                if(args[0].toLowerCase().equals("check")) {

                    // If command sender has the permission node "flight.check"
                    if(commandSender.hasPermission("flight.check")) {

                        // If arguments length is longer than 1
                        if (args.length >= 2) {

                            // Fetch player information
                            Player otherPlayer = Bukkit.getPlayer(args[1]);

                            // If the provided player couldn't be fetched
                            if (otherPlayer == null) {

                                String playerNotFoundRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NOT_FOUND).replace("{username}", args[1]);
                                String playerNotFound = ChatColor.translateAlternateColorCodes('&', playerNotFoundRaw);

                                commandSender.sendMessage(playerNotFound);
                            }

                            // If the provided player was fetched successfully
                            else {

                                String flightStateRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_STATE).replace("{state}", String.valueOf(otherPlayer.getAllowFlight())).replace("{player}", args[1]);
                                String flightState = ChatColor.translateAlternateColorCodes('&', flightStateRaw);

                                commandSender.sendMessage(flightState);

                            }

                        }

                        else {

                            String playerNameRequiredRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NAME_REQUIRED);
                            String playerNameRequired = ChatColor.translateAlternateColorCodes('&', playerNameRequiredRaw);

                            commandSender.sendMessage(playerNameRequired);

                        }
                    }

                    else {

                        String noPermissionRaw = main.getSettings().getProperty(Config.MESSAGES_NO_PERMISSION);
                        String noPermission = ChatColor.translateAlternateColorCodes('&', noPermissionRaw);

                        commandSender.sendMessage(noPermission);
                    }

                }

                // If the 1st supplied argument is "toggle" (Ignoring case)
                else if (args[0].toLowerCase().equals("toggle")) {

                    // If the amount of arguments supplied with the command is equal to 1 (No player username provided) or the command sender doesn't have the permission node "flight.others"
                    if (args.length == 1 || !commandSender.hasPermission("flight.others")) {

                        // If the command sender's flight is disabled
                        if (commandSender.getAllowFlight() == false) {

                            // Enables command sender's flight
                            commandSender.setAllowFlight(true);

                            String flightEnabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED);
                            String flightEnabled = ChatColor.translateAlternateColorCodes('&', flightEnabledRaw);

                            commandSender.sendMessage(flightEnabled);
                        }

                        // If the command sender's flight is enabled
                        else {

                            // Disables command sender's flight
                            commandSender.setAllowFlight(false);

                            String flightDisabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED);
                            String flightDisabled = ChatColor.translateAlternateColorCodes('&', flightDisabledRaw);

                            commandSender.sendMessage(flightDisabled);
                        }

                    }

                    // If the amount of arguments supplied with the command is not equal to 1 (Player username provided)
                    else {

                        // Fetches the provided player username
                        Player otherPlayer = Bukkit.getPlayer(args[1]);

                        // If the provided player couldn't be fetched
                        if (otherPlayer == null) {

                            String playerNotFoundRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_NOT_FOUND).replace("{username}", args[1]);
                            String playerNotFound = ChatColor.translateAlternateColorCodes('&', playerNotFoundRaw);

                            commandSender.sendMessage(playerNotFound);
                        }

                        // If the player is exempt from flight toggle
                        else if(otherPlayer.hasPermission("flight.toggle-exempt")) {

                            String exemptUserRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_EXEMPT).replace("{player}", args[1]);
                            String exemptUser = ChatColor.translateAlternateColorCodes('&', exemptUserRaw);

                            commandSender.sendMessage(exemptUser);

                        }

                        // If the provided player was fetched successfully
                        else {

                            // If the fetched player's flight is disabled
                            if (otherPlayer.getAllowFlight() == false) {

                                // Enables provided player's flight
                                otherPlayer.setAllowFlight(true);

                                // If the amount of supplied arguments is greater than 1
                                if(args.length >= 3) {

                                    // If the 3rd argument is "-s" (For silent flight toggle)
                                    if(args[2].toLowerCase().equals("-s")) {
                                        // Don't notify player
                                    }

                                    // If the 2nd argument isn't "-s"
                                    else {

                                        // Notify target player
                                        String flightEnabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_EXTERNAL).replace("{other}", commandSender.getName());
                                        String flightEnabledExternal = ChatColor.translateAlternateColorCodes('&', flightEnabledExternalRaw);

                                        otherPlayer.sendMessage(flightEnabledExternal);
                                    }
                                }

                                // If there is no 3rd argument
                                else {

                                    // Notify target Player
                                    String flightEnabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_EXTERNAL).replace("{other}", commandSender.getName());
                                    String flightEnabledExternal = ChatColor.translateAlternateColorCodes('&', flightEnabledExternalRaw);

                                    otherPlayer.sendMessage(flightEnabledExternal);

                                }

                                // Notify command sender
                                String flightEnabledOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_OTHER).replace("{player}", otherPlayer.getName());
                                String flightEnabledOther = ChatColor.translateAlternateColorCodes('&', flightEnabledOtherRaw);

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
                                    }
                                    else {

                                        // Notify target player
                                        String flightDisabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_EXTERNAL).replace("{other}", commandSender.getName());
                                        String flightDisabledExternal = ChatColor.translateAlternateColorCodes('&', flightDisabledExternalRaw);

                                        otherPlayer.sendMessage(flightDisabledExternal);
                                    }
                                }

                                // If there is no 3rd argument
                                else {

                                    // Notify target player
                                    String flightDisabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_EXTERNAL).replace("{other}", commandSender.getName());
                                    String flightDisabledExternal = ChatColor.translateAlternateColorCodes('&', flightDisabledExternalRaw);

                                    otherPlayer.sendMessage(flightDisabledExternal);

                                }

                                // Notify command sender
                                String flightDisabledOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_OTHER).replace("{player}", otherPlayer.getName());
                                String flightDisabledOther = ChatColor.translateAlternateColorCodes('&', flightDisabledOtherRaw);

                                commandSender.sendMessage(flightDisabledOther);
                            }
                        }

                    }
                }

                // If the 1st supplied argument is "on" (Ignoring case)
                else if (args[0].toLowerCase().equals("on")) {

                    // If the command sender has flight enabled
                    if (commandSender.getAllowFlight() == true) {

                        String flightAlreadyEnabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ALREADY_ENABLED);
                        String flightAlreadyEnabled = ChatColor.translateAlternateColorCodes('&', flightAlreadyEnabledRaw);

                        commandSender.sendMessage(flightAlreadyEnabled);
                    }

                    // If the command sender has flight disabled
                    else {
                        // Enables the command sender's flight
                        commandSender.setAllowFlight(true);

                        String flightEnabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED);
                        String flightEnabled = ChatColor.translateAlternateColorCodes('&', flightEnabledRaw);

                        commandSender.sendMessage(flightEnabled);
                    }
                }

                // If the 1st supplied argument is "off" (Ignoring case)
                else if (args[0].toLowerCase().equals("off")) {

                    // If the command sender has flight disabled
                    if (commandSender.getAllowFlight() == false) {

                        String flightAlreadyDisabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ALREADY_DISABLED);
                        String flightAlreadyDisabled = ChatColor.translateAlternateColorCodes('&', flightAlreadyDisabledRaw);

                        commandSender.sendMessage(flightAlreadyDisabled);
                    }

                    // If the command sender has flight enabled
                    else {
                        // Disables the command sender's flight
                        commandSender.setAllowFlight(false);

                        String flightDisabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED);
                        String flightDisabled = ChatColor.translateAlternateColorCodes('&', flightDisabledRaw);

                        commandSender.sendMessage(flightDisabled);
                    }
                }

                else if(args[0].toLowerCase().equals("reload") || args[0].toLowerCase().equals("rl")) {

                    if(commandSender.hasPermission("flight.reload")) {

                        main.getSettings().reload();

                        String configReloadedRaw = main.getSettings().getProperty(Config.MESSAGES_CONFIG_RELOADED);
                        String configReloaded = ChatColor.translateAlternateColorCodes('&', configReloadedRaw);

                        commandSender.sendMessage(configReloaded);

                    }

                    else {

                        String noPermissionRaw = main.getSettings().getProperty(Config.MESSAGES_NO_PERMISSION);
                        String noPermission = ChatColor.translateAlternateColorCodes('&', noPermissionRaw);

                        commandSender.sendMessage(noPermission);

                    }

                }

                else {

                    String unknownCommandRaw = main.getSettings().getProperty(Config.MESSAGES_UNKNOWN_COMMAND);
                    String unknownCommand = ChatColor.translateAlternateColorCodes('&', unknownCommandRaw);

                    commandSender.sendMessage(unknownCommand);

                }
            }

            // If command sender doesn't have the permission node "flight.use"
            else {

                String noPermissionRaw = main.getSettings().getProperty(Config.MESSAGES_NO_PERMISSION);
                String noPermission = ChatColor.translateAlternateColorCodes('&', noPermissionRaw);

                commandSender.sendMessage(noPermission);
            }
        }

        // If command sender is console
        else {

            ConsoleCommandSender console = getServer().getConsoleSender();

            if (args.length <= 0) {
                List<String> invalidArgsRaw = main.getSettings().getProperty(Config.MESSAGES_HELP);
                String msg = "\n";

                for(int i = 0; i < invalidArgsRaw.size(); i++) {
                    msg = msg + invalidArgsRaw.get(i) + "\n";
                }

                String invalidArgs = ChatColor.translateAlternateColorCodes('&', msg);
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
                        if (otherPlayer.getAllowFlight() == false) {

                            // Enables provided player's flight
                            otherPlayer.setAllowFlight(true);

                            // If the amount of supplied arguments is greater than 1
                            if(args.length >= 3) {

                                // If the 3rd argument is "-s" (For silent flight toggle)
                                if(args[2].toLowerCase().equals("-s")) {
                                    // Don't notify player
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
