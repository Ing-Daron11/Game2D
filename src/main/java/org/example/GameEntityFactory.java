package org.example;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class GameEntityFactory implements EntityFactory {

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .type(EntityType.PLAYER)
                .viewWithBBox("player1.png")
                .with(new CollidableComponent(true))
                .with(new PlayerComponent())
                .with(new WithWeapon())
                .scale(0.2,0.2)
                .build();
    }

    @Spawns("weapon")
    public Entity newWeapon(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .type(EntityType.WEAPON)
                .viewWithBBox("arma2.png")
                .with(new CollidableComponent(true))
                .with(new WeaponComponent(1, 15))
                .scale(0.1,0.1)
                .build();
    }

    @Spawns("weapon2")
    public Entity newWeapon2(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .type(EntityType.WEAPON)
                .viewWithBBox("arma1.png")
                .with(new CollidableComponent(true))
                .with(new WeaponComponent(1, 15))
                .scale(0.1, 0.1)
                .build();
    }

    @Spawns("life")
    public Entity newLife(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .type(EntityType.LIFE)
                .viewWithBBox("vida.png")
                .with(new CollidableComponent(true))
                .scale(0.1, 0.1)
                .build();
    }

    @Spawns("enemy")
    public Entity newEnemy(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .type(EntityType.ENEMY)
                .viewWithBBox("enemy1.png")
                .with(new RandomMoveComponent(new Rectangle2D(0,0, getAppWidth(), getAppHeight()),25 ))
                .with(new CollidableComponent(true))
                .scale(0.15,0.15)
                .build();
    }

    @Spawns("enemy2")
    public Entity newEnemy2(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .type(EntityType.ENEMY)
                .viewWithBBox("enemy2.png")
                .with(new RandomMoveComponent(new Rectangle2D(0,0, getAppWidth(), getAppHeight()),25 ))
                .with(new CollidableComponent(true))
                .scale(0.15,0.15)
                .build();
    }

    @Spawns("enemy3")
    public Entity newEnemy3(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .type(EntityType.ENEMY)
                .viewWithBBox("enemy2.png")
                .with(new RandomMoveComponent(new Rectangle2D(0,0, getAppWidth(), getAppHeight()),75 ))
                .with(new CollidableComponent(true))
                .scale(0.15,0.15)
                .build();
    }

    @Spawns("bullet")
    public Entity newBullet(SpawnData data){
        Entity player =getGameWorld().getSingleton(EntityType.PLAYER);
        Entity weapon = player.getComponent(WithWeapon.class).getWeapon();
        Point2D direction = getInput().getMousePositionWorld().subtract(player.getPosition());
        return FXGL.entityBuilder()
                .from(data)
                .type(EntityType.BULLET)
                .viewWithBBox("bullet.png")
                .with(new CollidableComponent(true))
                .with(new ProjectileComponent(direction, 200))
                .with(new OffscreenCleanComponent())
                .scale(0.1, 0.1)
                .at(weapon.getPosition().subtract(-10, -50))
                .build();
    }

    @Spawns("explosion")
    public Entity newExplosion(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .view(texture("explosion.png").toAnimatedTexture(7, Duration.seconds(0.66)).play())
                .with(new ExpireCleanComponent(Duration.seconds(0.66)))
                .build();
    }

    @Spawns("restLife")
    public Entity newRestLife(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .view(texture("lifeSecuence.png").toAnimatedTexture(3, Duration.seconds(0.80)).play())
                .with(new ExpireCleanComponent(Duration.seconds(0.90)))
                .scale(0.3,0.3)
                .build();
    }

    @Spawns("scoreText")
    public Entity newScoreText(SpawnData data) {
        String text = data.get("text");
        var e =  FXGL.entityBuilder()
                .from(data)
                .view(getUIFactory().newText(text, 20))
                .with(new ExpireCleanComponent(Duration.seconds(0.66)))
                .build();

            animationBuilder()
                    .duration(Duration.seconds(0.1))
                    .interpolator(Interpolators.EXPONENTIAL.EASE_OUT())
                    .translate(e)
                    .from(new Point2D(data.getX(), data.getY()))
                    .to(new Point2D(data.getX(), data.getY()+35))
                    .build();
        return e;
    }

    @Spawns("gameOver")
    public Entity newGameOver(SpawnData data) {
        return FXGL.entityBuilder()
                .from(data)
                .view("mapa1.png")
                .scale(100,100)
                .build();
    }

}
