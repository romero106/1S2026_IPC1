import java.util.Scanner;
import java.util.Random;

public class Main {
    static String vctrUsername[] = new String[50];
    static int vctrPunteo[] = new int[50];
    static int historialTotal = 0;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int op = 0;

        while(op != 3){
            System.out.println("================ MENU ================");
            System.out.println("1. Iniciar juego");
            System.out.println("2. Historial de partidas");
            System.out.println("3. Salir");
            System.out.print("Ingrese la opcion que desee: ");
            op = sc.nextInt();
            sc.nextLine();

            switch(op) {
                case 1: parametrosJuego(sc); break;
                case 2: historial(sc); break;
                case 3: System.out.println("\n¡Gracias por jugar!"); break;
                default: System.out.println("Ingrese una opcion valida...\n"); break;
            }
        }
        sc.close();
    }

    public static void parametrosJuego(Scanner sc){
        Random rand = new Random();
        
        String username = "", tamano = "";
        int m = 0, n = 0, total = 0, premios = 0, paredes = 0, enemigos = 0, punteo = 0, vidas = 3, playerm = -1, playern = -1;

        System.out.println("=======================================");
        System.out.println("        PARAMETROS PARA EL JUEGO       ");
        System.out.println("=======================================");
        
        while(username.trim().isEmpty()){ //trim elimina espacios al inicio y al final
            System.out.print("Ingrese su nombre: ");
            username = sc.nextLine();

            if(username.trim().isEmpty()){
                System.out.println("El nombre no puede estar vacío");
            }
        }

        System.out.println("Tablero:"); //definir tamano del tablero
        while(!tamano.equals("P") && !tamano.equals("G")){
            System.out.println("'P' para un tablero pequeño");
            System.out.println("'G' para un tablero grande");
            System.out.print("Tamaño del tablero: ");
            tamano = sc.nextLine().toUpperCase(); //toUpperCase vuelve a mayuscula
        }

        if(tamano.equals("P")){ //valores para un tablero pequeno
            m = 5; 
            n = 6;
            total = m*n;
        }
        
        if(tamano.equals("G")){ //valores para un tablero grande
            m = 10;
            n = 10;
            total = m*n;
        }

        System.out.println("=======================================");
        System.out.println("CANTIDAD DE PREMIOS \nSe mide por porcentaje (40%)");
        while(premios < 1 || premios > (total*0.4)){
            System.out.print("Escoja entre 1.00 - "+(total*0.4)+": ");
            premios = sc.nextInt(); //cantidad de premios en el tablero
        }
        int premiosEspeciales = (int)Math.round(premios * 0.2);
        int premiosNormales = premios - premiosEspeciales;

        if(premiosNormales < 0){
            premiosNormales = 0;
        }

        System.out.println("=======================================");
        System.out.println("CANTIDAD DE PAREDES \nSe mide por porcentaje (20%)");
        while(paredes < 1 || paredes > (total*0.2)){
            System.out.print("Escoja entre 1.00 - "+(total*0.2)+": ");
            paredes = sc.nextInt(); //cantidad de paredes en el tablero
        }

        System.out.println("=======================================");
        System.out.println("CANTIDAD DE ENEMIGOS \nSe mide por porcentaje (20%)");
        while(enemigos < 1 || enemigos > (total*0.2)){
            System.out.print("Escoja entre 1.00 - "+(total*0.2)+": ");
            enemigos = sc.nextInt(); //cantidad de enemigos en el tablero
        }
        
        String tablero[][] = new String[m][n];

        for(int i = 1; i <= premiosNormales; i++){ //premios normales
            int randm = rand.nextInt(m);
            int randn = rand.nextInt(n);

            if(tablero[randm][randn] == null){
                tablero[randm][randn] = "0";
            }
            else{
                i = i - 1;
            }
        }

        for(int i = 1; i <= premiosEspeciales; i++){ //premios especiales
            int randm = rand.nextInt(m);
            int randn = rand.nextInt(n);

            if(tablero[randm][randn] == null){
                tablero[randm][randn] = "$";
            }
            else{
                i = i - 1;
            }
        }
        for(int i = 1; i <= paredes; i++){ //paredes en el tablero
            int randm = rand.nextInt(m);
            int randn = rand.nextInt(n);

            if(tablero[randm][randn] == null){
                tablero[randm][randn] = "X";
            }
            else{
                i = i - 1;
            }
        }
        for(int i = 1; i <= enemigos; i++){ //enemigos en el tablero
            int randm = rand.nextInt(m);
            int randn = rand.nextInt(n);

            if(tablero[randm][randn] == null){
                tablero[randm][randn] = "@";
            }
            else{
                i = i - 1;
            }
        }
        for(int i = 0; i < tablero.length; i++){ //espacios vacios
            for(int j = 0; j < tablero[i].length; j++){
                if(tablero[i][j] == null){
                    tablero[i][j] = " ";
                }
            }
        }

        if(tamano.equals("P")){
            System.out.println("=======================================");
            System.out.println("Usuario: "+username);
            System.out.println("Punteo: "+punteo);
            System.out.println("Vidas: "+vidas);
            System.out.println("=======================================");
            for(int i = 0; i < tablero.length; i++){
                for(int j = 0; j < tablero[i].length; j++){
                    System.out.print(tablero[i][j] + ("   "));
                }
                System.out.println("");
            }
            System.out.println("------------------------");
        }

        if(tamano.equals("G")){
            System.out.println("=======================================");
            System.out.println("Usuario: "+username);
            System.out.println("Punteo: "+punteo);
            System.out.println("Vidas: "+vidas);
            System.out.println("-------------------------------------");
            for(int i = 0; i < tablero.length; i++){
                for(int j = 0; j < tablero[i].length; j++){
                    System.out.print(tablero[i][j] + ("   "));
                }
                System.out.println("");
            }
            System.out.println("-------------------------------------");
        }

        boolean jugar = false;

        System.out.println("¿En que posicion desea aparecer?");
        while(!jugar){
            while(playerm < 0 || playerm >= m){
                System.out.print("Fila (0 - "+(m-1)+"): ");
                playerm = sc.nextInt();
            }
        
            while(playern < 0 || playern >= n){
                System.out.print("Columna (0 - "+(n-1)+"): ");
                playern = sc.nextInt();
            }

            if(tablero[playerm][playern].equals(" ")){
                tablero[playerm][playern] = "<";
                jugar = true;
            }
            else{
                System.out.println("La casilla está ocupada, intente con otra.");
                playerm = -1;
                playern = -1;
            }
        }
        
        if(tamano.equals("P")){
            juegoP(sc,tablero,username,punteo,vidas,playerm,playern,premios);
        }

        if(tamano.equals("G")){
            juegoG(sc,tablero,username,punteo,vidas,playerm,playern);
        }
        
    }

    public static int juegoP(Scanner sc, String tablero[][], String username, int punteo, int vidas, int playerm, int playern, int premios){
        int mov, newm = 0, newn = 0;
        while(vidas > 0 && win(tablero)){
            newm = playerm;
            newn = playern;
            System.out.println("------------------------");
            System.out.println("Usuario: "+username);
            System.out.println("Punteo: "+punteo);
            System.out.println("Vidas: "+vidas);
            System.out.println("------------------------");
            for(int i = 0; i < tablero.length; i++){
                for(int j = 0; j < tablero[i].length; j++){
                    System.out.print(tablero[i][j] + ("   "));
                }
                System.out.println("");
            }
            System.out.println("------------------------");
            System.out.print("Mover [8][2][4][6]\nPausa [5]\nAccion: ");
            mov = sc.nextInt();

            switch(mov){
                case 8: newm = playerm - 1; System.out.println("Te mueves arriba."); break;
                case 2: newm = playerm + 1; System.out.println("Te mueves abajo."); break;
                case 4: newn = playern - 1; System.out.println("Te mueves izquierda."); break;
                case 6: newn = playern + 1; System.out.println("Te mueves derecha."); break;
                case 5: 
                    int accion = menuPausa(sc);
                    if(accion == 1){
                        continue; //reanudar juego
                    } 
                    else{
                        if(punteo > 0){
                            guardarPartida(username, punteo);
                        }
                    return punteo; //volver al menu
                    }
                default:
                    System.out.println("Tecla incorrecta");
                    continue; //vuelve al while sin mover
            }

            newm = (newm + tablero.length) % tablero.length;
            newn = (newn + tablero[0].length) % tablero[0].length;

            if(tablero[newm][newn].equals("X")){
                System.out.println("Choque con pared, no te mueves.");
                continue;
            }            
            if(tablero[newm][newn].equals("0")){
                punteo = punteo + 10;
            } 
            if(tablero[newm][newn].equals("$")){
                punteo = punteo + 15;
            }
            if(tablero[newm][newn].equals("@")){
                vidas = vidas - 1;
            }
            //mover al jugadpr
            tablero[playerm][playern] = " ";
            playerm = newm;
            playern = newn;
            tablero[playerm][playern] = "<";
        }
        if(vidas == 0){
            System.out.println("\n¡PERDISTE! Mejor suerte la proxima.");
        }
        else{
            System.out.println("\n¡GANASTE! No quedan premios.");
        }
        guardarPartida(username, punteo);
        sc.nextLine(); // limpiar
        System.out.println("Presione ENTER para volver al menú...");
        sc.nextLine();

        return(punteo);
    }    

    public static int juegoG(Scanner sc, String tablero[][], String username, int punteo, int vidas, int playerm, int playern){
        int mov, newm = 0, newn = 0;
        while(vidas > 0 && win(tablero)){
            newm = playerm;
            newn = playern;
            System.out.println("-------------------------------------");
            System.out.println("Usuario: "+username);
            System.out.println("Punteo: "+punteo);
            System.out.println("Vidas: "+vidas);
            System.out.println("-------------------------------------");
            for(int i = 0; i < tablero.length; i++){
                for(int j = 0; j < tablero[i].length; j++){
                    System.out.print(tablero[i][j] + ("   "));
                }
                System.out.println("");
            }
            System.out.println("-------------------------------------");
            System.out.print("Mover [8][2][4][6]\nPausa [5]\nAccion: ");
            mov = sc.nextInt();

            switch(mov){
                case 8: newm = playerm - 1; System.out.println("Te mueves arriba."); break;
                case 2: newm = playerm + 1; System.out.println("Te mueves abajo."); break;
                case 4: newn = playern - 1; System.out.println("Te mueves izquierda."); break;
                case 6: newn = playern + 1; System.out.println("Te mueves derecha."); break;
                case 5: 
                    int accion = menuPausa(sc);
                    if(accion == 1){
                        continue; //reanudar juego
                    } 
                    else{
                        if(punteo > 0){
                            guardarPartida(username, punteo);
                        }
                    return punteo; //volver al menu
                    }
                default:
                    System.out.println("Tecla incorrecta.");
                    continue; //vuelve al while sin mover
            }

            newm = (newm + tablero.length) % tablero.length;
            newn = (newn + tablero[0].length) % tablero[0].length;

            if(tablero[newm][newn].equals("X")){
                System.out.println("Choque con pared, no te mueves.");
                continue;
            }            
            if(tablero[newm][newn].equals("0")){
                punteo = punteo + 10;
            } 
            if(tablero[newm][newn].equals("$")){
                punteo = punteo + 15;
            }
            if(tablero[newm][newn].equals("@")){
                vidas = vidas - 1;
            }
            //mover al jugador
            tablero[playerm][playern] = " ";
            playerm = newm;
            playern = newn;
            tablero[playerm][playern] = "<";
        }
        if(vidas == 0){
            System.out.println("\n¡PERDISTE! Mejor suerte la proxima.");
        }
        else{
            System.out.println("\n¡GANASTE! No quedan premios.");
        }
        guardarPartida(username, punteo);
        sc.nextLine(); // limpiar
        System.out.println("Presione ENTER para volver al menú...");
        sc.nextLine();
    
        return(punteo);
    }

    public static int menuPausa(Scanner sc){
    int op = 0;
    while(op < 1 || op > 2){
        System.out.println("================ PAUSA ===============");
        System.out.println("1. Reanudar");
        System.out.println("2. Volver al menu principal");
        System.out.print("Elige una opción: ");
        op = sc.nextInt();

        if(op < 1 || op > 2){
            System.out.println("Opción inválida.");
        }
    }
    return op;
}

    public static boolean win(String[][] tablero){
        for(int i = 0; i < tablero.length; i++){
            for(int j = 0; j < tablero[i].length; j++){
                if(tablero[i][j].equals("0") || tablero[i][j].equals("$")){
                    return true; //aun hay premios
                }
            }
        }
        return false; //ya no hay premios
    }

    public static void guardarPartida(String username, int punteo){
        if(historialTotal < 50){
            vctrUsername[historialTotal] = username;
            vctrPunteo[historialTotal] = punteo;
            historialTotal = historialTotal + 1;
        }
    }

    public static void historial(Scanner sc){
        System.out.println("=======================================");
        System.out.println("        HISTORIAL DE PARTIDAS       ");
        System.out.println("=======================================");

        if(historialTotal == 0){
            System.out.println("No hay partidas registradas...");
        } 
        else{
            System.out.println("NO.  NOMBRE         PUNTEO");
            for(int i = historialTotal - 1; i >= 0; i--){
                System.out.printf("%-4d %-14s %-5d\n", (historialTotal - i), vctrUsername[i], vctrPunteo[i]);
            }
        }
        System.out.println("=======================================");
        System.out.print("Presione ENTER para volver al menú...");
        sc.nextLine();
    }
}