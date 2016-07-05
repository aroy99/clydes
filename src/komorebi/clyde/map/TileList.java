/**
 * TileList.java    May 18, 2016, 8:42:36 PM
 */

package komorebi.clyde.map;

/**
 * All of the tiles in the game and their ids
 * 
 * @author Aaron Roy
 */
public enum TileList {
  BLANK     (  0),    yBLANK    ( 64),    aBLANK    (128),    cBLANK    (192),
  GRASS     (  1),    yGRASS    ( 65),    aGRASS    (129),    cGRASS    (193),
  FLOWER    (  2),    yFLOWER   ( 66),    aFLOWER   (130),    cFLOWER   (194),
  G_DECOR1  (  3),    yG_DECOR1 ( 67),    aG_DECOR1 (131),    cG_DECOR1 (195),
  G_DECOR2  (  4),    yG_DECOR2 ( 68),    aG_DECOR2 (132),    cG_DECOR2 (196),
  HEDGE_L   (  5),    yHEDGE_L  ( 69),    aHEDGE_L  (133),    cHEDGE_L  (197),
  HEDGE_C   (  6),    yHEDGE_C  ( 70),    aHEDGE_C  (134),    cHEDGE_C  (198),
  HEDGE_R   (  7),    yHEDGE_R  ( 71),    aHEDGE_R  (135),    cHEDGE_R  (199),
  DOOR1     (  8),    yDOOR1    ( 72),    aDOOR1    (136),    cDOOR1    (200),
  DOOR2     (  9),    yDOOR2    ( 73),    aDOOR2    (137),    cDOOR2    (201),
  DOOR3_L   ( 10),    yDOOR3_L  ( 74),    aDOOR3_L  (138),    cDOOR3_L  (202),
  DOOR3_R   ( 11),    yDOOR3_R  ( 75),    aDOOR3_R  (139),    cDOOR3_R  (203),
  DOOR4     ( 12),    yDOOR4    ( 76),    aDOOR4    (140),    cDOOR4    (204),
  DOOR5     ( 13),    yDOOR5    ( 77),    aDOOR5    (141),    cDOOR5    (205),
  ROOF_UL   ( 14),    yROOF_UL  ( 78),    aROOF_UL  (142),    cROOF_UL  (206),
  ROOF_U    ( 15),    yROOF_U   ( 79),    aROOF_U   (143),    cROOF_U   (207),
  ROOF_UR   ( 16),    yROOF_UR  ( 80),    aROOF_UR  (144),    cROOF_UR  (208),
  ROOF_L    ( 17),    yROOF_L   ( 81),    aROOF_L   (145),    cROOF_L   (209),
  ROOF_R    ( 18),    yROOF_R   ( 82),    aROOF_R   (146),    cROOF_R   (210),
  ROOF_C    ( 19),    yROOF_C   ( 83),    aROOF_C   (147),    cROOF_C   (211),
  ROOF_BL   ( 20),    yROOF_BL  ( 84),    aROOF_BL  (148),    cROOF_BL  (212),
  ROOF_B    ( 21),    yROOF_B   ( 85),    aROOF_B   (149),    cROOF_B   (213),
  ROOF_BR   ( 22),    yROOF_BR  ( 86),    aROOF_BR  (150),    cROOF_BR  (214),
  WIN1      ( 23),    yWIN1     ( 87),    aWIN1     (151),    cWIN1     (215),
  WIN2      ( 24),    yWIN2     ( 88),    aWIN2     (152),    cWIN2     (216),
  JAIL_WIN  ( 25),    yJAIL_WIN ( 89),    aJAIL_WIN (153),    cJAIL_WIN (217),
  REST_WIN  ( 26),    yREST_WIN ( 90),    aREST_WIN (154),    cREST_WIN (218),
  CHURCH_WIN( 27),    yCHURCH_WI( 91),    aCHURCH_WI(155),    cCHURCH_WI(219),
  D_ARC1    ( 28),    yD_ARC1   ( 92),    aD_ARC1   (156),    cD_ARC1   (220),
  D_ARC2    ( 29),    yD_ARC2   ( 93),    aD_ARC2   (157),    cD_ARC2   (221),
  D_ARC3_L  ( 30),    yD_ARC3_L ( 94),    aD_ARC3_L (158),    cD_ARC3_L (222),
  D_ARC3_R  ( 31),    yD_ARC3_R ( 95),    aD_ARC3_R (159),    cD_ARC3_R (223),
  D_ARC4    ( 32),    yD_ARC4   ( 96),    aD_ARC4   (160),    cD_ARC4   (224),
  D_ARC5    ( 33),    yD_ARC5   ( 97),    aD_ARC5   (161),    cD_ARC5   (225),
  xBLANK    ( 34),    zBLANK    ( 98),    bBLANK    (162),    dBLANK    (226),
  xGRASS    ( 35),    zGRASS    ( 99),    bGRASS    (163),    dGRASS    (227),
  xFLOWER   ( 36),    zFLOWER   (100),    bFLOWER   (164),    dFLOWER   (228),
  xG_DECOR1 ( 37),    zG_DECOR1 (101),    bG_DECOR1 (165),    dG_DECOR1 (229),
  xG_DECOR2 ( 38),    zG_DECOR2 (102),    bG_DECOR2 (166),    dG_DECOR2 (230),
  xHEDGE_L  ( 39),    zHEDGE_L  (103),    bHEDGE_L  (167),    dHEDGE_L  (231),
  xHEDGE_C  ( 40),    zHEDGE_C  (104),    bHEDGE_C  (168),    dHEDGE_C  (232),
  xHEDGE_R  ( 41),    zHEDGE_R  (105),    bHEDGE_R  (169),    dHEDGE_R  (233),
  xDOOR1    ( 42),    zDOOR1    (106),    bDOOR1    (170),    dDOOR1    (234),
  xDOOR2    ( 43),    zDOOR2    (107),    bDOOR2    (171),    dDOOR2    (235),
  xDOOR3_L  ( 44),    zDOOR3_L  (108),    bDOOR3_L  (172),    dDOOR3_L  (236),
  xDOOR3_R  ( 45),    zDOOR3_R  (109),    bDOOR3_R  (173),    dDOOR3_R  (237),
  xDOOR4    ( 46),    zDOOR4    (110),    bDOOR4    (174),    dDOOR4    (238),
  xDOOR5    ( 47),    zDOOR5    (111),    bDOOR5    (175),    dDOOR5    (239),
  xROOF_UL  ( 48),    zROOF_UL  (112),    bROOF_UL  (176),    dROOF_UL  (240),
  xROOF_U   ( 49),    zROOF_U   (113),    bROOF_U   (177),    dROOF_U   (241),
  xROOF_UR  ( 50),    zROOF_UR  (114),    bROOF_UR  (178),    dROOF_UR  (242),
  xROOF_L   ( 51),    zROOF_L   (115),    bROOF_L   (179),    dROOF_L   (243),
  xROOF_R   ( 52),    zROOF_R   (116),    bROOF_R   (180),    dROOF_R   (244),
  xROOF_C   ( 53),    zROOF_C   (117),    bROOF_C   (181),    dROOF_C   (245),
  xROOF_BL  ( 54),    zROOF_BL  (118),    bROOF_BL  (182),    dROOF_BL  (246),
  xROOF_B   ( 55),    zROOF_B   (119),    bROOF_B   (183),    dROOF_B   (247),
  xROOF_BR  ( 56),    zROOF_BR  (120),    bROOF_BR  (184),    dROOF_BR  (248),
  xWIN1     ( 57),    zWIN1     (121),    bWIN1     (185),    dWIN1     (249),
  xWIN2     ( 58),    zWIN2     (122),    bWIN2     (186),    dWIN2     (250),
  xJAIL_WIN ( 59),    zJAIL_WIN (123),    bJAIL_WIN (187),    dJAIL_WIN (251),
  xREST_WIN ( 60),    zREST_WIN (124),    bREST_WIN (188),    dREST_WIN (252),
  xCHURCH_WI( 61),    zCHURCH_WI(125),    bCHURCH_WI(189),    dCHURCH_WI(253),
  xD_ARC1   ( 62),    zD_ARC1   (126),    bD_ARC1   (190),    dD_ARC1   (254),
  xD_ARC2   ( 63),    zD_ARC2   (127),    bD_ARC2   (191),    dD_ARC2   (255);

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
   * 
   * @return The full name of the enum, or bleh if not found
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
