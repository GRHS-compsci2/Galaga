package com.github.grhscompsci2.galaga.ashley.systems;

import com.badlogic.ashley.core.Entity;
import com.github.grhscompsci2.galaga.ashley.K2ComponentMappers;

import java.util.Comparator;

/**
 * A comparator to Order by Z index
 */
public class ZComparator implements Comparator<Entity> {

  public ZComparator() {

  }

  @Override
  public int compare(Entity entityA, Entity entityB) {
    return (int) Math.signum(K2ComponentMappers.transform.get(entityB).position.z -
        K2ComponentMappers.transform.get(entityA).position.z);
  }
}
