package org.example;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.input.KeyCode;


import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class MainApp extends GameApplication {

    private Entity player;
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(750);
        gameSettings.setHeight(750);
        gameSettings.setTitle("Dungeons");
        gameSettings.setVersion("0.1");
    }


    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new GameEntityFactory());
        player = spawn("player", 150, 150);
        spawn("weapon", 300, 250);
        //Todo esto reemplaza al método run, sirve para generar 4 enemigos con un intervalo de 2 segundos
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> spawnEnemy())
        );
        timeline.setCycleCount(4);
        timeline.play();





    }

    private void spawnEnemy() {
        spawn("enemy",250,500);
    }

    public void onCollision(Entity player, Entity weapon) {
        // Verifica los tipos de entidades involucradas en la colisión
        if (weapon.getType() == EntityType.WEAPON && player.getType() == EntityType.PLAYER) {
            // Convierte las entidades en instancias de las clases correspondientes
            Player playerEntity = (Player) player;
            Weapon weaponEntity = (Weapon) weapon;

            // Realiza las acciones necesarias para que el personaje recoja el arma
            playerEntity.pickUpWeapon(weaponEntity);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}