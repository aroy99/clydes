/**
 * Encryptor.java		Jul 19, 2016, 2:41:09 PM
 */
package komorebi.clyde.map;

/**
 * 
 * @author Aaron Roy
 * @version 
 */
public class Encryptor {
  
  public static String encrypt(String s)
  {
    String end = "";
    char[] chars = s.toCharArray();
    boolean multOf4 = true;
    
    for (int i=0; i<chars.length; i++)
    {
      end = end + charAfter(chars[i], (3+(2*i)));
      if (multOf4 && i%4==0)
      {
        end = end + toChar((i*7)%95);
        multOf4=!multOf4;
      } else if (!multOf4 && i%7==0)
      {
        end = end + toChar((i*4)%95);
        multOf4=!multOf4;
      }
    }
    
    return end;
  }
  
  public static String decrypt(String s)
  {
    String end = "";
    char[] chars = s.toCharArray();
    boolean multOf4=true;
    int extra = 0;
    
    for (int i=0; i<chars.length; i++)
    {
      
      end = end + charAfter(chars[i], -(2*(i-extra) + 3));
      
      if (multOf4 && (i-extra)%4==0)
      {
        i++;
        extra++;
        multOf4=!multOf4;
        continue;
      } else if (!multOf4 && (i-extra)%7==0)
      {
        i++;
        extra++;
        multOf4=!multOf4;
        continue;
      }
      
    }
    
    
    return end;
  }
  
  public static char charAfter(char orig, int i)
  {
    while (!(i>=0&&i<=94)) i+=95;
    return toChar(((toInt(orig) + i) % 95));
  }
  
  public static int toInt(char c)
  {
    switch (c)
    {
      case'a':return 0;           case'k':return 10;
      case'b':return 1;           case'l':return 11;
      case'c':return 2;           case'm':return 12;
      case'd':return 3;           case'n':return 13;
      case'e':return 4;           case'o':return 14;
      case'f':return 5;           case'p':return 15;
      case'g':return 6;           case'q':return 16;
      case'h':return 7;           case'r':return 17;
      case'i':return 8;           case's':return 18;
      case'j':return 9;           case't':return 19;
      
      case'u':return 20;          case'4':return 30;
      case'v':return 21;          case'5':return 31;
      case'w':return 22;          case'6':return 32;
      case'x':return 23;          case'7':return 33;
      case'y':return 24;          case'8':return 34;
      case'z':return 25;          case'9':return 35;
      case'0':return 26;          case'!':return 36;
      case'1':return 27;          case'@':return 37;
      case'2':return 28;          case'#':return 38;
      case'3':return 29;          case'$':return 39;
      
      case'%':return 40;          case'{':return 50;
      case'^':return 41;          case'}':return 51;
      case'&':return 42;          case'[':return 52;
      case'*':return 43;          case']':return 53;
      case'(':return 44;          case'|':return 54;
      case')':return 45;          case':':return 55;
      case'_':return 46;          case';':return 56;
      case'-':return 47;          case'/':return 57;
      case'=':return 48;          case'.':return 58;
      case'+':return 49;          case',':return 59;
      
      case'?':return 60;          case'C':return 70;
      case'<':return 61;          case'D':return 71;
      case'>':return 62;          case'E':return 72;
      case'\'':return 63;         case'F':return 73;
      case'\"':return 64;         case'G':return 74;
      case'\n':return 65;         case'H':return 75;
      case'`':return 66;          case'I':return 76;
      case'~':return 67;          case'J':return 77;
      case'A':return 68;          case'K':return 78;
      case'B':return 69;          case'L':return 79;
      
      case'M':return 80;          case'W':return 90;
      case'N':return 81;          case'X':return 91;
      case'O':return 82;          case'Y':return 92;
      case'P':return 83;          case'Z':return 93;
      case'Q':return 84;          case' ':return 94;
      case'R':return 85;          default:return 95;
      case'S':return 86;
      case'T':return 87;
      case'U':return 88;
      case'V':return 89;
    }
      
  }
  
  public static char toChar(int i)
  {
    switch (i)
    {
      case 0:return'a';           case 10:return'k';
      case 1:return'b';           case 11:return'l';
      case 2:return'c';           case 12:return'm';
      case 3:return'd';           case 13:return'n';
      case 4:return'e';           case 14:return'o';
      case 5:return'f';           case 15:return'p';
      case 6:return'g';           case 16:return'q';
      case 7:return'h';           case 17:return'r';
      case 8:return'i';           case 18:return's';
      case 9:return'j';           case 19:return't';
      
      case 20:return'u';          case 30:return'4';
      case 21:return'v';          case 31:return'5';
      case 22:return'w';          case 32:return'6';
      case 23:return'x';          case 33:return'7';
      case 24:return'y';          case 34:return'8';
      case 25:return'z';          case 35:return'9';
      case 26:return'0';          case 36:return'!';
      case 27:return'1';          case 37:return'@';
      case 28:return'2';          case 38:return'#';
      case 29:return'3';          case 39:return'$';
      
      case 40:return'%';          case 50:return'{';
      case 41:return'^';          case 51:return'}';
      case 42:return'&';          case 52:return'[';
      case 43:return'*';          case 53:return']';
      case 44:return'(';          case 54:return'|';
      case 45:return')';          case 55:return':';
      case 46:return'_';          case 56:return';';
      case 47:return'-';          case 57:return'/';
      case 48:return'=';          case 58:return'.';
      case 49:return'+';          case 59:return',';
      
      case 60:return'?';          case 70:return'C';
      case 61:return'<';          case 71:return'D';
      case 62:return'>';          case 72:return'E';
      case 63:return'\'';         case 73:return'F';
      case 64:return'\"';         case 74:return'G';
      case 65:return'\n';         case 75:return'H';
      case 66:return'`';          case 76:return'I';
      case 67:return'~';          case 77:return'J';
      case 68:return'A';          case 78:return'K';
      case 69:return'B';          case 79:return'L';
      
      case 80:return'M';          case 90:return'W';
      case 81:return'N';          case 91:return'X';
      case 82:return'O';          case 92:return'Y';
      case 83:return'P';          case 93:return'Z';
      case 84:return'Q';          case 94:return' ';
      case 85:return'R';          default:return' ';
      case 86:return'S';
      case 87:return'T';
      case 88:return'U';
      case 89:return'V';
      
    }
  }
}
