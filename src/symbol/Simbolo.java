/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package symbol;

import javafx.util.Pair;

/**
 *
 * @author 5966868
 */
public class Simbolo {
    public Simbolo(String id, Integer tipo, Pair<String, Integer> escopo, Integer parametro, Boolean vetor, Boolean funcao) {
        this.id = id;
        this.tipo = tipo;
        this.escopo = escopo;
        this.parametro = parametro;
        this.vetor = vetor;
        this.funcao  = funcao;
        this.inicializado = false;
        this.utilizado = false;
    }
    public String id;
    public Integer tipo;
    public Pair<String, Integer> escopo;
    public Boolean inicializado;
    public Boolean utilizado;
    public Integer parametro;
    public Boolean vetor;
    public Boolean funcao;
    public Integer dimensoes = 0;
    
    public String getTipo() {
        switch(this.tipo) {
            case SemanticTable.INT: return "INT";
            case SemanticTable.FLO: return "FLOAT";
            case SemanticTable.CHA: return "CHAR";
            case SemanticTable.STR: return "STRING";
            case SemanticTable.BOO: return "BOOLEAN";
            case SemanticTable.VOI: return "VOID";
        }
        return "";
    }
}

