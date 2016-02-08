package com.regex;

import com.Token.*;

/*
 * Regular Expression
 */

public class Regex {
    public Tok tok;
    
    public Regex(){
        
    }
    
    public Regex(Tok tok){
        this.tok = tok;
    }
    
    public void setTok(Tok tok){
        this.tok = tok;
    }
}
