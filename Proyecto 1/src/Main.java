import gui.auth.LoginFrame;
import service.SistemaBiblioteca;
GameZoneApp
public class Main {
    public static void main(String[] args) {
        SistemaBiblioteca sistema = new SistemaBiblioteca();

        sistema.registrarLibro("LIB001", "El Principito", "Antoine de Saint-Exupéry", 1943, 5);
        sistema.registrarLibro("LIB002", "Don Quijote de la Mancha", "Miguel de Cervantes", 1605, 4);
        sistema.registrarLibro("LIB003", "Cien años de soledad", "Gabriel García Márquez", 1967, 3);
        sistema.registrarLibro("LIB004", "1984", "George Orwell", 1949, 6);
        sistema.registrarLibro("LIB005", "El Hobbit", "J.R.R. Tolkien", 1937, 2);
        sistema.registrarLibro("LIB006", "Matar a un ruiseñor", "Harper Lee", 1960, 4);
        sistema.registrarLibro("LIB007", "Crimen y castigo", "Fiódor Dostoievski", 1866, 3);
        sistema.registrarLibro("LIB008", "Orgullo y prejuicio", "Jane Austen", 1813, 5);
        sistema.registrarLibro("LIB009", "El Gran Gatsby", "F. Scott Fitzgerald", 1925, 4);
        sistema.registrarLibro("LIB010", "Rayuela", "Julio Cortázar", 1963, 2);
        sistema.registrarLibro("LIB011", "La sombra del viento", "Carlos Ruiz Zafón", 2001, 7);
        sistema.registrarLibro("LIB012", "Fahrenheit 451", "Ray Bradbury", 1953, 3);
        sistema.registrarLibro("LIB013", "El amor en los tiempos del cólera", "Gabriel García Márquez", 1985, 4);
        sistema.registrarLibro("LIB014", "La metamorfosis", "Franz Kafka", 1915, 6);
        sistema.registrarLibro("LIB015", "Moby Dick", "Herman Melville", 1851, 2);
        sistema.registrarLibro("LIB016", "Las aventuras de Sherlock Holmes", "Arthur Conan Doyle", 1892, 5);
        sistema.registrarLibro("LIB017", "Drácula", "Bram Stoker", 1897, 3);
        sistema.registrarLibro("LIB018", "Frankenstein", "Mary Shelley", 1818, 4);
        sistema.registrarLibro("LIB019", "El alquimista", "Paulo Coelho", 1988, 8);
        sistema.registrarLibro("LIB020", "Harry Potter y la piedra filosofal", "J.K. Rowling", 1997, 5);

        sistema.cargarUsuariosDesdeArchivo();
        sistema.cargarPrestamosDesdeArchivo();

        LoginFrame Login = new LoginFrame(sistema);
        Login.setVisible(true);
    }
}