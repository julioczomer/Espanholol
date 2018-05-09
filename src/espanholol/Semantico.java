package espanholol;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import symbol.SemanticTable;
import symbol.Simbolo;

public class Semantico implements Constants
{
    public List<Simbolo> simbolos = new ArrayList<>();    
    Integer tipo;
    String id;
    
    /* Expressões */
    Stack<Integer> expr = new Stack<>();
    
    /* Escopo */
    Stack<String> escopo = new Stack<>();
    Integer nivel = 0;
    
    public Semantico() {
        escopo.add("global");
    }
        
    public void executeAction(int action, Token token) throws SemanticError
    {
        String lex = token.getLexeme();
        switch(action) {
            case 1: // TIPO
                this.tipo = obterTipoEnum(lex);
            break;
            case 2: // ID
                this.id = lex;
                if(existeSimboloComMesmoId(this.id, this.escopo.peek()))
                    throw new SemanticError("Já existe uma variável com este id.");
                simbolos.add(new Simbolo(this.id, this.tipo, escopo.peek(), false));
            break;
            case 3: // VETOR
                this.id = lex;
                if(existeSimboloComMesmoId(this.id, this.escopo.peek()))
                    throw new SemanticError("Já existe uma variável com este id.");
                simbolos.add(new Simbolo(this.id, this.tipo, escopo.peek(), false));
            break;
            case 4: // ATRIBUICAO                
                if(obterTipoPorId(this.id, escopo.peek()) != expr.pop())
                    throw new SemanticError("Atribuição de tipos diferentes.");
                inicializarVariavel(this.id, escopo.peek());
            break;
            // OPERACOES
            case 5: // SOMAR
                if(SemanticTable.resultType(expr.pop(), expr.pop(), SemanticTable.SUM) == SemanticTable.ERR)
                    throw new SemanticError("Não é possível somar estes tipos");
            break;
            case 6: // SUBTRAIR
                if(SemanticTable.resultType(expr.pop(), expr.pop(), SemanticTable.SUB) == SemanticTable.ERR)
                    throw new SemanticError("Não é possível subtrair estes tipos");
            break;
            case 7: // MULTIPLICAR
                if(SemanticTable.resultType(expr.pop(), expr.pop(), SemanticTable.MUL) == SemanticTable.ERR)
                    throw new SemanticError("Não é possível multiplicar estes tipos");
            break;
            case 8: // DIVIDIR
                if(SemanticTable.resultType(expr.pop(), expr.pop(), SemanticTable.DIV) == SemanticTable.ERR)
                    throw new SemanticError("Não é possível dividir estes tipos");
            break;            
            // EXPRESSOES
            case 9: // INT
                expr.add(SemanticTable.INT);
            break;
            case 10: // FLOAT
                expr.add(SemanticTable.FLO);
            break;
            case 11: // CHAR
                expr.add(SemanticTable.CHA);
            break;
            case 12: // STRING
                expr.add(SemanticTable.STR);
            break;
            case 13: // ID
                expr.add(obterTipoPorId(lex, escopo.peek()));
            break;
            /*case 3: // INICIO ESCOPO
                if(this.nivel == 0) {
                    escopo.add(id);
                } else {
                    String nome = escopo.get(1).concat(this.nivel.toString());
                    escopo.add(nome);
                }
            break;
            case 4: // FINAL ESCOPO
                escopo.pop();
                if(escopo.peek().equals("global"))
                    this.nivel = 0;
            break;
            // OPERACOES
            case 5: // SOMAR
                if(SemanticTable.resultType(expr.pop(), expr.pop(), SemanticTable.SUM) == SemanticTable.ERR)
                    throw new SemanticError("Não é possível somar estes tipos");
            break;
            case 6: // SUBTRAIR
                if(SemanticTable.resultType(expr.pop(), expr.pop(), SemanticTable.SUB) == SemanticTable.ERR)
                    throw new SemanticError("Não é possível subtrair estes tipos");
            break;
            case 7: // MULTIPLICAR
                if(SemanticTable.resultType(expr.pop(), expr.pop(), SemanticTable.MUL) == SemanticTable.ERR)
                    throw new SemanticError("Não é possível multiplicar estes tipos");
            break;
            case 8: // DIVIDIR
                if(SemanticTable.resultType(expr.pop(), expr.pop(), SemanticTable.DIV) == SemanticTable.ERR)
                    throw new SemanticError("Não é possível dividir estes tipos");
            break;            
            // EXPRESSOES
            case 9: // INT
                expr.add(SemanticTable.INT);
            break;
            case 10: // FLOAT
                expr.add(SemanticTable.FLO);
            break;
            case 11: // CHAR
                expr.add(SemanticTable.CHA);
            break;
            case 12: // STRING
                expr.add(SemanticTable.STR);
            break;
            // ATRIBUIR
            case 13:
                if(SemanticTable.atribType(this.tipo, this.expr.pop()) == SemanticTable.ERR)
                    throw new SemanticError("Atribuição de tipo inválida");
            break;
            // FOR
            case 14:
                this.id = lex;
                Integer proxNivel = nivel+1;
                String escopoFor = escopo.get(1).concat(proxNivel.toString());
                simbolos.add(new Simbolo(this.id, this.tipo, escopoFor, false));
            break;*/
        }
    }
    
    private Simbolo obterSimbolo(String id, String scopo) {
        for (Simbolo simbolo : simbolos) {
            if(simbolo.id.equals(id) && simbolo.escopo.equals(escopo))
                return simbolo;
        }
        return null;
    }
    
    private void inicializarVariavel(String id, String escopo) {
        for (Simbolo simbolo : simbolos) {
            if(simbolo.id.equals(id) && simbolo.escopo.equals(escopo))
                simbolo.inicializado = true;
        }
    }
    
    private void utilizarVariavel(String id, String escopo) {
        for (Simbolo simbolo : simbolos) {
            if(simbolo.id.equals(id) && simbolo.escopo.equals(escopo))
                simbolo.utilizado = true;
        }
    }
    
    private Integer obterTipoPorId(String id, String escopo) {
        return obterSimbolo(id, escopo).tipo;
    }
    
    private Integer obterTipoEnum(String tipo) {
        switch(tipo) {
            case "int":
                return SemanticTable.INT;
            case "float":
                return SemanticTable.FLO;
            case "char":
                return SemanticTable.CHA;
            case "string":
                return SemanticTable.STR;
            case "bool":
                return SemanticTable.BOO;
        }
        return SemanticTable.ERR;
    }
    
    private Boolean existeSimboloComMesmoId(String nome, String escopo) {
        for(Simbolo simbolo : simbolos)
            if(simbolo.id.equals(nome) && simbolo.escopo.equals(escopo))
                return true;
        return false;
    }
}
