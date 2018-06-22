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
    
    /* Utilitários para gerar assembly bip */
    public List<String> assemblies = new ArrayList<>();
    Boolean primeira_expr = true;
    String id_vetor;
    Integer op_vector;
    Boolean vet_lado_esquerdo = false;
    Boolean vet_lado_esquerdo_resolvido = false;
    Boolean primeira_expr_vetor = true;
    Boolean operando_vetor = false;
    Boolean imprimir_vetor = false;
    
    public Semantico() {
        escopos.push(new Pair("global", 0));
    }
        
    public void executeAction(int action, Token token) throws SemanticError
    {
        String lex = token.getLexeme();
        System.out.println(Integer.toString(action).concat(" ").concat(lex));
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
                vet_lado_esquerdo = false;
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
                vet_lado_esquerdo = true;
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
                
                if(vet_lado_esquerdo) {
                    System.out.println("AAAAAAAAAAAAAAAAA");
                    assemblies.add("STO 1001");
                    assemblies.add("LD 1000");
                    assemblies.add("STO $indr");
                    assemblies.add("LD 1001");
                    assemblies.add("STOV ".concat(this.id));
                } else {
                    addInstrucaoAssembly(-2, this.id, true);
                }                
            break;
            // OPERACOES
            case 5: // SOMAR
                if(expr_vetor == 0)
                    op.push(SemanticTable.SUM);
                else
                    op_vector = SemanticTable.SUM;                
            break;
            case 6: // SUBTRAIR
                if(expr_vetor == 0)
                    op.push(SemanticTable.SUB);
                else
                    op_vector = SemanticTable.SUB;                                                
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
                if(expr_vetor == 0) {
                    expr.push(SemanticTable.INT);
                    if(op.size() > 0) {                        
                        addInstrucaoAssembly(op.peek(), lex, false);
                    } else {
                        addInstrucaoAssembly(-1, lex, false);
                        primeira_expr = false;
                    }
                } else {
                    if(primeira_expr_vetor) {
                        this.assemblies.add("LDI ".concat(lex));
                        primeira_expr_vetor = false;
                    } else {
                        this.assemblies.add(getAssemblyInstruction(op_vector).concat("I ").concat(lex));
                    }
                }
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
                int indice = obterIndiceSimboloMaisProximo(lex);
                Simbolo sim = this.simbolos.get(indice);
                if(!sim.funcao && !sim.inicializado && sim.parametro == 0)
                    this.warnings.add(lex.concat(" em " + sim.escopo.getKey() + " ainda não foi inicializado."));
                utilizarVariavel(lex);
                                
                if(expr_vetor == 0) {
                    expr.push(obterTipoPorId(lex));
                    if(op.size() > 0) {
                        addInstrucaoAssembly(op.peek(), lex, true);
                    } else {
                        addInstrucaoAssembly(-1, lex, true);
                        primeira_expr = false;
                    }
                } else {
                    if(primeira_expr_vetor) {
                        addInstrucaoAssembly(-1, lex, true);
                        primeira_expr_vetor = false;
                    } else {
                        assemblies.add(getAssemblyInstruction(op_vector).concat(" ").concat(lex));                        
                    }
                }                
            break;
            case 14:
                this.vet_lado_esquerdo_resolvido = false;
                this.operando_vetor = false;
                this.declarando = false;
                this.tipo = SemanticTable.ERR;
            break;       
            case 15:
                this.primeira_expr_vetor = true;
                this.expr_vetor++;
            break;
            case 16:
                if(vet_lado_esquerdo && !vet_lado_esquerdo_resolvido) {
                    assemblies.add("STO 1000");
                    vet_lado_esquerdo_resolvido = true;
                } else if(primeira_expr) {
                    assemblies.add("STO $indr");                    
                    if(imprimir_vetor) {
                        assemblies.add("LD $in_port");
                        assemblies.add("STOV ".concat(id_vetor));
                        this.imprimir_vetor = false;
                    } else {
                        assemblies.add("LDV ".concat(id_vetor));
                    }
                    primeira_expr = false;
                } else {
                    assemblies.add("STO $indr");
                    assemblies.add("LDV ".concat(id_vetor));
                    assemblies.add("STO 1001");
                    assemblies.add("LD 1000");
                    assemblies.add(getAssemblyInstruction(op.peek()).concat(" ").concat("1001"));
                }
                
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
            case 26:
                if(expr_vetor == 0)
                    expr.push(obterTipoPorId(lex));
                System.out.println(Integer.toString(op.size()).concat(" ").concat(Integer.toString(expr.size())));
                if(!primeira_expr)
                    assemblies.add("STO 1000");
                
                Simbolo simb = this.simbolos.get(obterIndiceSimboloMaisProximo(lex));
                if(!simb.funcao && !simb.inicializado && simb.parametro == 0)
                    this.warnings.add(lex.concat(" em " + simb.escopo.getKey() + " ainda não foi inicializado."));
                utilizarVariavel(lex);
                
                id_vetor = lex;
            break;
            case 27:
                this.op.clear();
                this.expr.clear();
                primeira_expr = true;
                this.operando_vetor = false;                
            break;
            case 28:
                if(expr_vetor == 0)
                    op.push(SemanticTable.SLT);
                else
                    op_vector = SemanticTable.SLT;
            break;
            case 29:
                if(expr_vetor == 0)
                    op.push(SemanticTable.SRT);
                else
                    op_vector = SemanticTable.SRT;
            break;
            case 30: // CIN
                this.primeira_expr = true;
                this.vet_lado_esquerdo = false;
                if(simbolos.get(obterIndiceSimboloMaisProximo(lex)).vetor) {
                    id_vetor = lex;
                    this.imprimir_vetor = true;
                } else {
                    assemblies.add("LD $in_port");
                    assemblies.add("STO ".concat(lex));
                }
            break;
            case 31: // COUT
                assemblies.add("STO $out_port");
            break;
        }
    }
    
    private void addInstrucaoAssembly(Integer op, String value, Boolean id) {
        if(op == -2) {
            assemblies.add("STO ".concat(value));
            primeira_expr = true;
            return;
        }

        if(primeira_expr) {
            String instr = "LD";
            if(!id)
                instr = instr.concat("I");
            assemblies.add(instr.concat(" ").concat(value));
        } else {
            String instr = getAssemblyInstruction(op);
            if(!id)
                instr = instr.concat("I");
            assemblies.add(instr.concat(" ").concat(value));
        }
    }
    
    private String getAssemblyInstruction(Integer op) {
        String instr = "";
        switch(op) {
            case SemanticTable.SUM:
                instr = "ADD";
            break;
            case SemanticTable.SUB:
                instr = "SUB";
            break;            
            case SemanticTable.SLT:
                instr = "SLL";
            break;
            case SemanticTable.SRT:
                instr = "SRR";
            break;
        }
        return instr;
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
