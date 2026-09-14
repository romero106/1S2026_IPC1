package app;

public class GameZoneProApp {
    public static void main(String[] args) {
        String rutaBase = System.getProperty("user.dir");
        GameZoneProFrame frame = new GameZoneProFrame(rutaBase);
        frame.setVisible(true);
    }
}