package main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Idea {

  private String description;
  private User author;
  private Session session;
  private List<User> voters;
  
  public Idea(User author, String description) {
    this.author = author;
    this.description = description;
    
    voters = new ArrayList<>();
  }

  public void registerVote(User voter) {
    boolean isVotingPhase = (session.getPhase() == SessionPhase.VOTING);
    
    User[] participants = session.getParticipants();
    boolean isParticipant = Arrays.asList(participants).contains(voter);
    
    boolean isAuthor = (author == voter);
    
    boolean alreadyVoted = voters.contains(voter);
    
    int votesCount = 0;
    Idea[] ideas = session.getIdeas();
    for(Idea idea : ideas) {
      if (idea != this && idea.voters.contains(voter)) {
          votesCount++;
      }
    }
    boolean reachedLimit = (votesCount == session.getVotingLimit());
    
    if (isVotingPhase && isParticipant && !isAuthor && !alreadyVoted && !reachedLimit) {
      voters.add(voter);
    }
  }
  
  public void removeVote(User voter) {
    voters.remove(voter);
  }
  
  public Integer countVotes() {
    return voters.size();
  }

  public String getDescription() {
    return description;
  }

  public User getAuthor() {
    return author;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }
}
