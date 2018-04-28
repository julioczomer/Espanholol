/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package symbol;

/**
 *
 * @author 5966868
 */
public class Simbolo {
    public Simbolo(String id, String tipo) {
        this.id = id;
        this.tipo = tipo;
        this.inicializado = false;
        this.utilizado = false;
    }
    public String id;
    public String tipo;
    public Boolean inicializado;
    public Boolean utilizado;
}

