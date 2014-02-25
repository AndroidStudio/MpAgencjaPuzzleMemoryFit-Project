package mp.agencja.puzzle.memory.fit;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.View;

public class CustomDragShadowBuldier extends View.DragShadowBuilder {

    private Drawable shadow;

    public CustomDragShadowBuldier() {
        super();
    }

    public void fromResource(Context context, int drawableId) {
        shadow = context.getResources().getDrawable(drawableId);
        if (shadow == null) {
            throw new NullPointerException("Drawable from id is null");
        }
        shadow.setBounds(0, 0, shadow.getMinimumWidth(), shadow.getMinimumHeight());
    }

    @Override
    public void onDrawShadow(Canvas canvas) {
        shadow.draw(canvas);
    }

    @Override
    public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
        shadowSize.x = shadow.getMinimumWidth();
        shadowSize.y = shadow.getMinimumHeight();

        shadowTouchPoint.x = shadowSize.x / 2;
        shadowTouchPoint.y = (int) (0.9 * shadowSize.y);
    }
}