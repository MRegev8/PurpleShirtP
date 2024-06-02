package com.example.purpleshirt;

public class Leador extends leadster{

    public Leador(String fn, String nn,String pw,String e, String c, String p, int g, String d, String m, String y) {

        super(fn, nn,pw ,e, c, p, g, d, m, y);
        this.role="מוביל";
    }
    public Leador(String fn,String pw,String e, String c, String p, int g, String d, String m, String y) {

        super(fn,pw ,e, c, p, g, d, m, y);
        this.role="מוביל";
    }
    public Leador () {
    }
}
