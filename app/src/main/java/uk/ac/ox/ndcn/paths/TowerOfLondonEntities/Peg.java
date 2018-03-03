package uk.ac.ox.ndcn.paths.TowerOfLondonEntities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import uk.ac.ox.ndcn.paths.GeneralEntities.CollisionType;
import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;
import uk.ac.ox.ndcn.paths.MazeEntities.Obstacle;

/**
 * Created by appdev on 09/01/2017.
 */
public class Peg extends Obstacle {
    public static int count = 1;
    protected int _count;
    private World world;
    public Peg(float _x, float _y, float _width, float _height, World _world){
        super(_x,_y, _width, _height);
        collisionType = CollisionType.PEG;
        world = _world;
        this._count = count;
        count ++;
    }
    public ArrayList<TowerBlock> getBlocks(){
        ArrayList<TowerBlock> blocks = new ArrayList<TowerBlock>();
        for (Entity entity : world.entities) {
            if (entity.collideLine(x,y,x+width-1,y+height-1)) {
                if (entity.collisionType == collisionType.OBSTACLE){
                    if (entity instanceof TowerBlock){
                        blocks.add((TowerBlock)entity);
                    }
                }
            }

        };
        Collections.sort(blocks, new Comparator<TowerBlock>() {
            @Override
            public int compare(TowerBlock lhs, TowerBlock rhs) {
                return (int)(lhs.y - rhs.y);
            }
        });
        return blocks;
    }
    @Override

    public int hashCode(){
        return getBlocks().hashCode();
    }
    public boolean equals(Object that){
        if(!(that instanceof Peg)) return false;
        return getBlocks().equals(((Peg)that).getBlocks());
    }

    @Override public String toString(){
        return "" + _count;
    }

}
