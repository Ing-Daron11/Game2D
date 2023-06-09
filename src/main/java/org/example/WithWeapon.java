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
}
