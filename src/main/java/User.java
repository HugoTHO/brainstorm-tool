package main.java;

/**
 * Classe que representa um usuário para o sistema de brainstorm.
 * 
 * @author hugo
 */
public class User {

  /**
   * Nome do usuário
   */
  private String username;

  /**
   * Número de votos do usuário
   */
  private Integer numVotes;

  /**
   * Construtor para objetos do tipo usuário. Estes deve ter um nome definido.
   * 
   * @param username Nome do usuário
   */
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
