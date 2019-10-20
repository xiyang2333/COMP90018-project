package com.example.login;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.service.entity.RegisterRequest;

import java.io.ByteArrayOutputStream;

public class painting extends AppCompatActivity {

    ImageView iv;
    Button b1, b2, b3;
    float startx, starty, newx, newy, tempx, tempy;
    Paint paint;
    Canvas canvas;
    Bitmap bitmap1, bitmap = null;
    public int view_width = 0;
    public int view_height = 0;
    public int m;
    public android.graphics.PathEffect n;
    int number = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-------------------------------------------------------------------
        WindowManager wm1 = this.getWindowManager();
        view_width = wm1.getDefaultDisplay().getWidth();
        view_height = wm1.getDefaultDisplay().getHeight();

        Toast.makeText(painting.this, "width:" + view_width + "\n" + "height:" + view_height, Toast.LENGTH_SHORT).show();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
        //创建白纸,宽 高，图片的参数
        //bitmap1=Bitmap.createBitmap(view_width,view_height,bitmap.getConfig());
        bitmap1 = Bitmap.createBitmap(view_width, 1650, bitmap.getConfig());
        //bitmap1 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

        //创建画板
        canvas = new Canvas(bitmap1);
        //创建画笔
        paint = new Paint();

        //在纸上作画
        canvas.drawBitmap(bitmap1, new Matrix(), paint);
        canvas.drawColor(Color.WHITE);
        iv = (ImageView) findViewById(R.id.iv);
        iv.setImageBitmap(bitmap1);
        b1 = (Button) findViewById(R.id.btn1);
        b2 = (Button) findViewById(R.id.btn2);
        b3 = (Button) findViewById(R.id.btn3);
        b1.setText("Width");
        b2.setText("Colour");
        b3.setText("Written Form");
        //-------------------------------------------------------------------
        paintLine();

    }

    //线型改为虚线
    public void paintdottedLine() {
        paint.setPathEffect(new DashPathEffect(new float[]{20, 10}, 5.0f));
    }

    //线型改为实线
    public void paintrealLine() {
        paint.setPathEffect(null);
    }

    //绘制平滑线
    public void paintLine() {
        //给控件设置手势适配器，可以得到用户在这个控件上所做的手势；
        iv.setOnTouchListener(new View.OnTouchListener() { //当用户的手在这个控件时，自动回调
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //获取用户的行动
                switch (motionEvent.getAction()) {
                    //按下时回调
                    case MotionEvent.ACTION_DOWN:
                        Log.d("print:", "按下");
                        //获取用户手指按下时的坐标
                        startx = motionEvent.getX();
                        starty = motionEvent.getY();
                        //Toast.makeText(MainActivity.this,startx+""+starty,Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_MOVE://手指滑动时调用
                        Log.d("print:", "正在滑动");
                        newx = motionEvent.getX();
                        newy = motionEvent.getY();
                        //在背景图画线
                        canvas.drawLine(startx, starty, newx, newy, paint);
                        startx = newx;
                        starty = newy;
                        iv.setImageBitmap(bitmap1);
                        break;
                    case MotionEvent.ACTION_UP://松开（抬起）的调用
                        Log.d("print:", "松开");
                        break;
                }
                return true;
            }
        });
    }

    //绘制直线
    public void paintLine1() {
        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("print:", "按下");
                        startx = motionEvent.getX();
                        starty = motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        newx = motionEvent.getX();
                        newy = motionEvent.getY();
                        canvas.drawLine(startx, starty, newx, newy, paint);
                        iv.setImageBitmap(bitmap1);
                        Log.d("print:", "松开");
                        break;
                }
                return true;
            }
        });
    }

    //绘制矩形
    public void paintrect() {
        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //空心效果
                paint.setStyle(Paint.Style.STROKE);
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("print:", "按下");
                        startx = motionEvent.getX();
                        starty = motionEvent.getY();
                        break;
                    /*case MotionEvent.ACTION_MOVE:
                        Log.d("print:","正在滑动");
                        newx = motionEvent.getX();
                        newy = motionEvent.getY();
                        tempx=startx;
                        tempy=starty;
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                        canvas.drawRect(tempx,tempy,newx,newy,paint);
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                        iv.setImageBitmap(bitmap1);
                        break;*/
                    case MotionEvent.ACTION_UP:
                        newx = motionEvent.getX();
                        newy = motionEvent.getY();
                        canvas.drawRect(startx, starty, newx, newy, paint);
                        iv.setImageBitmap(bitmap1);
                        Log.d("print:", "松开");
                        break;
                }
                return true;
            }
        });
    }

    //绘制圆形
    public void paintcircle() {
        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //空心效果
                paint.setStyle(Paint.Style.STROKE);

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("print:", "按下");
                        startx = motionEvent.getX();
                        starty = motionEvent.getY();
                        break;
                    /*case MotionEvent.ACTION_MOVE:
                        Log.d("print:","正在滑动");
                        newx = motionEvent.getX();
                        newy = motionEvent.getY();
                        tempx=startx;
                        tempy=starty;
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                        canvas.drawCircle(tempx,tempy,(float)Math.sqrt((startx-newx)*(startx-newx)+(starty-newy)*(starty-newy)),paint);
                        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));

                        iv.setImageBitmap(bitmap1);
                        break;*/
                    case MotionEvent.ACTION_UP:
                        newx = motionEvent.getX();
                        newy = motionEvent.getY();
                        canvas.drawCircle(startx, starty, (float) Math.sqrt((startx - newx) * (startx - newx) + (starty - newy) * (starty - newy)), paint);
                        iv.setImageBitmap(bitmap1);
                        Log.d("print:", "松开");
                        break;
                }
                return true;
            }
        });
    }

    //-----NumberPicker数字选择器---------------------------------------------------------
    public void numberpick() {
        final Dialog d = new Dialog(painting.this);
        d.setContentView(R.layout.paint_size_select);
        Button bb1 = (Button) d.findViewById(R.id.button1);
        Button bb2 = (Button) d.findViewById(R.id.button2);
        final MyNumberPicker np = (MyNumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(50);
        np.setMinValue(1);
        np.setValue(5);
        np.setWrapSelectorWheel(false);
        np.setNumberPickerDividerColor(np);

        bb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number = np.getValue();
                paint.setStrokeWidth(number);
                b1.setText("Width：" + number);
                Toast.makeText(painting.this, "The width of painting brush is：" + number, Toast.LENGTH_SHORT).show();
                d.dismiss();
            }
        });
        bb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss(); // dismiss the dialog
            }
        });
        d.show();
    }
    //-----------------------------------------------------------------------------------


    //菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //菜单响应
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.green:
                green(iv);
                Toast.makeText(painting.this, "The colour has changed into green", Toast.LENGTH_SHORT).show();
                b2.setText("Colour：Green");
                break;
            case R.id.red:
                red(iv);
                Toast.makeText(painting.this, "The colour has changed into red", Toast.LENGTH_SHORT).show();
                b2.setText("Colour：Red");
                break;
            case R.id.yellow:
                yellow(iv);
                Toast.makeText(painting.this, "The colour has changed into yellow", Toast.LENGTH_SHORT).show();
                b2.setText("Colour: Yellow");
                break;
            case R.id.black:
                black(iv);
                Toast.makeText(painting.this, "The colour has changed into black", Toast.LENGTH_SHORT).show();
                b2.setText("Colour：Black");
                break;

            case R.id.size5:
                brush5(iv);
                Toast.makeText(painting.this, "The width has changed into 5", Toast.LENGTH_SHORT).show();
                b1.setText("Width：5");
                break;
            case R.id.size10:
                brush10(iv);
                Toast.makeText(painting.this, "The width has changed into 10", Toast.LENGTH_SHORT).show();
                b1.setText("Width：10");
                break;
            case R.id.size20:
                brush20(iv);
                Toast.makeText(painting.this, "The width has changed into 20", Toast.LENGTH_SHORT).show();
                b1.setText("Width：20");
                break;
            case R.id.clear:
                clear(iv);
                paintLine();
                Toast.makeText(painting.this, "Change into the eraser", Toast.LENGTH_SHORT).show();
                b3.setText("Eraser");
                break;
            case R.id.clearall:
                clearall(iv);
                break;
            case R.id.rect:
                recover();
                paintrect();
                b3.setText("Rectangle");
                break;
            case R.id.real_line:
                recover();
                paintrealLine();
                Toast.makeText(painting.this, "Solid line", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dotted_line:
                recover();
                paintdottedLine();
                Toast.makeText(painting.this, "Dotted line", Toast.LENGTH_SHORT).show();
                break;
            case R.id.smooth_line:
                recover();
                paintLine();
                b3.setText("Smooth");
                break;
            case R.id.line1:
                recover();
                paintLine1();
                b3.setText("Straight");
                break;
            case R.id.circle:
                recover();
                paintcircle();
                b3.setText("Circle");
                break;
            case R.id.save:
                save(iv);
                break;
        }
        return true;
    }

    //恢复橡皮擦之前的画笔
    public void recover() {
        //判断颜色
        if (paint.getColor() == Color.WHITE)
            paint.setColor(m);
        //判断实线还是虚线
        if (paint.getPathEffect() == null)
            paint.setPathEffect(n);
    }

    //点击改变颜色（红色）
    public void red(View view) {
        paint.setColor(Color.RED);
    }

    //点击改变颜色（黄色）
    public void yellow(View view) {
        paint.setColor(Color.YELLOW);
    }

    //点击改变颜色（黑色）
    public void black(View view) {
        paint.setColor(Color.BLACK);
    }

    //点击改变颜色（绿色）
    public void green(View view) {
        paint.setColor(Color.GREEN);
    }

    //笔画橡皮
    public void clear(View view) {
        //记住画笔的颜色
        m = paint.getColor();
        //记住画笔实线还是虚线
        n = paint.getPathEffect();
        paint.setColor(Color.WHITE);
        paint.setPathEffect(null);
    }

    //全部清除
    public void clearall(View view) {
        canvas.drawColor(Color.WHITE);
    }

    //画笔粗细
    public void brush10(View view) {
        paint.setStrokeWidth(10);
    }

    public void brush20(View view) {
        paint.setStrokeWidth(20);
    }

    public void brush5(View view) {
        paint.setStrokeWidth(5);
    }

    //点击保存并存储到SD卡中
    public String save(View view) {

        if (bitmap1 != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bytes = bos.toByteArray();
            return Base64.encodeToString(bytes,Base64.DEFAULT);
        }

        return null;
    }

}
