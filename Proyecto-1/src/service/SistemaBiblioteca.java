package service;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.Libro;
import model.Prestamo;
import model.Usuario;

public class SistemaBiblioteca {
    // ATRIBUTOS
    private Libro[] libros;
    private Usuario[] usuarios;
    private Prestamo[] prestamos;

    private int totalLibros;
    private int totalUsuarios;
    private int totalPrestamos;

    private DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formato de fecha

    private final String BASE_PATH = System.getProperty("user.dir") + File.separator;
    private final String DATA_PATH = BASE_PATH + "data" + File.separator;
    private final String LOGS_PATH = BASE_PATH + "logs" + File.separator;
    private final String REPORTS_PATH = BASE_PATH + "reports" + File.separator;
    // CONSTRUCTOR
    public SistemaBiblioteca() {
        libros = new Libro[100];
        usuarios = new Usuario[100];
        prestamos = new Prestamo[100];

        totalLibros = 0;
        totalPrestamos = 0;
        totalUsuarios = 0;

        usuarios[totalUsuarios] = new Usuario("admin", "Administrador", "N/A", "admin","Administrador");
        totalUsuarios++;
    }
    // METODOS
    /*===============================================================================*/
    /*                                    LIBROS                                     */
    /*===============================================================================*/
    public boolean registrarLibro(String codigo, String titulo, String autor, int anio, int cantidad) {
        if(totalLibros < libros.length) {
            if(buscarLibro(codigo) == null) {
                libros[totalLibros] = new Libro(codigo, titulo, autor, anio, cantidad);
                totalLibros++;
                return true;
            }
        }
        return false;
    }
    /*===============================================================================*/
    public Libro buscarLibro(String codigoLBuscado) {
        for(int i = 0; i < totalLibros; i++) {
            if(libros[i].getCodigo().equals(codigoLBuscado)) {
                return libros[i];
            }
        }
        return null;
    }
    /*===============================================================================*/
    public String mostrarLibros() {
        if(totalLibros == 0) {
            return "No hay libros registrados.";
        } else {
            String mostrarLibros = "";
            
            for(int i = 0; i < totalLibros; i++) {
                mostrarLibros = mostrarLibros + libros[i].getInformacion() + "\n";
            }
            return mostrarLibros;
        }
    }
    /*===============================================================================*/
    public boolean tienePrestamosActivosLibro(String codigoLibro) {
        for(int i = 0; i < totalPrestamos; i++) {
            if(prestamos[i].getLibro().getCodigo().equals(codigoLibro) && prestamos[i].estaActivo()) {
                return true;
            }
        }
        return false;
    }
    /*===============================================================================*/
    public int contarPrestamosActivosDeLibro(String codigoLibro) {
        int contador = 0;

        for(int i = 0; i < totalPrestamos; i++) {
            if(prestamos[i].getLibro().getCodigo().equals(codigoLibro) && prestamos[i].estaActivo()) {
                contador++;
            }
        }
        return contador;
    }
    /*===============================================================================*/
    public String validarEliminarLibro(String codigoBuscado) {
        Libro libroEncontrado = buscarLibro(codigoBuscado);

        if(libroEncontrado == null) {
            return "El libro no existe.";
        }

        if(tienePrestamosActivosLibro(codigoBuscado)) {
            return "No se puede eliminar: el libro tiene préstamos activos.";
        }

        return "OK";
    }
    /*===============================================================================*/
    public boolean eliminarLibro(String codigoBuscado) {
        String validacion = validarEliminarLibro(codigoBuscado);

        if(!validacion.equals("OK")) {
            return false;
        }

        for(int i = 0; i < totalLibros; i++) {
            if(libros[i].getCodigo().equals(codigoBuscado)) {
                for(int j = i; j < totalLibros - 1; j++) {
                    libros[j] = libros[j + 1];
                }
                libros[totalLibros - 1] = null;
                totalLibros--;
                return true;
            }
        }
        return false;
    }
    /*===============================================================================*/
    public String validarModificarLibro(String codigoLibro, int nuevaCantidad) {
        Libro libroEncontrado = buscarLibro(codigoLibro);

        if(libroEncontrado == null) {
            return "El libro no existe.";
        }

        int prestados = contarPrestamosActivosDeLibro(codigoLibro);
        
        if(nuevaCantidad < prestados) {
            return "La nueva cantidad no puede ser menor a los libros actualmente prestados (" + prestados + ").";
        }
        return "OK";
    }
    /*===============================================================================*/
    public boolean modificarLibro(String codigoLibro, String nuevoTitulo, String nuevoAutor, int nuevoAnio, int nuevaCantidad) {
        String validacion = validarModificarLibro(codigoLibro, nuevaCantidad);

        if(!validacion.equals("OK")) {
            return false;
        }

        Libro libroEncontrado = buscarLibro(codigoLibro);

        if(libroEncontrado != null) {
            libroEncontrado.setTitulo(nuevoTitulo);
            libroEncontrado.setAutor(nuevoAutor);
            libroEncontrado.setAnio(nuevoAnio);
            libroEncontrado.setCantidad(nuevaCantidad);
            return true;
        }
        return false;
    }
    /*===============================================================================*/
    /*                                   USUARIOS                                    */
    /*===============================================================================*/ 
    public boolean registrarUsuario(String carne, String nombre, String carrera, String contrasena, String rol) {
        if(totalUsuarios < usuarios.length) {
            if(buscarUsuario(carne) == null) {
                usuarios[totalUsuarios] = new Usuario(carne, nombre, carrera, contrasena, rol);
                totalUsuarios++;
                guardarUsuariosEnArchivo();
                return true;
            }
        }
        return false;
    }
    /*===============================================================================*/
    public Usuario buscarUsuario(String usuarioBuscado) {
        for(int i = 0; i < totalUsuarios; i++) {
            if(usuarios[i].getCarne().equals(usuarioBuscado)) {
                return usuarios[i];
            }
        }
        return null;
    }
    /*===============================================================================*/
    public String mostrarEstudiantes() {
        if(totalUsuarios == 0) {
            return "No hay usuarios registrados.";
        } else {
            String mostrarEstudiantes = "";
            for(int i = 0; i < totalUsuarios; i++) {
                if(usuarios[i].getRol().equals("Estudiante")) {
                    mostrarEstudiantes = mostrarEstudiantes + usuarios[i].getInformacion() + "\n";
                }
            }
            return mostrarEstudiantes;
        }
    }
    /*===============================================================================*/
    public String mostrarOperadores() {
        if(totalUsuarios == 0) {
            return "No hay usuarios registrados.";
        } else {
            String mostrarOperadores = "";
            for(int i = 0; i < totalUsuarios; i++) {
                if(usuarios[i].getRol().equals("Operador")) {
                    mostrarOperadores = mostrarOperadores + usuarios[i].getInformacion() + "\n";
                }
            }
            return mostrarOperadores;
        }
    }
    /*===============================================================================*/
    public String validarEliminarOperador(String carneBuscado) {
        Usuario usuarioEncontrado = buscarUsuario(carneBuscado);

        if(usuarioEncontrado == null) {
            return "El operador no existe.";
        }

        if(!usuarioEncontrado.getRol().equals("Operador")) {
            return "El usuario ingresado no es un operador.";
        }

        return "OK";
    }
    /*===============================================================================*/
    public boolean eliminarOperador(String carneBuscado) {
        String validacion = validarEliminarOperador(carneBuscado);

        if(!validacion.equals("OK")) {
            return false;
        }

        for(int i = 0; i < totalUsuarios; i++) {
            if(usuarios[i].getCarne().equals(carneBuscado) && usuarios[i].getRol().equals("Operador")) {
                for(int j = i; j < totalUsuarios - 1; j++) {
                    usuarios[j] = usuarios[j + 1];
                }
                usuarios[totalUsuarios - 1] = null;
                totalUsuarios--;
                guardarUsuariosEnArchivo();
                return true;
            }
        }
        return false;
    }
    /*===============================================================================*/
    public String validarEliminarEstudiante(String carneBuscado) {
        Usuario usuarioEncontrado = buscarUsuario(carneBuscado);

        if (usuarioEncontrado == null) {
            return "El estudiante no existe.";
        }

        if (!usuarioEncontrado.getRol().equals("Estudiante")) {
            return "El usuario ingresado no es un estudiante.";
        }

        if (usuarioTienePrestamosActivos(carneBuscado)) {
            return "No se puede eliminar: el estudiante tiene préstamos activos.";
        }

        if (usuarioTienePrestamosVencidos(carneBuscado)) {
            return "No se puede eliminar: el estudiante tiene préstamos vencidos.";
        }

        return "OK";
    }
    /*===============================================================================*/
    public boolean eliminarEstudiante(String carneBuscado) {
        String validacion = validarEliminarEstudiante(carneBuscado);

        if(!validacion.equals("OK")) {
            return false;
        }

        for(int i = 0; i < totalUsuarios; i++) {
            if(usuarios[i].getCarne().equals(carneBuscado) && usuarios[i].getRol().equals("Estudiante")) {
                for(int j = i; j < totalUsuarios - 1; j++) {
                    usuarios[j] = usuarios[j + 1];
                }
                usuarios[totalUsuarios - 1] = null;
                totalUsuarios--;
                guardarUsuariosEnArchivo();
                return true;
            }
        }
        return false;
    }
    /*===============================================================================*/
    public String consultarEstudiante(String carneBuscado) {
        Usuario usuarioEncontrado = buscarUsuario(carneBuscado);

        if (usuarioEncontrado == null) {
            return "El estudiante no existe.";
        }

        if (!usuarioEncontrado.getRol().equals("Estudiante")) {
            return "El usuario ingresado no es un estudiante.";
        }

        int activos = contarPrestamosActivosUsuario(carneBuscado);
        int vencidos = contarPrestamosVencidosUsuario(carneBuscado);

        return "Carné: " + usuarioEncontrado.getCarne() +
                "\nNombre: " + usuarioEncontrado.getNombre() +
                "\nCarrera: " + usuarioEncontrado.getCarrera() +
                "\nPréstamos activos: " + activos +
                "\nPréstamos vencidos: " + vencidos;
    }
    /*===============================================================================*/
    public Usuario iniciarSesion(String carneIngresado, String contrasenaIngresada) {
        for (int i = 0; i < totalUsuarios; i++) {
            if (usuarios[i].getCarne().equals(carneIngresado) && usuarios[i].validarContrasena(contrasenaIngresada)) {
                return usuarios[i];
            }
        }
        return null;
    }
    /*===============================================================================*/
    /*                                   PRESTAMOS                                   */
    /*===============================================================================*/
    public boolean usuarioTienePrestamosActivos(String carneBuscado) {
        for(int i = 0; i < totalPrestamos; i++) {
            if(prestamos[i].getCarneUsuario().equals(carneBuscado) && prestamos[i].estaActivo()) {
                return true;
            }
        }
        return false;
    }
    /*===============================================================================*/
    public int contarPrestamosActivosUsuario(String carneBuscado) {
        int contador = 0;
        for(int i = 0; i < totalPrestamos; i++) {
            if(prestamos[i].getCarneUsuario().equals(carneBuscado) && prestamos[i].estaActivo()) {
                contador++;
            }
        }
        return contador;
    }
    /*===============================================================================*/
    public boolean usuarioTienePrestamosVencidos(String carneBuscado) {
        for(int i = 0; i < totalPrestamos; i++) {
            if(prestamos[i].getCarneUsuario().equals(carneBuscado) && prestamos[i].estaVencido()) {
                return true;
            }
        }
        return false;
    }
    /*===============================================================================*/
    public int contarPrestamosVencidosUsuario(String carneBuscado) {
        int contador = 0;
        for(int i = 0; i < totalPrestamos; i++) {
            if(prestamos[i].getCarneUsuario().equals(carneBuscado) && prestamos[i].estaVencido()) {
                contador++;
            }
        }
        return contador;
    }
    /*===============================================================================*/
    public String validarPrestamo(String usuarioBuscado, String codigoLBuscado) {
        Usuario usuarioEncontrado = buscarUsuario(usuarioBuscado);
        Libro libroEncontrado = buscarLibro(codigoLBuscado);

        if(usuarioEncontrado == null) {
            return "El usuario no existe.";
        }

        if(libroEncontrado == null) {
            return "El libro no existe.";
        }

        if(contarPrestamosActivosUsuario(usuarioBuscado) >= 3) {
            return "El estudiante ya tiene 3 préstamos activos.";
        }

        if(usuarioTienePrestamosVencidos(usuarioBuscado)) {
            return "El estudiante tiene préstamos vencidos y no puede solicitar otro.";
        }

        if(libroEncontrado.getCantidad() <= 0) {
            return "No hay ejemplares disponibles de este libro.";
        }

        if(totalPrestamos >= prestamos.length) {
            return "No hay espacio para más préstamos.";
        }

        return "OK";
    }
    /*===============================================================================*/
    public boolean registrarPrestamo(String codigoPrestamo, String usuarioBuscado, String codigoLBuscado, String fechaPrestamo) {
        if(buscarPrestamo(codigoPrestamo) != null) {
            return false;
        }

        String validacion = validarPrestamo(usuarioBuscado, codigoLBuscado);

        if(!validacion.equals("OK")) {
            return false;
        }

        Usuario usuarioEncontrado = buscarUsuario(usuarioBuscado);
        Libro libroEncontrado = buscarLibro(codigoLBuscado);

        if(libroEncontrado.prestarLibro()) {
            prestamos[totalPrestamos] = new Prestamo(codigoPrestamo, usuarioEncontrado, libroEncontrado, fechaPrestamo);
            totalPrestamos++;
            guardarPrestamosEnArchivo();
            return true;
        }
        return false;
    }
    /*===============================================================================*/
    public Prestamo buscarPrestamo(String codigoPrestamoBuscado) {
        for(int i = 0; i < totalPrestamos; i++) {
            if(prestamos[i].getCodigoPrestamo().equals(codigoPrestamoBuscado)) {
                return prestamos[i];
            }
        }
        return null;
    }
    /*===============================================================================*/
    public String mostrarPrestamos() {
        if(totalPrestamos == 0) {
            return "No hay préstamos registrados.";
        } else {
            String mostrarPrestamos = "";

            for(int i = 0; i < totalPrestamos; i++) {
                mostrarPrestamos = mostrarPrestamos + prestamos[i].getInformacion() + "\n";
            }
            return mostrarPrestamos;
        }
    }
    /*===============================================================================*/
    public String mostrarPrestamosActivos() {
        String resultado = "";

        for(int i = 0; i < totalPrestamos; i++) {
            if(prestamos[i].estaActivo()) {
                resultado = resultado + prestamos[i].getInformacion() + "\n";
            }
        }

        if(resultado.equals("")) {
            return "No hay préstamos activos.";
        }
        return resultado;
    }
    /*===============================================================================*/
    public String mostrarPrestamosPorUsuario(String carneBuscado) {
        String resultado = "";

        for(int i = 0; i < totalPrestamos; i++) {
            if(prestamos[i].getCarneUsuario().equals(carneBuscado)) {
                resultado = resultado + prestamos[i].getInformacion() + "\n";
            }
        }

        if(resultado.equals("")) {
            return "No hay préstamos registrados para este estudiante.";
        }
        return resultado;
    }
    /*===============================================================================*/
    public boolean devolverPrestamo(String codigoPrestamoBuscado) {
        Prestamo prestamoEncontrado = buscarPrestamo(codigoPrestamoBuscado);

        if(prestamoEncontrado != null) {
            String fechaHoy = LocalDate.now().format(FORMATO);
            boolean devuelto = prestamoEncontrado.registrarDevolucion(fechaHoy);

            if(devuelto) {
                guardarPrestamosEnArchivo();
            }

            return devuelto;
        }
        return false;
    }
    /*===============================================================================*/
    /*                                   BITACORA                                    */
    /*===============================================================================*/    
    public void registrarBitacora(String operacion, String usuario, String modulo) {
        try {
            crearCarpetaLogs();

            File archivo = new File(LOGS_PATH + "bitacora.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));

            LocalDateTime ahora = LocalDateTime.now();
            String fecha = ahora.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
            String hora = ahora.format(DateTimeFormatter.ofPattern("hh:mm a"));

            String registro = "[" + operacion + "]" +
                    "[" + usuario + "]" +
                    "[" + modulo + "]" +
                    "[FECHA " + fecha + "]" +
                    "[HORA " + hora + "]";

            bw.write(registro);
            bw.newLine();
            bw.close();

        } catch (IOException e) {
            System.out.println("Error en bitácora: " + e.getMessage());
        }
    }
    /*===============================================================================*/
    /*                                REPORTE HTML                                   */
    /*===============================================================================*/
    public boolean generarReporteBitacoraHTML() {
        try {
            crearCarpetaLogs();
            crearCarpetaReports();

            File archivoBitacora = new File(LOGS_PATH + "bitacora.txt");

            if (!archivoBitacora.exists()) {
                return false;
            }

            BufferedReader br = new BufferedReader(new FileReader(archivoBitacora));

            String fechaArchivo = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            File archivoHTML = new File(REPORTS_PATH + "reporte_bitacora_" + fechaArchivo + ".html");
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivoHTML));

            bw.write("<!DOCTYPE html>");
            bw.newLine();
            bw.write("<html lang='es'>");
            bw.newLine();
            bw.write("<head>");
            bw.newLine();
            bw.write("<meta charset='UTF-8'>");
            bw.newLine();
            bw.write("<title>Reporte de Bitácora</title>");
            bw.newLine();
            bw.write("<style>");
            bw.newLine();
            bw.write("body { font-family: Arial, sans-serif; margin: 30px; background-color: #f4f4f4; }");
            bw.newLine();
            bw.write("h1 { color: #333; }");
            bw.newLine();
            bw.write("table { width: 100%; border-collapse: collapse; background-color: white; }");
            bw.newLine();
            bw.write("th, td { border: 1px solid #ccc; padding: 10px; text-align: left; }");
            bw.newLine();
            bw.write("th { background-color: #2c3e50; color: white; }");
            bw.newLine();
            bw.write("tr:nth-child(even) { background-color: #f9f9f9; }");
            bw.newLine();
            bw.write("</style>");
            bw.newLine();
            bw.write("</head>");
            bw.newLine();
            bw.write("<body>");
            bw.newLine();
            bw.write("<h1>Reporte de Bitácora del Sistema</h1>");
            bw.newLine();
            bw.write("<p>Fecha de generación: " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")) + "</p>");
            bw.newLine();

            bw.write("<table>");
            bw.newLine();
            bw.write("<tr><th>Operación</th><th>Usuario</th><th>Módulo</th><th>Fecha</th><th>Hora</th></tr>");
            bw.newLine();

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("\\]");

                if (partes.length >= 5) {
                    String operacion = partes[0].replace("[", "").trim();
                    String usuario = partes[1].replace("[", "").trim();
                    String modulo = partes[2].replace("[", "").trim();
                    String fecha = partes[3].replace("[FECHA ", "").trim();
                    String hora = partes[4].replace("[HORA ", "").trim();

                    bw.write("<tr>");
                    bw.newLine();
                    bw.write("<td>" + operacion + "</td>");
                    bw.newLine();
                    bw.write("<td>" + usuario + "</td>");
                    bw.newLine();
                    bw.write("<td>" + modulo + "</td>");
                    bw.newLine();
                    bw.write("<td>" + fecha + "</td>");
                    bw.newLine();
                    bw.write("<td>" + hora + "</td>");
                    bw.newLine();
                    bw.write("</tr>");
                    bw.newLine();
                }
            }

            bw.write("</table>");
            bw.newLine();
            bw.write("</body>");
            bw.newLine();
            bw.write("</html>");

            br.close();
            bw.close();

            return true;

        } catch (Exception e) {
            System.out.println("Error al generar reporte de bitácora: " + e.getMessage());
            return false;
        }
    }
    /*===============================================================================*/
    /*                                 PERSISTENCIA                                  */
    /*===============================================================================*/
    private void crearCarpeta(String ruta) {
        File carpeta = new File(ruta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }
    /*===============================================================================*/
    public void crearCarpetaData() {
        crearCarpeta(DATA_PATH);
    }
    /*===============================================================================*/
    public void crearCarpetaLogs() {
        crearCarpeta(LOGS_PATH);
    }
    /*===============================================================================*/
    public void crearCarpetaReports() {
        crearCarpeta(REPORTS_PATH);
    }
    /*===============================================================================*/
    public void guardarUsuariosEnArchivo() {
        try {
            crearCarpetaData();

            BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_PATH + "cuentas.txt"));

            for (int i = 0; i < totalUsuarios; i++) {
                if (!usuarios[i].getRol().equals("Administrador")) {
                    String linea = usuarios[i].getRol() + ";" +
                            usuarios[i].getCarne() + ";" +
                            usuarios[i].getNombre() + ";" +
                            usuarios[i].getCarrera() + ";" +
                            usuarios[i].getContrasena();

                    bw.write(linea);
                    bw.newLine();
                }
            }

            bw.close();

        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }
    /*===============================================================================*/
    public void cargarUsuariosDesdeArchivo() {
        try {
            crearCarpetaData();

            File archivo = new File(DATA_PATH + "cuentas.txt");
            if (!archivo.exists()) {
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");

                if (partes.length == 5) {
                    String rol = partes[0];
                    String carne = partes[1];
                    String nombre = partes[2];
                    String carrera = partes[3];
                    String contrasena = partes[4];

                    if (buscarUsuario(carne) == null && totalUsuarios < usuarios.length) {
                        usuarios[totalUsuarios] = new Usuario(carne, nombre, carrera, contrasena, rol);
                        totalUsuarios++;
                    }
                }
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
    }
    /*===============================================================================*/
    public void guardarPrestamosEnArchivo() {
        try {
            crearCarpetaData();

            BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_PATH + "prestamos.txt"));

            for (int i = 0; i < totalPrestamos; i++) {
                String linea = prestamos[i].getCodigoPrestamo() + ";" +
                        prestamos[i].getCarneUsuario() + ";" +
                        prestamos[i].getLibro().getCodigo() + ";" +
                        prestamos[i].getFechaPrestamo() + ";" +
                        prestamos[i].getFechaLimite() + ";" +
                        prestamos[i].getFechaDevolucion() + ";" +
                        prestamos[i].getEstado();

                bw.write(linea);
                bw.newLine();
            }

            bw.close();

        } catch (IOException e) {
            System.out.println("Error al guardar préstamos: " + e.getMessage());
        }
    }
    /*===============================================================================*/
    public void cargarPrestamosDesdeArchivo() {
        try {
            crearCarpetaData();

            File archivo = new File(DATA_PATH + "prestamos.txt");
            if (!archivo.exists()) {
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");

                if (partes.length == 7) {
                    String codigoPrestamo = partes[0];
                    String carneUsuario = partes[1];
                    String codigoLibro = partes[2];
                    String fechaPrestamo = partes[3];
                    String fechaLimite = partes[4];
                    String fechaDevolucion = partes[5];
                    String estado = partes[6];

                    Usuario usuarioEncontrado = buscarUsuario(carneUsuario);
                    Libro libroEncontrado = buscarLibro(codigoLibro);

                    if (usuarioEncontrado != null && libroEncontrado != null && totalPrestamos < prestamos.length) {
                        Prestamo nuevoPrestamo = new Prestamo(codigoPrestamo, usuarioEncontrado, libroEncontrado,
                                fechaPrestamo);

                        nuevoPrestamo.setFechaLimite(fechaLimite);
                        nuevoPrestamo.setFechaDevolucion(fechaDevolucion);
                        nuevoPrestamo.setEstado(estado);

                        prestamos[totalPrestamos] = nuevoPrestamo;
                        totalPrestamos++;
                    }
                }
            }

            br.close();

        } catch (IOException e) {
            System.out.println("Error al cargar préstamos: " + e.getMessage());
        }
    }
    /*===============================================================================*/
    /*                                   GETTERS                                     */
    /*===============================================================================*/
    public Libro[] getLibros() {
        return libros;
    }

    public Usuario[] getUsuarios() {
        return usuarios;
    }

    public Prestamo[] getPrestamos() {
        return prestamos;
    }

    public int getTotalLibros() {
        return totalLibros;
    }
    /*===============================================================================*/
    public int getTotalUsuarios() {
        return totalUsuarios;
    }
    /*===============================================================================*/
    public int getTotalPrestamos() {
        return totalPrestamos;
    }
}