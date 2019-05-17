package vision.thomas.government;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Proposal {

    private Government plugin;

    private String command;

    private String reason;

    private Player proposer;

    private ArrayList<Player> votedYes;

    private ArrayList<Player> votedNo;

    public Proposal(Government plugin) {

        this.plugin = plugin;
        votedYes = new ArrayList<>();
        votedNo = new ArrayList<>();
    }

    public void cancelProposal(Player p) {

        //Since there is no vote, setting these to null SHOULD be harmless. If I get null pointers, then I know why. : )
        command = null;
        proposer = null;
        votedYes = null;
        votedNo = null;

        p.sendMessage(plugin.prefix + "Your proposal has been cancelled.");

    }

    public String getCommand() {

        return command;
    }

    public Player getProposer() {

        return proposer;
    }

    public ArrayList<Player> getVotedYes() {

        return votedYes;
    }

    public ArrayList<Player> getVotedNo() {

        return votedNo;
    }

    public void setCommand(String command) {

        this.command = command;
    }

    public void setProposer(Player proposer) {

        this.proposer = proposer;
    }

    public String getReason() {

        return reason;
    }

    public void setReason(String reason) {

        this.reason = reason;
    }

}