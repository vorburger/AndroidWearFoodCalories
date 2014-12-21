package calories.fit.vorburger.ch.foodcalories;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.wearable.view.WearableListView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WearableListItemLayout extends LinearLayout implements WearableListView.OnCenterProximityListener {

    private ImageView mCircle;
    private TextView mName;

    private final float mFadedTextAlpha;
    private final int mFadedCircleColor;
    private final int mChosenCircleColor;
    private float mScale;

    public WearableListItemLayout(Context context) {
        this(context, null);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WearableListItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WearableListItemLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        mFadedTextAlpha = getResources().getInteger(R.integer.action_text_faded_alpha) / 100f;
        mFadedCircleColor = getResources().getColor(R.color.wl_gray);
        mChosenCircleColor = getResources().getColor(R.color.wl_blue);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCircle = (ImageView) findViewById(R.id.circle);
        mName = (TextView) findViewById(R.id.name);
    }

    @Override
    public void onCenterPosition(boolean b) {
        mName.setAlpha(1f);
        ((GradientDrawable) mCircle.getDrawable()).setColor(mChosenCircleColor);
    }

    @Override
    public void onNonCenterPosition(boolean b) {
        ((GradientDrawable) mCircle.getDrawable()).setColor(mFadedCircleColor);
        mName.setAlpha(mFadedTextAlpha);
    }
}
