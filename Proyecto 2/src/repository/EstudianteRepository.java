package repository;

import java.io.*;

import model.*;

public class EstudianteRepository {

    /* MÉTODOS */
    // Carga los datos del estudiante desde un archivo de texto
    public Estudiante cargarEstudiante(String rutaArchivo) {
        File archivo = new File(rutaArchivo);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea = reader.readLine();
            reader.close();

            if (linea == null || linea.trim().isEmpty()) {
                return new Estudiante("Patricio Manuel Romero Castellanos", "202504020", "30588919280301@ingenieria.usac.edu.gt", "E", "Primer semestre 2026", "N/A");
            }

            String[] partes = linea.split("\\|", -1);
            if (partes.length >= 6) {
                return new Estudiante(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5]);
            }

            return new Estudiante("Patricio Manuel Romero Castellanos", "202504020", "30588919280301@ingenieria.usac.edu.gt", "E", "Primer semestre 2026", "N/A");
        } catch (IOException error) {
            throw new RuntimeException("No fue posible cargar los datos del estudiante.", error);
        }
    }
}