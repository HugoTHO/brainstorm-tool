package java;

import java.util.List;

public class Session {

  private String description;
  private Integer votingLimit;
  private User owner;
  private List<User> participants;
  private List<Idea> ideas;
  private SessionPhase phase;

  public void nextPhase() {
    // Ainda vazio
  }

  public SessionPhase getPhase() {
    return null;
  }

  public void addIdea(Idea idea) {
    // Ainda vazio
  }
  
  public Idea[] getIdeas() {
    return new Idea[0];
  }
  
  public Idea[] rankIdeas() {
    return new Idea[0];
  }
  
  public void addParticipant(User user) {
    // Ainda vazio
  }
  
  public void removeParticipant(User user) {
    // Ainda vazio
  }
  
  public User[] getParticipants() {
    return new User[0];
  }
  
  public Integer getVotingLimit() {
    return null;
  }
}
