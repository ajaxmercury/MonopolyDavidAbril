import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazGrafica extends JFrame {
    private ControladorJuego controlador;
    private JLabel lblTurnoActual;
    private JButton btnLanzarDado;
    private JTextArea txtEstadoJuego;
    private JPanel pnlTablero;
    private JScrollPane scrollPane;

    // Paleta de colores
    private Color rojoOscuro = new Color(139, 0, 0);
    private Color verdeOscuro = new Color(0, 100, 0);
    private Color amarillo = new Color(255, 215, 0);
    private Color azulClaro = new Color(173, 216, 230);
    private Color blanco = Color.WHITE;

    public InterfazGrafica(ControladorJuego controlador) {
        this.controlador = controlador;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("Monopoly");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(azulClaro);

        lblTurnoActual = new JLabel("Turno: " + controlador.getJugadorActual().getNombre());
        lblTurnoActual.setForeground(rojoOscuro);
        add(lblTurnoActual, BorderLayout.NORTH);

        txtEstadoJuego = new JTextArea();
        txtEstadoJuego.setEditable(false);
        txtEstadoJuego.setBackground(azulClaro);
        txtEstadoJuego.setForeground(blanco);
        scrollPane = new JScrollPane(txtEstadoJuego);
        add(scrollPane, BorderLayout.CENTER);

        pnlTablero = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dibujarTablero(g);
            }
        };
        pnlTablero.setPreferredSize(new Dimension(600, 600));
        pnlTablero.setBackground(azulClaro);
        add(pnlTablero, BorderLayout.WEST);

        btnLanzarDado = new JButton("Lanzar Dado");
        btnLanzarDado.setBackground(verdeOscuro);
        btnLanzarDado.setForeground(blanco);
        btnLanzarDado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlador.lanzarDado();
                actualizarInterfaz();
            }
        });
        add(btnLanzarDado, BorderLayout.SOUTH);

        actualizarInterfaz();
    }

    private void dibujarTablero(Graphics g) {
        int casillaSize = 50; // Tamaño de las casillas

        // Dibujar casillas
        for (int i = 0; i < 40; i++) {
            int x = 0, y = 0;

            // Calcular posición de las casillas (esquinas incluidas)
            if (i >= 0 && i < 10) {
                x = 510 - (i * casillaSize);
                y = 610 - casillaSize; // Ajuste de la primera fila hacia abajo
            } else if (i >= 10 && i < 20) {
                x = 0;
                y = 510 - ((i - 10) * casillaSize);
            } else if (i >= 20 && i < 30) {
                x = (i - 20) * casillaSize;
                y = 0;
            } else if (i >= 30 && i < 40) {
                x = 510;
                y = (i - 30) * casillaSize;
            }

            g.setColor(blanco);
            g.drawRect(x, y, casillaSize, casillaSize);
            Object casilla = controlador.getTablero().obtenerCasilla(i);

            if (casilla instanceof Propiedad) {
                g.setColor(verdeOscuro);
                g.fillRect(x + 1, y + 1, casillaSize - 1, casillaSize - 1);
                g.setColor(blanco);
                String nombreCasilla = ((Propiedad) casilla).getNombre();
                g.drawString(nombreCasilla, x + 5, y + casillaSize / 2);
            } else if (casilla instanceof CasillaEspecial) {
                g.setColor(rojoOscuro);
                g.fillRect(x + 1, y + 1, casillaSize - 1, casillaSize - 1);
                g.setColor(blanco);
                String nombreCasilla = ((CasillaEspecial) casilla).getTipo();
                g.drawString(nombreCasilla, x + 5, y + casillaSize / 2);
            }
        }

        // Dibujar jugadores
        for (Jugador jugador : controlador.getJugadores()) {
            int x = 0, y = 0;
            int pos = jugador.getPosicion();

            // Calcular posición de los jugadores (mismo formato simplificado)
            if (pos >= 0  && pos < 10) {
                x = 510 - (pos * casillaSize);
                y = 610 - casillaSize; // Ajuste de la primera fila hacia abajo
            } else if (pos >= 10 && pos < 20) {
                x = 0;
                y = 510 - ((pos - 10) * casillaSize);
            } else if (pos >= 20 && pos < 30) {
                x = (pos - 20) * casillaSize;
                y = 0;
            } else if (pos >= 30 && pos < 40) {
                x = 510;
                y = (pos - 30) * casillaSize;
            }

            g.setColor(jugador.getColorFicha());
            g.fillOval(x + 5, y + 5, 40, 40); // Tamaño de los jugadores
        }
    }

    private void actualizarInterfaz() {
        lblTurnoActual.setText("Turno: " + controlador.getJugadorActual().getNombre());
        txtEstadoJuego.setText("Resultado del turno:\n");
        txtEstadoJuego.append(controlador.getEstadoJuego());
        txtEstadoJuego.setCaretPosition(0); // Scroll hasta el principio del texto
        pnlTablero.repaint();

        if (controlador.juegoTerminado()) {
            JOptionPane.showMessageDialog(this, "¡Juego Terminado! El ganador es " + controlador.getGanador().getNombre());
            btnLanzarDado.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        ControladorJuego controlador = new ControladorJuego(4, 1500); // Ejemplo con 4 jugadores y $1500 de dinero inicial
        InterfazGrafica interfaz = new InterfazGrafica(controlador);
        interfaz.setVisible(true);
    }
}

