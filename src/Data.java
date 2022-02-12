import java.util.HashMap;

public class Data {
    public int width;//spielflächenbreitebreite
    public int height;//spielflächenhöhe
    public int cellWidth;//zellenbreite
    public int cellHeight;//zellenhöhe

    public String playerSelected;
    public String playerName;
    public HashMap<String, Tile> tiles = new HashMap<String, Tile>();//weg usw - kacheln
    public Inventory inventory;
    public Club club;
    public Status status;

    public int points;
    public int time;

    public Figure playerBackup = null;
}
