import java.awt.*;

public class LargerTile extends Building{

    public LargerTile(Vector position) {
        super(new Vector(-128, -64), position, new Vector(3, 3));
        super.setImage("src/Images/3by3EmptyTile.png");
        System.out.println(position);
    }

    @Override
    public void update() {}
}
