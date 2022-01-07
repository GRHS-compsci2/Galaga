package com.github.grhscompsci2.galaga.components;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.ashley.core.Component;
public class TranslationComponent implements Component {
    public Vector3 position=new Vector3();
    public Vector2 scale = new Vector2(1.0f,1.0f);
    public float rotation= 0.0f;
    public boolean isHidden=false;
    public void setPosition(float x, float y) {
        position.set(x, y, 0.0f);
    }
    public Vector3 getPosition() {
        return position;
    }
    /*public void setPosition(Vector3 position2) {
       float i = 0.0f;
       float j = 0.0f;
       //Vector3 fill = new Vector3(i,j,0.0f);
       //fill = position2;
        //position.set(i,j,0.0f);
    }*/
}
