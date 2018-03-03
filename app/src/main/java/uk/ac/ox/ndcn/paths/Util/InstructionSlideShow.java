package uk.ac.ox.ndcn.paths.Util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

import uk.ac.ox.ndcn.paths.GeneralEntities.DoneHandler;
import uk.ac.ox.ndcn.paths.GeneralEntities.Entity;
import uk.ac.ox.ndcn.paths.GeneralEntities.World;
import uk.ac.ox.ndcn.paths.R;

/**
 * Created by appdev on 01/06/2017.
 */
public class InstructionSlideShow extends Entity implements DoneHandler{
    int stage;
    ArrayList<Integer> images;
    DoneHandler handler;
    int w,h;
    World world;
    Resources res;
    public void done(String s){
        if (stage == -1){
            handler.done(s);
        }
        else {
            world.entities.clear();
            world.add(new ClickableImage(0,0,w,h, BitmapFactory.decodeResource(res, images.get(stage)), this));
            stage -= 1;
        }
    }

    public InstructionSlideShow(int w, int h, ArrayList<Integer> images, DoneHandler handler, World world, Resources res){
        this.images = images;
        this.handler = handler;
        this.world = world;
        this.w = w;
        this.h = h;
        this.res = res;
        stage = images.size()-2;
        world.add(new ClickableImage(0,0,w,h, BitmapFactory.decodeResource(res, images.get(images.size()-1)), this));


    }
}
