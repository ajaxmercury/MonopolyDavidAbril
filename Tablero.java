import java.util.ArrayList;

public class Tablero {
    private ArrayList<Object> casillas;

    public Tablero() {
        casillas = new ArrayList<>(40);
        // Añadir propiedades y casillas especiales al tablero
        for (int i = 0; i < 40; i++) {
            if (i == 0) {
                casillas.add(new CasillaEspecial("Salida"));
            } else if (i == 10) {
                casillas.add(new CasillaEspecial("Cárcel"));
            } else if (i == 20) {
                casillas.add(new CasillaEspecial("Impuesto"));
            } else if (i == 30) {
                casillas.add(new CasillaEspecial("Ir a la cárcel"));
            } else {
                casillas.add(new Propiedad("Propiedad " + i, 100 + i * 10, 10 + i * 2));
            }
        }
    }

    public Object obtenerCasilla(int posicion) {
        return casillas.get(posicion);
    }
}
