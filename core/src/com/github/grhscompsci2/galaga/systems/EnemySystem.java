package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleByAction;
import com.github.grhscompsci2.galaga.components.B2dBodyComponent;
import com.github.grhscompsci2.galaga.components.EnemyComponent;
import com.github.grhscompsci2.galaga.components.TypeComponent;

public class EnemySystem extends IteratingSystem{
    private ComponentMapper<EnemyComponent> eCMapper;
	private ComponentMapper<B2dBodyComponent> bodm;
   //private ComponentMapper<TypeComponent> tCMapper;

    @SuppressWarnings("unchecked")
    public EnemySystem(){
        super(Family.all(EnemyComponent.class).get());
        eCMapper=ComponentMapper.getFor(EnemyComponent.class);
        bodm=ComponentMapper.getFor(B2dBodyComponent.class);
        //tCMapper=ComponentMapper.getFor(TypeComponent.class);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        EnemyComponent enemyComponent=eCMapper.get(entity);
        B2dBodyComponent bodyComponent=bodm.get(entity);
        
        switch(enemyComponent.getPath()){
            case 1:
                moveRightCircle(bodyComponent);
                break;
            default:
                moveLeftCircle(bodyComponent);
        }
    }
    private void moveRightCircle(B2dBodyComponent bodyComponent) {
        Vector2 center=new Vector2(20,18);
        Vector2 rotated=bodyComponent.body.getPosition().rotateAroundDeg(center,1.0f);
        bodyComponent.body.setTransform(rotated, rotated.angleDeg());
    }
    private void moveLeftCircle(B2dBodyComponent bodyComponent) {
        Vector2 center=new Vector2(8,18);
        Vector2 rotated=bodyComponent.body.getPosition().rotateAroundDeg(center, 1.0f);
        bodyComponent.body.setTransform(rotated, rotated.angleDeg());
    }
    
}
