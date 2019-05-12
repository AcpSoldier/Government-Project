package vision.thomas.government.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vision.thomas.government.Announcement;
import vision.thomas.government.Government;
import vision.thomas.government.VoteManager;
import vision.thomas.government.commands.helpers.SubCommand;

public class VoteCommand extends SubCommand {

    private Government plugin;

    private VoteManager voteManager;

    private Announcement announcement;

    public VoteCommand(Government plugin) {

        super(plugin, plugin.getName().toLowerCase(), "vote", "[yes | no]", "Allows players to vote on an active proposal or election.");
        this.plugin = plugin;
        voteManager = new VoteManager(plugin);
        announcement = new Announcement(plugin);
    }

    public boolean execute(CommandSender sender, String[] args) {

        if (sender instanceof Player) {
            if (args.length > 0) {

                Player voter = (Player) sender;

                if (voteManager.voteInProgress) {
                    if (!voteManager.getCurrentProposal().votedNo.contains(voter) && !voteManager.getCurrentProposal().votedYes.contains(voter)) {
                        if (args[0].equalsIgnoreCase("yes")) {
                            voteManager.castVote(voter, voteManager.getCurrentProposal(), args[0]);

                            if (args.length > 1) {
                                announcement.announceVote(voter, voteManager.getCurrentProposal(), args[0], args.toString().replace(args[0], ""));
                            }
                            else {
                                announcement.announceVote(voter, voteManager.getCurrentProposal(), args[0]);
                            }
                        }
                        else if (args[0].equalsIgnoreCase("no")) {

                            voteManager.castVote(voter, voteManager.getCurrentProposal(), args[0]);

                            if (args.length > 1) {
                                announcement.announceVote(voter, voteManager.getCurrentProposal(), args[0], args.toString().replace(args[0], ""));
                            }
                            else {
                                announcement.announceVote(voter, voteManager.getCurrentProposal(), args[0]);
                            }
                        }
                        else {
                            sender.sendMessage(plugin.prefix + "Please cast your vote as 'yes' or 'no'.");
                            sender.sendMessage(this.getUsage());
                        }
                    }
                    else {
                        voter.sendMessage(plugin.prefix + "You have already voted on this proposal.");
                    }
                }
                else {
                    voter.sendMessage(plugin.prefix + "There is currently no active proposal to vote on.");
                }
            }
            else {
                sender.sendMessage(plugin.prefix + "Please cast your vote as 'yes' or 'no'.");
                sender.sendMessage(this.getUsage());
            }
        }
        else {
            sender.sendMessage(plugin.prefix + "Only players can vote on proposals, sorry console : ( ");
        }
        return true;
    }

}
