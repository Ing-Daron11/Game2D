package org.example;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Rectangle2D;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppHeight;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getAppWidth;

public class GameEntityFactory implements EntityFactory {

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .view("player1.png")
                .bbox(new HitBox(BoundingShape.box(32, 32)))
                .scale(0.2,0.2)
                .build();
    }

    @Spawns("weapon")
    public Entity newWeapon(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .view("arma1.png")
                .bbox(new HitBox(BoundingShape.box(32, 32)))
                .scale(0.1,0.1)
                .build();
    }

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .view("enemy1.png")
                .with(new RandomMoveComponent(new Rectangle2D(200,200, 400, 400),25 ))
                .scale(0.15,0.15)
                .build();
    }


}
