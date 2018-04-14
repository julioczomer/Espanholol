package espanholol.studio;

import espanholol.*;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 5966868
 */
public class EspanhololStudio {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SemanticError {
        // Stack<Double> pilha = new Stack<Double>();
        try {
            Lexico    lexico    = new Lexico("entero mayor(){ entero a, b; a = a + b; }");
            Semantico semantico = new Semantico();
            Sintatico sintatico = new Sintatico();
            sintatico.parse(lexico, semantico);
            EspanhololFRM frm = new EspanhololFRM();
        } catch (LexicalError | SyntaticError ex) {
            Logger.getLogger(EspanhololStudio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
