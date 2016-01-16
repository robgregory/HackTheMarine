package hackthemarine.coastalwearable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class BeachLayout extends LinearLayout {

    public BeachLayout(Context context) {
        super(context);
        this.initComponent(context);
    }

    public BeachLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initComponent(context);
    }


    private void initComponent(Context context) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.beach_small, null, false);
        this.addView(v);

    }

}