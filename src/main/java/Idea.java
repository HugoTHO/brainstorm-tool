package main.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe que representa uma ideia gerada por um participante de uma sessão e que pode ser 
 * adicionada a esta ultima assim como receber votos. 
 * @author hugo
 */
public class Idea {

  /**
   * Texto descritivo da ideia
   */
  private String description;
  /**
   * Autor da ideia
   */
  private User author;
  /**
   * Sessão a que a ideia é vinculada
   */
  private Session session;
  /**
   * Lista de votantes
   */
  private List<User> voters;

  /**
   * Construtor para os objetos de Idea, nele definese o autor e descrição da ideia, assim como 
   * cria-se uma lista vazia de votos.
   * 
   * @param author O autor da ideia.
   * @param description Texto descritivo da ideia
   */
  public Idea(User author, String description) {
    this.author = author;
    this.description = description;

    voters = new ArrayList<>();
  }

  /**
   * Método para registro de um voto na ideia, para tanto adiciona-se o votante à lista de votantes
   * da ideia.
   * 
   * @param voter Votante a adicionar.
   */
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

  /**
   * Método para remoção de votos da ideia, para tanto, remove-se o votante da lista.
   * 
   * @param voter Votante a remover.
   */
  public void removeVote(User voter) {
    voters.remove(voter);
  }

  /**
   * Método para a obtenção da contagem de votos em uma ideia.
   * 
   * @return Contagem de votos.
   */
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
