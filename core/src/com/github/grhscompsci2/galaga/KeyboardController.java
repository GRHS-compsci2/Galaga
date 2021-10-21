package com.github.grhscompsci2.galaga;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

public class KeyboardController implements InputProcessor {
    public boolean left,right,up,down;
	@Override
	public boolean keyDown(int keycode) {
	boolean keyProcessed = false;
	switch (keycode) 
        {
	        case Keys.LEFT:  	
	            left = true;	
	            keyProcessed = true; 
	            break;
	        case Keys.RIGHT: 	
	            right = true;	
	            keyProcessed = true; 
	            break;
	        case Keys.UP: 		
	            up = true;		
	            keyProcessed = true; 
	            break;
	        case Keys.DOWN: 	
	            down = true;	
	            keyProcessed = true;
        }
	return keyProcessed;	
}
@Override
public boolean keyUp(int keycode) {
boolean keyProcessed = false;
switch (keycode) 
    {
        case Keys.LEFT:  	
            left = false;	
            keyProcessed = true;	 
            break;
        case Keys.RIGHT: 	
            right = false;	
            keyProcessed = true;	 
            break;
        case Keys.UP: 		
            up = false;		
            keyProcessed = true;	 
            break;
        case Keys.DOWN: 	
            down = false;	
            keyProcessed = true;	
    }
return keyProcessed;	
}
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        
        return false;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        
        return false;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        
        return false;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        
        return false;
    }
    @Override
    public boolean scrolled(float amountX, float amountY) {
        
        return false;
    }
	
}