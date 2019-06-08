package ir.fearefull.wheretoapp.view.base;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class MyEditText extends androidx.appcompat.widget.AppCompatEditText {

    public MyEditText(Context context) {
        super(context);
        init();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "font/my_font.ttf");
        setTypeface(font);
    }
}