package th.co.fingertip.pillsalert.dragndrop;

/**
 * Interface defining an object that can receive a drag.
 *
 */
public interface DropTarget {

    /**
     * Handle an object being dropped on the DropTarget
     * 
     * @param source DragSource where the drag started
     * @param x X coordinate of the drop location
     * @param y Y coordinate of the drop location
     * @param xOffset Horizontal offset with the object being dragged where the original touch happened
     * @param yOffset Vertical offset with the object being dragged where the original touch happened
     * @param dragInfo Data associated with the object being dragged
     * 
     */
    void onDrop(DragSource source, int x, int y, int xOffset, int yOffset, Object dragInfo);
    
    void onDragEnter(DragSource source, int x, int y, int xOffset, int yOffset, Object dragInfo);

    void onDragOver(DragSource source, int x, int y, int xOffset, int yOffset, Object dragInfo);

    void onDragExit(DragSource source, int x, int y, int xOffset, int yOffset, Object dragInfo);

    /**
     * Indicates whether a drop action can occur at the specified location. The method
     * {@link #onDrop(DragSource, int, int, int, int, Object)} will be invoked on this
     * drop target only if this method returns true. 
     *
     * @param source DragSource where the drag started
     * @param x X coordinate of the drop location
     * @param y Y coordinate of the drop location
     * @param xOffset Horizontal offset with the object being dragged where the original touch happened
     * @param yOffset Vertical offset with the object being dragged where the original touch happened
     * @param dragInfo Data associated with the object being dragged
     *
     * return True if the drop is accepted, false otherwise.
     */
    boolean acceptDrop(DragSource source, int x, int y, int xOffset, int yOffset, Object dragInfo);
}

