package it.objectmethod.login.model.enums;

public enum LoginStatusMsg{
		
	  OK_LOGIN(0, "Login effettuato correttamente."),
	  OK_REGITRAZIONE(1, "Registrazione avvenuta con successo"),
	  KO_LOGIN(2,"Username e/o password non corretta"),
	  KO_REGISTRAZIONE(3,"email già registrata"),
	  ERR_LOGIN(4,"Errore, impossibile effettuare il login"),
	  ERR_REGISTRAZIONE(5,"Errore durante la registrazione");
	
	  private final int code;
	  private final String description;

	  private LoginStatusMsg(int code, String description) {
	    this.code = code;
	    this.description = description;
	  }

	  public String getDescription() {
	     return description;
	  }

	  public int getCode() {
	     return code;
	  }

	  @Override
	  public String toString() {
	    return code + ": " + description;
	  }


}
