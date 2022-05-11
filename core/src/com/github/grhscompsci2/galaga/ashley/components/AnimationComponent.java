package com.github.grhscompsci2.galaga.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Pool.Poolable;

public class AnimationComponent implements Component, Poolable {
    public ArrayMap<String, Animation<TextureRegion>> animations = new ArrayMap<>();
    public boolean isPaused=false;
    public boolean shouldClearOnBlankState = false;

    public static AnimationComponent create(Engine engine){
      if(engine instanceof PooledEngine){
          return ((PooledEngine)engine).createComponent(AnimationComponent.class);
      }else {
          return new AnimationComponent();
      }
  }

  public AnimationComponent addAnimation(String stateName, Animation<TextureRegion> animation){
      this.animations.put(stateName, animation);
      return this;
  }

  public AnimationComponent setPaused(boolean isPaused){
      this.isPaused = isPaused;
      return this;
  }

  public AnimationComponent setShouldClearOnBlankState(boolean shouldClear){
      this.shouldClearOnBlankState = shouldClear;
      return this;
  }

  @Override
  public void reset() {
      this.animations.clear();
      this.isPaused = false;
      this.shouldClearOnBlankState = false;
  }
}