public class Pacman
{    
    public static String direccionDeseada = "arriba";
    public static String direccionEnEspera = "arriba";

    private String[] direcciones = {"arriba", "abajo", "derecha", "izquierda"};
    public static String direccion;

    public int posicionX;
    public int posicionY;

    public int[] coordenadasPacman = {posicionY, posicionX};

    private Controlador controlador;

    String[][] mapa;
    
    // Constructor que inicializa el controlador y el mapa
    public Pacman(Controlador controlador) {
        this.controlador = controlador;
        this.mapa = controlador.getMapa().getArrayMapa1();
    }

    // Método para mover a Pacman en la dirección deseada
    public void mover(String direccionDeseada) {
        if(Controlador.juegoIniciado) {
            for(int i = 0; i < mapa.length; i++) {
                for(int j = 0; j < mapa[i].length; j++) {
                    if(mapa[i][j].contains("P")) {
                        switch(direccionDeseada) {
                            case "arriba":
                                if(mapa[i - 1][j].contains(" ") || mapa[i - 1][j].contains("S") || mapa[i - 1][j].contains("C") || mapa[i - 1][j].contains("1")
                                    || mapa[i - 1][j].contains("2") || mapa[i - 1][j].contains("3") || mapa[i - 1][j].contains("4")) {
                                    controlador.verificarMuerte();
                                    if(mapa[i - 1][j].contains(" ")) {
                                        controlador.incrementarPuntos();
                                    } else if(mapa[i - 1][j].contains("S")) {
                                        controlador.SuperIncrementarPuntos();
                                    }
                                    mapa[i - 1][j] += "P";
                                    mapa[i][j] = mapa[i][j].replaceAll(" ", "C");
                                    mapa[i][j] = mapa[i][j].replaceAll("S", "C");
                                    mapa[i][j] = mapa[i][j].replaceAll("P", "");
                                    direccion = "arriba";
                                    posicionY = i - 1;
                                    posicionX = j;
                                    coordenadasPacman[0] = posicionY;
                                    coordenadasPacman[1] = posicionX;
                                    return;
                                } else if(mapa[i - 1][j].contains("X")) {
                                    direccionDeseada = direccion;
                                    direccionEnEspera = "arriba";
                                }
                                break;
                            case "abajo":
                                if(mapa[i + 1][j].contains(" ") || mapa[i + 1][j].contains("S") || mapa[i + 1][j].contains("C") || mapa[i + 1][j].contains("1")
                                    || mapa[i + 1][j].contains("2") || mapa[i + 1][j].contains("3") || mapa[i + 1][j].contains("4")) {
                                    controlador.verificarMuerte();
                                    if(mapa[i + 1][j].equals(" ")) {
                                        controlador.incrementarPuntos();
                                    } else if(mapa[i + 1][j].equals("S")) {
                                        controlador.SuperIncrementarPuntos();
                                    }
                                    mapa[i + 1][j] += "P";
                                    mapa[i][j] = mapa[i][j].replaceAll(" ", "C");
                                    mapa[i][j] = mapa[i][j].replaceAll("S", "C");
                                    mapa[i][j] = mapa[i][j].replaceAll("P", "");
                                    direccion = "abajo";
                                    posicionY = i + 1;
                                    posicionX = j;
                                    coordenadasPacman[0] = posicionY;
                                    coordenadasPacman[1] = posicionX;
                                    return;
                                } else if(mapa[i + 1][j].contains("X")) {
                                    direccionDeseada = direccion;
                                    direccionEnEspera = "abajo";
                                }
                                break;
                            case "derecha":
                                if(mapa[i][j + 1].contains(" ") || mapa[i][j + 1].contains("S") || mapa[i][j + 1].contains("C") || mapa[i][j + 1].contains("1")
                                    || mapa[i][j + 1].contains("2") || mapa[i][j + 1].contains("3") || mapa[i][j + 1].contains("4")) {
                                    controlador.verificarMuerte();
                                    if(mapa[i][j + 1].equals(" ")) {
                                        controlador.incrementarPuntos();
                                    } else if(mapa[i][j + 1].equals("S")) {
                                        controlador.SuperIncrementarPuntos();
                                    }
                                    mapa[i][j + 1] += "P";
                                    mapa[i][j] = mapa[i][j].replaceAll(" ", "C");
                                    mapa[i][j] = mapa[i][j].replaceAll("S", "C");
                                    mapa[i][j] = mapa[i][j].replaceAll("P", "");
                                    direccion = "derecha";
                                    posicionY = i;
                                    posicionX = j + 1;
                                    coordenadasPacman[0] = posicionY;
                                    coordenadasPacman[1] = posicionX;
                                    return;
                                } else if(mapa[i][j + 1].contains("X")) {
                                    direccionDeseada = direccion;
                                    direccionEnEspera = "derecha";
                                }
                                break;
                            case "izquierda":
                                if(mapa[i][j - 1].contains(" ") || mapa[i][j - 1].contains("S") || mapa[i][j - 1].contains("C") || mapa[i][j - 1].contains("1")
                                    || mapa[i][j - 1].contains("2") || mapa[i][j - 1].contains("3") || mapa[i][j - 1].contains("4")) {
                                    controlador.verificarMuerte();
                                    if(mapa[i][j - 1].equals(" ")) {
                                        controlador.incrementarPuntos();
                                    } else if(mapa[i][j - 1].equals("S")) {
                                        controlador.SuperIncrementarPuntos();
                                    }
                                    mapa[i][j - 1] += "P";
                                    mapa[i][j] = mapa[i][j].replaceAll(" ", "C");
                                    mapa[i][j] = mapa[i][j].replaceAll("S", "C");
                                    mapa[i][j] = mapa[i][j].replaceAll("P", "");
                                    direccion = "izquierda";
                                    posicionY = i;
                                    posicionX = j - 1;
                                    coordenadasPacman[0] = posicionY;
                                    coordenadasPacman[1] = posicionX;
                                    return;
                                } else if(mapa[i][j - 1].contains("X")) {
                                    direccionDeseada = direccion;
                                    direccionEnEspera = "izquierda";
                                }
                                break;
                        }
                    }
                }
            }
        }
    }
    
    // Método principal para iniciar el juego
    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        controlador.iniciarJuego();
    }
}