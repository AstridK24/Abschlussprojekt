import java.util.HashMap;

public class Data {
    public int width;//spielflächenbreitebreite
    public int height;//spielflächenhöhe
    public int cellWidth;//zellenbreite
    public int cellHeight;//zellenhöhe

    public String playerSelected;
    public HashMap<String, Tile> tiles = new HashMap<String, Tile>();//weg usw - kacheln
    public Inventory inventory;
}
