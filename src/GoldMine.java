import javax.swing.*;
import java.awt.*;
import java.awt.*;
public class GoldMine extends Building {
    int gold = 0;
    Vector collectIcon = new Vector(80, 80);

    public GoldMine(Vector position) {
        super(new Vector(-128, -64), position, new Vector(3, 3));
        super.setImage("src/Images/Mine.png");
        }

    @Override
    public void draw_Exta(Graphics g, Vector v) {
        if(!super.is_selected && gold > 200){
            g.setColor(Color.WHITE);
            g.fillRect(v.x - this.offset.x / 2 - (int)collectIcon.x/2, v.y - this.offset.y - 200, collectIcon.x, collectIcon.y);
        }
        if(super.is_selected){
            gold = 0;
        }
    }

    @Override
    public void update() {
        gold++;
    }
}

