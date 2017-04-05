package uk.ac.ox.ndcn.paths.Util;

/**
 * Created by appdev on 21/08/2016.
 */
public class CollisionFunctions {

    public static boolean CircleWithPoint(float pointX, float pointY, float x, float y, float radius){
        return distance(pointX, pointY, x, y) < radius;

    }

    public static boolean CircleWithLine(float x1, float y1, float x2, float y2, float x, float y, float radius){
        //Calculate the shortest distance between the centre of the circle and the line segment:
        // Consider the following vectors: v = x1, y1; w = x2, y2; p = x, y
        // Consider the line extending the segment, parameterized as v + t (w - v).
        // We find projection of point p onto the line.
        // It falls where t = [(p-v) . (w-v)] / |w-v|^2
        // (ax, ay) = p - v = (x-x1, y-y1)
        float ax = x - x1;
        float ay = y - y1;
        // (bx, by) = w - v = (x2-x1, y2-y1)
        float bx = x2 - x1;
        float by = y2 - y1;
        // l2 = (x2 - x1)^2 + (y2 - y1)^2
        float l2 = (float) (Math.pow((x2 - x1),2) + Math.pow((y2 - y1),2));

        // calculate the dotproduct of a and b over l2
        float t = ((ax * bx) + (ay * by)) / l2;
        if (t < 0.0) return distance(x, y, x1, y1) < radius;       // Beyond the 'v' end of the segment
        else if (t > 1.0) return distance(x, y, x2, y2) < radius;  // Beyond the 'w' end of the segment
        float projectionx = x1 + t * (x2 - x1);  // Projection falls on the segment
        float projectiony = y1 + t * (y2 - y1);  // Projection falls on the segment
        return distance(x, y, projectionx, projectiony) < radius;
    }

    private static float distance(float ax, float ay, float bx, float by){
        return (float) Math.sqrt(Math.pow((bx - ax),2) + Math.pow((by - ay),2));
    }

    public static boolean RectWithPoint(float pointX, float pointY, float x, float y, float width, float height){
        return pointX > x && pointY > y && pointX < (x + width) && pointY < (y + height);
    }

    //Rectangle/Line intersection algorithm from java2d (GPL V2)

    private static final int OUT_LEFT = 1;
    private static final int OUT_TOP = 2;
    private static final int OUT_RIGHT = 4;
    private static final int OUT_BOTTOM = 8;
    public static boolean RectWithLine(float lineX1, float lineY1, float lineX2, float lineY2, float x, float y, float width, float height)
    {
        int out1, out2;
        if ((out2 = outcode(lineX2, lineY2, x, y, width, height)) == 0) {
            return true;
        }
        while ((out1 = outcode(lineX1, lineY1, x, y, width, height)) != 0) {
            if ((out1 & out2) != 0) {
                return false;
            }
            if ((out1 & (OUT_LEFT | OUT_RIGHT)) != 0) {
                float _x = x;
                if ((out1 & OUT_RIGHT) != 0) {
                    _x += width;
                }
                lineY1 = lineY1 + (_x - lineX1) * (lineY2 - lineY1) / (lineX2 - lineX1);
                lineX1 = _x;
            } else {
                float _y = y;
                if ((out1 & OUT_BOTTOM) != 0) {
                    _y += height;
                }
                lineX1 = lineX1 + (_y - lineY1) * (lineX2 - lineX1) / (lineY2 - lineY1);
                lineY1 = _y;
            }
        }
        return true;
    }
    private static int outcode(double pX, double pY, double rectX, double rectY, double rectWidth, double rectHeight) {
        int out = 0;
        if (rectWidth <= 0) {
            out |= OUT_LEFT | OUT_RIGHT;
        } else if (pX < rectX) {
            out |= OUT_LEFT;
        } else if (pX > rectX + rectWidth) {
            out |= OUT_RIGHT;
        }
        if (rectHeight <= 0) {
            out |= OUT_TOP | OUT_BOTTOM;
        } else if (pY < rectY) {
            out |= OUT_TOP;
        } else if (pY > rectY + rectHeight) {
            out |= OUT_BOTTOM;
        }
        return out;
    }
}
