package com.github.grhscompsci2.galaga.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.utils.Path;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath.LinePathParam;
import com.badlogic.gdx.math.Vector2;
import com.github.grhscompsci2.galaga.EnemyFormation;

public class EnemyComponent implements Component {

  private static final String TAG = EnemyComponent.class.getSimpleName();

  // The home position in the formation
  private Vector2 home;
  private Vector2 realHome;
  // array to hold all possible paths
  private LinePath<Vector2> path;

  /**
   * Initialize the arrays for the paths
   * 
   * @param x the x coordinate of the home position
   * @param y the y coordinate of the home position
   */
  public void initPaths(Vector2 home, LinePath<Vector2> entryPath) {
    // set the home position
    this.home = home;
    realHome = home.cpy();
    // create LinePaths (curves) for each path
    path = entryPath;
  }

  /**
   * gets the current path as a LinePath
   * 
   * @return current path
   */
  public LinePath<Vector2> getPath() {
    return path;
  }

  /**
   * get the last point of the current path
   * 
   * @return last point as a Vector2 object
   */
  public Vector2 getLastPoint() {
    return path.getEndPoint();
  }

  /**
   * Check to see if we are close enough to home
   * 
   * @param vector2
   */
  public boolean areWeThereYet(float tolerance, Vector2 position) {
    Vector2 extremity = path.getEndPoint();
    if (position.dst2(extremity) < tolerance * tolerance) {
      return true;
    }
    return false;
  }

  public Vector2 getHome() {
    return home;
  }

  public Vector2 updateHome() {
    home = realHome.cpy();
    home.add(EnemyFormation.getIdle());
    return home;
  }

  public void setPath(Path<Vector2, LinePathParam> path2) {
    this.path = (LinePath<Vector2>) path2;
  }

  public void setPath(LinePath<Vector2> path) {
    this.path = path;
  }
}
