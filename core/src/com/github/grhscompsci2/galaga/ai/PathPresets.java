package com.github.grhscompsci2.galaga.ai;

import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class PathPresets {
    public static final Vector2[] ENTRY_WP_0 = { new Vector2(36, 4), new Vector2(28, 4), new Vector2(20, 10),
            new Vector2(16, 18), new Vector2(17, 20.646f), new Vector2(20, 22), new Vector2(22.4f, 21.2f),
            new Vector2(24, 18), new Vector2(22, 14.536f), new Vector2(20, 14), new Vector2(16, 14) };
    public static final Vector2[] ENTRY_WP_1 = { new Vector2(0, 0), new Vector2(0, 0) };

    public static final LinePath<Vector2> ENTRY_PATH_0=new LinePath<Vector2>(new Array<Vector2>(ENTRY_WP_0),true);
    public static final LinePath<Vector2> ENTRY_PATH_1=new LinePath<Vector2>(new Array<Vector2>(ENTRY_WP_1),true);
}
