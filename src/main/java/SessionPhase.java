package main.java;

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
  
  public SessionPhase next() {
    return null;
  }
}
