package uk.ac.ox.ndcn.paths.ButtonsAndKeyPads;

import uk.ac.ox.ndcn.paths.ButtonsAndKeyPads.Button;
import uk.ac.ox.ndcn.paths.GeneralEntities.DoneHandler;

/**
 * Created by appdev on 29/05/2016.
 */
public class DoneButton extends Button {
    DoneHandler handler;
    private boolean pressed = false;
    private String text = "Done!";
    public DoneButton(int _x, int _y, int _width, int _height, DoneHandler h) {
        super(_x, _y, _width, _height, "Done!");
        handler = h;
    }

    public DoneButton(int _x, int _y, int _width, int _height, String text, DoneHandler h) {
        super(_x, _y, _width, _height, text);
        handler = h;
    }


    @Override
    protected void clicked() {
        if(!pressed) {
            handler.done(text);

            pressed = true;
        }
    }

}
