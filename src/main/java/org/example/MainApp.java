package org.example;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;


import static com.almasb.fxgl.dsl.FXGL.*;

public class MainApp extends GameApplication {

    private Entity player;
    private Entity weapon;
    public static boolean isRealoding;

    private PlayerComponent playerComponent;
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(750);
        gameSettings.setHeight(750);
        //gameSettings.setIntroEnabled(true);
        gameSettings.setFullScreenAllowed(true);
        gameSettings.setTitle("Nuke Dungeons");
        gameSettings.setVersion("0.3");
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new GameEntityFactory());
        player = spawn("player", 150, 150);
        weapon = spawn("weapon", 250, 250);
        playerComponent = new PlayerComponent();
        run(() ->{
           spawn("enemy", new SpawnData(random(100,400),random(100,500)));
        }, Duration.seconds(2), 10);
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("MoveUp") {
            @Override
            protected void onAction() {
                playerComponent.moveUp(player);
            }
        }, KeyCode.W);
        getInput().addAction(new UserAction("MoveDown") {
            @Override
            protected void onAction() {
                playerComponent.moveDown(player);
            }
        },KeyCode.S);
        getInput().addAction(new UserAction("MoveRight") {
            @Override
            protected void onAction() {
                playerComponent.moveRight(player);
            }
        },KeyCode.D);
        getInput().addAction(new UserAction("MoveLeft") {
            @Override
            protected void onAction() {
                playerComponent.moveLeft(player);
            }
        },KeyCode.A);

        getInput().addAction(new UserAction("Shoot") {
            @Override
            protected void onActionBegin() {
                WithWeapon withWeapon = player.getComponent(WithWeapon.class);
                if (withWeapon.getWeapon() != null) {
                    if (withWeapon.getAmmoCount() == 0 && !isRealoding) {
                        player.getComponent(PlayerComponent.class).reload();
                    }
                    if (withWeapon.getAmmoCount() > 0 && !isRealoding) {
                        withWeapon.decreaseAmmoCount();
                        spawn("bullet");
                    } else {
                        if (!isRealoding) {
                            player.getComponent(PlayerComponent.class).reload();
                            isRealoding = true;
                        }
                    }
                }
            }
        }, MouseButton.PRIMARY);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.WEAPON) {
            @Override
            protected void onCollisionBegin(Entity player, Entity weapon) {
                WithWeapon weaponComponent = player.getComponent(WithWeapon.class);
                weaponComponent.setWeapon(weapon);
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BULLET, EntityType.ENEMY) {
            @Override
            protected void onCollisionBegin(Entity bullet, Entity enemy){
                Point2D center = enemy.getCenter().subtract(40, 218); //(x, y)
                spawn("explosion", center);
                bullet.removeFromWorld();
                enemy.removeFromWorld();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}