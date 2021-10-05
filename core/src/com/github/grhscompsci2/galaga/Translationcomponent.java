package com.github.grhscompsci2.galaga;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.ashley.core.Component;
public class Translationcomponent implements Component {
    public final Vector3 position = new Vector3();
    public final Vector2 scale = new Vector2(1.0f,1.0f);
    public float rotation= 0.0f;
    public boolean isHidden=false;
}
