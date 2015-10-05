//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short CLASS=261;
public final static short NULL=262;
public final static short EXTENDS=263;
public final static short THIS=264;
public final static short WHILE=265;
public final static short FOR=266;
public final static short IF=267;
public final static short ELSE=268;
public final static short RETURN=269;
public final static short BREAK=270;
public final static short NEW=271;
public final static short PRINT=272;
public final static short READ_INTEGER=273;
public final static short READ_LINE=274;
public final static short LITERAL=275;
public final static short IDENTIFIER=276;
public final static short AND=277;
public final static short OR=278;
public final static short UMINUS=279;
public final static short STATIC=280;
public final static short INSTANCEOF=281;
public final static short NUMINSTANCES=282;
public final static short LESS_EQUAL=283;
public final static short GREATER_EQUAL=284;
public final static short EQUAL=285;
public final static short NOT_EQUAL=286;
public final static short SELF_PLUS=287;
public final static short SELF_MINUS=288;
public final static short EMPTY=289;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    6,    6,    7,    7,    7,    9,    9,   10,
   10,    8,    8,   11,   12,   12,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   14,   14,   14,   24,   24,
   21,   21,   23,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   26,   26,   25,   25,   27,
   27,   16,   17,   20,   15,   28,   28,   18,   18,   19,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    2,    0,    2,    2,    0,    1,    0,    3,
    1,    7,    6,    3,    2,    0,    1,    2,    1,    1,
    1,    2,    2,    2,    1,    3,    1,    0,    2,    0,
    2,    4,    5,    1,    1,    1,    2,    2,    2,    2,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    2,    2,    5,    3,    3,    1,
    4,    5,    6,    4,    5,    1,    1,    1,    0,    3,
    1,    5,    9,    1,    6,    2,    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   13,   17,
    0,    7,    8,    6,    9,    0,    0,   12,   15,    0,
    0,   16,   10,    0,    4,    0,    0,    0,    0,   11,
    0,   21,    0,    0,    0,    0,    5,    0,    0,    0,
   26,   23,   20,   22,    0,   77,   70,    0,    0,    0,
    0,   84,    0,    0,    0,    0,   76,    0,    0,    0,
    0,    0,    0,    0,   24,   27,   35,   25,    0,   29,
   30,   31,    0,    0,    0,    0,    0,    0,    0,   46,
    0,    0,    0,   44,    0,   45,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   28,
   32,   33,   34,    0,    0,    0,    0,    0,    0,    0,
   49,   50,    0,    0,    0,    0,    0,    0,    0,   39,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   68,   69,    0,    0,    0,   64,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   71,    0,    0,   90,
    0,   74,    0,   42,    0,    0,    0,   82,    0,    0,
   72,    0,    0,   75,    0,   43,    0,    0,   85,   73,
    0,   86,    0,   83,
};
final static short yydgoto[] = {                          2,
    3,    4,   66,   20,   33,    8,   11,   22,   34,   35,
   67,   45,   68,   69,   70,   71,   72,   73,   74,   75,
   84,   77,   86,   79,  166,   80,  130,  179,
};
final static short yysindex[] = {                      -245,
 -255,    0, -245,    0, -241,    0, -248,  -85,    0,    0,
  -80,    0,    0,    0,    0, -233, -128,    0,    0,   -5,
  -83,    0,    0,  -81,    0,   15,  -34,   21, -128,    0,
 -128,    0,  -78,   24,   30,   34,    0,  -46, -128,  -46,
    0,    0,    0,    0,   -1,    0,    0,   38,   40,   41,
   95,    0, -208,   42,   45,   48,    0,   49,   51,   95,
   95,   95,   95,   53,    0,    0,    0,    0,   35,    0,
    0,    0,   36,   37,   44,   22,  541,    0, -179,    0,
   95,   95,   95,    0,  541,    0,   59,   13,   95,   64,
   67,   95, -167,  -44,  -44,  -43,  -43, -166,  160,    0,
    0,    0,    0,   95,   95,   95,   95,   95,   95,   95,
    0,    0,   95,   95,   95,   95,   95,   95,   95,    0,
   95,   95,   72,  389,   54,  413,   75,   74,  541,   19,
    0,    0,  425,   76,   77,    0,  541,  770,  592,  -12,
  -12,  844,  844,   20,   20,  -43,  -43,  -43,  -12,  -12,
  447,  481,   95,   31,   95,   31,    0,  505,   95,    0,
 -161,    0,   95,    0,   95,   79,   81,    0,  517, -147,
    0,  541,   82,    0,  580,    0,   95,   31,    0,    0,
   85,    0,   31,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  127,    0,   16,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   90,    0,    0,  100,    0,
  100,    0,    0,    0,  109,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -58,    0,    0,    0,    0,    0,
  -55,    0,    0,    0,    0,    0,    0,    0,    0, -123,
 -123, -123, -123, -123,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  568,    0,  128,    0,    0,
 -123,  -58, -123,    0,   96,    0,    0,    0, -123,    0,
    0, -123,    0,  856,  880,  909,  933,    0,    0,    0,
    0,    0,    0, -123, -123, -123, -123, -123, -123, -123,
    0,    0, -123, -123, -123, -123, -123, -123, -123,    0,
 -123, -123,  101,    0,    0,    0,    0, -123,   28,    0,
    0,    0,    0,    0,    0,    0,  -23,   43,   93,  437,
  462,  473, 1085, 1052, 1109,  969, 1001, 1028,  946,  959,
    0,    0,  -30,  -58, -123,  -58,    0,    0, -123,    0,
    0,    0, -123,    0, -123,    0,  116,    0,    0,  -33,
    0,   29,    0,    0,  -35,    0,  -26,  -58,    0,    0,
    0,    0,  -58,    0,
};
final static short yygindex[] = {                         0,
    0,  155,  157,   -2,    3,    0,    0,    0,  135,    0,
    2,    0, -137,  -77,    0,    0,    0,    0,    0,    0,
  240, 1292,  598,    0,    0,    0,   23,    0,
};
final static int YYTABLESIZE=1457;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         87,
   38,  120,  120,   89,  125,   67,   87,   27,   67,   27,
   79,   87,   27,   21,   38,    1,  168,   36,  170,   24,
    5,    7,   67,   67,  117,   87,   32,    9,   32,  115,
  113,   63,  114,  120,  116,   36,   43,   10,   64,   42,
  182,   44,   23,   62,   18,  184,  121,  121,   12,   13,
   14,   15,   16,   25,   29,   88,  117,   67,   30,  160,
   31,  115,  159,   63,   38,  120,  116,   87,   81,   80,
   64,   81,   80,   39,   40,   62,   41,   81,  121,   82,
   83,   89,  104,   62,   90,   63,   62,   91,   92,   87,
   93,   87,   64,  100,  101,  102,  123,   62,  127,  181,
   62,   62,  103,  128,  131,   62,   63,  132,  134,  135,
  121,  153,  155,   64,  173,  157,  162,  163,   62,  176,
  178,   41,  180,   65,  159,  183,    1,   63,   12,   13,
   14,   15,   16,   63,   64,   62,   63,   41,   14,   62,
   19,   41,   41,   41,   41,   41,   41,   41,    5,   18,
   63,   63,   40,   41,   88,   63,   78,    6,   41,   41,
   41,   41,   41,   41,   45,   36,   30,   19,   37,   45,
   45,    0,   45,   45,   45,  167,   12,   13,   14,   15,
   16,    0,    0,    0,    0,   63,   37,   45,    0,   45,
   45,   41,   26,   41,   28,    0,  117,   37,    0,   17,
  136,  115,  113,    0,  114,  120,  116,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   40,   45,  119,
   40,  118,  122,   87,   87,   87,   87,   87,   87,    0,
   87,   87,   87,   87,    0,   87,   87,   87,   87,   87,
   87,   87,   87,  111,  112,   40,    0,   87,   87,   40,
  121,    0,    0,   87,   87,   12,   13,   14,   15,   16,
   46,    0,   47,   48,   49,   50,    0,   51,   52,   53,
   54,   55,   56,   57,  111,  112,    0,    0,    0,   58,
   59,    0,    0,    0,   76,   60,   61,   12,   13,   14,
   15,   16,   46,    0,   47,   48,   49,   50,    0,   51,
   52,   53,   54,   55,   56,   57,  111,  112,    0,    0,
    0,   58,   59,   98,   46,    0,   47,   60,   61,   62,
   62,   76,    0,   53,    0,   55,   56,   57,    0,    0,
    0,    0,    0,   58,   59,   46,    0,   47,    0,   60,
   61,    0,    0,    0,   53,    0,   55,   56,   57,    0,
    0,    0,    0,    0,   58,   59,   46,    0,   47,    0,
   60,   61,    0,    0,    0,   53,    0,   55,   56,   57,
   63,    0,    0,    0,    0,   58,   59,   41,   41,    0,
    0,   60,   61,   41,   41,   41,   41,   41,   41,    0,
    0,    0,    0,   76,    0,   76,    0,    0,    0,    0,
    0,    0,    0,    0,   45,   45,    0,    0,    0,    0,
   45,   45,   45,   45,   45,   45,   76,   76,    0,    0,
    0,    0,   76,    0,    0,  117,    0,    0,    0,  154,
  115,  113,    0,  114,  120,  116,  105,  106,    0,    0,
    0,    0,  107,  108,  109,  110,  111,  112,  119,  117,
  118,  122,    0,  156,  115,  113,    0,  114,  120,  116,
    0,  117,    0,    0,    0,    0,  115,  113,  161,  114,
  120,  116,  119,    0,  118,  122,    0,   60,    0,  121,
   60,    0,    0,  117,  119,    0,  118,  122,  115,  113,
    0,  114,  120,  116,   60,   60,    0,    0,    0,   60,
    0,    0,   61,  121,    0,   61,  119,    0,  118,  122,
    0,    0,    0,   56,    0,  121,   56,  117,    0,   61,
   61,    0,  115,  113,   61,  114,  120,  116,    0,   60,
   56,   56,    0,    0,    0,   56,    0,  121,  165,  164,
  119,  117,  118,  122,    0,    0,  115,  113,    0,  114,
  120,  116,    0,  117,   61,    0,    0,    0,  115,  113,
    0,  114,  120,  116,  119,   56,  118,  122,    0,    0,
    0,  121,    0,    0,    0,  177,  119,  117,  118,  122,
    0,    0,  115,  113,    0,  114,  120,  116,    0,    0,
    0,    0,    0,    0,    0,  121,    0,  171,    0,    0,
  119,    0,  118,  122,   44,    0,    0,  121,    0,   44,
   44,    0,   44,   44,   44,    0,  117,    0,    0,    0,
    0,  115,  113,    0,  114,  120,  116,   44,  117,   44,
   44,  121,    0,  115,  113,    0,  114,  120,  116,  119,
    0,  118,   78,    0,    0,    0,    0,    0,    0,    0,
    0,  119,    0,  118,    0,    0,    0,    0,   44,    0,
    0,    0,    0,    0,    0,  105,  106,    0,    0,    0,
  121,  107,  108,  109,  110,  111,  112,    0,    0,   78,
    0,    0,  121,    0,    0,    0,    0,    0,    0,  105,
  106,    0,    0,    0,    0,  107,  108,  109,  110,  111,
  112,  105,  106,    0,    0,    0,    0,  107,  108,  109,
  110,  111,  112,   60,   60,    0,    0,    0,    0,    0,
    0,   60,   60,  105,  106,    0,    0,    0,    0,  107,
  108,  109,  110,  111,  112,    0,    0,    0,   61,   61,
    0,    0,    0,    0,    0,    0,   61,   61,    0,   56,
   56,   78,    0,   78,    0,    0,    0,  105,  106,    0,
    0,    0,    0,  107,  108,  109,  110,  111,  112,    0,
    0,    0,    0,    0,   78,   78,    0,    0,    0,    0,
   78,  105,  106,    0,    0,    0,    0,  107,  108,  109,
  110,  111,  112,  105,  106,    0,    0,    0,    0,  107,
  108,  109,  110,  111,  112,    0,  117,    0,    0,    0,
    0,  115,  113,    0,  114,  120,  116,  105,  106,    0,
    0,    0,    0,  107,  108,  109,  110,  111,  112,  119,
    0,  118,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   44,   44,    0,    0,    0,    0,
   44,   44,   44,   44,   44,   44,  105,  106,    0,    0,
  121,    0,  107,  108,  109,  110,  111,  112,  105,    0,
    0,    0,    0,    0,  107,  108,  109,  110,  111,  112,
  117,    0,    0,    0,    0,  115,  113,    0,  114,  120,
  116,    0,   47,    0,    0,    0,   47,   47,   47,   47,
   47,    0,   47,  119,    0,  118,    0,    0,    0,    0,
    0,    0,    0,   47,   47,   47,   48,   47,   47,    0,
   48,   48,   48,   48,   48,    0,   48,    0,    0,    0,
    0,    0,    0,    0,  121,    0,    0,   48,   48,   48,
    0,   48,   48,    0,    0,   65,    0,    0,   47,   65,
   65,   65,   65,   65,    0,   65,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   65,   65,   65,   66,
   65,   65,   48,   66,   66,   66,   66,   66,    0,   66,
    0,    0,    0,    0,    0,    0,   59,    0,    0,   59,
   66,   66,   66,    0,   66,   66,    0,    0,    0,   58,
    0,   65,   58,   59,   59,   53,    0,    0,   59,   53,
   53,   53,   53,   53,    0,   53,   58,   58,    0,    0,
    0,   58,    0,    0,    0,   66,   53,   53,   53,    0,
   53,   53,    0,    0,    0,    0,    0,   54,   59,    0,
    0,   54,   54,   54,   54,   54,    0,   54,    0,    0,
    0,   58,  107,  108,  109,  110,  111,  112,   54,   54,
   54,   53,   54,   54,   55,    0,    0,    0,   55,   55,
   55,   55,   55,    0,   55,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   55,   55,   55,    0,   55,
   55,    0,   51,   54,   51,   51,   51,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   51,
   51,   51,    0,   51,   51,    0,    0,    0,    0,    0,
   55,    0,    0,    0,    0,   57,  107,  108,   57,    0,
  111,  112,   47,   47,    0,    0,    0,    0,   47,   47,
   47,   47,   57,   57,   51,    0,    0,   57,    0,   52,
    0,   52,   52,   52,    0,    0,   48,   48,    0,    0,
    0,    0,   48,   48,   48,   48,   52,   52,   52,    0,
   52,   52,    0,    0,    0,    0,    0,   57,    0,    0,
    0,    0,    0,    0,    0,   65,   65,    0,    0,    0,
    0,   65,   65,   65,   65,    0,    0,    0,    0,    0,
    0,   52,    0,    0,    0,    0,    0,    0,    0,   66,
   66,    0,    0,    0,    0,   66,   66,   66,   66,    0,
    0,    0,   59,   59,    0,    0,    0,    0,    0,    0,
   59,   59,    0,    0,    0,   58,   58,    0,    0,    0,
    0,    0,    0,   58,   58,   53,   53,    0,    0,    0,
    0,   53,   53,   53,   53,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   54,   54,    0,
    0,    0,    0,   54,   54,   54,   54,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   55,   55,    0,    0,    0,    0,
   55,   55,   55,   55,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   51,   51,
    0,    0,    0,    0,   51,   51,   51,   51,    0,    0,
    0,    0,   85,    0,    0,    0,    0,    0,    0,    0,
    0,   94,   95,   96,   97,   99,    0,    0,    0,    0,
    0,   57,   57,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  124,    0,  126,    0,    0,    0,    0,    0,
  129,    0,    0,  133,    0,   52,   52,    0,    0,    0,
    0,   52,   52,   52,   52,  137,  138,  139,  140,  141,
  142,  143,    0,    0,  144,  145,  146,  147,  148,  149,
  150,    0,  151,  152,    0,    0,    0,    0,    0,  158,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  129,    0,  169,    0,    0,    0,
  172,    0,    0,    0,  174,    0,  175,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   46,   46,   59,   82,   41,   40,   91,   44,   91,
   41,   45,   91,   11,   41,  261,  154,   41,  156,   17,
  276,  263,   58,   59,   37,   59,   29,  276,   31,   42,
   43,   33,   45,   46,   47,   59,   39,  123,   40,   38,
  178,   40,  276,   45,  125,  183,   91,   91,  257,  258,
  259,  260,  261,   59,   40,   53,   37,   93,   93,   41,
   40,   42,   44,   33,   41,   46,   47,  276,   41,   41,
   40,   44,   44,   44,   41,   45,  123,   40,   91,   40,
   40,   40,   61,   41,   40,   33,   44,   40,   40,  123,
   40,  125,   40,   59,   59,   59,  276,   45,   40,  177,
   58,   59,   59,   91,   41,   63,   33,   41,  276,  276,
   91,   40,   59,   40,  276,   41,   41,   41,   45,   41,
  268,  123,   41,  125,   44,   41,    0,   33,  257,  258,
  259,  260,  261,   41,   40,   93,   44,   37,  123,   45,
   41,   41,   42,   43,   44,   45,   46,   47,   59,   41,
   58,   59,  276,  123,   59,   63,   41,    3,   58,   59,
   60,   61,   62,   63,   37,   31,   93,   11,   41,   42,
   43,   -1,   45,   46,   47,  153,  257,  258,  259,  260,
  261,   -1,   -1,   -1,   -1,   93,   59,   60,   -1,   62,
   63,   91,  276,   93,  276,   -1,   37,  276,   -1,  280,
   41,   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  276,   91,   60,
  276,   62,   63,  257,  258,  259,  260,  261,  262,   -1,
  264,  265,  266,  267,   -1,  269,  270,  271,  272,  273,
  274,  275,  276,  287,  288,  276,   -1,  281,  282,  276,
   91,   -1,   -1,  287,  288,  257,  258,  259,  260,  261,
  262,   -1,  264,  265,  266,  267,   -1,  269,  270,  271,
  272,  273,  274,  275,  287,  288,   -1,   -1,   -1,  281,
  282,   -1,   -1,   -1,   45,  287,  288,  257,  258,  259,
  260,  261,  262,   -1,  264,  265,  266,  267,   -1,  269,
  270,  271,  272,  273,  274,  275,  287,  288,   -1,   -1,
   -1,  281,  282,  261,  262,   -1,  264,  287,  288,  277,
  278,   82,   -1,  271,   -1,  273,  274,  275,   -1,   -1,
   -1,   -1,   -1,  281,  282,  262,   -1,  264,   -1,  287,
  288,   -1,   -1,   -1,  271,   -1,  273,  274,  275,   -1,
   -1,   -1,   -1,   -1,  281,  282,  262,   -1,  264,   -1,
  287,  288,   -1,   -1,   -1,  271,   -1,  273,  274,  275,
  278,   -1,   -1,   -1,   -1,  281,  282,  277,  278,   -1,
   -1,  287,  288,  283,  284,  285,  286,  287,  288,   -1,
   -1,   -1,   -1,  154,   -1,  156,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,   -1,   -1,
  283,  284,  285,  286,  287,  288,  177,  178,   -1,   -1,
   -1,   -1,  183,   -1,   -1,   37,   -1,   -1,   -1,   41,
   42,   43,   -1,   45,   46,   47,  277,  278,   -1,   -1,
   -1,   -1,  283,  284,  285,  286,  287,  288,   60,   37,
   62,   63,   -1,   41,   42,   43,   -1,   45,   46,   47,
   -1,   37,   -1,   -1,   -1,   -1,   42,   43,   44,   45,
   46,   47,   60,   -1,   62,   63,   -1,   41,   -1,   91,
   44,   -1,   -1,   37,   60,   -1,   62,   63,   42,   43,
   -1,   45,   46,   47,   58,   59,   -1,   -1,   -1,   63,
   -1,   -1,   41,   91,   -1,   44,   60,   -1,   62,   63,
   -1,   -1,   -1,   41,   -1,   91,   44,   37,   -1,   58,
   59,   -1,   42,   43,   63,   45,   46,   47,   -1,   93,
   58,   59,   -1,   -1,   -1,   63,   -1,   91,   58,   93,
   60,   37,   62,   63,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   -1,   37,   93,   -1,   -1,   -1,   42,   43,
   -1,   45,   46,   47,   60,   93,   62,   63,   -1,   -1,
   -1,   91,   -1,   -1,   -1,   59,   60,   37,   62,   63,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   91,   -1,   93,   -1,   -1,
   60,   -1,   62,   63,   37,   -1,   -1,   91,   -1,   42,
   43,   -1,   45,   46,   47,   -1,   37,   -1,   -1,   -1,
   -1,   42,   43,   -1,   45,   46,   47,   60,   37,   62,
   63,   91,   -1,   42,   43,   -1,   45,   46,   47,   60,
   -1,   62,   45,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   60,   -1,   62,   -1,   -1,   -1,   -1,   91,   -1,
   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,   -1,
   91,  283,  284,  285,  286,  287,  288,   -1,   -1,   82,
   -1,   -1,   91,   -1,   -1,   -1,   -1,   -1,   -1,  277,
  278,   -1,   -1,   -1,   -1,  283,  284,  285,  286,  287,
  288,  277,  278,   -1,   -1,   -1,   -1,  283,  284,  285,
  286,  287,  288,  277,  278,   -1,   -1,   -1,   -1,   -1,
   -1,  285,  286,  277,  278,   -1,   -1,   -1,   -1,  283,
  284,  285,  286,  287,  288,   -1,   -1,   -1,  277,  278,
   -1,   -1,   -1,   -1,   -1,   -1,  285,  286,   -1,  277,
  278,  154,   -1,  156,   -1,   -1,   -1,  277,  278,   -1,
   -1,   -1,   -1,  283,  284,  285,  286,  287,  288,   -1,
   -1,   -1,   -1,   -1,  177,  178,   -1,   -1,   -1,   -1,
  183,  277,  278,   -1,   -1,   -1,   -1,  283,  284,  285,
  286,  287,  288,  277,  278,   -1,   -1,   -1,   -1,  283,
  284,  285,  286,  287,  288,   -1,   37,   -1,   -1,   -1,
   -1,   42,   43,   -1,   45,   46,   47,  277,  278,   -1,
   -1,   -1,   -1,  283,  284,  285,  286,  287,  288,   60,
   -1,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,   -1,   -1,
  283,  284,  285,  286,  287,  288,  277,  278,   -1,   -1,
   91,   -1,  283,  284,  285,  286,  287,  288,  277,   -1,
   -1,   -1,   -1,   -1,  283,  284,  285,  286,  287,  288,
   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   46,
   47,   -1,   37,   -1,   -1,   -1,   41,   42,   43,   44,
   45,   -1,   47,   60,   -1,   62,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   58,   59,   60,   37,   62,   63,   -1,
   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   91,   -1,   -1,   58,   59,   60,
   -1,   62,   63,   -1,   -1,   37,   -1,   -1,   93,   41,
   42,   43,   44,   45,   -1,   47,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,   60,   37,
   62,   63,   93,   41,   42,   43,   44,   45,   -1,   47,
   -1,   -1,   -1,   -1,   -1,   -1,   41,   -1,   -1,   44,
   58,   59,   60,   -1,   62,   63,   -1,   -1,   -1,   41,
   -1,   93,   44,   58,   59,   37,   -1,   -1,   63,   41,
   42,   43,   44,   45,   -1,   47,   58,   59,   -1,   -1,
   -1,   63,   -1,   -1,   -1,   93,   58,   59,   60,   -1,
   62,   63,   -1,   -1,   -1,   -1,   -1,   37,   93,   -1,
   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,
   -1,   93,  283,  284,  285,  286,  287,  288,   58,   59,
   60,   93,   62,   63,   37,   -1,   -1,   -1,   41,   42,
   43,   44,   45,   -1,   47,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   58,   59,   60,   -1,   62,
   63,   -1,   41,   93,   43,   44,   45,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,
   59,   60,   -1,   62,   63,   -1,   -1,   -1,   -1,   -1,
   93,   -1,   -1,   -1,   -1,   41,  283,  284,   44,   -1,
  287,  288,  277,  278,   -1,   -1,   -1,   -1,  283,  284,
  285,  286,   58,   59,   93,   -1,   -1,   63,   -1,   41,
   -1,   43,   44,   45,   -1,   -1,  277,  278,   -1,   -1,
   -1,   -1,  283,  284,  285,  286,   58,   59,   60,   -1,
   62,   63,   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,   -1,
   -1,  283,  284,  285,  286,   -1,   -1,   -1,   -1,   -1,
   -1,   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,
  278,   -1,   -1,   -1,   -1,  283,  284,  285,  286,   -1,
   -1,   -1,  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,
  285,  286,   -1,   -1,   -1,  277,  278,   -1,   -1,   -1,
   -1,   -1,   -1,  285,  286,  277,  278,   -1,   -1,   -1,
   -1,  283,  284,  285,  286,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,
   -1,   -1,   -1,  283,  284,  285,  286,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,   -1,   -1,
  283,  284,  285,  286,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,
   -1,   -1,   -1,   -1,  283,  284,  285,  286,   -1,   -1,
   -1,   -1,   51,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   60,   61,   62,   63,   64,   -1,   -1,   -1,   -1,
   -1,  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   81,   -1,   83,   -1,   -1,   -1,   -1,   -1,
   89,   -1,   -1,   92,   -1,  277,  278,   -1,   -1,   -1,
   -1,  283,  284,  285,  286,  104,  105,  106,  107,  108,
  109,  110,   -1,   -1,  113,  114,  115,  116,  117,  118,
  119,   -1,  121,  122,   -1,   -1,   -1,   -1,   -1,  128,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  153,   -1,  155,   -1,   -1,   -1,
  159,   -1,   -1,   -1,  163,   -1,  165,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=289;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'","'?'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"CLASS","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN","BREAK",
"NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND","OR",
"UMINUS","STATIC","INSTANCEOF","NUMINSTANCES","LESS_EQUAL","GREATER_EQUAL",
"EQUAL","NOT_EQUAL","SELF_PLUS","SELF_MINUS","EMPTY",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : SELF_PLUS Expr",
"Expr : SELF_MINUS Expr",
"Expr : Expr SELF_PLUS",
"Expr : Expr SELF_MINUS",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : Expr '?' Expr ':' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : NUMINSTANCES '(' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
};

//#line 450 "Parser.y"
    
	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 680 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 55 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 61 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 65 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 75 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 81 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 85 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 89 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 93 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 97 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 11:
//#line 101 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 12:
//#line 107 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 13:
//#line 113 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 14:
//#line 117 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 15:
//#line 123 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 16:
//#line 127 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 17:
//#line 131 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 19:
//#line 139 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 20:
//#line 146 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 21:
//#line 150 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 22:
//#line 157 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 23:
//#line 161 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 167 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 25:
//#line 173 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 26:
//#line 177 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 27:
//#line 184 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 28:
//#line 189 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 36:
//#line 204 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 37:
//#line 208 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 38:
//#line 212 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 40:
//#line 219 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 41:
//#line 225 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 42:
//#line 232 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 43:
//#line 238 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 44:
//#line 247 "Parser.y"
{
                        yyval.expr = val_peek(0).lvalue;
                    }
break;
case 47:
//#line 253 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.PREINC, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 48:
//#line 257 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.PREDEC, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 49:
//#line 261 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.POSTINC, val_peek(1).expr, val_peek(0).loc);
                    }
break;
case 50:
//#line 265 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.POSTDEC, val_peek(1).expr, val_peek(0).loc);
                    }
break;
case 51:
//#line 269 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 52:
//#line 273 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 53:
//#line 277 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 54:
//#line 281 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 55:
//#line 285 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 56:
//#line 289 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 57:
//#line 293 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 58:
//#line 297 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 59:
//#line 301 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 60:
//#line 305 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 61:
//#line 309 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 62:
//#line 313 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 63:
//#line 317 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 64:
//#line 321 "Parser.y"
{
                        yyval = val_peek(1);
                    }
break;
case 65:
//#line 325 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 66:
//#line 329 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 67:
//#line 333 "Parser.y"
{
                        yyval.expr = new Tree.Ternary(Tree.TERNARY, val_peek(4).expr, val_peek(2).expr, val_peek(0).expr, val_peek(4).loc);
                    }
break;
case 68:
//#line 337 "Parser.y"
{
                        yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                    }
break;
case 69:
//#line 341 "Parser.y"
{
                        yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                    }
break;
case 70:
//#line 345 "Parser.y"
{
                        yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                    }
break;
case 71:
//#line 349 "Parser.y"
{
                        yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                    }
break;
case 72:
//#line 353 "Parser.y"
{
                        yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                    }
break;
case 73:
//#line 357 "Parser.y"
{
                        yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                    }
break;
case 74:
//#line 361 "Parser.y"
{
                        yyval.expr = new Tree.Numinstances(val_peek(1).ident, val_peek(3).loc);
                    }
break;
case 75:
//#line 365 "Parser.y"
{
                        yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                    }
break;
case 76:
//#line 371 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 77:
//#line 375 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 79:
//#line 382 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 80:
//#line 389 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 81:
//#line 393 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 82:
//#line 400 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 83:
//#line 406 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 84:
//#line 412 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 85:
//#line 418 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 86:
//#line 424 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 87:
//#line 428 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 88:
//#line 434 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 89:
//#line 438 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 90:
//#line 444 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1303 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
