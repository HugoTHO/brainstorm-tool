package test.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import main.java.Idea;
import main.java.Session;
import main.java.SessionPhase;
import main.java.User;

public class IdeaTest {

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
    users.add(new User("Tester5"));
    
    ideas = new ArrayList<>();
    ideas.add(new Idea(users.get(0), "Ideia 1 para teste"));
    ideas.add(new Idea(users.get(1), "Ideia 2 para teste"));
    
    session = new Session(users.get(0), "Teste", 1);
    
    session.addParticipant(users.get(0));
    session.addParticipant(users.get(1));
    session.addParticipant(users.get(2));
    session.addParticipant(users.get(3));
    
    session.nextPhase();
    
    session.addIdea(ideas.get(0));
    session.addIdea(ideas.get(1));
  }

  @Test
  public void creation() {
    // Quando uma ideia é criada
    Idea idea = new Idea(users.get(0), "Ideia para teste");
    
    // Seu autor deve ser o mesmo do que foi passado na sua criação (construtor)
    assertEquals(users.get(0), idea.getAuthor());
    
    // A lista de votantes na ideia deve estar vazia
    assertEquals(0, idea.countVotes().intValue());
  }

  @Test
  public void registerVote() {
    // Quando se tenta votar em uma ideia...
    // Os votos permanecem inalterados se a sessão não estiver na fase de votação
    assertNotEquals(SessionPhase.VOTING, session.getPhase());
    ideas.get(0).registerVote(users.get(1));
    assertEquals(0, ideas.get(0).countVotes().intValue());
    
    // Os votos permanecem inalterados se o votante não estiver participando da sessão
    session.nextPhase();
    assertEquals(SessionPhase.VOTING, session.getPhase());
    ideas.get(0).registerVote(users.get(4));
    assertEquals(0, ideas.get(0).countVotes().intValue());
    
    // Os votos permanecem inalterados se o votante for o autor da ideia
    ideas.get(0).registerVote(users.get(0));
    assertEquals(0, ideas.get(0).countVotes().intValue());
    
    // Os votos permanecem inalterados se o votante já tiver votado na ideia
    ideas.get(0).registerVote(users.get(1));
    ideas.get(0).registerVote(users.get(1));
    assertEquals(1, ideas.get(0).countVotes().intValue());
    
    // Os votos permanecem inalterados se o votante já tiver alcançado o limite de votos
    ideas.get(1).registerVote(users.get(2));
    ideas.get(0).registerVote(users.get(2));
    assertEquals(1, ideas.get(0).countVotes().intValue());
    
    // Caso contrário, o votante passa a fazer parte da lista de vontantes e o tamanho da lista é
    // incrementado de 1
    ideas.get(0).registerVote(users.get(3));
    assertEquals(2, ideas.get(0).countVotes().intValue());
  }
  
  @Test
  public void removeVote() {
    // Quando se tenta remover o voto de uma ideia...
    // Os votos permanecem inalterados se a sessão não estiver na fase de votação
    assertNotEquals(SessionPhase.VOTING, session.getPhase());
    ideas.get(0).removeVote(users.get(1));
    assertEquals(0, ideas.get(0).countVotes().intValue());
    
    // Os votos permanecem inalterados se quem quer remover o voto não estiver na lista de votantes
    // da ideia
    session.nextPhase();
    assertEquals(SessionPhase.VOTING, session.getPhase());
    ideas.get(0).registerVote(users.get(1));
    ideas.get(0).removeVote(users.get(2));
    assertEquals(1, ideas.get(0).countVotes().intValue());
    
    // Caso contrário, quem quer remover o voto deixa de fazer parte da lista de votantes e o
    // tamanho da lista é decrementado de 1
    ideas.get(0).removeVote(users.get(1));
    assertEquals(0, ideas.get(0).countVotes().intValue());
  }
}
