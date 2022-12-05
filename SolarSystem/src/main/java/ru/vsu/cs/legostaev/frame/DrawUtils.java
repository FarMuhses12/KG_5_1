package ru.vsu.cs.legostaev.frame;

import java.awt.geom.Rectangle2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class DrawUtils {

    private DrawUtils() {}

    public static Vector2D fromPlaneToVisibleRect(Vector2D planeCoordinates, Rectangle2D visibleRect) {
        double xOnRect = planeCoordinates.getX() - visibleRect.getX();
        double yOnRect = visibleRect.getY() - planeCoordinates.getY();

        return new Vector2D(xOnRect, yOnRect);
    }

    public static Vector2D fromVisibleRectToImage(Vector2D pOnRect, Rectangle2D visibleRect, int width, int height) {

        double xOnImg = pOnRect.getX() * width / visibleRect.getWidth();
        double yOnImg = pOnRect.getY() * height / visibleRect.getHeight();

        return new Vector2D(xOnImg, yOnImg);
    }

    public static Vector2D fromPlaneToImage(Vector2D planeCoordinates, Rectangle2D visibleRect, int width, int height) {

        Vector2D pOnRect = fromPlaneToVisibleRect(planeCoordinates, visibleRect);

        return fromVisibleRectToImage(pOnRect, visibleRect, width, height);

    }

    public static Vector2D fromImageToPlane(Vector2D imageCoordinates, int width, int height, Rectangle2D visibleRect) {

        Vector2D pOnRect = fromImageToVisibleRect(imageCoordinates, width, height, visibleRect);

        return fromVisibleRectToPlane(pOnRect, visibleRect);

    }

    public static Vector2D fromImageToVisibleRect(Vector2D pOnImg, int width, int height, Rectangle2D visibleRect) {

        double pOnRectX = pOnImg.getX() * visibleRect.getWidth() / width;
        double pOnRectY = pOnImg.getY() * visibleRect.getHeight() / height;

        return new Vector2D(pOnRectX, pOnRectY);
    }

    public static Vector2D fromVisibleRectToPlane(Vector2D xOnRect, Rectangle2D visibleRect) {
        double planeX = xOnRect.getX() + visibleRect.getX();
        double planeY = visibleRect.getY() - xOnRect.getY();

        return new Vector2D(planeX, planeY);
    }
}
