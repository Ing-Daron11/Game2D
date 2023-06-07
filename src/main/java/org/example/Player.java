package org.example;

import com.almasb.fxgl.entity.Entity;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

public class Player extends Entity {
    private double offsetX; // Agrega offsetX como una variable miembro
    private double offsetY; // Agrega offsetY como una variable miembro
    private Weapon currentWeapon;

    public void pickUpWeapon(Weapon weapon) {
        // Verifica si el personaje ya tiene un arma equipada
        if (currentWeapon != null) {
            // Si el personaje ya tiene un arma, puedes dejarla caer al suelo o hacer cualquier otra acción
            // antes de recoger la nueva arma. Por ejemplo:
            dropWeapon();
        }

        // Asigna el arma recibida como parámetro como el arma actual del personaje
        currentWeapon = weapon;
        // Configura la posición del arma en relación con el personaje para que se vea como si la está sosteniendo
        currentWeapon.setPosition(getX() + offsetX, getY() + offsetY);
        // Añade el arma al mundo del juego
        getGameWorld().addEntity(currentWeapon);
    }


    public void dropWeapon() {
        // Verifica si el personaje tiene un arma equipada
        if (currentWeapon != null) {
            // Elimina el arma del mundo del juego
            getGameWorld().removeEntity(currentWeapon);
            // Configura la posición del arma en el suelo, cerca del personaje
            currentWeapon.setPosition(getX() + offsetX, getY() + offsetY);
            // Establece el arma actual como nulo, ya que el personaje ya no lo tiene equipado
            currentWeapon = null;
        }
    }



}
