package de.caffeineaddicted.ld35.logic;

public class ShapeRef {
    static final public int numShapes = 5;
    static final public int numSlots = 4;

    private boolean dirty;

    private int[] slots;

    public ShapeRef(){
        slots = new int[numSlots];
        dirty=true;
        Reset();
    }

    public void Reset() {
        for(int i = 0; i < numSlots; ++i)
            slots[i] = 0;
    }

    public void PlaceShape(int shape, int slot) {
        assert 0 <= shape && shape < numShapes;
        assert 0 <= slot && slot < numSlots;
        slots[slot] = shape;
        dirty=true;
    }

    public int GetShapeID(){
        int id = 0;
        for(int i = 0; i < numSlots; ++i) {
            id += (Math.pow(numShapes, i) * slots[i]);
        }
        return id;
    }
    public int[] GetShapes(){
        return slots;
    }
    public int GetShape(int slot){
        assert 0 <= slot && slot < numSlots;
        return slots[slot];
    }
    public void SetShape(int shapeID){
        Reset();
        for(int i = numSlots-1; i >= 0; --i){
            int mod = (int) Math.pow(numShapes,i);
            slots[i] = shapeID / mod;
            shapeID %= mod;
        }
    }
    public boolean isDirty(){
        return dirty;
    }

    public void setDirty(boolean dirty){
        this.dirty=dirty;
    }

    public boolean equals(ShapeRef other){
        return GetShapeID() == other.GetShapeID();
    }

}
