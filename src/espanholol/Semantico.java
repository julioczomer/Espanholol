package espanholol;

import java.util.HashMap;
import java.util.Map;
import symbol.Simbolo;

public class Semantico implements Constants
{
    Map<String, Simbolo> declaracoes = new HashMap<>();
    String tipo;
    public void executeAction(int action, Token token)	throws SemanticError
    {
        String lex = token.getLexeme();
        switch(action) {
            case 1: // TIPO
                this.tipo = lex;
            break;
            case 2: // ID                
                declaracoes.put(tipo, new Simbolo(lex, tipo));
            break;
        }
    }	
}
