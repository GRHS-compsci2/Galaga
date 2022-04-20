package com.github.grhscompsci2.galaga.components;
import com.badlogic.ashley.core.Component;

public class PlayerComponent implements Component {
    public float shootDelay = 0.5f;
	public float timeSinceLastShot = 0f;
    public int numMissiles = 0;
    public int score=0;
    public float speed = 15.0f;
}
