package main.java;

/**
 * Classe de enumeração para representar as fases da sessão.
 * 
 * @author hugo
 *
 */
public enum SessionPhase {

  WELCOME {
    @Override
    public SessionPhase next() {
      return SessionPhase.BRAINSTORM;
    }
  },
  BRAINSTORM{
    @Override
    public SessionPhase next() {
      return SessionPhase.VOTING;
    }
  },
  VOTING{
    @Override
    public SessionPhase next() {
      return SessionPhase.RANKING;
    }
  },
  RANKING{
    @Override
    public SessionPhase next() {
      return SessionPhase.RANKING;
    }
  };

  /**
   * Método para obtenção da próxima fase.
   * 
   * @return Próxima fase.
   */
  public SessionPhase next() {
    return null;
  }
}
