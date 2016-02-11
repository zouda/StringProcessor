package com;

/*
 * Token
 */
public class Token {
    
    public enum Tok{
        StartTok, EndTok, AlphaTok, NumTok, LowerTok, UpperTok, 
        HyphenTok, DotTok, SemicolonTok, ColonTok, CommaTok, SpaceTok, 
        SlashTok, LparenTok, RparenTok, VoidTok
    }
    
    public static Tok getTokenType(String s){
        if (s.length() == 1){
            if (s.charAt(0) == '-')
                return Tok.HyphenTok;
            if (s.charAt(0) == '.')
                return Tok.DotTok;
            if (s.charAt(0) == ';')
                return Tok.SemicolonTok;
            if (s.charAt(0) == ',')
                return Tok.ColonTok;
            if (s.charAt(0) == ':')
                return Tok.CommaTok;
            if (s.charAt(0) == ' ')
                return Tok.SpaceTok;
            if (s.charAt(0) == '/')
                return Tok.SlashTok;
            if (s.charAt(0) == '(')
                return Tok.LparenTok;
            if (s.charAt(0) == ')')
                return Tok.RparenTok;
        }
        if (Tool.isNumber(s))
            return Tok.NumTok;
        if (Tool.isLowercaseAlphabet(s))
            return Tok.LowerTok;
        if (Tool.isUppercaseAlphabet(s))
            return Tok.UpperTok;
        if (Tool.isAlphabet(s))
            return Tok.AlphaTok;
        return null;
    }
    
}
