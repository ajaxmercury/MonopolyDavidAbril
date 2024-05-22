import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public class ControladorJuego {
    private ArrayList<Jugador> jugadores;
    private Tablero tablero;
    private int turnoActual;
    private String estadoJuego;

    public ControladorJuego(int numJugadores, int dineroInicial) {
        jugadores = new ArrayList<>(numJugadores);
        Color[] colores = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.MAGENTA};

        for (int i = 0; i < numJugadores; i++) {
            jugadores.add(new Jugador("Jugador " + (i + 1), dineroInicial, colores[i]));
        }
        tablero = new Tablero();
        turnoActual = 0;
        estadoJuego = "";
    }

    public void lanzarDado() {
        Random rand = new Random();
        int resultadoDado = rand.nextInt(6) + 1;
        Jugador jugador = jugadores.get(turnoActual);
        String eventoTurno = "El jugador " + jugador.getNombre() + " ha lanzado un " + resultadoDado + ". ";
    
        if (jugador.isEnCarcel()) {
            if (jugador.getTurnosEnCarcel() > 0) {
                jugador.setTurnosEnCarcel(jugador.getTurnosEnCarcel() - 1);
                turnoActual = (turnoActual + 1) % jugadores.size();
                estadoJuego += eventoTurno + "A " + jugador.getNombre() + " le quedan " + jugador.getTurnosEnCarcel() + " turnos en la cárcel.\n";
                return;
            } else {
                jugador.setEnCarcel(false);
                estadoJuego += "¡" + jugador.getNombre() + " ha salido de la cárcel!\n";
            }
        }
    
        int dineroAntes = jugador.getDinero();
        jugador.mover(resultadoDado);
        manejarCasilla(jugador);
        int dineroDespues = jugador.getDinero();
        int dineroGastado = dineroAntes - dineroDespues;
        int dineroGanado = dineroDespues - dineroAntes;
        String infoDinero = "Gastado: $" + dineroGastado + ", Ganado: $" + dineroGanado + ", Disponible: $" + dineroDespues;
    
        turnoActual = (turnoActual + 1) % jugadores.size();
        estadoJuego += eventoTurno + jugador.getNombre() + " está en la casilla " + jugador.getPosicion() + ". " + infoDinero + ". Saldo total: $" + jugador.getDinero() + "\n";
    }
    

    private void manejarCasilla(Jugador jugador) {
        Object casilla = tablero.obtenerCasilla(jugador.getPosicion());

        if (casilla instanceof Propiedad) {
            Propiedad propiedad = (Propiedad) casilla;
            if (propiedad.getPropietario() == null) {
                if (jugador.getDinero() >= propiedad.getCostoCompra()) {
                    jugador.setDinero(jugador.getDinero() - propiedad.getCostoCompra());
                    propiedad.setPropietario(jugador);
                }
            } else if (propiedad.getPropietario() != jugador) {
                jugador.setDinero(jugador.getDinero() - propiedad.getAlquiler());
                propiedad.getPropietario().setDinero(propiedad.getPropietario().getDinero() + propiedad.getAlquiler());
            }
        } else if (casilla instanceof CasillaEspecial) {
            CasillaEspecial casillaEspecial = (CasillaEspecial) casilla;
            manejarCasillaEspecial(jugador, casillaEspecial);
        }
    }

    private void manejarCasillaEspecial(Jugador jugador, CasillaEspecial casillaEspecial) {
        switch (casillaEspecial.getTipo()) {
            case "Salida":
                jugador.setDinero(jugador.getDinero() + 200);
                break;
            case "Impuesto":
                jugador.setDinero(jugador.getDinero() - 75);
                break;
            case "Cárcel":
                jugador.setEnCarcel(true);
                jugador.setTurnosEnCarcel(3);
                break;
            case "Estacionamiento":
                // No hacer nada
                break;
            case "Ir a la cárcel":
                jugador.setEnCarcel(true);
                jugador.setPosicion(10); // Enviar al jugador a la cárcel
                break;
            // Implementar más efectos para otras casillas especiales
        }
    }

    public Jugador getJugadorActual() {
        return jugadores.get(turnoActual);
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public boolean juegoTerminado() {
        for (Jugador jugador : jugadores) {
            if (jugador.getDinero() <= 0) {
                return true;
            }
        }
        return false;
    }

    public Jugador getGanador() {
        Jugador ganador = null;
        int maxDinero = -1;
        for (Jugador jugador : jugadores) {
            if (jugador.getDinero() > maxDinero) {
                maxDinero = jugador.getDinero();
                ganador = jugador;
            }
        }
        return ganador;
    }

    public String getEstadoJuego() {
        return estadoJuego;
    }
}
