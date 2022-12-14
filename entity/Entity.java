package Tetris.entity;

import Tetris.interfaces.GameObject;

public abstract class Entity implements GameObject {
    
    // The position of the entity
    public int x;
    public int y;

    // The width and height of the object
    public int width;
    public int height;

    public Entity(int posX, int posY, int entityWidth, int entityHeight) {
        this.x = posX;
        this.y = posY;
        this.width = entityWidth;
        this.height = entityHeight;
    }

}
