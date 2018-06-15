package main.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa a sessão, que é o elemento central do sistema, ela é composta de usuários
 * participantes e ideias, e possui quatro fases (Acolhimento, Brainstorm, Votação e Ranqueamento).
 * @author hugo
 */
public class Session {

  /**
   * Texto descritivo da sessão.
   */
  private String description;
  /**
   * Limite de votos que um participante pode dar em uma mesma sessão.
   */
  private Integer votingLimit;
  /**
   * Criador e anfitrião da sessão.
   */
  private User owner;
  /**
   * Lista de Participantes.
   */
  private List<User> participants;
  /**
   * Lista de Ideias.
   */
  private List<Idea> ideas;
  /**
   * Fase da sessão.
   */
  private SessionPhase phase;

  /**
   * Construtor para os objetos sessão, estes iniciam com um anfitrião, destrição e limites de votos
   * definidos, assim como as listas de participantes e ideias vazias, e na fase de acolhimento.
   * 
   * @param owner Anfitrião.
   * @param description Texto descritivo da sessão.
   * @param votingLimit Limite de votos para participante.
   */
  public Session(User owner, String description, Integer votingLimit) {
    this.owner = owner;
    this.description = description;
    this.votingLimit = votingLimit;

    phase = SessionPhase.WELCOME;
    ideas = new ArrayList<>();
    participants = new ArrayList<>();
  }

  /**
   * Método para passagem da sessão para a próxima fase.
   */
  public void nextPhase() {
    phase = phase.next();
  }

  public User getOwner() {
    return owner;
  }

  public SessionPhase getPhase() {
    return phase;
  }

  /**
   * Método para a adição de uma ideia, para tanto a sessão deve ainda estar na fase de brainstorm.
   * 
   * @param Idea Idéia a ser adicionada.
   */
  public void addIdea(Idea idea) {
    if (phase == SessionPhase.BRAINSTORM && participants.contains(idea.getAuthor())) {
      idea.setSession(this);
      ideas.add(idea);
    }
  }

  public Idea[] getIdeas() {
    return ideas.toArray(new Idea[ideas.size()]);
  }

  /**
   * Método para ranqueamento das ideias.
   * 
   * @return Arranjo com as ideias (com votos) ordenadas em ordem decrescente de votos
   */
  public Idea[] rankIdeas() {
    if (phase == SessionPhase.RANKING) {
      List<Idea> rankeredIdeas = new ArrayList<>();
      ideas.forEach(idea -> {
          if (idea.countVotes() != 0) {
            rankeredIdeas.add(idea);
          }
          });

      rankeredIdeas.sort((a, b) -> 
          b.countVotes() - a.countVotes());

      return rankeredIdeas.toArray(new Idea[rankeredIdeas.size()]);
    }
    return new Idea[0];
  }

  /**
   * Método para a adição de um participante à sessão. Para que isto ocorra esta deve estar na fase
   * de acolhimento.
   * 
   * @param user usuário a adicionar.
   */
  public void addParticipant(User user) {
    if (phase == SessionPhase.WELCOME && !participants.contains(user)) {
      participants.add(user);
    }
  }

  /**
   * Método para remoção de um participante.
   * 
   * @param user usuário a ser removido.
   */
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
