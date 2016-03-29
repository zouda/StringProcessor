package com.dag;

import java.util.ArrayList;

public class RouteGroup {
    private ArrayList<Route> RouteList;
    
    public RouteGroup(){
        RouteList = new ArrayList<Route>();
    }
    
    public ArrayList<Route> getRouteList() {
        return RouteList;
    }

    public void setRouteList(ArrayList<Route> routeList) {
        RouteList = routeList;
    }

    public int getNumber() {
        return RouteList.size(); 
    }

    public void add(Route route) {
       this.RouteList.add(route);
    }
    
    public Route get(int index){
        return this.RouteList.get(index);
    }

    public int size() {
        return this.RouteList.size();
    }
}
