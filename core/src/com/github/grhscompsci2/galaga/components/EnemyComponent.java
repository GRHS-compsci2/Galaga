package com.github.grhscompsci2.galaga.components;

import com.badlogic.ashley.core.Component;

public class EnemyComponent implements Component{
    public static final int PATH_1 = 0;
    public static final int PATH_2 = 1;
    public static final int PATH_3 = 2;
    public static final int PATH_4 = 3;
	
    private int path;

    public void setPath(int path){
        this.path=path;
    }

    public int getPath() {
        return path;
    }
}
