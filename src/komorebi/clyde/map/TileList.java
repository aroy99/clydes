/**
 * TileList.java  May 18, 2016, 8:42:36 PM
 */

package komorebi.clyde.map;

/**
 * All of the tiles in the game and their ids
 * 
 * @author Aaron Roy
 */
public enum TileList {
 
  BLANK     (0,  0, 0),           CROOF_EAVR(16, 16, 0),
  CROOF_CRTL(1,  0, 16),          CROOF_FSTL(17, 16, 16),
  CROOF_CRTR(2,  0, 32),          CROOF_FSTR(18, 16, 32),
  CROOF_SLTL(3,  0, 48),          CROOF_FCSL(19, 16, 48),
  CROOF_SLTR(4,  0, 64),          CROOF_FCSR(20, 16, 64),
  CROOF_CTRT(5,  0, 80),          CROOF_F0CS(21, 16, 80),
  CROOF_CHMB(6,  0, 96),          CROOF_MCSR(22, 16, 96),
  CROOF_CHMT(7,  0, 112),         CROOF_MCSL(23, 16, 112),
  CROOF_FILL(8,  0, 128),         CHOUS_CRSL(24, 16, 128),
  CROOF_EDTL(9,  0, 144),         CHOUS_CRSR(25, 16, 144),
  CROOF_EDTR(10, 0, 160),         CHOUS_CS2L(26, 16, 160),
  CROOF_DIPL(11, 0, 176),         CHOUS_CS2R(27, 16, 176),
  CROOF_DIPR(12, 0, 192),         CHOUS_CS2S(28, 16, 192),
  CROOF_EDBL(13, 0, 208),         CHOUS_CS3R(29, 16, 208),
  CROOF_EDBR(14, 0, 224),         CHOUS_CS3L(30, 16, 224),
  CROOF_EAVL(15, 0, 240),         CHOUS_LWTL(31, 16, 240),
  
  CHOUS_RWTR(32, 32, 0),          CHOUS_CRBR(48, 48, 0),
  CHOUS_LWBL(33, 32, 16),         CHOUS_FNCL(49, 48, 16),
  CHOUS_RWBR(34, 32, 32),         CHOUS_FNCR(50, 48, 32),
  CHOUS_LWTR(35, 32, 48),         CHOUS_FNDL(51, 48, 48),
  CHOUS_RWTL(36, 32, 64),         CHOUS_FNDR(52, 48, 64),
  CHOUS_LWBR(37, 32, 80),         CHOUS_DRBL(53, 48, 80),
  CHOUS_RWBL(38, 32, 96),         CHOUS_DRBR(54, 48, 96),
  CHOUS_WITL(39, 32, 112),        CHOUS_DOLL(55, 48, 112),
  CHOUS_WITR(40, 32, 128),        CHOUS_DOLR(56, 48, 128),
  CHOUS_WIBL(41, 32, 144),        CHOUS_DORL(57, 48, 144),
  CHOUS_WIBR(42, 32, 160),        CHOUS_DORR(58, 48, 160),
  CHOUS_CS4R(43, 32, 176),        PATH_1    (59, 48, 176),
  CHOUS_CS4L(44, 32, 192),        PATH_2    (60, 48, 192),
  CHOUS_DRTL(45, 32, 208),        PATH_3    (61, 48, 208),
  CHOUS_DRTR(46, 32, 224),        PATH_4    (62, 48, 224),
  CHOUS_CRBL(47, 32, 240),        PATH_5    (63, 48, 240),
  
  PATH_6    (64, 64, 0),          HATS_COUNC(80, 80, 0),
  PATH_7    (65, 64, 16),         HATS_CANBL(81, 80, 16),
  PATH_8    (66, 64, 32),         HATS_CANBC(82, 80, 32),
  PATH_9    (67, 64, 48),         HATS_CANBR(83, 80, 48),
  PATH_10   (68, 64, 64),         HATS_CANTR(84, 80, 64),
  PATH_11   (69, 64, 80),         HATS_CANTC(85, 80, 80),
  PATH_12   (70, 64, 96),         HATS_CANTL(86, 80, 96),
  PATH_13   (71, 64, 112),        NFHS_ROFTL(87, 80, 112),
  PATH_14   (72, 64, 128),        NFHS_ROFTC(88, 80, 128),
  PATH_15   (73, 64, 144),        NFHS_ROFTR(89, 80, 144),
  BLANK_2   (74, 64, 160),        NFHS_ROFCL(90, 80, 160),
  BLANK_3   (75, 64, 176),        NFHS_ROFCR(91, 80, 176),
  BLANK_4   (76, 64, 192),        NFHS_ROFBL(92, 80, 192),
  BLANK_5   (77, 64, 208),        NFHS_ROFBR(93, 80, 208),
  HATS_COUNR(78, 64, 224),        NFHS_RFBC1(94, 80, 224),
  HATS_COUNL(79, 64, 240),        NFHS_RFBC2(95, 80, 240),
  
  NFHS_VENT (96, 96, 0),        NFHS_SIGNN(112,112, 0),
  NFHS_CHIMN(97, 96, 16),       NFHS_SIGNF(113,112,16),
  NFHS_ROOF (98, 96, 32),       NFHS_SIGNH(114,112,32),
  NFHS_EDGL (99, 96, 48),       NFHS_SIGNS(115,112,48),
  NFHS_EDGR (100,96, 64),       GRASS     (116,112,64),
  NFHS_EDGBL(101,96, 80),       NFPD_ROFTL(117,112,80),
  NFHS_EDGBR(102,96, 96),       NFPD_ROFTR(118,112,96),
  NFHS_EDGB1(103,96, 112),      NFPD_ROFBL(119,112,112),
  NFHS_EDGB2(104,96, 128),      NFPD_ROFBR(120,112,128),
  NFHS_WINL (105,96, 144),      NFPD_ROFCL(121,112,144),
  NFHS_WINR (106,96, 160),      NFPD_ROFCR(122,112,160),
  NFHS_BRICK(107,96, 176),      NFPD_ROFTC(123,112,176),
  NFHS_DORTL(108,96, 192),      NFPD_ROFBC(124,112,192),
  NFHS_DORTR(109,96, 208),      NFPD_ROOF (125,112,208),
  NFHS_DORBL(110,96, 224),      NFPD_WINTL(126,112,224),
  NFHS_DORBR(111,96, 240),      NFPD_WINTC(127,112,240),
  
  NFPD_TWNTR(128,128,0),        NFPD_WN3TR(144,144,0),
  NFPD_TWNCL(129,128,16),       NFPD_WN3CL(145,144,16),
  NFPD_WINDW(130,128,32),       NFPD_WN3CR(146,144,32),
  NFPD_TWNCR(131,128,48),       NFPD_SGNTL(147,144,48),
  NFPD_BTHWL(132,128,64),       NFPD_SGNTC(148,144,64),
  NFPD_BTHWC(133,128,80),       NFPD_SGNTR(149,144,80),
  NFPD_BTHWR(134,128,96),       NFPD_SGNBL(150,144,96),
  NFPD_BWINL(135,128,112),      NFPD_SGNBC(151,144,112),
  NFPD_BWINC(136,128,128),      NFPD_SGNBR(152,144,128),
  NFPD_BWINR(137,128,144),      NFPD_ROFSH(153,144,144),
  NPFD_EDGL (138,128,160),      NFPD_FACAD(154,144,160),
  NFPD_FOUN1(139,128,176),      NFPD_EDGR2(155,144,176),
  NFPD_FOUN2(140,128,192),      NFPD_EDGL2(156,144,192),
  NFPD_WN3BL(141,128,208),      NFPD_CORBR(157,144,208),
  NFPD_WN3BR(142,128,224),      NFPD_CORBL(158,144,224),
  NFPD_WN3TL(143,128,240),      HOTL_CORBL(159,144,240)
  
  
  


  
  ;
  
  
  
  
  private int id;
  private int x,y;

  /**
   * Creates a new Tile Type
   * 
   * @param num The numerical ID of this type 
   * @param x The x value of this tile
   * @param y The y value of this tile
   */
  TileList(int num, int x, int y){
    id = num;
    this.x = x;
    this.y = y;
  }

  /**
   * Returns a tile's numerical id
   * 
   * @return id of this value
   */
  public int getID(){
    return id;
  }
  
  public int getX(){
    return x;
  }
  
  public int getY(){
    return y;
  }
  
  /**
   * A string value for this enum
   * 
   * @return The full name of the enum, or bleh if not found
   */
  public String toString(){
    /*
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
    */
    return "blank";
  }

  /**
   * Returns the TileList with the specified index
   * 
   * @param index The index of the tile
   * @return Tile that matched the index, BLANK if not found
   */
  public static TileList getTile(int index) {
    for(TileList tl: TileList.values()){
      if(tl.getID() == index){
        return tl;
      }
    }
    return BLANK;
  }
}
