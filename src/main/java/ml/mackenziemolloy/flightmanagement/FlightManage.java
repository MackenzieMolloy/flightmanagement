package ml.mackenziemolloy.flightmanagement;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FlightManage {

    private final Main main;

    public FlightManage(final Main main, Player commandSender, Player targetPlayer, String action, boolean silent) {
        this.main = main;
        flightManage(commandSender, targetPlayer, action, silent);
    }

    public void flightManage(Player commandSender, Player targetPlayer, String action, boolean silent) {

        if(commandSender == null) {
            return;
        }

        if(targetPlayer == null) {
            targetPlayer = commandSender;
        }

        if(action.toLowerCase().equals("toggle")) {

            if (commandSender != targetPlayer && targetPlayer.hasPermission("flight.toggle-exempt")) {

                // Converts the config message, to a formatted chat message
                String playerExemptRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_EXEMPT).replace("{player}", targetPlayer.getName());
                String playerExempt = ChatColor.translateAlternateColorCodes('&', playerExemptRaw);

                // Sends the converted message to the player
                commandSender.sendMessage(playerExempt);

            }

            if (targetPlayer.getAllowFlight()) {

                targetPlayer.setAllowFlight(false);

                if(targetPlayer != commandSender) {

                    if (silent) {

                        // Converts the config message, to a formatted chat message
                        String flightDisabledOtherSilentRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_OTHER_SILENT).replace("{player}", targetPlayer.getName());
                        String flightDisabledOtherSilent = ChatColor.translateAlternateColorCodes('&', flightDisabledOtherSilentRaw);

                        // Sends the converted message to the player
                        commandSender.sendMessage(flightDisabledOtherSilent);

                    } else {

                        // Converts the config message, to a formatted chat message
                        String flightDisabledOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_OTHER).replace("{player}", targetPlayer.getName());
                        String flightDisabledOther = ChatColor.translateAlternateColorCodes('&', flightDisabledOtherRaw);

                        // Sends the converted message to the player
                        commandSender.sendMessage(flightDisabledOther);

                        // Converts the config message, to a formatted chat message
                        String flightDisabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED_EXTERNAL).replace("{other}", commandSender.getName());
                        String flightDisabledExternal = ChatColor.translateAlternateColorCodes('&', flightDisabledExternalRaw);

                        // Sends the converted message to the player
                        targetPlayer.sendMessage(flightDisabledExternal);

                    }
                }

                else {

                    // Converts the config message, to a formatted chat message
                    String flightDisabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_DISABLED);
                    String flightDisabled = ChatColor.translateAlternateColorCodes('&', flightDisabledRaw);

                    // Sends the converted message to the player
                    targetPlayer.sendMessage(flightDisabled);

                }
            }

            else {

                targetPlayer.setAllowFlight(true);

                if (targetPlayer != commandSender) {

                    if (silent) {

                        // Converts the config message, to a formatted chat message
                        String flightEnabledOtherSilentRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_OTHER_SILENT).replace("{player}", targetPlayer.getName());
                        String flightEnabledOtherSilent = ChatColor.translateAlternateColorCodes('&', flightEnabledOtherSilentRaw);

                        // Sends the converted message to the player
                        commandSender.sendMessage(flightEnabledOtherSilent);

                    } else {

                        // Converts the config message, to a formatted chat message
                        String flightEnabledOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_OTHER).replace("{player}", targetPlayer.getName());
                        String flightEnabledOther = ChatColor.translateAlternateColorCodes('&', flightEnabledOtherRaw);

                        // Sends the converted message to the player
                        commandSender.sendMessage(flightEnabledOther);

                        // Converts the config message, to a formatted chat message
                        String flightEnabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_EXTERNAL).replace("{other}", commandSender.getName());
                        String flightEnabledExternal = ChatColor.translateAlternateColorCodes('&', flightEnabledExternalRaw);

                        // Sends the converted message to the player
                        targetPlayer.sendMessage(flightEnabledExternal);

                    }
                }

                else {

                    // Converts the config message, to a formatted chat message
                    String flightEnabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED);
                    String flightEnabled = ChatColor.translateAlternateColorCodes('&', flightEnabledRaw);

                    // Sends the converted message to the player
                    commandSender.sendMessage(flightEnabled);

                }

            }
        }

        else if(action.toLowerCase().equals("on")) {

            if(targetPlayer.hasPermission("flight.toggle-exempt")) {
                if (targetPlayer.getAllowFlight()) {

                    if (commandSender != targetPlayer) {
                        // Converts the config message, to a formatted chat message
                        String flightEnabledAlreadyOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ALREADY_ENABLED_OTHER).replace("{player}", targetPlayer.getName());
                        String flightEnabledAlreadyOther = ChatColor.translateAlternateColorCodes('&', flightEnabledAlreadyOtherRaw);

                        // Sends the converted message to the player
                        commandSender.sendMessage(flightEnabledAlreadyOther);
                    } else {

                        // Converts the config message, to a formatted chat message
                        String flightEnabledAlreadyRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ALREADY_ENABLED);
                        String flightEnabledAlready = ChatColor.translateAlternateColorCodes('&', flightEnabledAlreadyRaw);

                        // Sends the converted message to the player
                        commandSender.sendMessage(flightEnabledAlready);

                    }

                }

                else {

                    targetPlayer.setAllowFlight(true);

                    if (commandSender != targetPlayer) {

                        if (silent) {

                            // Converts the config message, to a formatted chat message
                            String flightEnabledOtherSilentRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_OTHER_SILENT).replace("{player}", targetPlayer.getName());
                            String flightEnabledOtherSilent = ChatColor.translateAlternateColorCodes('&', flightEnabledOtherSilentRaw);

                            // Sends the converted message to the player
                            commandSender.sendMessage(flightEnabledOtherSilent);

                        } else {

                            // Converts the config message, to a formatted chat message
                            String flightEnabledOtherRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_OTHER).replace("{player}", targetPlayer.getName());
                            String flightEnabledOther = ChatColor.translateAlternateColorCodes('&', flightEnabledOtherRaw);

                            // Sends the converted message to the player
                            commandSender.sendMessage(flightEnabledOther);

                            // Converts the config message, to a formatted chat message
                            String flightEnabledExternalRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED_EXTERNAL).replace("{other}", commandSender.getName());
                            String flightEnabledExternal = ChatColor.translateAlternateColorCodes('&', flightEnabledExternalRaw);

                            // Sends the converted message to the player
                            targetPlayer.sendMessage(flightEnabledExternal);

                        }
                    }

                    else {

                        // Converts the config message, to a formatted chat message
                        String flightEnabledRaw = main.getSettings().getProperty(Config.MESSAGES_FLIGHT_ENABLED);
                        String flightEnabled = ChatColor.translateAlternateColorCodes('&', flightEnabledRaw);

                        // Sends the converted message to the player
                        targetPlayer.sendMessage(flightEnabled);

                    }
                }
            }

            else {

                // Converts the config message, to a formatted chat message
                String playerExemptRaw = main.getSettings().getProperty(Config.MESSAGES_PLAYER_EXEMPT).replace("{player}", targetPlayer.getName());
                String playerExempt = ChatColor.translateAlternateColorCodes('&', playerExemptRaw);

                // Sends the converted message to the player
                targetPlayer.sendMessage(playerExempt);

            }

        }

    }

}
