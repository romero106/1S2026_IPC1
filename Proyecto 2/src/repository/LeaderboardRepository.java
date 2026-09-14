package repository;

import java.io.*;

import model.*;

public class LeaderboardRepository {

    /* MÉTODOS */
    // Cargar el leaderboard desde un archivo de texto
    public UsuarioXP[] cargarLeaderboard(String rutaArchivo, int capacidad) {
        UsuarioXP[] usuarios = new UsuarioXP[capacidad];
        int indice = 0;
        File archivo = new File(rutaArchivo);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = reader.readLine()) != null && indice < capacidad) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] partes = linea.split("\\|", -1);
                if (partes.length >= 2) {
                    usuarios[indice] = new UsuarioXP(partes[0], Integer.parseInt(partes[1]));
                    indice++;
                }
            }

            reader.close();
            return usuarios;
        } catch (IOException error) {
            throw new RuntimeException("No fue posible cargar el leaderboard.", error);
        }
    }

    // Guardar el leaderboard en un archivo de texto
    public void guardarLeaderboard(String rutaArchivo, UsuarioXP[] usuarios) {
        File archivo = new File(rutaArchivo);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));

            for (int i = 0; i < usuarios.length; i++) {
                if (usuarios[i] != null) {
                    writer.write(usuarios[i].getNombre() + "|" + usuarios[i].getExperiencia());
                    writer.newLine();
                }
            }

            writer.close();
        } catch (IOException error) {
            throw new RuntimeException("No fue posible guardar el leaderboard.", error);
        }
    }
}