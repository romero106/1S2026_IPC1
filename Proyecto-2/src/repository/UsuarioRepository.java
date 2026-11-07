package repository;

import java.io.*;

import model.*;

public class UsuarioRepository {

    /* MÉTODOS */
    // Carga el usuario actual desde un archivo de texto
    public UsuarioActual cargarUsuario(String rutaArchivo) {
        File archivo = new File(rutaArchivo);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(archivo));
            String linea = reader.readLine();
            reader.close();

            if (linea == null || linea.trim().isEmpty()) {
                return new UsuarioActual("Jugador Actual");
            }

            String[] partes = linea.split("\\|", -1);
            UsuarioActual usuarioActual = new UsuarioActual(partes[0]);

            if (partes.length >= 9) {
                usuarioActual.setExperiencia(Integer.parseInt(partes[1]));
                usuarioActual.setComprasRealizadas(Integer.parseInt(partes[2]));
                usuarioActual.setCartasAgregadas(Integer.parseInt(partes[3]));
                usuarioActual.setFilasCompletas(Integer.parseInt(partes[4]));
                usuarioActual.setCartasLegendarias(Integer.parseInt(partes[5]));
                usuarioActual.setTorneosComprados(Integer.parseInt(partes[6]));
                usuarioActual.setGastoTotal(Double.parseDouble(partes[7]));
                usuarioActual.setTorneosDistintos(partes[8].isEmpty() ? "|" : partes[8]);
            }

            return usuarioActual;
        } catch (IOException error) {
            throw new RuntimeException("No fue posible cargar el usuario actual.", error);
        }
    }

    // Guarda el usuario actual en un archivo de texto
    public void guardarUsuario(String rutaArchivo, UsuarioActual usuarioActual) {
        File archivo = new File(rutaArchivo);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(archivo));
            writer.write(usuarioActual.getNombre() + "|" + usuarioActual.getExperiencia() + "|" + usuarioActual.getComprasRealizadas() + "|" + usuarioActual.getCartasAgregadas() + "|" + usuarioActual.getFilasCompletas() + "|" + usuarioActual.getCartasLegendarias() + "|" + usuarioActual.getTorneosComprados() + "|" + usuarioActual.getGastoTotal() + "|" + usuarioActual.getTorneosDistintos());
            writer.close();
        } catch (IOException error) {
            throw new RuntimeException("No fue posible guardar el usuario.", error);
        }
    }
}