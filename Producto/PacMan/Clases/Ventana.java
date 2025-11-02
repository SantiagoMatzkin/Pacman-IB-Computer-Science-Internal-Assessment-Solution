
import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.*;

public class Ventana extends JFrame {

    private Controlador controlador;
    private static final int tamañoCelda = 35;
    private static final int espacioExtraY = 7;

    // Imágenes 
    public static Image pacmanArriba;
    public static Image pacmanAbajo;
    public static Image pacmanIzquierda;
    public static Image pacmanDerecha;
    public static Image pacmanDireccion;

    public Image fantasma1;
    public Image fantasma2;
    public Image fantasma3;
    public Image fantasma4;

    private String[][] mapa;

    public Ventana(Controlador controlador) {
        this.controlador = controlador;
        setTitle("PacMan SMA");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        createBufferStrategy(2);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        renderizar();
    }

    public void renderizar() {
        pacmanArriba = new ImageIcon(getClass().getResource("Pacman/Imágenes/pacmanArriba.png")).getImage();
        pacmanAbajo = new ImageIcon(getClass().getResource("Pacman/Imágenes/pacmanAbajo.png")).getImage();
        pacmanIzquierda = new ImageIcon(getClass().getResource("Pacman/Imágenes/pacmanIzquierda.png")).getImage();
        pacmanDerecha = new ImageIcon(getClass().getResource("Pacman/Imágenes/pacmanDerecha.png")).getImage();

        fantasma1 = new ImageIcon(getClass().getResource("Pacman/Imágenes/fantasma1.png")).getImage();
        fantasma2 = new ImageIcon(getClass().getResource("Pacman/Imágenes/fantasma2.png")).getImage();
        fantasma3 = new ImageIcon(getClass().getResource("Pacman/Imágenes/fantasma3.png")).getImage();
        fantasma4 = new ImageIcon(getClass().getResource("Pacman/Imágenes/fantasma4.png")).getImage();

        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(2);
            return;
        }

        Graphics g = bufferStrategy.getDrawGraphics();

        if (g != null) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            if (controlador.getVidasRestantes() > 0) {
                mapa = controlador.getMapa().getArrayMapa1();
            } else if (controlador.getVidasRestantes() <= 0) {
                mapa = controlador.getMapa().getArrayMapaGameOver();
            }

            int offsetX = (getWidth() - mapa[0].length * tamañoCelda) / 2;
            int offsetY = (getHeight() - mapa.length * tamañoCelda) / 2 + espacioExtraY;

            // RENDERIZAR PAREDES
            for (int i = 0; i < mapa.length; i++) {
                for (int j = 0; j < mapa[i].length; j++) {
                    if (mapa[i][j].equals("X")) {
                        if (controlador.getVidasRestantes() > 0) {
                            g.setColor(Color.BLUE);
                        } else if (controlador.getVidasRestantes() <= 0) {
                            g.setColor(Color.RED);
                        }
                        g.fillRect(j * tamañoCelda + offsetX, i * tamañoCelda + offsetY, tamañoCelda, tamañoCelda);
                    }
                }
            }

            // RENDERIZAR COMIDA
            for (int i = 0; i < mapa.length; i++) {
                for (int j = 0; j < mapa[i].length; j++) {
                    if (mapa[i][j].contains(" ")) {
                        g.setColor(Color.GREEN);
                        int centroX = j * tamañoCelda + offsetX + tamañoCelda / 2;
                        int centroY = i * tamañoCelda + offsetY + tamañoCelda / 2;
                        int radioComida = tamañoCelda / 4;
                        g.fillOval(centroX - radioComida, centroY - radioComida, radioComida * 2, radioComida * 2);
                    }
                }
            }

            // RENDERIZAR SUPERCOMIDA
            for (int i = 0; i < mapa.length; i++) {
                for (int j = 0; j < mapa[i].length; j++) {
                    if (mapa[i][j].contains("S")) {
                        g.setColor(Color.WHITE);
                        int centroX = j * tamañoCelda + offsetX + tamañoCelda / 2;
                        int centroY = i * tamañoCelda + offsetY + tamañoCelda / 2;
                        int radioComida = tamañoCelda / 3;
                        g.fillOval(centroX - radioComida, centroY - radioComida, radioComida * 2, radioComida * 2);
                    }
                }
            }

            // RENDERIZAR PACMAN
            for (int i = 0; i < mapa.length; i++) {
                for (int j = 0; j < mapa[i].length; j++) {
                    if (mapa[i][j].contains("P")) {
                        int centroX = j * tamañoCelda + offsetX + tamañoCelda / 2;
                        int centroY = i * tamañoCelda + offsetY + tamañoCelda / 2;
                        int radioPacman = tamañoCelda / 2;

                        String direccion = Pacman.direccion;
                        if (direccion != null) {
                            switch (direccion) {
                                case "arriba":
                                    pacmanDireccion = pacmanArriba;
                                    break;
                                case "abajo":
                                    pacmanDireccion = pacmanAbajo;
                                    break;
                                case "izquierda":
                                    pacmanDireccion = pacmanIzquierda;
                                    break;
                                case "derecha":
                                    pacmanDireccion = pacmanDerecha;
                                    break;
                            }
                        } else {
                            pacmanDireccion = pacmanDerecha;
                        }

                        g.drawImage(pacmanDireccion, centroX - radioPacman, centroY - radioPacman, radioPacman * 2, radioPacman * 2, null);
                    }
                }
            }

            // RENDERIZAR FANTASMAS
            for (int i = 0; i < mapa.length; i++) {
                for (int j = 0; j < mapa[i].length; j++) {
                    if (mapa[i][j].contains("1")) {
                        g.drawImage(fantasma1, j * tamañoCelda + offsetX, i * tamañoCelda + offsetY, tamañoCelda, tamañoCelda, null);
                    }
                    if (mapa[i][j].contains("2")) {
                        g.drawImage(fantasma2, j * tamañoCelda + offsetX, i * tamañoCelda + offsetY, tamañoCelda, tamañoCelda, null);
                    }
                    if (mapa[i][j].contains("3")) {
                        g.drawImage(fantasma3, j * tamañoCelda + offsetX, i * tamañoCelda + offsetY, tamañoCelda, tamañoCelda, null);
                    }
                    if (mapa[i][j].contains("4")) {
                        g.drawImage(fantasma4, j * tamañoCelda + offsetX, i * tamañoCelda + offsetY, tamañoCelda, tamañoCelda, null);
                    }
                }
            }

            // RENDERIZAR PUNTO5
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", Font.BOLD, 25));
            g.drawString("PUNTOS: " + controlador.getPuntos(), 57, 90);

            // RENDERIZAR VIDAS
            g.setColor(Color.WHITE);
            g.setFont(new Font("Verdana", Font.BOLD, 25));

            if (controlador.getVidasRestantes() >= 0) {
                g.drawString("Vidas restantes: " + controlador.getVidasRestantes(), 890, 90);
            } else {
                g.drawString("Vidas restantes: 0", 890, 90);
            }

            g.dispose();
            bufferStrategy.show();
        }
    }

    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        controlador.iniciarJuego();
    }
}
