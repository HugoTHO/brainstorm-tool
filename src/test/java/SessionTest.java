package test.java;

import main.java.Idea;
import main.java.Session;
import main.java.SessionPhase;
import main.java.User;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Classe de testes unitários para a classe Session.
 * @author hugo
 */
public class SessionTest {

  /**
   * Lista de usuários para os testes
   */
  List<User> users;
  /**
   * Lista de ideias para os testes
   */
  List<Idea> ideas;
  /**
   * Objeto sessão para realização dos testes
   */
  Session session;

  /**
   * Método de pré-configuração para os testes, neste cria-se e popula as listas de usuários e de
   * ideias, assim como cria-se a sessão para teste.
   */
  @Before
  public void setUp() {
    users = new ArrayList<>();
    users.add(new User("Tester1"));
    users.add(new User("Tester2"));
    users.add(new User("Tester3"));
    users.add(new User("Tester4"));
    
    ideas = new ArrayList<>();
    ideas.add(new Idea(users.get(0), "Ideia 1 para teste"));
    ideas.add(new Idea(users.get(1), "Ideia 2 para teste"));
    ideas.add(new Idea(users.get(2), "Ideia 3 para teste"));
    ideas.add(new Idea(users.get(1), "Ideia 4 para teste"));
    
    session = new Session(users.get(0), "Teste", 3);
  }

  /**
   * Testa para o construtor da Sessão, nele testa-se a correta configuração do objeto após sua
   * construção: Deve ter um autor, estar na fase de acolhimento, e ter as lista de participantes e
   * ideias vazias.
   */
  @Test
  public void creation() {
    // Quando a Sessão é criada
    session = new Session(users.get(0), "Teste", 3);
    
    // O proprietário deve ser o mesmo do que foi passado na sua criação (construtor)
    assertEquals(users.get(0), session.getOwner());
    
    // Ela deve se encontrar na fase de acolhimento de participantes
    assertEquals(SessionPhase.WELCOME, session.getPhase());
    
    // A lista de ideias deve estar vazia
    assertEquals(0, session.getIdeas().length);
    
    // A lista de participantes deve estar vazia
    assertEquals(0, session.getParticipants().length);
  }
  
  /**
   * Teste para a passagem de fase da sessão, que basicamente testa se o fluxo de fases está 
   * funcionando como deve: Acolhimento -> Brainstorm -> Votação -> Ranqueamento.
   */
  @Test
  public void nextPhase() {
    // Ao ir para a próxima fase
    // Se estiver na fase de acolhimento, a sessão passa para a fase de brainstorm
    session.nextPhase();
    assertEquals(SessionPhase.BRAINSTORM, session.getPhase());
    
    // Se estiver na fase de brainstorm, a sessão passa para a fase de votação
    session.nextPhase();
    assertEquals(SessionPhase.VOTING, session.getPhase());
    
    // Se estiver na fase de votação, a sessão passa para a fase de ranqueamento
    session.nextPhase();
    assertEquals(SessionPhase.RANKING, session.getPhase());
    
    // Se estiver na fase de ranqueamento, não ocorre mais nada 
    session.nextPhase();
    assertEquals(SessionPhase.RANKING, session.getPhase());
  }
  
  /**
   * Teste para adição de participantes, que testa as seguintes regras de negócio: A adição só pode
   * ocorrer se a sessão estiver na fase de acolhimento, e o participante não já estiver na lista.
   */
  @Test
  public void addParticipant() {
    // Quando se tenta registrar um novo participante (usuário)
    // A lista de participantes é inalterada se a sessão não estiver na fase de acolhimento
    session.nextPhase(); // BRAINSTORM
    session.addParticipant(users.get(0));
    assertNotEquals(SessionPhase.WELCOME, session.getPhase());
    assertEquals(0, session.getParticipants().length);
    
    // A lista de participantes é inalterada se o usuário já estiver registrado como participante
    session = new Session(users.get(0), "Teste", 10);
    session.addParticipant(users.get(0));
    assertEquals(1, session.getParticipants().length);
    session.addParticipant(users.get(0));
    assertEquals(1, session.getParticipants().length);
    
    // Caso contrário, o usuário passa a fazer parte da lista de participants e o tamanho da lista
    // é incremantado de 1
    session.addParticipant(users.get(1));
    assertEquals(2, session.getParticipants().length);
  }
  
  /**
   * Teste para remoção de participantes, que testa as seguintes regras de negócio: a remoção só 
   * pode ocorrer se o participante estiver na lista.
   */
  @Test
  public void remoreParticipant() {
    session.addParticipant(users.get(0));
    
    // Quando se tenta remover um participante (Usuário)
    // A lista de participantes é inalterada se dado usuário não é um dos participantes
    User[] participants = session.getParticipants();
    session.removeParticipant(users.get(1));
    assertArrayEquals(participants, session.getParticipants());
    
    // Caso contrário o usuário deixa de fazer parte da lista de participantes e o tamanho da lista
    // é decrementada de 1
    session.removeParticipant(users.get(0));
    assertEquals(0, session.getParticipants().length);
  }

  /**
   * Teste para a adição de ideias, nele testa-se as seguintes regras de negócio: a ideia só pode
   * ser adicionada se a sessão estiver em fase de brainstorm e o autor deve ser participante da
   * sessão.
   */
  @Test
  public void addIdea() {
    session.addParticipant(users.get(0));
    
    // Quando se tenta registrar uma nova ideia...
    // A lista de ideias é inalterada se a sessão não estiver na fase de brainstorm
    assertNotEquals(SessionPhase.BRAINSTORM, session.getPhase());
    session.addIdea(ideas.get(0));
    assertEquals(0, session.getIdeas().length);

    // A lista de ideias é inalterada se o autor da ideia não for um dos participantes da sessão
    session.nextPhase(); // BRAINSTORM
    session.addIdea(ideas.get(1));
    assertEquals(0, session.getIdeas().length);
    
    // Caso contrário, a ideia passa a fazer parte da lista de ideias e o tamanho da lista é
    // incrementado de 1
    session.addParticipant(users.get(1));
    session.addIdea(ideas.get(0));
    assertEquals(1, session.getIdeas().length);
  }
  
  /**
   * Teste para ranqueamento das ideias, nele testa-se as seguintes regras de negócio: A conferencia
   * dos votos só pode ocorrer se a sessão estiver na fase de ranqueamento e a lista de ranqueamento
   * deve estar ordenada e não conter ideias com nenhum voto.
   */
  @Test
  public void rankIdeas() {
    
    session.addParticipant(users.get(0));
    session.addParticipant(users.get(1));
    session.addParticipant(users.get(2));
    session.addParticipant(users.get(3));
    
    session.nextPhase(); // BRAINSTORM
    session.addIdea(ideas.get(0));
    session.addIdea(ideas.get(1));
    session.addIdea(ideas.get(2));
    session.addIdea(ideas.get(3));
    
    session.nextPhase(); // VOTING
    ideas.get(0).registerVote(users.get(1));
    ideas.get(2).registerVote(users.get(1));
    ideas.get(2).registerVote(users.get(3));
    ideas.get(3).registerVote(users.get(2));
    ideas.get(3).registerVote(users.get(3));
    ideas.get(3).registerVote(users.get(0));
    
    
    // Quando se consulta o ranking das ideias...
    // O ranqueamento não é efetuado se a sessão não estiver na fase de ranqueamento
    assertNotEquals(SessionPhase.BRAINSTORM, session.getPhase());
    assertEquals(0, session.rankIdeas().length);
    
    // São retornadas apenas as ideias com pelo menos um voto em ordem decrescente de número de 
    // votos
    session.nextPhase(); // RANKING
    Idea[] expectedArray = {ideas.get(3), ideas.get(2), ideas.get(0)};
    assertArrayEquals(expectedArray, session.rankIdeas());
    
    // Caso lista de ideia sem nenhum voto
    session = new Session(users.get(0), "Teste", 3);
    
    session.addParticipant(users.get(0));
    
    session.nextPhase(); // BRAINSTORM
    session.addIdea(new Idea(users.get(0), "Ideia para teste vazio"));
    
    session.nextPhase(); // VOTING
    session.nextPhase(); // RANKING
    expectedArray = new Idea[0];
    assertArrayEquals(expectedArray, session.rankIdeas());
    
  }
}
