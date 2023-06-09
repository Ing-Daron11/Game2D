package org.example;

import com.almasb.fxgl.entity.component.Component;

public class WeaponComponent extends Component {

    int gunType = -1;
    int ammunition = 0;

    public WeaponComponent(int gunType, int ammunition){
        this.gunType = gunType;
        this.ammunition = ammunition;
    }

    public int getType(){
        return this.gunType;
    }
}
