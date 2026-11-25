import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Building {
    BufferedImage image;
    Vector offset;
    Vector position;
    Vector dimensions;
    boolean is_selected = false; //change later
     
    public Building(Vector offset, Vector position, Vector dimensions){
        this.offset = offset;
        this.position = position;
        this.dimensions = dimensions;
    }

    public void setImage(String filePath){
        try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, Vector v){
        g.drawImage(this.image, v.x + this.offset.x, v.y + 32 - Tile.HEIGHT + this.offset.y, null); //the 30 is a counter offset
    }

    public abstract void draw_Exta(Graphics g, Vector v);

    public void setIs_selected(){
        is_selected = !is_selected;
    }

    public abstract void update();
}
