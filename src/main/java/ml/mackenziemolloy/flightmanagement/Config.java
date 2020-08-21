package ml.mackenziemolloy.flightmanagement;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;

import java.util.List;

import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class Config implements SettingsHolder {

    // Sets-up all the needed configuration elements
    @Comment("Message sent to player when they don't provide any arguments")
    public static final Property<List<String>> MESSAGES_HELP =
            newListProperty("messages.help", "&7", "&6&lFlight Management ([] - optional)", "&7", "&e/fly toggle [player] [-s]&f toggles your (or others) fly", "&e/fly on [player] [-s]&f turns on your fly", "&e/fly off [player] [-s]&fturns your fly off", "&e/fly check (username) &freturns player's flight state","&7");

    @Comment("Message sent to player when they don't have permission")
    public static final Property<String> MESSAGES_NO_PERMISSION =
            newProperty("messages.no_permission", "&cYou do not have permission");

    @Comment("Message sent to player when they enable their flight")
    public static final Property<String> MESSAGES_FLIGHT_ENABLED =
            newProperty("messages.flight_enabled", "&fYour flight has been &aenabled&f.");

    @Comment("Message sent to player when they disable their flight")
    public static final Property<String> MESSAGES_FLIGHT_DISABLED =
            newProperty("messages.flight_disabled", "&fYour flight has been &cdisabled&f.");

    @Comment("Message sent to player when their flight was enabled by another player")
    public static final Property<String> MESSAGES_FLIGHT_ENABLED_EXTERNAL =
            newProperty("messages.flight_enabled_external", "&fYour flight has been &aenabled&f by {other}.");

    @Comment("Message sent to player when they enabled another player's flight")
    public static final Property<String> MESSAGES_FLIGHT_ENABLED_OTHER =
            newProperty("messages.flight_enabled_other", "&fEnabled {player}'s flight");

    @Comment("Message sent to player when they try to enabled another player's flight when it's already enabled")
    public static final Property<String> MESSAGES_FLIGHT_ALREADY_ENABLED_OTHER =
            newProperty("messages.flight_already_enabled_other", "&e{player}'s &fflight is already &aenabled&f!");

    @Comment("Message sent to player when they enabled another player's flight silently")
    public static final Property<String> MESSAGES_FLIGHT_ENABLED_OTHER_SILENT =
            newProperty("messages.flight_enabled_other_silent", "&7(SILENT) &fEnabled {player}'s flight");

    @Comment("Message sent to player when their flight was disabled by another player")
    public static final Property<String> MESSAGES_FLIGHT_DISABLED_EXTERNAL =
            newProperty("messages.flight_disabled_external", "&fYour flight has been &cdisabled&f by {other}.");

    @Comment("Message sent to player when they disabled another player's flight")
    public static final Property<String> MESSAGES_FLIGHT_DISABLED_OTHER =
            newProperty("messages.flight_disabled_other", "&fDisabled {player}'s flight");

    @Comment("Message sent to player when they try to enabled another player's flight when it's already enabled")
    public static final Property<String> MESSAGES_FLIGHT_ALREADY_DISABLED_OTHER =
            newProperty("messages.flight_already_disabled_other", "&e{player}'s &fflight is already &cdisabled&f!");

    @Comment("Message sent to player when they disabled another player's flight silently")
    public static final Property<String> MESSAGES_FLIGHT_DISABLED_OTHER_SILENT =
            newProperty("messages.flight_disabled_other_silent", "&7(SILENT) &fDisabled {player}'s flight");

    @Comment("Message sent to player when player couldn't be found")
    public static final Property<String> MESSAGES_PLAYER_NOT_FOUND =
            newProperty("messages.player_not_found", "Couldn't find player with username {username}");

    @Comment("Message sent to player when their flight is already enabled")
    public static final Property<String> MESSAGES_FLIGHT_ALREADY_ENABLED =
            newProperty("messages.flight_already_enabled", "Silly Billy! Your flight is already enabled");

    @Comment("Message sent to player when their flight is already disabled")
    public static final Property<String> MESSAGES_FLIGHT_ALREADY_DISABLED =
            newProperty("messages.flight_already_disabled", "Silly Billy! Your flight is already disabled");

    @Comment("Message sent to player when they reload the config")
    public static final Property<String> MESSAGES_CONFIG_RELOADED =
            newProperty("messages.config_reloaded", "&6FlightManagement&f config has been &ereloaded!");

    @Comment("The server's name when command are run through console")
    public static final Property<String> MESSAGES_SERVER_NAME =
            newProperty("messages.server_name", "CONSOLE");

    @Comment("Message sent to console when console sends toggle command without player argument")
    public static final Property<String> MESSAGES_PLAYER_NAME_REQUIRED =
            newProperty("messages.player_name_required", "You need to provide a player username.");

    @Comment("Message sent to player when they send an unknown command")
    public static final Property<String> MESSAGES_UNKNOWN_COMMAND =
            newProperty("messages.unknown_command", "&fUnknown sub-command, type &e/fly&f for help.");

    @Comment("Message sent to player when they try to toggle an except user's flight")
    public static final Property<String> MESSAGES_PLAYER_EXEMPT =
            newProperty("messages.player_exempt", "&e{player} &fis exempt from external flight toggle");

    @Comment("Message sent to player when they check a user's flight state")
    public static final Property<String> MESSAGES_FLIGHT_STATE =
            newProperty("messages.flight_state", "&e{player} &fhas flight &e{state}");

}
