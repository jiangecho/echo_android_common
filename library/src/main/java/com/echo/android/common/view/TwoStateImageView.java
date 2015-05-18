package com.echo.android.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.echo.android_common.R;

/**
 * Created by jiangecho on 15/5/14.
 */
public class TwoStateImageView extends ImageView {
    private Drawable selectedDrawable;
    private Drawable unSelectedDrawable;
    private boolean isSelected;
    private State state;

    public TwoStateImageView(Context context) {
        super(context);
        init(context, null);
    }

    public TwoStateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TwoStateImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TwoStateImageView);
        if (typedArray != null){
            selectedDrawable = typedArray.getDrawable(R.styleable.TwoStateImageView_selectedSrc);
            unSelectedDrawable = typedArray.getDrawable(R.styleable.TwoStateImageView_unSelectedSrc);
            isSelected = typedArray.getBoolean(R.styleable.TwoStateImageView_isSelected, false);
            typedArray.recycle();
        }
    }

    public void setSelectedDrawable(Drawable drawable){
        this.selectedDrawable = drawable;
    }

    public void setUnSelectedDrawable(Drawable drawable){
        this.unSelectedDrawable = drawable;
    }

    public void setState(State state){
        if (state == State.SELECTED){
            setImageDrawable(selectedDrawable);
        }else {
            setImageDrawable(unSelectedDrawable);
        }
    }

    /**
     * Finalize inflating a view from XML.  This is called as the last phase
     * of inflation, after all child views have been added.
     * <p/>
     * <p>Even if the subclass overrides onFinishInflate, they should always be
     * sure to call the super method, so that we get called.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (isSelected){
            setImageDrawable(selectedDrawable);
        }else {
            setImageDrawable(unSelectedDrawable);
        }
    }

    public enum State{
        SELECTED,
        UN_SELECTED
    }
}
