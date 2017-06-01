package uk.ac.ox.ndcn.paths.DraggingEntities;

import android.graphics.Color;

import uk.ac.ox.ndcn.paths.Games.TolView;

import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import uk.ac.ox.ndcn.paths.GeneralEntities.TextBox;
import uk.ac.ox.ndcn.paths.MazeEntities.Obstacle;

/**
 * Created by appdev on 14/03/2017.
 */
public class TolLevel extends Entity{
    public float [][] pegData = {};
    public float [][] blockData = {};
    public float [][] targetblockData = {};
    float block_height = 75;
    float base_offset = 20;
    float peg_width = 20;
    float peg_offset = peg_width/2f;
    float block_width = 200;
    float block_offset = block_width/2f;
    public TolLevel(float[][] _pegData, float [][] _blockData, float [][] _targetblockData){
        pegData = _pegData;
        blockData = _blockData;
        targetblockData = _targetblockData;
    }

    public void build(TolView world){
        int w = world.w;
        int h = world.h;
        float block_height = 75;
        float base_offset = 20;
        float peg_width = 20;
        float peg_offset = peg_width/2f;
        float block_width = 200;
        float block_offset = block_width/2f;


          /*
        t1 = (TargetPeg)add(new TargetPeg(w/4-10,h/2-170,20,150, this));
        t2 = (TargetPeg)add(new TargetPeg(w/2-10, h/2-95, 20, 75, this));
        t3 = (TargetPeg)add(new TargetPeg(3*w/4-10,h/2-245,20,225, this));
        add(new TargetObstacle(0, h / 2 - 20, w, 20));
        */
        for (float [] peg : pegData){
            float x = peg[0] * w - peg_offset;
            float height = peg[1] * block_height;
            world.targetPegs.add((TargetPeg)
                            world.add(
                                    new TargetPeg(x, h / 2 - height - base_offset, peg_width, height, world)
                            )
            );
        }
        world.add(new Obstacle(0, h/2-20, w, 20));
        world.add(new TextBox(w/2,20,"Copy This Below"));
                /*
        p1 = (Peg)add(new Peg(w/4-10,h-170,20,150, this));
        p2 = (Peg) add(new Peg(w/2-10, h-95, 20, 75, this));
        p3 = (Peg)add(new Peg(3*w/4-10,h-245,20,225, this));

        */
        for (float [] peg : pegData){
            float x = peg[0] * w - peg_offset;
            float height = peg[1] * block_height;
            world.pegs.add((Peg)
                            world.add(
                                    new Peg(x, h-height-base_offset, peg_width, height, world)
                            )
            );
        }
        world.add(new Obstacle(0, h-20, w, 20));

        /*
        TowerBlock r = new TowerBlock(this, 3*w/4-100,h-170+1,200,75, Color.RED);
        add(r);
        TowerBlock b = new TowerBlock(this, w/2-100,h-95+1,200,75, Color.BLUE);
        add(b);
        TowerBlock g = new TowerBlock(this, w/4-100, h - 245 + 1, 200, 75, Color.GREEN);
        add(g);

        */
        for (float [] block : blockData){
            float x = block[0] * w - block_offset;
            float y = block[1] * block_height * 1.1f;
            world.add(
                    new TowerBlock(world, x, h-y-base_offset, block_width, block_height, (int) block[2])
            );
        }

        /*
        TargetBlock rt = new TargetBlock(this, 3*w/4-100,h/2-170+1,200,75, Color.GREEN);
        add(rt);
        TargetBlock bt = new TargetBlock(this, w/2-100,h/2-95+1,200,75, Color.BLUE);
        add(bt);
        TargetBlock gt = new TargetBlock(this, w/4-100, h/2 - 245 + 1, 200, 75, Color.RED);
        add(gt);
        */

        for (float [] block : targetblockData){
            float x = block[0] * w - block_offset;
            float y = block[1] * block_height* 1.1f;
            world.add(
                    new TargetBlock(world, x, h/2-y-base_offset, block_width, block_height, (int)block[2])
            );
        }



    }
}
