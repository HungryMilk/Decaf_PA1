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
public final static short LESS_EQUAL=282;
public final static short GREATER_EQUAL=283;
public final static short EQUAL=284;
public final static short NOT_EQUAL=285;
public final static short SELF_PLUS=286;
public final static short SELF_MINUS=287;
public final static short EMPTY=288;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    6,    6,    7,    7,    7,    9,    9,   10,
   10,    8,    8,   11,   12,   12,   13,   13,   13,   13,
   13,   13,   13,   13,   13,   14,   14,   14,   24,   24,
   21,   21,   23,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   22,   22,   22,   22,
   22,   22,   22,   26,   26,   25,   25,   27,   27,   16,
   17,   20,   15,   28,   28,   18,   18,   19,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    2,    0,    2,    2,    0,    1,    0,    3,
    1,    7,    6,    3,    2,    0,    1,    2,    1,    1,
    1,    2,    2,    2,    1,    3,    1,    0,    2,    0,
    2,    4,    5,    1,    1,    1,    2,    2,    2,    2,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    2,    2,    3,    3,    1,    4,
    5,    6,    5,    1,    1,    1,    0,    3,    1,    5,
    9,    1,    6,    2,    0,    2,    1,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    2,    0,    0,   13,   17,
    0,    7,    8,    6,    9,    0,    0,   12,   15,    0,
    0,   16,   10,    0,    4,    0,    0,    0,    0,   11,
    0,   21,    0,    0,    0,    0,    5,    0,    0,    0,
   26,   23,   20,   22,    0,   75,   69,    0,    0,    0,
    0,   82,    0,    0,    0,    0,   74,    0,    0,    0,
    0,    0,    0,   24,   27,   35,   25,    0,   29,   30,
   31,    0,    0,    0,    0,    0,    0,    0,   46,    0,
    0,    0,   44,    0,   45,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   28,   32,   33,
   34,    0,    0,    0,    0,    0,    0,    0,   49,   50,
    0,    0,    0,    0,    0,    0,    0,   39,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   67,   68,    0,
    0,   64,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   70,    0,    0,   88,    0,    0,   42,    0,    0,
   80,    0,    0,   71,    0,    0,   73,   43,    0,    0,
   83,   72,    0,   84,    0,   81,
};
final static short yydgoto[] = {                          2,
    3,    4,   65,   20,   33,    8,   11,   22,   34,   35,
   66,   45,   67,   68,   69,   70,   71,   72,   73,   74,
   83,   76,   85,   78,  159,   79,  127,  171,
};
final static short yysindex[] = {                      -248,
 -255,    0, -248,    0, -238,    0, -247,  -93,    0,    0,
  219,    0,    0,    0,    0, -242, -149,    0,    0,  -18,
  -83,    0,    0,  -82,    0,    2,  -37,   18, -149,    0,
 -149,    0,  -76,   24,   26,   25,    0,  -51, -149,  -51,
    0,    0,    0,    0,   -2,    0,    0,   33,   39,   42,
   92,    0,   64,   43,   44,   45,    0,   47,   92,   92,
   92,   92,   56,    0,    0,    0,    0,   17,    0,    0,
    0,   32,   35,   40,   37,  486,    0, -188,    0,   92,
   92,   92,    0,  486,    0,   60,   11,   92,   62,   65,
   92,  -44,  -44,  -42,  -42, -171,  139,    0,    0,    0,
    0,   92,   92,   92,   92,   92,   92,   92,    0,    0,
   92,   92,   92,   92,   92,   92,   92,    0,   92,   73,
  161,   57,  390,   76,   74,  486,  -22,    0,    0,  412,
   77,    0,  486,  539,  518,  -10,  -10,  546,  546,  -36,
  -36,  -42,  -42,  -42,  -10,  -10,  423,   92,   29,   92,
   29,    0,  445,   92,    0, -161,   92,    0,   81,   80,
    0,  475, -142,    0,  486,   90,    0,    0,   92,   29,
    0,    0,   93,    0,   29,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  133,    0,   15,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   87,    0,    0,   95,    0,
   95,    0,    0,    0,  106,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -56,    0,    0,    0,    0,    0,
  -54,    0,    0,    0,    0,    0,    0,    0, -126, -126,
 -126, -126, -126,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  507,    0,  128,    0,    0, -126,
  -56, -126,    0,   94,    0,    0,    0, -126,    0,    0,
 -126,  573,  599,  674,  800,    0,    0,    0,    0,    0,
    0, -126, -126, -126, -126, -126, -126, -126,    0,    0,
 -126, -126, -126, -126, -126, -126, -126,    0, -126,   98,
    0,    0,    0,    0, -126,   13,    0,    0,    0,    0,
    0,    0,  -13,    4,   27,  327,  531,   34,   36,  465,
  737,  826,  853,  862,  873,  882,    0,  -27,  -56, -126,
  -56,    0,    0, -126,    0,    0, -126,    0,    0,  110,
    0,    0,  -33,    0,   23,    0,    0,    0,   -1,  -56,
    0,    0,    0,    0,  -56,    0,
};
final static short yygindex[] = {                         0,
    0,  152,  145,   22,    6,    0,    0,    0,  130,    0,
   12,    0, -131,  -65,    0,    0,    0,    0,    0,    0,
  -21,  922,  767,    0,    0,    0,   14,    0,
};
final static int YYTABLESIZE=1167;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         85,
  115,  118,   38,  118,   87,  113,   85,   27,   27,  118,
  114,   85,    1,   77,   27,  122,   21,  161,  155,  163,
    5,  154,   24,   75,    7,   85,  115,   36,    9,   10,
   62,  113,  111,   23,  112,  118,  114,   63,  174,   38,
   25,   29,   61,  176,   62,   36,  119,   62,  119,   42,
   32,   44,   32,   79,  119,   30,   79,   31,   87,   75,
   43,   62,   62,   78,   38,   40,   78,   63,   63,   39,
   63,   41,   80,   61,   56,   98,   57,   56,   81,   57,
  119,   82,   88,   89,   90,   63,   91,  120,   62,   85,
   99,   85,   56,  100,   57,   63,   62,  102,  101,  124,
   61,  125,  128,  173,  131,  129,   62,   12,   13,   14,
   15,   16,  148,   63,  166,  150,  152,  157,   61,   63,
   41,  168,   64,  154,   62,  170,   56,   75,   57,   75,
  172,   63,    1,  175,   41,   19,   61,   14,   41,   41,
   41,   41,   41,   41,   41,    5,   18,   75,   75,   40,
   76,   41,   86,   75,    6,   19,   41,   41,   41,   41,
   36,  160,    0,    0,   45,    0,   30,    0,   37,   45,
   45,    0,   45,   45,   45,  115,    0,    0,    0,  132,
  113,  111,    0,  112,  118,  114,   37,   45,   41,   45,
   41,    0,   26,   28,    0,    0,    0,  115,  117,   37,
  116,  149,  113,  111,    0,  112,  118,  114,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   45,   40,
  117,   40,  116,   85,   85,   85,   85,   85,   85,  119,
   85,   85,   85,   85,    0,   85,   85,   85,   85,   85,
   85,   85,   85,  109,  110,    0,    0,   85,   40,  109,
  110,  119,   85,   85,   12,   13,   14,   15,   16,   46,
    0,   47,   48,   49,   50,    0,   51,   52,   53,   54,
   55,   56,   57,    0,   40,  109,  110,    0,   58,    0,
   62,   62,    0,   59,   60,   12,   13,   14,   15,   16,
   46,    0,   47,   48,   49,   50,    0,   51,   52,   53,
   54,   55,   56,   57,   63,    0,    0,    0,    0,   58,
   56,   56,   57,   57,   59,   60,   96,   46,    0,   47,
   12,   13,   14,   15,   16,    0,   53,    0,   55,   56,
   57,    0,    0,    0,    0,   46,   58,   47,    0,   86,
    0,   59,   60,   18,   53,    0,   55,   56,   57,    0,
    0,    0,    0,   46,   58,   47,    0,    0,    0,   59,
   60,    0,   53,    0,   55,   56,   57,   60,    0,    0,
   60,    0,   58,    0,   41,   41,    0,   59,   60,   41,
   41,   41,   41,   41,   41,   60,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   45,   45,    0,    0,    0,   45,
   45,   45,   45,   45,   45,  103,  104,    0,    0,   60,
  105,  106,  107,  108,  109,  110,  115,    0,    0,    0,
  151,  113,  111,    0,  112,  118,  114,  103,  104,    0,
    0,    0,  105,  106,  107,  108,  109,  110,  115,  117,
    0,  116,    0,  113,  111,  156,  112,  118,  114,  115,
    0,    0,    0,    0,  113,  111,    0,  112,  118,  114,
    0,  117,    0,  116,    0,   12,   13,   14,   15,   16,
  119,  115,  117,    0,  116,    0,  113,  111,    0,  112,
  118,  114,    0,    0,    0,    0,    0,    0,   17,    0,
    0,    0,  119,    0,  117,   51,  116,   51,   51,   51,
    0,  115,    0,  119,    0,  158,  113,  111,    0,  112,
  118,  114,  115,   51,   51,    0,   51,  113,  111,    0,
  112,  118,  114,  169,  117,  119,  116,  164,    0,    0,
    0,    0,    0,   44,    0,  117,    0,  116,   44,   44,
    0,   44,   44,   44,  115,    0,    0,   51,    0,  113,
  111,    0,  112,  118,  114,  119,   44,    0,   44,    0,
    0,   61,    0,    0,   61,  115,  119,  117,    0,  116,
  113,  111,  115,  112,  118,  114,    0,  113,  111,   61,
  112,  118,  114,    0,    0,    0,    0,   44,  117,    0,
  116,    0,    0,   60,   60,  117,    0,  116,  119,   47,
   60,   60,    0,   47,   47,   47,   47,   47,    0,   47,
    0,    0,    0,   61,    0,    0,    0,    0,    0,  119,
    0,   47,   47,    0,   47,   48,  119,    0,    0,   48,
   48,   48,   48,   48,    0,   48,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   48,   48,    0,
   48,    0,    0,    0,    0,   47,  103,  104,    0,    0,
    0,  105,  106,  107,  108,  109,  110,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  103,  104,
    0,   48,    0,  105,  106,  107,  108,  109,  110,  103,
  104,    0,    0,    0,  105,  106,  107,  108,  109,  110,
   65,    0,    0,    0,   65,   65,   65,   65,   65,    0,
   65,  103,  104,    0,    0,    0,  105,  106,  107,  108,
  109,  110,   65,   65,    0,   65,    0,    0,    0,    0,
    0,   51,   51,    0,    0,    0,   51,   51,   51,   51,
    0,  103,  104,    0,    0,    0,  105,  106,  107,  108,
  109,  110,  103,  104,    0,    0,   65,  105,  106,  107,
  108,  109,  110,    0,    0,    0,    0,   52,    0,   52,
   52,   52,    0,   44,   44,    0,    0,    0,   44,   44,
   44,   44,   44,   44,  103,   52,   52,    0,   52,  105,
  106,  107,  108,  109,  110,    0,    0,   61,   61,    0,
    0,   77,    0,    0,   61,   61,    0,    0,    0,    0,
  105,  106,  107,  108,  109,  110,    0,  105,  106,   52,
    0,  109,  110,    0,    0,    0,   66,    0,    0,    0,
   66,   66,   66,   66,   66,    0,   66,   77,    0,   47,
   47,    0,    0,    0,   47,   47,   47,   47,   66,   66,
    0,   66,   53,    0,    0,    0,   53,   53,   53,   53,
   53,    0,   53,    0,    0,   48,   48,    0,    0,    0,
   48,   48,   48,   48,   53,   53,    0,   53,    0,   54,
    0,    0,   66,   54,   54,   54,   54,   54,   55,   54,
    0,    0,   55,   55,   55,   55,   55,    0,   55,    0,
    0,   54,   54,   59,   54,   77,   59,   77,   53,    0,
   55,   55,   58,   55,    0,   58,    0,    0,    0,    0,
    0,   59,    0,    0,    0,   77,   77,    0,    0,    0,
   58,   77,    0,    0,    0,   54,    0,    0,    0,    0,
   65,   65,    0,    0,   55,   65,   65,   65,   65,    0,
    0,    0,    0,    0,    0,   59,    0,    0,    0,    0,
    0,    0,   84,    0,   58,    0,    0,    0,    0,    0,
   92,   93,   94,   95,   97,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  121,    0,  123,    0,    0,    0,    0,    0,  126,
    0,    0,  130,   52,   52,    0,    0,    0,   52,   52,
   52,   52,    0,  133,  134,  135,  136,  137,  138,  139,
    0,    0,  140,  141,  142,  143,  144,  145,  146,    0,
  147,    0,    0,    0,    0,    0,  153,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  126,
    0,  162,    0,    0,    0,  165,   66,   66,  167,    0,
    0,   66,   66,   66,   66,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   53,   53,    0,    0,    0,   53,   53,   53,
   53,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   54,
   54,    0,    0,    0,   54,   54,   54,   54,   55,   55,
    0,    0,    0,   55,   55,   55,   55,    0,    0,   59,
   59,    0,    0,    0,    0,    0,   59,   59,   58,   58,
    0,    0,    0,    0,    0,   58,   58,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   37,   46,   59,   46,   59,   42,   40,   91,   91,   46,
   47,   45,  261,   41,   91,   81,   11,  149,   41,  151,
  276,   44,   17,   45,  263,   59,   37,   41,  276,  123,
   33,   42,   43,  276,   45,   46,   47,   40,  170,   41,
   59,   40,   45,  175,   41,   59,   91,   44,   91,   38,
   29,   40,   31,   41,   91,   93,   44,   40,   53,   81,
   39,   33,   59,   41,   41,   41,   44,   41,   40,   44,
   44,  123,   40,   45,   41,   59,   41,   44,   40,   44,
   91,   40,   40,   40,   40,   59,   40,  276,   33,  123,
   59,  125,   59,   59,   59,   40,   93,   61,   59,   40,
   45,   91,   41,  169,  276,   41,   33,  257,  258,  259,
  260,  261,   40,   40,  276,   59,   41,   41,   45,   93,
  123,   41,  125,   44,   33,  268,   93,  149,   93,  151,
   41,   40,    0,   41,   37,   41,   45,  123,   41,   42,
   43,   44,   45,   46,   47,   59,   41,  169,  170,  276,
   41,  123,   59,  175,    3,   11,   59,   60,   61,   62,
   31,  148,   -1,   -1,   37,   -1,   93,   -1,   41,   42,
   43,   -1,   45,   46,   47,   37,   -1,   -1,   -1,   41,
   42,   43,   -1,   45,   46,   47,   59,   60,   91,   62,
   93,   -1,  276,  276,   -1,   -1,   -1,   37,   60,  276,
   62,   41,   42,   43,   -1,   45,   46,   47,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,  276,
   60,  276,   62,  257,  258,  259,  260,  261,  262,   91,
  264,  265,  266,  267,   -1,  269,  270,  271,  272,  273,
  274,  275,  276,  286,  287,   -1,   -1,  281,  276,  286,
  287,   91,  286,  287,  257,  258,  259,  260,  261,  262,
   -1,  264,  265,  266,  267,   -1,  269,  270,  271,  272,
  273,  274,  275,   -1,  276,  286,  287,   -1,  281,   -1,
  277,  278,   -1,  286,  287,  257,  258,  259,  260,  261,
  262,   -1,  264,  265,  266,  267,   -1,  269,  270,  271,
  272,  273,  274,  275,  278,   -1,   -1,   -1,   -1,  281,
  277,  278,  277,  278,  286,  287,  261,  262,   -1,  264,
  257,  258,  259,  260,  261,   -1,  271,   -1,  273,  274,
  275,   -1,   -1,   -1,   -1,  262,  281,  264,   -1,  276,
   -1,  286,  287,  125,  271,   -1,  273,  274,  275,   -1,
   -1,   -1,   -1,  262,  281,  264,   -1,   -1,   -1,  286,
  287,   -1,  271,   -1,  273,  274,  275,   41,   -1,   -1,
   44,   -1,  281,   -1,  277,  278,   -1,  286,  287,  282,
  283,  284,  285,  286,  287,   59,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  277,  278,   -1,   -1,   -1,  282,
  283,  284,  285,  286,  287,  277,  278,   -1,   -1,   93,
  282,  283,  284,  285,  286,  287,   37,   -1,   -1,   -1,
   41,   42,   43,   -1,   45,   46,   47,  277,  278,   -1,
   -1,   -1,  282,  283,  284,  285,  286,  287,   37,   60,
   -1,   62,   -1,   42,   43,   44,   45,   46,   47,   37,
   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,
   -1,   60,   -1,   62,   -1,  257,  258,  259,  260,  261,
   91,   37,   60,   -1,   62,   -1,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,  280,   -1,
   -1,   -1,   91,   -1,   60,   41,   62,   43,   44,   45,
   -1,   37,   -1,   91,   -1,   93,   42,   43,   -1,   45,
   46,   47,   37,   59,   60,   -1,   62,   42,   43,   -1,
   45,   46,   47,   59,   60,   91,   62,   93,   -1,   -1,
   -1,   -1,   -1,   37,   -1,   60,   -1,   62,   42,   43,
   -1,   45,   46,   47,   37,   -1,   -1,   93,   -1,   42,
   43,   -1,   45,   46,   47,   91,   60,   -1,   62,   -1,
   -1,   41,   -1,   -1,   44,   37,   91,   60,   -1,   62,
   42,   43,   37,   45,   46,   47,   -1,   42,   43,   59,
   45,   46,   47,   -1,   -1,   -1,   -1,   91,   60,   -1,
   62,   -1,   -1,  277,  278,   60,   -1,   62,   91,   37,
  284,  285,   -1,   41,   42,   43,   44,   45,   -1,   47,
   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,   -1,   91,
   -1,   59,   60,   -1,   62,   37,   91,   -1,   -1,   41,
   42,   43,   44,   45,   -1,   47,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   60,   -1,
   62,   -1,   -1,   -1,   -1,   93,  277,  278,   -1,   -1,
   -1,  282,  283,  284,  285,  286,  287,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,
   -1,   93,   -1,  282,  283,  284,  285,  286,  287,  277,
  278,   -1,   -1,   -1,  282,  283,  284,  285,  286,  287,
   37,   -1,   -1,   -1,   41,   42,   43,   44,   45,   -1,
   47,  277,  278,   -1,   -1,   -1,  282,  283,  284,  285,
  286,  287,   59,   60,   -1,   62,   -1,   -1,   -1,   -1,
   -1,  277,  278,   -1,   -1,   -1,  282,  283,  284,  285,
   -1,  277,  278,   -1,   -1,   -1,  282,  283,  284,  285,
  286,  287,  277,  278,   -1,   -1,   93,  282,  283,  284,
  285,  286,  287,   -1,   -1,   -1,   -1,   41,   -1,   43,
   44,   45,   -1,  277,  278,   -1,   -1,   -1,  282,  283,
  284,  285,  286,  287,  277,   59,   60,   -1,   62,  282,
  283,  284,  285,  286,  287,   -1,   -1,  277,  278,   -1,
   -1,   45,   -1,   -1,  284,  285,   -1,   -1,   -1,   -1,
  282,  283,  284,  285,  286,  287,   -1,  282,  283,   93,
   -1,  286,  287,   -1,   -1,   -1,   37,   -1,   -1,   -1,
   41,   42,   43,   44,   45,   -1,   47,   81,   -1,  277,
  278,   -1,   -1,   -1,  282,  283,  284,  285,   59,   60,
   -1,   62,   37,   -1,   -1,   -1,   41,   42,   43,   44,
   45,   -1,   47,   -1,   -1,  277,  278,   -1,   -1,   -1,
  282,  283,  284,  285,   59,   60,   -1,   62,   -1,   37,
   -1,   -1,   93,   41,   42,   43,   44,   45,   37,   47,
   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,   -1,
   -1,   59,   60,   41,   62,  149,   44,  151,   93,   -1,
   59,   60,   41,   62,   -1,   44,   -1,   -1,   -1,   -1,
   -1,   59,   -1,   -1,   -1,  169,  170,   -1,   -1,   -1,
   59,  175,   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,   93,  282,  283,  284,  285,   -1,
   -1,   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,
   -1,   -1,   51,   -1,   93,   -1,   -1,   -1,   -1,   -1,
   59,   60,   61,   62,   63,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   80,   -1,   82,   -1,   -1,   -1,   -1,   -1,   88,
   -1,   -1,   91,  277,  278,   -1,   -1,   -1,  282,  283,
  284,  285,   -1,  102,  103,  104,  105,  106,  107,  108,
   -1,   -1,  111,  112,  113,  114,  115,  116,  117,   -1,
  119,   -1,   -1,   -1,   -1,   -1,  125,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  148,
   -1,  150,   -1,   -1,   -1,  154,  277,  278,  157,   -1,
   -1,  282,  283,  284,  285,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  277,  278,   -1,   -1,   -1,  282,  283,  284,
  285,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,
  278,   -1,   -1,   -1,  282,  283,  284,  285,  277,  278,
   -1,   -1,   -1,  282,  283,  284,  285,   -1,   -1,  277,
  278,   -1,   -1,   -1,   -1,   -1,  284,  285,  277,  278,
   -1,   -1,   -1,   -1,   -1,  284,  285,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=288;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
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
"UMINUS","STATIC","INSTANCEOF","LESS_EQUAL","GREATER_EQUAL","EQUAL","NOT_EQUAL",
"SELF_PLUS","SELF_MINUS","EMPTY",
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
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
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

//#line 441 "Parser.y"
    
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
//#line 616 "Parser.java"
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
//#line 54 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 60 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 64 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 74 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 80 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 84 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 88 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 92 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 96 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 11:
//#line 100 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 12:
//#line 106 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 13:
//#line 112 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 14:
//#line 116 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 15:
//#line 122 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 16:
//#line 126 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 17:
//#line 130 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 19:
//#line 138 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 20:
//#line 145 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 21:
//#line 149 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 22:
//#line 156 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 23:
//#line 160 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 166 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 25:
//#line 172 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 26:
//#line 176 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 27:
//#line 183 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 28:
//#line 188 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 36:
//#line 203 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 37:
//#line 207 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 38:
//#line 211 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 40:
//#line 218 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 41:
//#line 224 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 42:
//#line 231 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 43:
//#line 237 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 44:
//#line 246 "Parser.y"
{
                        yyval.expr = val_peek(0).lvalue;
                    }
break;
case 47:
//#line 252 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.PREINC, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 48:
//#line 256 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.PREDEC, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 49:
//#line 260 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.POSTINC, val_peek(1).expr, val_peek(0).loc);
                    }
break;
case 50:
//#line 264 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.POSTDEC, val_peek(1).expr, val_peek(0).loc);
                    }
break;
case 51:
//#line 268 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 52:
//#line 272 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 53:
//#line 276 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 54:
//#line 280 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 55:
//#line 284 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 56:
//#line 288 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 57:
//#line 292 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 58:
//#line 296 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 59:
//#line 300 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 60:
//#line 304 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 61:
//#line 308 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 62:
//#line 312 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 63:
//#line 316 "Parser.y"
{
                        yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 64:
//#line 320 "Parser.y"
{
                        yyval = val_peek(1);
                    }
break;
case 65:
//#line 324 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 66:
//#line 328 "Parser.y"
{
                        yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 67:
//#line 332 "Parser.y"
{
                        yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                    }
break;
case 68:
//#line 336 "Parser.y"
{
                        yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                    }
break;
case 69:
//#line 340 "Parser.y"
{
                        yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                    }
break;
case 70:
//#line 344 "Parser.y"
{
                        yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                    }
break;
case 71:
//#line 348 "Parser.y"
{
                        yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                    }
break;
case 72:
//#line 352 "Parser.y"
{
                        yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                    }
break;
case 73:
//#line 356 "Parser.y"
{
                        yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                    }
break;
case 74:
//#line 362 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 75:
//#line 366 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 77:
//#line 373 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 78:
//#line 380 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 79:
//#line 384 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 80:
//#line 391 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 81:
//#line 397 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 82:
//#line 403 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 83:
//#line 409 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 84:
//#line 415 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 85:
//#line 419 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 86:
//#line 425 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 87:
//#line 429 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 88:
//#line 435 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
//#line 1227 "Parser.java"
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
