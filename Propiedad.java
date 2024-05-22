public class Propiedad {
    private String nombre;
    private int costoCompra;
    private int alquiler;
    private Jugador propietario;

    public Propiedad(String nombre, int costoCompra, int alquiler) {
        this.nombre = nombre;
        this.costoCompra = costoCompra;
        this.alquiler = alquiler;
        this.propietario = null;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCostoCompra() {
        return costoCompra;
    }

    public int getAlquiler() {
        return alquiler;
    }

    public Jugador getPropietario() {
        return propietario;
    }

    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }
}
