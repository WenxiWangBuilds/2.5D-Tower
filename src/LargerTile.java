public class LargerTile extends Building{

    public LargerTile(Vector position) {
        super("src/Images/3by3EmptyTile.png", new Vector(-128, -64), position, new Vector(3, 3));
        System.out.println(position);
    }
    
}
