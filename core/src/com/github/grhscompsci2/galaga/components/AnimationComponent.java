package com.github.grhscompsci2.galaga.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.IntMap;

public class AnimationComponent implements Component {
    public static final float FRAME_RATE = 0.5f;
    public IntMap<Animation> animations = new IntMap<Animation>();
}