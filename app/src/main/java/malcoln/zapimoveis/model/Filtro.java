package malcoln.zapimoveis.model;

import java.io.Serializable;

/**
 * Created by Malcoln on 01/12/2017.
 */

public class Filtro implements Serializable{

    private int valor;

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Filtro(int valor) {
        this.valor = valor;
    }
    public Filtro() {

    }
}
