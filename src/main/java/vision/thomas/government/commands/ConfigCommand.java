package vision.thomas.government.commands;

import org.bukkit.command.CommandSender;
import vision.thomas.government.Config;
import vision.thomas.government.Government;
import vision.thomas.government.GovernmentManager;
import vision.thomas.government.commands.helpers.SubCommand;

public class ConfigCommand extends SubCommand {

    private final Government plugin;

    private GovernmentManager govManager;

    private Config config;

    public ConfigCommand(Government plugin) {

        super(plugin, plugin.getName().toLowerCase(), "config", "[enable | disable | reload | addLeader | removeLeader | setType]", "Allows the plugin configuration to be modified in-game.");
        this.plugin = plugin;
        config = plugin.getConf();
        govManager = plugin.getGovManager();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (args.length > 0) {

            if (args[0].equalsIgnoreCase("disable")) {
                if (!config.isPluginEnabled()) {
                    sender.sendMessage(plugin.prefix + "The plugin is already disabled.");
                }
                else {
                    config.setPluginEnabled(false);
                    sender.sendMessage(plugin.prefix + "Plugin disabled.");
                }
            }

            else if (args[0].equalsIgnoreCase("enable")) {
                if (config.isPluginEnabled()) {
                    sender.sendMessage(plugin.prefix + "The plugin is already enabled.");
                }
                else {
                    config.setPluginEnabled(true);
                    sender.sendMessage(plugin.prefix + "Plugin has been re-enabled.");
                }
            }

            else if (args[0].equalsIgnoreCase("reload")) {

                sender.sendMessage(plugin.prefix + "Retrieving latest values from config.yml...");
                config.reloadConfig();
                sender.sendMessage(plugin.prefix + "Configuration reloaded!");
            }

            else if (args[0].equalsIgnoreCase("addLeader")) {

                if (args.length > 1) {

                    String newLeader = args[1];

                    if (!config.getGovLeaders().contains(govManager.getGovLeaderId(newLeader))) {
                        govManager.addGovLeader(govManager.getGovLeaderId(newLeader));
                        sender.sendMessage(plugin.prefix + newLeader + " is now a " + govManager.getTypeOfGovLeader() + ".");
                    }
                    else {
                        sender.sendMessage(plugin.prefix + newLeader + " is already " + govManager.getTypeOfGovLeader() + ".");
                    }
                }
                else {
                    sender.sendMessage(plugin.prefix + "Please specify what player you would like to become a " + govManager.getTypeOfGovLeader() + ".");
                }
            }

            else if (args[0].equalsIgnoreCase("removeLeader")) {

                if (args.length > 1) {

                    String oldLeader = args[1];

                    if (config.getGovLeaders().contains(govManager.getGovLeaderId(oldLeader))) {

                        govManager.removeGovLeader(govManager.getGovLeaderId(oldLeader));
                        sender.sendMessage(plugin.prefix + oldLeader + " is no longer a " + govManager.getTypeOfGovLeader() + ".");
                    }
                    else {
                        sender.sendMessage(plugin.prefix + oldLeader + " is not a " + govManager.getTypeOfGovLeader() + " and can't be removed.");
                    }
                }
                else {
                    sender.sendMessage(plugin.prefix + "Please specify what player you would no longer like to be a " + govManager.getTypeOfGovLeader() + ".");
                }
            }

            else if (args[0].equalsIgnoreCase("setType")) {

                if (args.length > 1) {
                    sender.sendMessage(govManager.setGovType(Integer.parseInt(args[1])));
                }
                else {
                    sender.sendMessage(plugin.prefix + "Please specify what what type of government.");
                }
            }

            else if (args[0].equalsIgnoreCase("addcommand")) {

                if (args.length > 1) {

                    String command = "";
                    for (int i = 0; i < args.length; i++) {
                        if (i != 0) {
                            command += args[i] + " ";
                        }
                    }
                    command = command.substring(0, command.length() - 1);

                    if (!config.getAllowedCommands().contains(command.toLowerCase())) {

                        govManager.addAllowedCommand(command);
                        sender.sendMessage(plugin.prefix + "Proposals can now be created to run " + plugin.mainColor + "/" + command + ".");
                    }
                    else {
                        sender.sendMessage(plugin.prefix + "/" + command + " is already an allowed command.");
                    }
                }
                else {
                    sender.sendMessage(plugin.prefix + "Please specify what command you would like to add as a proposal.");
                }
            }
            else if (args[0].equalsIgnoreCase("removecommand")) {

                if (args.length > 1) {

                    String command = "";
                    for (int i = 0; i < args.length; i++) {
                        if (i != 0) {
                            command += args[i] + " ";
                        }
                    }
                    command = command.substring(0, command.length() - 1);

                    if (config.getAllowedCommands().contains(command.toLowerCase())) {

                        govManager.removeAllowedCommand(command);
                        sender.sendMessage(plugin.prefix + "Proposals can no longer be made for " + plugin.mainColor + "/" + command + ".");
                    }
                    else {
                        sender.sendMessage(plugin.prefix + plugin.mainColor + "/" + command + plugin.defaultColor + " is not an allowed command and therefore can't be removed.");
                    }
                }
                else {
                    sender.sendMessage(plugin.prefix + "Please specify what command you would like remove.");
                }
            }

            //
            // Everything below this line is just a placeholder.
            //

            else if (args[0].equalsIgnoreCase("autoUpdate")) {
                sender.sendMessage(plugin.prefix + "Logic for editing config file.");
            }
            else if (args[0].equalsIgnoreCase("repeatTime")) {
                sender.sendMessage(plugin.prefix + "Logic for editing config file.");
            }
            else if (args[0].equalsIgnoreCase("expireTime")) {
                sender.sendMessage(plugin.prefix + "Logic for editing config file.");
            }
            else if (args[0].equalsIgnoreCase("minimumOnlinePlayers")) {
                sender.sendMessage(plugin.prefix + "Logic for editing config file.");
            }
            else if (args[0].equalsIgnoreCase("passPercent")) {
                sender.sendMessage(plugin.prefix + "Logic for editing config file.");
            }
            else if (args[0].equalsIgnoreCase("maxTermLength")) {
                sender.sendMessage(plugin.prefix + "Logic for editing config file.");
            }
            else if (args[0].equalsIgnoreCase("minTermLength")) {
                sender.sendMessage(plugin.prefix + "Logic for editing config file.");
            }
            else if (args[0].equalsIgnoreCase("maxInOffice")) {
                sender.sendMessage(plugin.prefix + "Logic for editing config file.");
            }
            else if (args[0].equalsIgnoreCase("runDelayTime")) {
                sender.sendMessage(plugin.prefix + "Logic for editing config file.");
            }
            else if (args[0].equalsIgnoreCase("minrespect")) {
                if (args.length > 1) {
                    if (args.length > 2) {
                        sender.sendMessage(plugin.prefix + "Logic for checking if a command exists in the config. If it does, edit it's minimum Respect Level.");
                        sender.sendMessage(plugin.prefix + "The minimum Respect Level needed to run the command '" + args[1] + "' has been set to " + args[2] + ".");
                    }
                    else {
                        sender.sendMessage(plugin.prefix + "Logic for checking if a command exists in the config. If it does, edit it's minimum Respect Level.");
                        sender.sendMessage(plugin.prefix + "Please specify what you like the minimum Respect Level to be for the command: '" + args[1] + "'.");
                        return true; // To make sure that the message below doesn't run? I'll have to test that out later.
                    }
                }
                else {
                    sender.sendMessage(plugin.prefix + "Please specify what command you would like to configure Respect Level for.");
                }
            }
            else {
                sender.sendMessage(plugin.prefix + "Incorrect arguments.");
                sender.sendMessage(this.getUsage());
            }
        }
        else {
            sender.sendMessage(plugin.prefix + "Incorrect arguments.");
            sender.sendMessage(this.getUsage());
        }
        return true;
    }

}
