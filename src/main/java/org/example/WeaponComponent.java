package org.example;

import com.almasb.fxgl.entity.component.Component;

public class WeaponComponent extends Component {

    int gunType = -1;
    int ammunition = 0;

    public WeaponComponent(int gunType, int ammunition){
        this.gunType = gunType;
        this.ammunition = ammunition;
    }

    public void decreaseAmmoCount() {
        ammunition--;
    }

    public int getType(){
        return this.gunType;
    }
    public int getAmmunition() {
        return ammunition;
    }
    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }


}
