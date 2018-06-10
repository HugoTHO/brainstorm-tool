package main.java;

import java.util.ArrayList;
import java.util.List;

public class Session {

  private String description;
  private Integer votingLimit;
  private User owner;
  private List<User> participants;
  private List<Idea> ideas;
  private SessionPhase phase;
  
  public Session(User owner, String description, Integer votingLimit) {
    this.owner = owner;
    this.description = description;
    this.votingLimit = votingLimit;
    
    phase = SessionPhase.WELCOME;
    ideas = new ArrayList<>();
    participants = new ArrayList<>();
  }

  public void nextPhase() {
    phase = phase.next();
  }
  
  public User getOwner() {
    return owner;
  }

  public SessionPhase getPhase() {
    return phase;
  }

  public void addIdea(Idea idea) {
    if (phase == SessionPhase.BRAINSTORM && participants.contains(idea.getAuthor())) {
      idea.setSession(this);
      ideas.add(idea);
    }
  }
  
  public Idea[] getIdeas() {
    return ideas.toArray(new Idea[ideas.size()]);
  }
  
  public Idea[] rankIdeas() {
    return new Idea[0];
  }
  
  public void addParticipant(User user) {
    if (phase == SessionPhase.WELCOME && !participants.contains(user)) {
      participants.add(user);
    }
  }
  
  public void removeParticipant(User user) {
    participants.remove(user);
  }
  
  public User[] getParticipants() {
    return participants.toArray(new User[participants.size()]);
  }
  
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getVotingLimit() {
    return votingLimit;
  }
}
