package com.github.grhscompsci2.galaga.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.github.grhscompsci2.galaga.b2d.BodyFactory;
import com.github.grhscompsci2.galaga.components.InactiveComponent;

public abstract class EnemyEntity extends Entity {
  InactiveComponent inactiveComponent = new InactiveComponent();

  public abstract void init(Engine engine, BodyFactory factory, Vector2 home);
}
