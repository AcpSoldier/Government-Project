package vision.thomas.government;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import vision.thomas.government.commands.helpers.CenteredMessage;

public class Announcement {

    private Government plugin;

    private Config config;

    private GovernmentManager governmentManager;

    private VoteManager voteManager;

    private CenteredMessage centeredMessage = new CenteredMessage();

    public Announcement(Government plugin) {

        this.plugin = plugin;
        config = new Config(plugin);
        voteManager = new VoteManager(plugin);
        governmentManager = new GovernmentManager(plugin);
    }

    public void announceProposal(Player proposer, Proposal proposal) {

        playSoundIfEnabled(proposer, Sound.BLOCK_BEACON_ACTIVATE, 2.0F, 2.0F);

        Bukkit.broadcastMessage(plugin.fullLine);
        Bukkit.broadcastMessage(centeredMessage.get(plugin.mainColor + proposer.getName() + "[0] " + " has proposed a vote!"));
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Run command: " + plugin.highlightColor + proposal.getCommand()));
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Reason: " + plugin.mainColor + "'" + proposal.getReason() + "'"));

        if (config.getGovType() == 1) {
            Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "You must be a " + governmentManager.getTypeOfGovLeader() + " to vote."));
        }

        TextComponent voteYesMessage = new TextComponent("      CLICK TO VOTE YES");
        voteYesMessage.setColor(ChatColor.GREEN);
        voteYesMessage.setBold(true);
        voteYesMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/gov vote yes [reason]").create()));
        voteYesMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gov vote yes"));

        TextComponent mediator = new TextComponent("   -   ");
        mediator.setColor(ChatColor.WHITE);
        mediator.setBold(true);

        TextComponent voteNoMessage = new TextComponent("CLICK TO VOTE NO");
        voteNoMessage.setColor(ChatColor.RED);
        voteNoMessage.setBold(true);
        voteNoMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/gov vote no [reason]").create()));
        voteNoMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gov vote no"));

        for (Player player : Bukkit.getOnlinePlayers()) {

            player.spigot().sendMessage(voteYesMessage, mediator, voteNoMessage);
        }

        Bukkit.broadcastMessage(plugin.fullLine);

    }

    public void announceElection(Player proposer, Player elected) {

        playSoundIfEnabled(proposer, Sound.BLOCK_BEACON_ACTIVATE, 2.0F, 2.0F);

        Bukkit.broadcastMessage(plugin.fullLine);
        Bukkit.broadcastMessage(centeredMessage.get(plugin.mainColor + proposer.getDisplayName() + "[0]" + " has nominated " + plugin.highlightColor + elected.getDisplayName() + plugin.mainColor + "[0] to be a " + governmentManager.getTypeOfGovLeader() + "!"));
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Current amount of " + governmentManager.getTypeOfGovLeader() + "s: " + config.getGovLeaders().size() + "."));
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Maximum amount of " + governmentManager.getTypeOfGovLeader() + "s: " + config.getMaxLeaders() + "."));
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "If elected, " + elected.getDisplayName() + " will not replace any current " + governmentManager.getTypeOfGovLeader() + "s."));
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Maximum term length: " + config.getElegantTermLength(config.getMaxTermLengthInMinutes())) + ".");
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Safety time: " + config.getElegantTermLength(config.getMinTermLengthInMinutes())) + ".");

        TextComponent voteYesMessage = new TextComponent("      CLICK TO VOTE YES");
        voteYesMessage.setColor(ChatColor.GREEN);
        voteYesMessage.setBold(true);
        voteYesMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/gov vote yes [reason]").create()));
        voteYesMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gov vote yes"));

        TextComponent mediator = new TextComponent("   -   ");
        mediator.setColor(ChatColor.WHITE);
        mediator.setBold(true);

        TextComponent voteNoMessage = new TextComponent("CLICK TO VOTE NO");
        voteNoMessage.setColor(ChatColor.RED);
        voteNoMessage.setBold(true);
        voteNoMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/gov vote no [reason]").create()));
        voteNoMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gov vote no"));

        for (Player player : Bukkit.getOnlinePlayers()) {

            player.spigot().sendMessage(voteYesMessage, mediator, voteNoMessage);
        }
        Bukkit.broadcastMessage(plugin.fullLine);
    }

    public void announceElectionCompetition(Player proposer, Player elected, Player competitor) {

        playSoundIfEnabled(proposer, Sound.BLOCK_BEACON_ACTIVATE, 2.0F, 2.0F);

        Bukkit.broadcastMessage(plugin.fullLine);
        Bukkit.broadcastMessage(centeredMessage.get(plugin.mainColor + proposer.getDisplayName() + "[0]" + " has nominated " + plugin.highlightColor + elected.getDisplayName() + plugin.mainColor + "[0] to be a " + governmentManager.getTypeOfGovLeader() + "!"));
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Current amount of " + governmentManager.getTypeOfGovLeader() + "s: " + config.getGovLeaders().size() + "."));
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Maximum amount of " + governmentManager.getTypeOfGovLeader() + "s: " + config.getMaxLeaders() + "."));
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "If elected, " + elected.getDisplayName() + " will replace " + competitor.getDisplayName() + "."));
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Maximum term length: " + config.getElegantTermLength(config.getMaxTermLengthInMinutes())) + ".");
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Safety time: " + config.getElegantTermLength(config.getMinTermLengthInMinutes())) + ".");

        TextComponent voteYesMessage = new TextComponent("      CLICK TO VOTE YES");
        voteYesMessage.setColor(ChatColor.GREEN);
        voteYesMessage.setBold(true);
        voteYesMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/gov vote yes [reason]").create()));
        voteYesMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gov vote yes"));

        TextComponent mediator = new TextComponent("   -   ");
        mediator.setColor(ChatColor.WHITE);
        mediator.setBold(true);

        TextComponent voteNoMessage = new TextComponent("CLICK TO VOTE NO");
        voteNoMessage.setColor(ChatColor.RED);
        voteNoMessage.setBold(true);
        voteNoMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("/gov vote no [reason]").create()));
        voteNoMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gov vote no"));

        for (Player player : Bukkit.getOnlinePlayers()) {

            player.spigot().sendMessage(voteYesMessage, mediator, voteNoMessage);
        }
        Bukkit.broadcastMessage(plugin.fullLine);
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

        if (config.isVoteSoundsForAll()) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

                onlinePlayer.playSound(player.getLocation(), sound, volume, pitch);
            }
        }
        else if (config.isVoteSoundsForSelf()) {
            player.playSound(player.getLocation(), sound, volume, pitch);
        }
    }

    public void announceProposalCancelation(Player canceler, Proposal proposal) {

        Bukkit.broadcastMessage(plugin.prefix + canceler.getName() + " has canceled the proposal " + proposal.getCommand() + ".");
        playSoundIfEnabled(canceler, Sound.BLOCK_BEACON_DEACTIVATE, 2.0F, 2.0F);
    }

    public void announceProposalCancelation(Player canceler, Proposal proposal, String reason) {

        Bukkit.broadcastMessage(plugin.prefix + canceler.getName() + " has canceled the proposal " + proposal.getCommand() + ". Reason: " + ChatColor.ITALIC + "" + ChatColor.WHITE + reason + ".");
        playSoundIfEnabled(canceler, Sound.BLOCK_BEACON_DEACTIVATE, 2.0F, 2.0F);
    }

    public void announceVoteTime(int timeLeftInSeconds) {

        String announceTime;

        if (timeLeftInSeconds >= 60) {
            announceTime = (timeLeftInSeconds / 60 + (timeLeftInSeconds / 60 != 1 ? " minutes" : " minute"));
        }
        else {
            announceTime = (timeLeftInSeconds + (timeLeftInSeconds != 1 ? " seconds" : " second"));
        }

        Bukkit.broadcastMessage(plugin.fullLine);
        Bukkit.broadcastMessage(centeredMessage.get(plugin.mainColor + "Only " + plugin.highlightColor + announceTime + plugin.mainColor + " left to vote on the current proposal!"));
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Run command: " + plugin.highlightColor + voteManager.getCurrentProposal().getCommand()));
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Reason: " + plugin.mainColor + "'" + voteManager.getCurrentProposal().getReason() + "'"));
        Bukkit.broadcastMessage(plugin.fullLine);

        for (Player player : Bukkit.getOnlinePlayers()) {
            playSoundIfEnabled(player, Sound.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, 2.0F, 2.0F);
        }

    }

    public void announceProposalResults(boolean passed, float percentYes, float percentNo, int votesYes, int votesNo, String reasonFailed) {

        Bukkit.broadcastMessage(plugin.fullLine);
        if (passed) {
            Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Proposal to run the command"));
            Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + plugin.highlightColor + voteManager.getCurrentProposal().getCommand() + plugin.defaultColor + " has " + ChatColor.GREEN + "" + ChatColor.BOLD + "PASSED!"));
            delayedAnnouncement("" + plugin.prefix + plugin.highlightColor + "Executing proposal...", 40, false);

            for (Player player : Bukkit.getOnlinePlayers()) {
                playSoundIfEnabled(player, Sound.BLOCK_BEACON_POWER_SELECT, 2.0F, 2.0F);
            }
        }
        else {
            Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Proposal to run the command"));
            Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + plugin.highlightColor + voteManager.getCurrentProposal().getCommand() + plugin.defaultColor + " has " + ChatColor.RED + "" + ChatColor.BOLD + "FAILED!"));
            for (Player player : Bukkit.getOnlinePlayers()) {
                playSoundIfEnabled(player, Sound.BLOCK_BEACON_DEACTIVATE, 2.0F, 2.0F);
            }
        }
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Voted yes: " + ChatColor.GREEN + "" + ChatColor.BOLD + percentYes + "%" + plugin.defaultColor + " Voted no: " + ChatColor.RED + "" + ChatColor.BOLD + percentNo + "%" + plugin.defaultColor + ". Total votes: " + ChatColor.BOLD + (votesYes + votesNo + ".")));

        if (reasonFailed.length() > 1) {
            Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + reasonFailed));
        }
        Bukkit.broadcastMessage(plugin.fullLine);
        voteManager.setVoteCountInProgress(false);
    }

    public void announceElectionResults(boolean passed, float percentYes, float percentNo, int votesYes, int votesNo, String reasonFailed) {

        String nominated = voteManager.getCurrentProposal().getNominated().getDisplayName();
        Player competitor = voteManager.getCurrentProposal().getCompetitor();

        Bukkit.broadcastMessage(plugin.fullLine);
        if (passed) {
            Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Proposal to elect " + plugin.highlightColor + nominated + plugin.defaultColor + " has " + ChatColor.GREEN + "" + ChatColor.BOLD + "PASSED!"));
            if (competitor != null) {
                Bukkit.broadcastMessage(centeredMessage.get(plugin.mainColor + nominated + " won!"));
            }
            delayedAnnouncement("" + plugin.prefix + plugin.highlightColor + "Putting " + nominated + " in office as a " + governmentManager.getTypeOfGovLeader() + "...", 40, false);
            for (Player player : Bukkit.getOnlinePlayers()) {
                playSoundIfEnabled(player, Sound.BLOCK_BEACON_POWER_SELECT, 2.0F, 2.0F);
            }
        }
        else {
            Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Proposal to elect " + plugin.highlightColor + nominated + plugin.defaultColor + " to be a " + plugin.highlightColor + governmentManager.getTypeOfGovLeader() + plugin.defaultColor + " has " + ChatColor.RED + "" + ChatColor.BOLD + "FAILED!"));
            if (competitor != null) {
                Bukkit.broadcastMessage(centeredMessage.get(plugin.mainColor + competitor.getDisplayName() + " won!"));
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                playSoundIfEnabled(player, Sound.BLOCK_BEACON_DEACTIVATE, 2.0F, 2.0F);
            }
        }

        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Voted yes: " + ChatColor.GREEN + "" + ChatColor.BOLD + percentYes + "%" + plugin.defaultColor + " Voted no: " + ChatColor.RED + "" + ChatColor.BOLD + percentNo + "%" + plugin.defaultColor + ". Total votes: " + ChatColor.BOLD + (votesYes + votesNo + ".")));

        if (reasonFailed.length() > 1) {
            Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + reasonFailed));
        }
        Bukkit.broadcastMessage(plugin.fullLine);
        voteManager.setVoteCountInProgress(false);
    }

    public void announceVoteCount() {

        int votesYes = voteManager.getCurrentProposal().getVotedYes().size();
        int votesNo = voteManager.getCurrentProposal().getVotedNo().size();
        int abstentions = Bukkit.getOnlinePlayers().size() - (votesYes + votesNo);

        if (config.getGovType() == 1) {
            abstentions = config.getGovLeaders().size() - (votesYes + votesNo);
        }

        Bukkit.broadcastMessage(plugin.fullLine);
        Bukkit.broadcastMessage(centeredMessage.get(plugin.prefix + plugin.defaultColor + "Counting votes..."));
        Bukkit.broadcastMessage(plugin.fullLine);

        for (Player player : Bukkit.getOnlinePlayers()) {
            playSoundIfEnabled(player, Sound.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, 2.0F, 2.0F);
        }

        delayedAnnouncement("" + ChatColor.GREEN + ChatColor.BOLD + "Number of votes in favor: " + votesYes, 15, false);
        delayedAnnouncement("" + ChatColor.RED + ChatColor.BOLD + "Number of votes against: " + votesNo, 30, false);
        delayedAnnouncement("" + ChatColor.YELLOW + ChatColor.BOLD + "Number of abstentions: " + abstentions, 45, false);
        delayedAnnouncement("" + ChatColor.WHITE + ChatColor.BOLD + "Percentage needed to pass: " + config.getPercentNeededToPass() + "%", 60, false);
        delayedAnnouncement("" + ChatColor.WHITE + ChatColor.BOLD + "Minimum votes required to pass: " + config.getMinVotesRequired(), 75, true);
    }

    private void delayedAnnouncement(String announcement, int delay, boolean lastAnnouncement) {

        new BukkitRunnable() {

            public void run() {

                for (Player player : Bukkit.getOnlinePlayers()) {
                    playSoundIfEnabled(player, Sound.BLOCK_METAL_HIT, 2.0F, 2.0F);
                }

                Bukkit.broadcastMessage(centeredMessage.get(announcement));

                if (lastAnnouncement) {
                    if (voteManager.getCurrentProposal().getNominated() != null) {
                        voteManager.countVotes(true);
                    }
                    else {
                        voteManager.countVotes(false);
                    }
                }
                cancel();
            }
        }.runTaskLater(plugin, delay);
    }

    public void announceDictatedProposal(Proposal proposal) {

        Bukkit.broadcastMessage(plugin.fullLine);

        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + voteManager.getCurrentProposal().getProposer().getDisplayName() + " (Dictator) has issued a decree"));
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Run command: " + plugin.highlightColor + proposal.getCommand()));
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Reason: " + plugin.mainColor + "'" + proposal.getReason() + "'"));
        delayedAnnouncement("" + plugin.prefix + plugin.highlightColor + "Executing proposal...", 40, false);

        for (Player player : Bukkit.getOnlinePlayers()) {
            playSoundIfEnabled(player, Sound.BLOCK_BEACON_POWER_SELECT, 2.0F, 2.0F);
        }
        Bukkit.broadcastMessage(plugin.fullLine);

    }

    public void announceElectionVoteCount() {

        int votesYes = voteManager.getCurrentProposal().getVotedYes().size();
        int votesNo = voteManager.getCurrentProposal().getVotedNo().size();
        int abstentions = Bukkit.getOnlinePlayers().size() - (votesYes + votesNo);

        Bukkit.broadcastMessage(plugin.fullLine);
        Bukkit.broadcastMessage(centeredMessage.get(plugin.prefix + plugin.defaultColor + "Counting votes..."));
        Bukkit.broadcastMessage(plugin.fullLine);

        for (Player player : Bukkit.getOnlinePlayers()) {
            playSoundIfEnabled(player, Sound.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, 2.0F, 2.0F);
        }

        delayedAnnouncement("" + ChatColor.GREEN + ChatColor.BOLD + "Number of votes in favor: " + votesYes, 15, false);
        delayedAnnouncement("" + ChatColor.RED + ChatColor.BOLD + "Number of votes against: " + votesNo, 30, false);
        delayedAnnouncement("" + ChatColor.YELLOW + ChatColor.BOLD + "Number of abstentions: " + abstentions, 45, false);
        delayedAnnouncement("" + ChatColor.WHITE + ChatColor.BOLD + "Percentage needed to pass: " + config.getPercentNeededToPass() + "%", 60, false);
        delayedAnnouncement("" + ChatColor.WHITE + ChatColor.BOLD + "Minimum votes required to pass: " + config.getMinVotesRequired(), 75, true);
    }

    public void announceElectionVoteTime(int timeLeftInSeconds) {

        String announceTime;
        String nominated = voteManager.getCurrentProposal().getNominated().getDisplayName();
        Player competitor = voteManager.getCurrentProposal().getCompetitor();

        if (timeLeftInSeconds >= 60) {
            announceTime = (timeLeftInSeconds / 60 + (timeLeftInSeconds / 60 != 1 ? " minutes" : " minute"));
        }
        else {
            announceTime = (timeLeftInSeconds + (timeLeftInSeconds != 1 ? " seconds" : " second"));
        }

        Bukkit.broadcastMessage(plugin.fullLine);
        Bukkit.broadcastMessage(centeredMessage.get(plugin.mainColor + "Only " + plugin.highlightColor + announceTime + plugin.mainColor + " left to vote in the election!"));
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Elect " + plugin.highlightColor + nominated + plugin.defaultColor + " to be a " + plugin.highlightColor + governmentManager.getTypeOfGovLeader() + plugin.defaultColor + "."));
        if (competitor != null) {
            Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "(" + nominated + " would replace " + competitor.getDisplayName() + ")"));
        }
        Bukkit.broadcastMessage(centeredMessage.get(plugin.defaultColor + "Reason: " + plugin.mainColor + "'" + voteManager.getCurrentProposal().getReason() + "'"));
        Bukkit.broadcastMessage(plugin.fullLine);

        for (Player player : Bukkit.getOnlinePlayers()) {
            playSoundIfEnabled(player, Sound.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, 2.0F, 2.0F);
        }
    }

}