package com.altsoft.togglegroupbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;

import com.altsoft.togglegroupbutton.button.ToggleButton;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MultiSelectToggleGroup extends ToggleButtonGroup {
    private static final String LOG_TAG = MultiSelectToggleGroup.class.getSimpleName();

    private OnCheckedStateChangeListener mOnCheckedStateChangeListener;
    private int mMaxSelectCount = -1;

    public MultiSelectToggleGroup(Context context) {
        super(context);
    }

    public MultiSelectToggleGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.MultiSelectToggleGroup, 0, 0);
        try {
            mMaxSelectCount = a.getInt(R.styleable.MultiSelectToggleGroup_tbgMaxSelect, -1);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int initialCheckedId = mInitialCheckedId != View.NO_ID ?
                mInitialCheckedId : mSilentInitialCheckedId;
        if (initialCheckedId != View.NO_ID) {
            setCheckedStateForView(initialCheckedId, true);
        }
    }

    @Override
    protected <T extends View & Checkable> void onChildCheckedChange(T child, boolean isChecked) {
        checkSelectCount();

        if (mSilentInitialCheckedId == child.getId()) {
            mSilentInitialCheckedId = View.NO_ID;
        } else {
            notifyCheckedStateChange(child.getId(), isChecked);
        }
    }

    public void check(int id) {
        setCheckedStateForView(id, true);
    }

    public void check(int id, boolean checked) {
        setCheckedStateForView(id, checked);
    }

    public void clearCheck() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof ToggleButton) {
                ((ToggleButton) child).setChecked(false);
            }
        }
    }

    public Set<Integer> getCheckedIds() {
        Set<Integer> ids = new LinkedHashSet<>();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof ToggleButton && ((ToggleButton) child).isChecked()) {
                ids.add(child.getId());
            }
        }
        return ids;
    }

    public void toggle(int id) {
        toggleCheckedStateForView(id);
    }

    public void setOnCheckedChangeListener(OnCheckedStateChangeListener listener) {
        mOnCheckedStateChangeListener = listener;
    }

    public int getMaxSelectCount() {
        return mMaxSelectCount;
    }

    public void setMaxSelectCount(int maxSelectCount) {
        mMaxSelectCount = maxSelectCount;
        checkSelectCount();
    }

    private void checkSelectCount() {
        if (mMaxSelectCount < 0) {
            return;
        }

        List<View> uncheckedViews = new ArrayList<>();
        int checkedCount = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof Checkable) {
                if (((Checkable) view).isChecked()) {
                    checkedCount++;
                } else {
                    uncheckedViews.add(view);
                }
            }
        }
        if (checkedCount >= mMaxSelectCount) {
            for (View view : uncheckedViews) {
                view.setEnabled(false);
            }
        } else {
            for (View view : uncheckedViews) {
                view.setEnabled(true);
            }
        }
    }

    private void notifyCheckedStateChange(int id, boolean isChecked) {
        if (mOnCheckedStateChangeListener != null) {
            mOnCheckedStateChangeListener.onCheckedStateChanged(this, id, isChecked);
        }
    }

    public interface OnCheckedStateChangeListener {
        void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked);
    }

    //동적으로 버튼추가
    public void addButton(String text) {
        addButton(getChildCount() + 1, text);
    }

    //동적으로 버튼추가
    public void addButton(int id, String text) {
        com.altsoft.togglegroupbutton.button.CustomToggleButton btn = new com.altsoft.togglegroupbutton.button.CustomToggleButton(getContext());
        btn.setId(id);
        btn.setText(text);
        btn.setTextColor(getResources().getColorStateList(R.color.selector_text_radio_button));
        btn.setBackgroundResource(R.drawable.selector_bg_radio_button);
        btn.setGravity(Gravity.CENTER);
        btn.setPadding(0,0,0,0);
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(getLayoutParams());
        margin.setMargins(0, 0, 0, 20);
        margin.width = LayoutParams.WRAP_CONTENT;
        margin.height = this.dpToPx(30);
        margin.width = dpToPx(100);
        btn.setLayoutParams(margin);

        this.addView(btn);
    }

    public Integer dpToPx(Integer dp) {
        float density = getContext().getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }
}
