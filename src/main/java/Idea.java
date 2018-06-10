package main.java;

import java.util.List;

public class Idea {

  private String description;
  private User author;
  private Session session;
  private List<User> voters;
  
  public Idea(User author, String description) {
    this.author = author;
    this.description = description;
  }

  public void registerVote(User voter) {
    // Ainda vazio
  }
  
  public void removeVote(User voter) {
    // Ainda vazio
  }
  
  public Integer countVotes() {
    return null;
  }

  public User getAuthor() {
    return author;
  }
}
