package com.controller.proyecto;

import java.util.HashMap;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class SelectOneMenuView {
     
    private String console; 
    private String rtl;
     
   
 
    public String getConsole() {
        return console;
    }
 
    public void setConsole(String console) {
        this.console = console;
    }
 
    public String getRtl() {
        return rtl;
    }
 
    public void setRtl(String rtl) {
        this.rtl = rtl;
    }
}
