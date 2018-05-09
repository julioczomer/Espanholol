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
    Boolean declarando = false;
    Boolean expr_vetor = false;
    
    /* Expressões */
    Stack<Integer> expr = new Stack<>();
    Stack<Integer> op = new Stack<>();
    
    /* Escopo */
    Stack<String> escopo = new Stack<>();
    Integer nivel = 0;
    Boolean escopo_antecipado = false;
    
    public Semantico() {
        escopo.add("global");
    }
        
    public void executeAction(int action, Token token) throws SemanticError
    {
        String lex = token.getLexeme();
        switch(action) {
            case 1: // TIPO
                this.tipo = obterTipoEnum(lex);
                this.declarando = true;
            break;
            case 2: // ID
                this.id = lex;
                if(declarando) {
                    if(existeSimboloComMesmoId(this.id, this.escopo.peek()))
                        throw new SemanticError("Já existe uma variável com este id.");
                    simbolos.add(new Simbolo(this.id, this.tipo, escopo.peek(), false, false));
                } else {
                    this.tipo = obterTipoPorId(this.id, escopo.peek());
                }
            break;
            case 3: // VETOR
                this.id = lex;
                if(existeSimboloComMesmoId(this.id, this.escopo.peek()))
                    throw new SemanticError("Já existe uma variável com este id.");
                simbolos.add(new Simbolo(this.id, this.tipo, escopo.peek(), true, false));
            break;
            case 4: // ATRIBUICAO
                int res = obterResultadoOperacoes();
                if(res == SemanticTable.ERR)
                    throw new SemanticError("Operação com tipos diferentes");
                if(obterTipoPorId(this.id, escopo.peek()) != res)
                    throw new SemanticError("Atribuição de tipos diferentes.");
                inicializarVariavel(this.id, escopo.peek());
            break;
            // OPERACOES
            case 5: // SOMAR
                if(!expr_vetor)
                    op.push(SemanticTable.SUM);
            break;
            case 6: // SUBTRAIR
                if(!expr_vetor)
                    op.push(SemanticTable.SUB);                
            break;
            case 7: // MULTIPLICAR
                if(!expr_vetor)
                    op.push(SemanticTable.MUL);
            break;
            case 8: // DIVIDIR
                if(!expr_vetor)
                    op.push(SemanticTable.DIV);
            break;            
            // EXPRESSOES
            case 9: // INT                
                if(!expr_vetor)
                    expr.push(SemanticTable.INT);
            break;
            case 10: // FLOAT
                if(!expr_vetor)
                    expr.push(SemanticTable.FLO);
            break;
            case 11: // CHAR
                if(!expr_vetor)
                    expr.push(SemanticTable.CHA);
            break;
            case 12: // STRING
                if(!expr_vetor)
                    expr.push(SemanticTable.STR);
            break;
            case 13: // ID
                if(!expr_vetor)
                    expr.push(obterTipoPorId(lex, escopo.peek()));
                utilizarVariavel(lex, escopo.peek());
            break;
            case 14:
                this.declarando = false;
            break;       
            case 15:
                this.expr_vetor = true;
            break;
            case 16:
                this.expr_vetor = false;
            break;
            case 17:
                this.simbolos.add(new Simbolo(lex, SemanticTable.INT, this.escopo.peek(), false, true));
                this.escopo.push(lex);
                this.escopo_antecipado = true;
            break;
            case 18:
                if(this.escopo_antecipado)
                    return;
                if(this.nivel == 0) {
                    escopo.add(id);
                } else {
                    String nome = escopo.get(1).concat(this.nivel.toString());
                    escopo.add(nome);
                }
            break;
            case 19:
                this.escopo_antecipado = false;
                escopo.pop();
                if(escopo.peek().equals("global"))
                    this.nivel = 0;
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
    
    private Integer obterResultadoOperacoes() {
        System.out.println(expr.size() + " " + op.size());
        while(expr.size() >= 2) {
            int t1 = expr.pop();
            int t2 = expr.pop();
            int op = this.op.pop();
            System.out.println(t1 + " " + t2);
            int res = SemanticTable.resultType(t1, t2, op);
            if(res == SemanticTable.ERR)
                return SemanticTable.ERR;
            expr.push(res);
        }
        return expr.pop();
    }
    
    private Simbolo obterSimbolo(String id, String escopo) {
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
            case "entero":
                return SemanticTable.INT;
            case "flotante":
                return SemanticTable.FLO;
            case "caracter":
                return SemanticTable.CHA;
            case "cuerda":
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
