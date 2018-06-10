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

public class SessionTest {

  List<User> users;
  List<Idea> ideas;
  Session session;

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
  
  @Test
  public void addParticipant() {
    // Quando se tenta registrar um novo participante (usuário)
    // A lista de participantes é inalterada se a sessão não estiver na fase de acolhimento
    session.nextPhase();
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

  @Test
  public void addIdea() {
    session.addParticipant(users.get(0));
    
    // Quando se tenta registrar uma nova ideia...
    // A lista de ideias é inalterada se a sessão não estiver na fase de brainstorm
    assertNotEquals(SessionPhase.BRAINSTORM, session.getPhase());
    session.addIdea(ideas.get(0));
    assertEquals(0, session.getIdeas().length);

    // A lista de ideias é inalterada se o autor da ideia não for um dos participantes da sessão
    session.nextPhase();
    assertEquals(SessionPhase.BRAINSTORM, session.getPhase());
    session.addIdea(ideas.get(1));
    assertEquals(0, session.getIdeas().length);
    
    // Caso contrário, a ideia passa a fazer parte da lista de ideias e o tamanho da lista é
    // incrementado de 1
    session.addParticipant(users.get(1));
    session.addIdea(ideas.get(0));
    assertEquals(1, session.getIdeas().length);
  }
  
  @Test
  public void rankIdeas() {
    
    
    // Quando se consulta o ranking das ideias...
    // O ranqueamento não é efetuado se a sessão não estiver na fase de ranqueamento
    assertEquals(0, session.rankIdeas().length);
    
    // São retornadas apenas as ideias com pelo menos um voto em ordem decrescente de número de 
    // votos
  }
}
