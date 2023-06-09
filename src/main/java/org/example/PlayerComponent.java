package org.example;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class PlayerComponent extends Component {
    double speed = 5;

    public PlayerComponent(){}
    public void moveUp(Entity player){
        player.translateY(-speed * 0.5);
    }
    public void moveDown(Entity player){
        player.translateY(speed * 0.5);
    }
    public void moveLeft(Entity player){
        player.translateX(-speed * 0.5);
    }
    public void moveRight(Entity player){
        player.translateX(speed * 0.5);
    }

    @Override
    public void onUpdate(double tpf){
        Entity weapon = getEntity().getComponent(WithWeapon.class).getWeapon();
        if (weapon != null) {
            weapon.setPosition(getEntity().getPosition().subtract(150,60));
            Point2D direction = getInput().getMousePositionWorld().subtract(entity.getPosition());
            weapon.rotateToVector(direction);
            if(direction.getX()<0){
                weapon.rotateBy(180);
                weapon.setScaleX(-0.09);
            }else{
                weapon.setScaleX(0.09);
            }
            if (!getGameWorld().getEntities().contains(weapon)) {
                getGameWorld().addEntity(weapon);
            }
        }

    }
    public void reload() {
        getGameTimer().runOnceAfter(() -> {
            WithWeapon withWeapon= getEntity().getComponent(WithWeapon.class);
            if(withWeapon.getWeapon().getComponent(WeaponComponent.class).getType()== 1){
                withWeapon.setAmmoCount(10);
            }
            else{
                withWeapon.setAmmoCount(30);
            }
            MainApp.isRealoding = false;
        }, Duration.millis(1500));
    }

}
