/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package symbol;

public class SemanticTable {
   
   public static final int ERR = -1;
   public static final int OK_ = 0;
   public static final int WAR = 1;


   public static final int INT = 0;
   public static final int FLO = 1;
   public static final int CHA = 2;
   public static final int STR = 3;
   public static final int BOO = 4;
   public static final int VOI = 5;

   public static final int SUM = 0;
   public static final int SUB = 1;
   public static final int MUL = 2;
   public static final int DIV = 3;
   public static final int REL = 4; // qualquer operador relacional
   public static final int SLT = 5; // SLL
   public static final int SRT = 6; // SRR

   // TIPO DE RETORNO DAS EXPRESSOES ENTRE TIPOS
   // 5 x 5 X 5  = TIPO X TIPO X OPER
   static int expTable [][][] = 
                {  /*     INT       */ /*       FLOAT    */  /*      CHAR       */   /*    STRING    */  /*     BOOL        */
    /*INT*/     {{INT,INT,INT,FLO,BOO},{FLO,FLO,FLO,FLO,BOO},{ERR,ERR,ERR,ERR,ERR},{ERR,ERR,ERR,ERR,ERR},{ERR,ERR,ERR,ERR,ERR}},
    /*FLOAT*/   {{FLO,FLO,FLO,FLO,BOO},{FLO,FLO,FLO,FLO,BOO},{ERR,ERR,ERR,ERR,ERR},{ERR,ERR,ERR,ERR,ERR},{ERR,ERR,ERR,ERR,ERR}}, 
    /*CHAR*/    {{ERR,ERR,ERR,ERR,ERR},{ERR,ERR,ERR,ERR,ERR},{STR,ERR,ERR,ERR,BOO},{STR,ERR,ERR,ERR,BOO},{ERR,ERR,ERR,ERR,ERR}}, 
    /*STRING*/  {{ERR,ERR,ERR,ERR,ERR},{ERR,ERR,ERR,ERR,ERR},{STR,ERR,ERR,ERR,BOO},{STR,ERR,ERR,ERR,BOO},{ERR,ERR,ERR,ERR,ERR}},
    /*BOOL*/    {{ERR,ERR,ERR,ERR,ERR},{ERR,ERR,ERR,ERR,ERR},{ERR,ERR,ERR,ERR,ERR},{ERR,ERR,ERR,ERR,ERR},{ERR,ERR,ERR,ERR,BOO}}                        
                };
   
   // atribuicoes compativeis 
   // 5 x 5 = TIPO X TIPO
   static int atribTable [][]={/* INT FLO CHA STR BOO  */
   /*INT*/                {OK_,WAR,ERR,ERR,ERR},
   /*FLO*/                {OK_,OK_,ERR,ERR,ERR},
   /*CHA*/                {ERR,ERR,OK_,ERR,ERR},
   /*STR*/                {ERR,ERR,OK_,OK_,ERR},
   /*BOO*/                {ERR,ERR,ERR,ERR,OK_}
                         };
   
   public static int resultType (int TP1, int TP2, int OP){
      return (expTable[TP1][TP2][OP]);
   }
   
   public static int atribType (int TP1, int TP2){
      return (atribTable[TP1][TP2]);
   }
   
   public static String getOperatorName(int operator) {
       switch(operator) {
           case SUM: return "soma";
           case SUB: return "subtração";
           case DIV: return "divisão";
           case MUL: return "multiplicação";
           case REL: return "relação";
       }
       return "";
   }
   
   public static String getTypeName(Integer type) {
        switch(type) {
            case SemanticTable.INT: return "INT";
            case SemanticTable.FLO: return "FLOAT";
            case SemanticTable.CHA: return "CHAR";
            case SemanticTable.STR: return "STRING";
            case SemanticTable.BOO: return "BOOLEAN";
            case SemanticTable.VOI: return "VOID";
        }
        return "";
    }
}
