package org.example;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class MainApp extends GameApplication {

    private Entity player;
    private Entity weapon;
    public static boolean isRealoding;
    private int currentLevel = 1;
    private Text text;
    public static boolean isDeployedLevel2;
    public static boolean isDeployedLevel3;
    private int score = 0;

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
        getGameScene().setBackgroundRepeat("fondoNivel1.jpeg");
        FXGL.getGameWorld().addEntityFactory(new GameEntityFactory());
        player = spawn("player", 150, 150);
        weapon = spawn("weapon", 250, 250);
        playerComponent = new PlayerComponent();
        run(() ->{
           spawn("enemy", new SpawnData(random(100,400),random(100,500)));
        }, Duration.seconds(2), 10);

    }
    @Override
    public void onUpdate(double tpf){
        Text text = new Text("Nivel: " + currentLevel);
        text.setTranslateX(50);
        text.setTranslateY(30);
        text.setFont(Font.font("Bodoni", 30));
        getGameScene().addUINode(text);
        if(score >= 1000 && !isDeployedLevel2){
            level2();
        }
        if(score >= 2500 && !isDeployedLevel3){
            level3();
        }
    }

    private void level2(){
        weapon = spawn("weapon2", 250, 250);
        run(() ->{
            spawn("enemy2", new SpawnData(random(100,400),random(100,500)));
        }, Duration.seconds(2), 10);

        run(() ->{
            spawn("enemy", new SpawnData(random(100,400),random(100,500)));
        }, Duration.seconds(2), 5);
        isDeployedLevel2 = true;
        currentLevel++;
    }

    private void level3(){
        spawn("life", random(20, getAppWidth()-20),random(20,getAppHeight()-20));
        run(() ->{
            spawn("enemy2", new SpawnData(random(100,400),random(100,500)));
        }, Duration.seconds(2), 15);

        run(() ->{
            spawn("enemy", new SpawnData(random(100,400),random(100,500)));
        }, Duration.seconds(2), 10);
        isDeployedLevel3 = true;
        currentLevel++;
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
                int weaponName = weapon.getComponent(WeaponComponent.class).getType();
                if (weaponName == 1) {
                    weaponComponent.setAmmoCount(30);
                } else if (weaponName == 2) {
                    weaponComponent.setAmmoCount(10);
                }
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.BULLET, EntityType.ENEMY) {
            @Override
            protected void onCollisionBegin(Entity bullet, Entity enemy){
                Point2D center = enemy.getCenter().subtract(40, 218); //(x, y)
                spawn("explosion", center);
                bullet.removeFromWorld();
                enemy.removeFromWorld();
                inc("score", +100);
                score += 100;
            }
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("score", 0);
    }

    @Override
    protected void initUI(){
        addVarText(50, 70, "score");
    }

    public static void main(String[] args) {
        launch(args);
    }
}