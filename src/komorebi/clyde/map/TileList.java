/**
 * TileList.java		May 18, 2016, 8:42:36 PM
 *
 * -
 */
package komorebi.clyde.map;

/**
 * All of the tiles in the game and their ids
 * 
 * @author Aaron Roy
 * @version 0.0.1.0
 */
public enum TileList {
    BLANK     ( 0),
    GRASS     ( 1),
    FLOWER    ( 2),
    G_DECOR1  ( 3),
    G_DECOR2  ( 4),
    HEDGE_L   ( 5),
    HEDGE_C   ( 6),
    HEDGE_R   ( 7),
    DOOR1     ( 8),
    DOOR2     ( 9),
    DOOR3_L   (10),
    DOOR3_R   (11),
    DOOR4     (12),
    DOOR5     (13),
    ROOF_UL   (14),
    ROOF_U    (15),
    ROOF_UR   (16),
    ROOF_L    (17),
    ROOF_R    (18),
    ROOF_C    (19),
    ROOF_BL   (20),
    ROOF_B    (21),
    ROOF_BR   (22),
    WIN1      (23),
    WIN2      (24),
    JAIL_WIN  (25),
    REST_WIN  (26),
    CHURCH_WIN(27),
    D_ARC1    (28),
    D_ARC2    (29),
    D_ARC3_L  (30),
    D_ARC3_R  (31),
    D_ARC4    (32),
    D_ARC5    (33);
    
    private int id;
    
    TileList(int num){
        id = num;
    }
    
    /**
     * Returns a tile's numerical id
     * 
     * @return id of this value
     */
    public int getID(){
        return id;
    }
    
    /**
     * A string value for this enum
     */
    public String toString(){
        switch (this) {
        case BLANK     : return "Blank";
        case GRASS     : return "Grass";
        case FLOWER    : return "Flower";
        case G_DECOR1  : return "Grass Decoration 1";
        case G_DECOR2  : return "Grass Decoration 2";
        case HEDGE_L   : return "Hedge Left"       ;
        case HEDGE_C   : return "Hedge Center"     ;
        case HEDGE_R   : return "Hedge Right"      ;
        case DOOR1     : return "Door 1"           ;
        case DOOR2     : return "Door 2"           ;
        case DOOR3_L   : return "Door 3 Left"      ;
        case DOOR3_R   : return "Door 3 Right"     ;
        case DOOR4     : return "Door 4"           ;
        case DOOR5     : return "Door 5"           ;
        case ROOF_UL   : return "Roof Up Left"     ;
        case ROOF_U    : return "Roof Up"          ;
        case ROOF_UR   : return "Roof Up Right"    ;
        case ROOF_L    : return "Roof Left"        ;
        case ROOF_R    : return "Roof Right"       ;
        case ROOF_C    : return "Roof Center"      ;
        case ROOF_BL   : return "Roof Bottom Left" ;
        case ROOF_B    : return "Roof Bottom"      ;
        case ROOF_BR   : return "Roof Bottom Right";
        case WIN1      : return "Window 1"       ;
        case WIN2      : return "Window 2"       ;
        case JAIL_WIN  : return "Jail Window"    ;
        case REST_WIN  : return "Rest Window"    ;
        case CHURCH_WIN: return "Church Window"  ;
        case D_ARC1    : return "Door Arc 1";
        case D_ARC2    : return "Door Arc 2";
        case D_ARC3_L  : return "Door Arc 3 Left";
        case D_ARC3_R  : return "Door Arc 3 Right";
        case D_ARC4    : return "Door Arc 4";
        case D_ARC5    : return "Door Arc 5";
        default        : return "bleh";
        }
    }

    /**
     * Returns the TileList with value i
     * 
     * @param i
     * @return Value 
     */
    public static TileList getTile(int i) {
        for(TileList tl: TileList.values()){
            if(tl.getID()==i)return tl;
        }
        return BLANK;
    }
}
