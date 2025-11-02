import java.util.*;

public class Fantasma
{
    private Pacman pacman;
    private Mapa mapa;

    // Constructor que inicializa el mapa y el pacman
    public Fantasma(Mapa mapa, Pacman pacman) {
        this.mapa = mapa;
        this.pacman = pacman;
    }

    /* 
        Algortitmo pathfinder para mover los fantasmas, conocido como A* (A-star).
        Clase anidada que representa un nodo en el camino. Los nodos son las celdas en el mapa.
        Esta clase se utiliza para representar cada celda en el camino que el fantasma tomará para alcanzar a Pacman
        Cada nodo contiene su posición (x, y), los costos g, h y f, y una referencia a su nodo padre.
        El costo g representa la distancia desde el nodo inicial hasta el nodo actual.
        El costo h es una estimación de la distancia desde el nodo actual hasta el nodo objetivo.
        El costo f es la suma de g y h, y se utiliza para determinar el nodo con el menor costo total.
    */
    private class Nodo {
        int x, y; // Coordenadas del nodo en el mapa
        int g, h, f; // Costos g, h y f. Los costos son la distancia de celdas que cuesta llegar al nodo en (x, y)
        Nodo padre; // Referencia al nodo padre

        // Constructor que inicializa las coordenadas, el nodo padre y los costos g y h
        Nodo(int x, int y, Nodo padre, int g, int h) {
            this.x = x; // Coordenada x del nodo actual
            this.y = y; // Coordenada y del nodo actual
            this.padre = padre; // Nodo padre (nodo anterior al actual)
            this.g = g; // Costo desde el nodo inicial hasta este nodo
            this.h = h; // Estimación del costo desde este nodo hasta el nodo objetivo
            this.f = g + h; // Costo total (g + h)
        }

        // Método equals para comparar dos nodos
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true; // Si los objetos son iguales, devuelve true
            if (obj == null || getClass() != obj.getClass()) return false; // Si el objeto es nulo o no es de la misma clase, devuelve false
            Nodo nodo = (Nodo) obj; // Convierte el objeto a Nodo
            return x == nodo.x && y == nodo.y; // Compara las coordenadas x e y
        }

        // Método hashCode para generar un código hash para el nodo
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    // Método para encontrar el camino desde una posición inicial a una posición final
    private List<Nodo> encontrarCamino(int startX, int startY, int endX, int endY) { // endX/endY son las coordenadas de Pacman, y startX/startY las del fantasma
        PriorityQueue<Nodo> abierta = new PriorityQueue<>(Comparator.comparingInt(n -> n.f)); // Cola de prioridad de nodos abiertos, ordenada por el costo total f
        Set<Nodo> cerrada = new HashSet<>(); // Conjunto de nodos cerrados
        abierta.add(new Nodo(startX, startY, null, 0, Math.abs(startX - endX) + Math.abs(startY - endY))); // Añadir el nodo inicial a la cola de prioridad

        while (!abierta.isEmpty()) { // Mientras haya nodos en la cola de prioridad
            Nodo actual = abierta.poll(); // Obtener el nodo con el menor costo total f
            if (actual.x == endX && actual.y == endY) { // Si el nodo actual es el nodo objetivo
                List<Nodo> camino = new ArrayList<>(); // Crear una lista para almacenar el camino
                while (actual != null) { // Mientras el nodo actual no sea nulo
                    camino.add(actual); // Añadir el nodo actual al camino
                    actual = actual.padre; // Moverse al nodo padre
                }
                Collections.reverse(camino); // Invertir el camino para obtener el orden correcto
                return camino; // Devolver el camino
            }

            cerrada.add(actual); // Añadir el nodo actual al conjunto de nodos cerrados

            for (int[] dir : new int[][]{{0, -1}, {0, 1}, {-1, 0}, {1, 0}}) { // Para cada dirección posible (arriba, abajo, izquierda, derecha)
                int nuevoX = actual.x + dir[0]; // Calcular la nueva coordenada x
                int nuevoY = actual.y + dir[1]; // Calcular la nueva coordenada y
                if (nuevoX < 0 || nuevoY < 0 || nuevoX >= mapa.getArrayMapa1().length || nuevoY >= mapa.getArrayMapa1()[0].length) { // Si la coordenada está fuera del mapa
                    continue; // Saltar a la siguiente iteración
                }
                if (mapa.getArrayMapa1()[nuevoX][nuevoY].equals("X")) { // Si la nueva coordenada es un obstáculo
                    continue; // Saltar a la siguiente iteración
                }
                Nodo vecino = new Nodo(nuevoX, nuevoY, actual, actual.g + 1, Math.abs(nuevoX - endX) + Math.abs(nuevoY - endY)); // Crear un nuevo nodo vecino
                if (cerrada.contains(vecino)) { // Si el nodo vecino ya está en el conjunto de nodos cerrados
                    continue; // Saltar a la siguiente iteración
                }
                abierta.add(vecino); // Añadir el nodo vecino a la cola de prioridad
            }
        }
        return null; // Si no se encuentra un camino, devolver null
    }

    // Método para mover un fantasma en una dirección específica
    private void moverFantasma(int fantasma, String idFantasma) {
        String[][] arrayMapa = mapa.getArrayMapa1();
        for (int i = 0; i < arrayMapa.length; i++) {
            for (int j = 0; j < arrayMapa[i].length; j++) {
                if (arrayMapa[i][j].contains(idFantasma)) {
                    List<Nodo> camino = encontrarCamino(i, j, pacman.coordenadasPacman[0], pacman.coordenadasPacman[1]);
                    if (camino != null && camino.size() > 1) {
                        Nodo siguientePaso = camino.get(1);
                        arrayMapa[siguientePaso.x][siguientePaso.y] += idFantasma;
                        arrayMapa[i][j] = arrayMapa[i][j].replaceAll(idFantasma, "");
                    } else {
                        int[][] direcciones = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
                        Collections.shuffle(Arrays.asList(direcciones));
                        for (int[] dir : direcciones) {
                            int nuevoX = i + dir[0];
                            int nuevoY = j + dir[1];
                            if (nuevoX >= 0 && nuevoY >= 0 && nuevoX < arrayMapa.length && nuevoY < arrayMapa[0].length && !arrayMapa[nuevoX][nuevoY].contains("X")) {
                                arrayMapa[nuevoX][nuevoY] += idFantasma;
                                arrayMapa[i][j] = arrayMapa[i][j].replaceAll(idFantasma, "");
                                break;
                            }
                        }
                    }
                    return;
                }
            }
        }
    }

    // Método para mover el fantasma 1
    public void moverFantasma1() {
        moverFantasma(1, "1");
    }

    // Método para mover el fantasma 2
    public void moverFantasma2() {
        moverFantasma(2, "2");
    }

    // Método para mover el fantasma 3
    public void moverFantasma3() {
        moverFantasma(3, "3");
    }

    // Método para mover el fantasma 4
    public void moverFantasma4() {
        moverFantasma(4, "4");
    }

    // Método principal para iniciar el juego
    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        controlador.iniciarJuego();
    }
}
