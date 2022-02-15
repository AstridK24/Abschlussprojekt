import java.util.HashMap;

public class Data {
    public int width;//spielflächenbreite
    public int height;//spielflächenhöhe
    public int cellWidth;//zellenbreite
    public int cellHeight;//zellenhöhe

    public String playerSelected;//spielerauswahl
    public String playerName;//spielername
    public HashMap<String, Tile> tiles = new HashMap<String, Tile>();//weg usw - kacheln
    public Inventory inventory;//inventar
    public Club club;//club
    public Status status;//spielerwerte - rechts vom bild

    public int points;//punkte - rechts vom bild
    public int time;//zeit - rechts vom bild

    public Figure playerBackup = null;//wenn spieler stirbt oder in nächstes level geht
}
