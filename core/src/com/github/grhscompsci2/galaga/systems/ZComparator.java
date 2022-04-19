package com.github.grhscompsci2.galaga.systems;

import com.badlogic.ashley.core.Entity;
import com.github.grhscompsci2.galaga.components.Mapper;
import java.util.Comparator;

public class ZComparator implements Comparator<Entity> {

    public ZComparator() {
    }

    @Override
    public int compare(Entity entityA, Entity entityB) {
        float az = Mapper.transCom.get(entityA).position.z;
        float bz = Mapper.transCom.get(entityB).position.z;
        int res = 0;
        if (az > bz) {
            res = 1;
        } else if (az < bz) {
            res = -1;
        }
        return res;
    }
}
