package main.java;

public class User {

  private String username;

  private Integer numVotes;

  public User(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getNumVotes() {
    return numVotes;
  }

  public void setNumVotes(Integer numVotes) {
    this.numVotes = numVotes;
  }
}
