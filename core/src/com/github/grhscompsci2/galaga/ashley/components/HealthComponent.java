package com.github.grhscompsci2.galaga.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Pool.Poolable;

public class HealthComponent implements Component, Poolable {

  public float health = 1f;
  public float maxHealth = 1f;

  public static HealthComponent create(Engine engine){
      if(engine instanceof PooledEngine){
          return ((PooledEngine)engine).createComponent(HealthComponent.class);
      }else {
          return new HealthComponent();
      }
  }

  public HealthComponent setMaxHealth(float maxHealth){
      this.maxHealth = maxHealth;
      return this;
  }

  public HealthComponent setHealth(float health){
      this.health = health;
      return this;
  }

  @Override
  public void reset() {
      this.health = 1f;
      this.maxHealth = 1f;
  }
}