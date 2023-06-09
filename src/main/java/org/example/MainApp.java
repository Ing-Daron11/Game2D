package org.example;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;


import static com.almasb.fxgl.dsl.FXGL.getPhysicsWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class MainApp extends GameApplication {

    private Entity player;
    private Entity weapon;

    private PlayerComponent playerComponent;
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(750);
        gameSettings.setHeight(750);
        //gameSettings.setIntroEnabled(true);
        gameSettings.setFullScreenAllowed(true);
        gameSettings.setTitle("Dungeons");
        gameSettings.setVersion("0.2");
    }

    private void spawnEnemy() {
        spawn("enemy",250,500);
    }
    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new GameEntityFactory());
        player = spawn("player", 150, 150);
        weapon = spawn("weapon", 250, 250);
        playerComponent = new PlayerComponent();
        //Todo esto reemplaza al método run, sirve para generar 4 enemigos con un intervalo de 2 segundos
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> spawnEnemy())
        );
        timeline.setCycleCount(4);
        timeline.play();
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
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.WEAPON) {
            @Override
            protected void onCollisionBegin(Entity player, Entity weapon) {
                WithWeapon weaponComponent = player.getComponent(WithWeapon.class);
                weaponComponent.setWeapon(weapon);
                int weaponType = weapon.getComponent(WeaponComponent.class).getType();
                if (weaponType == 1) {
                    weaponComponent.setAmmoCount(10);
                } else if (weaponType == 2) {
                    weaponComponent.setAmmoCount(30);
                }

            }
        });
    }



    public static void main(String[] args) {
        launch(args);
    }
}