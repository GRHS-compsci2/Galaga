package com.github.grhscompsci2.galaga.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;

public class StateComponent implements Component {
	public static final String TAG = StateComponent.class.getSimpleName();
	public static final int STATE_NORMAL = 0;
	public static final int STATE_HIT = 1;
	public static final int STATE_DEAD = 2;
	public static final int STATE_ENTRY = 3;
	public static final int STATE_ENTRY_IDLE = 4;
	public static final int STATE_SWARMING = 5;
	public static final int STATE_DIVING = 6;
	public static final int STATE_EXPLOSION = 7;

	String[] states = { "STATE_NORMAL", "STATE_HIT", "STATE_DEAD", "STATE_ENTRY", "STATE_ENTRY_IDLE", "STATE_SWARMING",
			"STATE_DIVING", "STATE_EXPLOSION" };
	private int state = 0;
	public float time = 0.0f;

	public void set(int newState) {
		Gdx.app.debug(TAG, "Changing from " + states[state] + " to " + states[newState]);
		state = newState;
		time = 0.0f;
	}

	public int getState() {
		return state;
	}

}
