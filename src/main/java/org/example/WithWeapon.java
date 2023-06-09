package org.example;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class WithWeapon extends Component {
    private Entity weapon;
    private int ammoCount;
    public WithWeapon() {
        ammoCount = 0;
    }
    public void setWeapon(Entity weapon) {
        this.weapon = weapon;
    }
    public Entity getWeapon() {
        return weapon;
    }
    public int getAmmoCount() {
        return ammoCount;
    }
    public void decreaseAmmoCount() {
        ammoCount--;
    }

    public void setAmmoCount(int ammoCount) {
        this.ammoCount = ammoCount;
    }
    public void attack() {
        // Verificar si hay suficiente munición para atacar
        if (ammoCount>0) {
            // Restar una bala de la munición
            ammoCount--;

            // Obtener la posición del enemigo
            Entity enemy = getEntity();
            Point2D enemyPosition = enemy.getPosition();

            // Obtener la posición actual del jugador
            Point2D playerPosition = getGameWorld().getSingleton(EntityType.PLAYER).getPosition();

            // Calcular la dirección del enemigo hacia la posición del jugador
            Point2D direction = playerPosition.subtract(enemyPosition).normalize();

            // Crear y spawnea una nueva bala
            Entity bullet = FXGL.spawn("Bullet",
                    new SpawnData(enemyPosition)
                            .put("direction", direction));
        }
    }
}
