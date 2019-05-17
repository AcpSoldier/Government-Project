package vision.thomas.government;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import vision.thomas.government.commands.helpers.CenteredMessage;

public class Announcement {

    private Government plugin;

    private Config config;

    private GovernmentManager governmentManager;

    private CenteredMessage centeredMessage = new CenteredMessage();

    public Announcement(Government plugin) {

        this.plugin = plugin;
        config = new Config(plugin);
        governmentManager = new GovernmentManager(plugin);
    }

    public void announceProposal(Player proposer, Proposal proposal) {

        Bukkit.broadcastMessage(plugin.fullLine);
        Bukkit.broadcastMessage(centeredMessage.get(plugin.mainColor + proposer.getName() + "[0] " + " has proposed a vote!"));
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Run command: " + plugin.highlightColor + "/" + proposal.getCommand()));
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Reason: " + plugin.mainColor + "'" + proposal.getReason() + "'"));

        TextComponent voteYesMessage = new TextComponent("                    CLICK TO VOTE YES");
        voteYesMessage.setColor(ChatColor.GREEN);
        voteYesMessage.setBold(true);
        voteYesMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/government vote yes [reason]").create()));
        voteYesMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/government vote yes"));

        TextComponent voteNoMessage = new TextComponent("                     CLICK TO VOTE NO");
        voteNoMessage.setColor(ChatColor.RED);
        voteNoMessage.setBold(true);
        voteNoMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/government vote no [reason]").create()));
        voteNoMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/government vote no"));

        for (Player player : Bukkit.getOnlinePlayers()) {

            player.spigot().sendMessage(voteYesMessage);
            player.spigot().sendMessage(voteNoMessage);
        }

        Bukkit.broadcastMessage(plugin.fullLine);

        playSoundIfEnabled(proposer, Sound.BLOCK_BEACON_ACTIVATE, 2.0F, 2.0F);
    }

    public void annouceElection(Player runner, boolean competition) {

        String positionType = "(Logic for what type of government Position)";
        String announcement = plugin.prefix + runner.getName() + "[getRespectLevel logic] " + " is running to be a " + positionType + "!";

        TextComponent voteYesMessage = new TextComponent(centeredMessage.get("CLICK TO VOTE YES"));
        voteYesMessage.setColor(ChatColor.GREEN);
        voteYesMessage.setBold(true);
        voteYesMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Vote to make " + runner.getName() + " the new " + positionType + ".").create()));
        voteYesMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/government vote yes"));

        TextComponent voteNoMessage = new TextComponent(centeredMessage.get("CLICK TO VOTE NO"));
        voteNoMessage.setColor(ChatColor.RED);
        voteNoMessage.setBold(true);
        voteNoMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Vote to make " + runner.getName()).create()));
        voteNoMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/government vote no"));

        if (competition) {

            String officeHolder = "[Logic for getting whoever is currently in office]";
            voteNoMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Vote to keep " + officeHolder + " as the " + positionType + ".").create()));
            announcement = plugin.prefix + runner.getName() + "[getRespectLevel logic] " + " is running to replace " + officeHolder + " as a " + positionType + "!";
        }

        Bukkit.broadcastMessage(announcement);

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.spigot().sendMessage(voteYesMessage);
            p.spigot().sendMessage(voteNoMessage);
        }
    }

    public void announceAppeal(Player p, String appeal) {

        Bukkit.broadcastMessage(plugin.prefix + " [" + governmentManager.getTypeOfGovLeader() + "] " + p.getName() + "'s public address: " + ChatColor.BLUE + appeal);
        playSoundIfEnabled(p, Sound.BLOCK_BEACON_ACTIVATE, 2.0F, 1.0F);
    }

    public void announceVote(Player voter, Proposal prop, String vote) {

        if (vote.equalsIgnoreCase("yes")) {
            Bukkit.broadcastMessage(plugin.prefix + voter.getName() + " voted " + ChatColor.GREEN + vote + plugin.defaultColor + " on the current proposal.");
            playSoundIfEnabled(voter, Sound.ENTITY_VILLAGER_YES, 2.0F, 1.0F);
        }
        else {
            Bukkit.broadcastMessage(plugin.prefix + voter.getName() + " voted " + ChatColor.RED + vote + plugin.defaultColor + " on the current proposal.");
            playSoundIfEnabled(voter, Sound.ENTITY_VILLAGER_NO, 2.0F, 1.0F);
        }
    }

    public void announceVote(Player voter, Proposal prop, String vote, String reason) {

        if (vote.equalsIgnoreCase("yes")) {

            Bukkit.broadcastMessage(plugin.prefix + voter.getName() + " voted " + ChatColor.GREEN + vote + plugin.defaultColor + " on the current proposal. Reason: " + ChatColor.WHITE + "" + ChatColor.ITALIC + "\"" + reason + "\".");
            playSoundIfEnabled(voter, Sound.ENTITY_VILLAGER_YES, 2.0F, 1.0F);
        }
        else {

            Bukkit.broadcastMessage(plugin.prefix + voter.getName() + " voted " + ChatColor.RED + vote + plugin.defaultColor + " on the current proposal. Reason: " + ChatColor.WHITE + "" + ChatColor.ITALIC + "\"" + reason + "\".");
            playSoundIfEnabled(voter, Sound.ENTITY_VILLAGER_NO, 2.0F, 1.0F);
        }
    }

    public void playSoundIfEnabled(Player player, Sound sound, float volume, float pitch) {

        if (config.isVoteSoundsForAll) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

                onlinePlayer.playSound(player.getLocation(), sound, volume, pitch);
            }
        }
        else if (config.isVoteSoundsForSelf) {
            player.playSound(player.getLocation(), sound, volume, pitch);
        }
    }

    public void announceProposalCancelation(Player canceler, Proposal proposal) {

        Bukkit.broadcastMessage(plugin.prefix + canceler.getName() + " has canceled the proposal /" + proposal.getCommand() + ".");
        playSoundIfEnabled(canceler, Sound.BLOCK_BEACON_DEACTIVATE, 2.0F, 2.0F);
    }

    public void announceProposalCancelation(Player canceler, Proposal proposal, String reason) {

        Bukkit.broadcastMessage(plugin.prefix + canceler.getName() + " has canceled the proposal /" + proposal.getCommand() + ". Reason: " + ChatColor.ITALIC + "" + ChatColor.WHITE + reason + ".");
        playSoundIfEnabled(canceler, Sound.BLOCK_BEACON_DEACTIVATE, 2.0F, 2.0F);
    }

}