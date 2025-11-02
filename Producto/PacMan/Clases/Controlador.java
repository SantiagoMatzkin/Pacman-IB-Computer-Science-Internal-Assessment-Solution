import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controlador implements KeyListener
{
    private final Mapa mapa;
    private final Ventana ventana;
    public static boolean juegoIniciado;
    private final Pacman pacman;
    private int puntos;
    private final Fantasma fantasmas;
    private int vidasRestantes = 3;

    String[][] arrayMapa;

    public Controlador() {
        mapa = new Mapa();
        ventana = new Ventana(this);
        pacman = new Pacman(this);
        fantasmas = new Fantasma(mapa, pacman);
        puntos = 0;
        arrayMapa = mapa.getArrayMapa1();
        ventana.addKeyListener(this);
        ventana.setFocusable(true);
        ventana.requestFocusInWindow();
    }
    
    public void iniciarJuego() {
        juegoIniciado = true;
        ventana.renderizar();
        
        new Thread(() -> {
            try {
            new Thread(this::iniciarFantasma1).start();
            Thread.sleep(3500);
            new Thread(this::iniciarFantasma2).start();
            Thread.sleep(3500);
            new Thread(this::iniciarFantasma3).start();
            Thread.sleep(3500);
            new Thread(this::iniciarFantasma4).start();
            } catch (InterruptedException e) {
            e.printStackTrace();
            }
        }).start();

        while(juegoIniciado) {
            pacman.mover(Pacman.direccionDeseada);
            verificarMuerte();
            reaparecerComida();
            ventana.renderizar();
            try {
                Thread.sleep(120);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void iniciarFantasma1() {
        while(juegoIniciado) {
            fantasmas.moverFantasma1();
            ventana.renderizar();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void iniciarFantasma2() {
        while(juegoIniciado) {
            fantasmas.moverFantasma2();
            ventana.renderizar();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void iniciarFantasma3() {
        while(juegoIniciado) {
            fantasmas.moverFantasma3();
            ventana.renderizar();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void iniciarFantasma4() {
        while(juegoIniciado) {
            fantasmas.moverFantasma4();
            ventana.renderizar();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Mapa getMapa() {
        return mapa;
    }
    
    public void incrementarPuntos() {
        puntos += 10;
    }

    public void SuperIncrementarPuntos() {
        puntos += 150;
    }
    
    public int getPuntos() {
        return puntos;
    }

    public void verificarMuerte() {
        for(int i = 0; i < arrayMapa.length; i++) {
            for(int j = 0; j < arrayMapa[i].length; j++) {
                if(arrayMapa[i][j].contains("P") && 
                   (arrayMapa[i][j].contains("1") || 
                    arrayMapa[i][j].contains("2") || 
                    arrayMapa[i][j].contains("3") || 
                    arrayMapa[i][j].contains("4"))) {
                    muertePacman();
                }
            }
        }
    }
    
    public void muertePacman() {
        vidasRestantes--;
        if(vidasRestantes <= 0) {
            try {
                Thread.sleep(121);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            juegoIniciado = false;
            System.out.println("Vidas restantes: " + vidasRestantes + ". GAME OVER");
            ventana.renderizar();
        } else if(vidasRestantes > 0){
            try {
                Thread.sleep(120); // Esperar al último movimiento antes de morir
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            juegoIniciado = false;
            System.out.println("Pacman ha muerto. Vidas restantes: " + vidasRestantes);
            reaparecerPacman();
            reaparecerFantasmas();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reaparecerPacman();
            juegoIniciado = true;
        }
    }

    private void reaparecerPacman() { // Reestablecer la posición de Pacman
        for(int i = 0; i < arrayMapa.length; i++) {
            for(int j = 0; j < arrayMapa[i].length; j++) {
                if(arrayMapa[i][j].contains("P")) {
                    arrayMapa[i][j] = arrayMapa[i][j].replace("P", "");
                }
            }
        }
        arrayMapa[13][15] = "PC";
        pacman.coordenadasPacman[0] = 13;
        pacman.coordenadasPacman[1] = 15;
        
    }

    public void reaparecerFantasmas() { // Restablecer las posiciones de los fantasmas
        for(int i = 0; i < arrayMapa.length; i++) {
            for(int j = 0; j < arrayMapa[i].length; j++) {
                if(arrayMapa[i][j].contains("1")) {
                    arrayMapa[i][j] = arrayMapa[i][j].replace("1", "");
                }
                if(arrayMapa[i][j].contains("2")) {
                    arrayMapa[i][j] = arrayMapa[i][j].replace("2", "");
                }
                if(arrayMapa[i][j].contains("3")) {
                    arrayMapa[i][j] = arrayMapa[i][j].replace("3", "");
                }
                if(arrayMapa[i][j].contains("4")) {
                    arrayMapa[i][j] = arrayMapa[i][j].replace("4", "");
                }
            }
        }
        arrayMapa[7][14] = "1 ";
        arrayMapa[7][16] = "2 ";
        arrayMapa[9][14] = "3 ";
        arrayMapa[9][16] = "4 ";
        
        if (!juegoIniciado) {
            new Thread(() -> {
            try {
                Thread.sleep(3500);
                new Thread(this::iniciarFantasma1).start();
                Thread.sleep(3500);
                new Thread(this::iniciarFantasma2).start();
                Thread.sleep(3500);
                new Thread(this::iniciarFantasma3).start();
                Thread.sleep(3500);
                new Thread(this::iniciarFantasma4).start();
                Thread.sleep(3500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            }).start();
        }
    }

    public void reaparecerComida() {
        boolean hayComida = false;
        for(int i = 0; i < arrayMapa.length; i++) {
            for(int j = 0; j < arrayMapa[i].length; j++) {
                if(arrayMapa[i][j].contains(" ") || arrayMapa[i][j].contains("S")) {
                    hayComida = true;
                    break;
                }
            }
            if(hayComida) { break; }
        }

        if (!hayComida) {
            for (int i = 0; i < arrayMapa.length; i++) {
                for (int j = 0; j < arrayMapa[i].length; j++) {
                    arrayMapa[i][j] = arrayMapa[i][j].replaceAll("C", " ");
                }
            }
        }
    }

    public int getVidasRestantes() {
        return vidasRestantes;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode) {
            case KeyEvent.VK_UP:
                Pacman.direccionDeseada = "arriba";
                break;
            case KeyEvent.VK_DOWN:
                Pacman.direccionDeseada = "abajo";
                break;
            case KeyEvent.VK_RIGHT:
                Pacman.direccionDeseada = "derecha";
                break;
            case KeyEvent.VK_LEFT:
                Pacman.direccionDeseada = "izquierda";
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        controlador.iniciarJuego();
    }
}
