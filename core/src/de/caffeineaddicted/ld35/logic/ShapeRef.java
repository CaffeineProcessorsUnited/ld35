package de.caffeineaddicted.ld35.logic;

public class ShapeRef {
    final public int shape1 = 0;
    final public int shape2 = 1;
    final public int shape3 = 2;
    final public int shape4 = 3;

    final public int numShapes = 4;

    private int[] slots;

    public ShapeRef(){
        slots = new int[4];
        for(int i = 0; i < 4; ++i)
            slots[i] = 0;
    }

    public void PlaceShape(int shape, int slot) {
        assert 0 <= shape && shape < 4;
        assert 0 <= slot && slot < 4;
        slots[slot] = shape;
    }

    public int GetShapeID(){
        int id = 0;
        for(int i = 0; i < 4; ++i) {
            id += (Math.pow(4, i) * slots[i]);
        }
        return id;
    }
    public int[] GetShapes(){
        return slots;
    }
    public int GetShape(int slot){
        assert 0 <= slot && slot < 4;
        return slots[slot];
    }
    public void SetShape(int shapeID){
        for(int i = 0; i < 4; ++i)
            slots[i] = 0;
        for(int i = 3; i >= 0; --i){
            int mod = (int) Math.pow(4,i);
            slots[i] = shapeID / mod;
            shapeID %= mod;
        }
    }
    public boolean equals(ShapeRef other){
        return GetShapeID() == other.GetShapeID();
    }

}
