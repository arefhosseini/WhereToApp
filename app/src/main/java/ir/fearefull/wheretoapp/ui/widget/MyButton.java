package ir.fearefull.wheretoapp.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MyButton extends androidx.appcompat.widget.AppCompatButton {


    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public MyButton(Context context) {
        super(context);
        init();
    }
    public MyButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    private void init(){
        Typeface font_type= Typeface.createFromAsset(getContext().getAssets(), "font/my_font.ttf");
        setTypeface(font_type);
    }
}