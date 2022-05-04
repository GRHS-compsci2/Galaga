package com.github.grhscompsci2.galaga;

public class Wave {
  private int x;
  private int y;
  private int path;

  public Wave(int x, int y, int path) {
    this.x = x;
    this.y = y;
    this.path = path;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getPath() {
    return path;
  }
}
