package com.github.grhscompsci2.galaga.ashley.components;

import com.badlogic.ashley.core.Component;

/*
 * Stores the type of entity this is
 */
public class TypeComponent implements Component {
  public static final int PLAYER = 0;
  public static final int ENEMY = 1;
  public static final int PLAYER_BULLET = 2;
  public static final int SCENERY = 3;
  public static final int OTHER = 4;
  public static final int ENEMY_BULLET = 5;

  public int type = OTHER;

}