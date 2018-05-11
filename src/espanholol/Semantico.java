package espanholol;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.util.Pair;
import symbol.SemanticTable;
import symbol.Simbolo;

public class Semantico implements Constants
{
    public List<String> warnings = new ArrayList<>();
    
    public List<Simbolo> simbolos = new ArrayList<>();
    public List<Simbolo> pendentes = new ArrayList<>();
    Integer tipo;
    String id;
    Integer ordem;
    Boolean parametro = false;
    Boolean declarando = false;
    Integer expr_vetor = 0;
    
    /* Expressões */
    Stack<Integer> expr = new Stack<>();
    Stack<Integer> op = new Stack<>();
    
    /* Escopo */
    Stack<Pair<String, Integer>> escopos = new Stack<>();
    Integer nivel = 1;
    Boolean escopo_antecipado = false;
    
    public Semantico() {
        escopos.push(new Pair("global", 0));
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
                    if(this.parametro)
                        this.ordem++;
                    if(existeSimboloComMesmoId(this.id))
                        throw new SemanticError("Já existe uma variável com este identificador: ".concat(id));                   
                    pendentes.add(new Simbolo(this.id, this.tipo, escopos.peek(), this.ordem, false, false));
                } else {
                    this.tipo = obterTipoPorId(this.id);
                }
            break;
            case 3: // VETOR
                this.id = lex;
                if(declarando) {
                    if(this.parametro)
                        this.ordem++;
                    if(existeSimboloComMesmoId(this.id))
                        throw new SemanticError("Já existe uma variável com este identificador: ".concat(id));
                    pendentes.add(new Simbolo(this.id, this.tipo, escopos.peek(), this.ordem, true, false));
                } else {
                    this.tipo = obterTipoPorId(this.id);
                }
            break;
            case 4: // ATRIBUICAO
                simbolos.addAll(pendentes);                
                
                int tipo = obterTipoPorId(this.id);
                if(tipo == SemanticTable.ERR)
                    throw new SemanticError("Variável não declarada: ".concat(this.id));
                int expr_res = obterResultadoOperacoes();
                if(expr_res == SemanticTable.ERR)
                    throw new SemanticError("Operação com tipos diferentes");
                
                int res = SemanticTable.atribType(tipo, expr_res);
                if(res == SemanticTable.ERR)
                    throw new SemanticError("Atribuição de tipos diferentes.");
                else if(res == SemanticTable.WAR)
                    this.warnings.add("Atribuição de " + SemanticTable.getTypeName(expr_res) + " para " + SemanticTable.getTypeName(tipo));
                
                inicializarVariavel(this.id);
                
                pendentes.clear();
            break;
            // OPERACOES
            case 5: // SOMAR
                if(expr_vetor == 0)
                    op.push(SemanticTable.SUM);
            break;
            case 6: // SUBTRAIR
                if(expr_vetor == 0)
                    op.push(SemanticTable.SUB);                
            break;
            case 7: // MULTIPLICAR
                if(expr_vetor == 0)
                    op.push(SemanticTable.MUL);
            break;
            case 8: // DIVIDIR
                if(expr_vetor == 0)
                    op.push(SemanticTable.DIV);
            break;            
            // EXPRESSOES
            case 9: // INT                
                if(expr_vetor == 0)
                    expr.push(SemanticTable.INT);
            break;
            case 10: // FLOAT
                if(expr_vetor == 0)
                    expr.push(SemanticTable.FLO);
            break;
            case 11: // CHAR
                if(expr_vetor == 0)
                    expr.push(SemanticTable.CHA);
            break;
            case 12: // STRING
                if(expr_vetor == 0)
                    expr.push(SemanticTable.STR);
            break;
            case 13: // ID
                System.out.println(lex + " " + expr_vetor);
                if(expr_vetor == 0)
                    expr.push(obterTipoPorId(lex));
                int indice = obterIndiceSimboloMaisProximo(lex);
                Simbolo sim = this.simbolos.get(indice);
                if(!sim.funcao && !sim.inicializado)
                    this.warnings.add(lex.concat(" em " + sim.escopo.getKey() + " ainda não foi inicializado."));
                utilizarVariavel(lex);
            break;
            case 14:
                this.declarando = false;
                this.tipo = SemanticTable.ERR;
            break;       
            case 15:
                this.expr_vetor++;
            break;
            case 16:
                this.expr_vetor--;
            break;
            case 17: // ESCOPO SUB-ROTINA
                this.ordem = 0;
                if(lex.equals("mayor"))
                    this.simbolos.add(new Simbolo(lex, SemanticTable.INT, this.escopos.peek(), this.ordem, false, true));
                else
                    this.simbolos.add(new Simbolo(lex, this.tipo, this.escopos.peek(), this.ordem, false, true));
                this.escopos.push(new Pair(lex, 0));
                this.nivel = 0;
                this.escopo_antecipado = true;
            break;
            case 18:
                if(this.escopo_antecipado) {
                    this.escopo_antecipado = false;
                } else {
                    this.nivel++;
                    escopos.push(new Pair(escopos.peek().getKey(), this.nivel));
                }
            break;
            case 19:
                escopos.pop();
            break;
            case 20:
                if(obterResultadoOperacoes() == SemanticTable.ERR)
                    throw new SemanticError("Não é permitida operação relacional entre estes tipos");
            break;
            case 21:
                if(expr_vetor == 0)
                    this.op.push(SemanticTable.REL);
            break;
            case 22:
                this.escopo_antecipado = true;
                this.nivel++;
                this.escopos.push(new Pair(escopos.peek().getKey(), this.nivel));                
            break;
            case 23:
                this.declarando = false;
                if(obterResultadoOperacoes() == SemanticTable.ERR)
                    throw new SemanticError("Não é permitida operação relacional entre estes tipos");
            break;
            case 24:
                simbolos.addAll(pendentes);
                this.ordem = 0;
                this.parametro = false;                
                pendentes.clear();
            break;
            case 25:
                this.parametro = true;
                this.ordem = 0;
            break;
        }
    }
    
    private Integer obterResultadoOperacoes() {
        while(expr.size() >= 2) {
            int t1 = expr.pop();
            int t2 = expr.pop();
            int op = this.op.pop();
            int res = SemanticTable.resultType(t1, t2, op);
            if(res == SemanticTable.ERR)
                return SemanticTable.ERR;
            else if(res == SemanticTable.WAR)
                this.warnings.add("Operação de " + SemanticTable.getOperatorName(op) + 
                                  " entre " + SemanticTable.getTypeName(t1) +
                                  " e " + SemanticTable.getTypeName(t2));
            expr.push(res);
        }
        return expr.pop();
    }
    
    private Integer obterIndiceSimbolo(String id, Pair<String, Integer> escopo) throws SemanticError {
        for (int i = 0; i < this.simbolos.size(); i++) {
            Simbolo s = this.simbolos.get(i);
            if(s.id.equals(id)
                && s.escopo.getKey().equals(escopo.getKey())
                && s.escopo.getValue().equals(escopo.getValue()))
                return i;
        }
        return -1;
    }
    
    private Integer obterIndiceSimboloMaisProximo(String id) throws SemanticError {
        for (int i = this.escopos.size() - 1; i >= 0; i--) {
            Integer indice = obterIndiceSimbolo(id, this.escopos.get(i));
            if(indice >= 0)
                return indice;
        }
        return -1;
    }
    
    private void inicializarVariavel(String id) throws SemanticError {        
        Integer indice;
        indice = obterIndiceSimboloMaisProximo(id);
        if(indice < 0)
            throw new SemanticError("Variável não declarada: ".concat(id));
        simbolos.get(indice).inicializado = true;
    }   
    
    private void utilizarVariavel(String id) throws SemanticError {        
        Integer indice;
        indice = obterIndiceSimboloMaisProximo(id);
        if(indice < 0)
            throw new SemanticError("Variável não declarada: ".concat(id));
        simbolos.get(indice).utilizado = true;
    }
    
    private Integer obterTipoPorId(String id) throws SemanticError {
        Integer indice = obterIndiceSimboloMaisProximo(id);
        if(indice >= 0)
            return simbolos.get(indice).tipo;
        else
            throw new SemanticError("Variável não declarada: ".concat(id));
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
            case "vacio":
                return SemanticTable.VOI;
        }
        return SemanticTable.ERR;
    }
    
    private Boolean existeSimboloComMesmoId(String id) throws SemanticError {
        Integer indice = obterIndiceSimboloMaisProximo(id);
        return (indice >= 0);
    }       
    
    public void obterSimbolosNaoUtilizadosInicializados() {
        for (Simbolo simbolo : this.simbolos) {
            if(simbolo.funcao)
                continue;
            if(!simbolo.utilizado)
                this.warnings.add(simbolo.id.concat(" em " + simbolo.escopo.getKey() + " não utilizado."));
        }
    }
}
