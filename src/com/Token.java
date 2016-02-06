package com;

public class Token {
    
    public enum Tok{
        StartTok, EndTok, AlphaTok, NumTok, LowerTok, UpperTok, 
        HyphenTok, DotTok, SemicolonTok, ColonTok, CommaTok, SpaceTok, 
        SlashTok, LparenTok, RparenTok
    }
    
    public static Tok getTokenType(String s){
        return Tok.StartTok;
    }
    
}
