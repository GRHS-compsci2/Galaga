package com.github.grhscompsci2.galaga.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;

public class StateComponent implements Component {
	public static final String TAG=StateComponent.class.getSimpleName();
	public static final int STATE_NORMAL = 0;
	public static final int STATE_JUMPING = 1;
	public static final int STATE_FALLING = 2;
	public static final int STATE_MOVING = 3;
	public static final int STATE_HIT = 4;
	public static final int STATE_ENTRY = 5;
	public static final int STATE_ENTRY_IDLE = 6;
	public static final int STATE_SWARMING = 7;
	public static final int STATE_DIVING = 8;
    public static final int STATE_EXPLOSION = 10;
	
	private int state = 0;
    public float time = 0.0f;
    public boolean isLooping = false;

    public void set(int newState){
		Gdx.app.debug(TAG, "Changing from "+state+" to "+newState);
        state = newState;
        time = 0.0f;
    }

    public int getState(){
        return state;
    }

}
