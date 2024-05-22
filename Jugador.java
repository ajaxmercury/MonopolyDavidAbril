import java.awt.Color;

public class Jugador {
    private String nombre;
    private int dinero;
    private int posicion;
    private boolean enCarcel;
    private int turnosEnCarcel;
    private Color colorFicha;

    public Jugador(String nombre, int dinero, Color colorFicha) {
        this.nombre = nombre;
        this.dinero = dinero;
        this.posicion = 0;
        this.enCarcel = false;
        this.turnosEnCarcel = 0;
        this.colorFicha = colorFicha;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDinero() {
        return dinero;
    }

    public void restarDinero(int cantidad) {
        dinero -= cantidad;
    }
    
    public void setDinero(int dinero) {
        this.dinero = dinero;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public void mover(int pasos) {
        posicion = (posicion + pasos) % 40;
    }

    public boolean isEnCarcel() {
        return enCarcel;
    }

    public void setEnCarcel(boolean enCarcel) {
        this.enCarcel = enCarcel;
    }

    public int getTurnosEnCarcel() {
        return turnosEnCarcel;
    }

    public void setTurnosEnCarcel(int turnosEnCarcel) {
        this.turnosEnCarcel = turnosEnCarcel;
    }

    public Color getColorFicha() {
        return colorFicha;
    }
}
