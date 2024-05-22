import javax.swing.*;

public class CasillaEspecial {
    private String tipo;

    public CasillaEspecial(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void efecto(Jugador jugador) {
        if (this.tipo.equals("Cárcel")) {
            int opcion = JOptionPane.showOptionDialog(null,
                    "¡Has caído en la cárcel, " + jugador.getNombre() + "!\n¿Qué quieres hacer?",
                    "Cárcel",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Pagar $200", "Perder dos turnos"},
                    "Pagar $200");

            if (opcion == JOptionPane.YES_OPTION) {
                if (jugador.getDinero() >= 200) {
                    jugador.restarDinero(200);
                    JOptionPane.showMessageDialog(null, jugador.getNombre() + ", has pagado $200 para salir de la cárcel.",
                            "Pago de Cárcel", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Lo siento, " + jugador.getNombre() + ", no tienes suficiente dinero para pagar.",
                            "Sin suficiente dinero", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                jugador.setTurnosEnCarcel(2);
                JOptionPane.showMessageDialog(null, jugador.getNombre() + ", has elegido perder dos turnos.",
                        "Perder turnos", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
