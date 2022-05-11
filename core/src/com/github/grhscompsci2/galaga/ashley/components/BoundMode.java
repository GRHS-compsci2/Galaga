package com.github.grhscompsci2.galaga.ashley.components;

public enum BoundMode {

  /***
   * When the same outer Edge must be on or adjacent to the Container bounds.
   *
   * ************
   * * *
   * ***** *
   * * * *
   * * o * *
   * * * *
   * ************
   */
  CONTAINED,
  /***
   * When the center of the entity must be within the Container Bounds.
   *
   * ************
   * * *
   * ***** *
   * * * * *
   * * o * *
   * * * * *
   * **************
   */
  CENTER,
  /***
   * When the opposite outer Edge must be on or adjacent to the Container bounds.
   *
   * ************
   * * *
   * ***** *
   * * * *
   * * o * *
   * * * *
   * ****************
   */
  EDGE
}