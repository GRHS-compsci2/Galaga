package com.github.grhscompsci2.galaga.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Pool.Poolable;

public class StateComponent implements Component, Poolable {
  public static final String TAG = StateComponent.class.getSimpleName();
  public static final String STATE_NORMAL = "STATE_NORMAL";
  public static final String STATE_DYING = "STATE_HIT";
  public static final String STATE_DEAD = "STATE_DEAD";
  public static final String STATE_ENTRY = "STATE_ENTRY";
  public static final String STATE_ENTRY_GO_HOME = "STATE_ENTRY_GO_HOME";
  public static final String STATE_ENTRY_IDLE = "STATE_ENTRY_IDLE";
  public static final String STATE_SWARMING = "STATE_SWARMING";
  public static final String STATE_DIVING = "STATE_DIVING";
  public static final String STATE_EXPLOSION = "STATE_EXPLOSION";

  private String state;
  // private int state = 0;
  public float time = 0.0f;
  public boolean isLooping = false;

  public static StateComponent create(Engine engine) {
    if (engine instanceof PooledEngine) {
      return ((PooledEngine) engine).createComponent(StateComponent.class);
    } else {
      return new StateComponent();
    }
  }

  // Creating Chainable Component Setters to make building easier
  public StateComponent set(String newState) {
    state = newState;
    time = 0.0f;
    return this;
  }

  public StateComponent setLooping(boolean isLoopin) {
    this.isLooping = isLoopin;
    return this;
  }

  public String get() {
    return state;
  }

  @Override
  public void reset() {
    this.state = "DEFAULT";
    this.time = 0f;
    this.isLooping = false;
  }

}
