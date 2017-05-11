package th.co.smk.smkagent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

public class ActivityCalendar extends AppCompatActivity {
    Paint paint = new Paint();
    DrawView drawView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        /*calendarView = (CalendarView) findViewById(R.id.calenD);

        paint.setColor(Color.BLACK);*/


        drawView = new DrawView(this);
        setContentView(drawView);
    }

    class DrawView extends View {
        public DrawView(Context context) {
            super(context);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(10);
        }
        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //Draw line.
            canvas.drawLine(100, 50, 200, 40, paint);
            canvas.drawLine(120, 10, 220, 20, paint);
            //Draw circle.
            canvas.drawCircle(200, 200, 50, paint);


            //Draw half circle.
            //float width = (float) getWidth();
            //float height = (float) getHeight();
            float width = (float) 800;
            float height = (float) 800;
            float radius;
            if (width > height) {
                radius = height / 4;
            } else {
                radius = width / 4;
            }
            Path path = new Path();
            path.addCircle(width / 2,
                    height / 2, radius,
                    Path.Direction.CW);
            float center_x, center_y;
            final RectF oval = new RectF();
            paint.setStyle(Paint.Style.STROKE);
            center_x = width / 2;
            center_y = height / 2;
            oval.set(center_x - radius,
                    center_y - radius,
                    center_x + radius,
                    center_y + radius);
            canvas.drawArc(oval, 90, 180, false, paint);
        }
    }
}
