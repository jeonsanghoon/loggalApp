package com.altsoft.Framework.control;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.altsoft.loggalapp.R;

public class altAutoCmpleateTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {
    // was the text just cleared?
    boolean justCleared = false;

    // if not set otherwise, the default clear listener clears the text in the
    // text view
    private OnClearListener defaultClearListener = new OnClearListener() {

        @Override
        public void onClear() {
            altAutoCmpleateTextView et = altAutoCmpleateTextView.this;
            et.setText("");
        }
    };

    private OnClearListener onClearListener = defaultClearListener;

    // The image we defined for the clear button
    public Drawable imgClearButton = getResources().getDrawable(
            R.drawable.ic_clear);

    public interface OnClearListener {
        void onClear();
    }

    /* Required methods, not used in this implementation */
    public altAutoCmpleateTextView(Context context) {
        super(context);

        init();
    }

    /* Required methods, not used in this implementation */
    public altAutoCmpleateTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /* Required methods, not used in this implementation */
    public altAutoCmpleateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    void init() {
        // Set the bounds of the button


        // if the clear button is pressed, fire up the handler. Otherwise do nothing
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                altAutoCmpleateTextView et = altAutoCmpleateTextView.this;

                if (et.getCompoundDrawables()[2] == null)
                    return false;

                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;

                if (event.getX() > et.getWidth() - et.getPaddingRight() - imgClearButton.getIntrinsicWidth()) {
                    onClearListener.onClear();
                    justCleared = true;
                }
                return false;
            }
        });


        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() > 0) {
                    altAutoCmpleateTextView.this.setCompoundDrawablesWithIntrinsicBounds(null, null,
                            imgClearButton, null);
                }
                else {
                    altAutoCmpleateTextView.this.setCompoundDrawablesWithIntrinsicBounds(null, null,
                            null, null);
                }
            }
        } );
        this.setLineSpacing(0, 1.2f);
        this.setTextSize(18);
        this.setPadding(15,0,15,0);
        this.setSingleLine();


    }

    public void setImgClearButton(Drawable imgClearButton) {
        this.imgClearButton = imgClearButton;
    }

    public void setOnClearListener(final OnClearListener clearListener) {
        this.onClearListener = clearListener;
    }

    public void hideClearButton() {
        this.setCompoundDrawables(null, null, null, null);
    }

    public void showClearButton() {
        this.setCompoundDrawablesWithIntrinsicBounds(null, null, imgClearButton, null);
    }

}