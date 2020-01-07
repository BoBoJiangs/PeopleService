package cn.sts.base.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;

import cn.sts.base.R;
import cn.sts.base.util.StringUtils;


/**
 * 多功能控件--选择/输入/显示
 * Created by weilin on 17/8/17.
 */

public class UtilityView extends LinearLayout {

    private LinearLayout infoLinearLayout;

    private TextView titleTextView;
    private TextView contentTextView;
    private EditText inputEditText;
    private ImageView contentImageView;
    private ImageView rightImageView;
    private View lineView;

    private Context context;

    private int contentColor;
    private int contentColorHint;

    /**
     * 下划线选中颜色
     */
    private int lineColorChecked;
    /**
     * 下划线颜色
     */
    private int lineColor;
    /**
     * 是否显示下划线
     */
    private boolean isShowLine;
    /**
     * 是否支持多行输入
     */
    private boolean isMultiLine;
    private int infoPaddingLeft;
    private int infoPaddingTop;
    private int infoPaddingRight;
    private int infoPaddingBottom;

    public UtilityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(attrs);
    }

    /**
     * 设置标题
     */
    public void setTitleText(String text) {
        if (titleTextView != null) {
            titleTextView.setText(text);
        }
    }

    /**
     * 获取标题
     */
    public String getTitleText() {
        return titleTextView.getText().toString();
    }

    /**
     * 设置标题的宽度
     *
     * @param width 宽
     */
    public void setTitleTextViewWidth(int width) {
        ViewGroup.LayoutParams layoutParams = titleTextView.getLayoutParams();
        layoutParams.width = width;
    }


    /**
     * 设置内容文本
     *
     * @param text 文本
     */
    public void setContentText(String text) {
        if (contentTextView != null) {
            contentTextView.setText(text);
            contentTextView.setTextColor(contentColor);
        } else if (inputEditText != null) {
            inputEditText.setText(text);
        }
    }

    /**
     * 获取内容文本
     */
    public String getContentText() {
        if (contentTextView != null) {
            return contentTextView.getText().toString();
        } else if (inputEditText != null) {
            return inputEditText.getText().toString();
        }
        return "";
    }

    /**
     * 设置内容提示文本
     *
     * @param text 文本
     */
    public void setContentHintText(String text) {
        if (contentTextView != null) {
            contentTextView.setText(text);
            contentTextView.setTextColor(contentColorHint);
        } else if (inputEditText != null) {
            inputEditText.setHint(text);
            inputEditText.setHintTextColor(contentColorHint);
        }
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public TextView getContentTextView() {
        return contentTextView;
    }

    public EditText getInputEditText() {
        return inputEditText;
    }

    public ImageView getContentImageView() {
        return contentImageView;
    }

    public ImageView getRightImageView() {
        return rightImageView;
    }

    public void setInfoPaddingLeft(int infoPaddingLeft) {
        this.infoPaddingLeft = infoPaddingLeft;
        infoLinearLayout.setPadding(infoPaddingLeft, infoPaddingTop, infoPaddingRight, infoPaddingBottom);
    }

    public void setInfoPaddingTop(int infoPaddingTop) {
        this.infoPaddingTop = infoPaddingTop;
        infoLinearLayout.setPadding(infoPaddingLeft, infoPaddingTop, infoPaddingRight, infoPaddingBottom);
    }

    public void setInfoPaddingRight(int infoPaddingRight) {
        this.infoPaddingRight = infoPaddingRight;
        infoLinearLayout.setPadding(infoPaddingLeft, infoPaddingTop, infoPaddingRight, infoPaddingBottom);
    }

    public void setInfoPaddingBottom(int infoPaddingBottom) {
        this.infoPaddingBottom = infoPaddingBottom;
        infoLinearLayout.setPadding(infoPaddingLeft, infoPaddingTop, infoPaddingRight, infoPaddingBottom);
    }

    private void initView(AttributeSet attrs) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.UtilityView);
        isMultiLine = typedArray.getBoolean(R.styleable.UtilityView_multi_line, false);
        //标题
        CharSequence titleText = typedArray.getText(R.styleable.UtilityView_text_title);
        int titleColor = typedArray.getColor(R.styleable.UtilityView_text_title_color, Color.DKGRAY);
        float titleFont = typedArray.getDimension(R.styleable.UtilityView_text_title_size, 32);
        boolean isVisibleTitle = typedArray.getBoolean(R.styleable.UtilityView_text_title_visible, true);
        boolean textIsSelectable = typedArray.getBoolean(R.styleable.UtilityView_text_is_selectable, false);
        int textTitleWidth = (int) typedArray.getDimension(R.styleable.UtilityView_text_title_width, 0);
        int titlePaddingLeft = (int) typedArray.getDimension(R.styleable.UtilityView_title_padding_left, 0);
        int titlePaddingTop = (int) typedArray.getDimension(R.styleable.UtilityView_title_padding_top, 0);
        int titlePaddingRight = (int) typedArray.getDimension(R.styleable.UtilityView_title_padding_right, 0);
        int titlePaddingBottom = (int) typedArray.getDimension(R.styleable.UtilityView_title_padding_bottom, 0);

        //内容
        CharSequence contentHintText = typedArray.getText(R.styleable.UtilityView_text_content_hint);
        CharSequence contentText = typedArray.getText(R.styleable.UtilityView_text_content);
        contentColor = typedArray.getColor(R.styleable.UtilityView_text_content_color, Color.BLACK);
        float contentFont = typedArray.getDimension(R.styleable.UtilityView_text_content_size, 32);
        contentColorHint = typedArray.getColor(R.styleable.UtilityView_text_content_hint_color, Color.GRAY);
        boolean isVisibleContent = typedArray.getBoolean(R.styleable.UtilityView_text_content_visible, true);

        //下划线的属性
        lineColorChecked = typedArray.getColor(R.styleable.UtilityView_line_color_checked, Color.GRAY);
        lineColor = typedArray.getColor(R.styleable.UtilityView_line_color, Color.GRAY);
        isShowLine = typedArray.getBoolean(R.styleable.UtilityView_line_show, true);

        infoPaddingLeft = (int) typedArray.getDimension(R.styleable.UtilityView_info_padding_left, 0);
        infoPaddingTop = (int) typedArray.getDimension(R.styleable.UtilityView_info_padding_top, 0);
        infoPaddingRight = (int) typedArray.getDimension(R.styleable.UtilityView_info_padding_right, 0);
        infoPaddingBottom = (int) typedArray.getDimension(R.styleable.UtilityView_info_padding_bottom, 0);

        //显示右边图片
        final int rightImageResId = typedArray.getResourceId(R.styleable.UtilityView_right_image_res, 0);
        //根据输入的内容来动态显示右侧图片
        boolean rightImageShowByInput = typedArray.getBoolean(R.styleable.UtilityView_right_image_show_by_input, true);

        //控件类型
        int viewType = typedArray.getInt(R.styleable.UtilityView_type, 0);

        //内容位置,默认在上下居中
        int contentGravity = typedArray.getInt(R.styleable.UtilityView_content_gravity, Gravity.CENTER_VERTICAL);

        try {

//            float scale = context.getResources().getDisplayMetrics().density;

            //上布局
            infoLinearLayout = new LinearLayout(context);
            infoLinearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1));
            if (getOrientation() == VERTICAL) {
                infoLinearLayout.setOrientation(VERTICAL);
            } else {
                infoLinearLayout.setOrientation(HORIZONTAL);
            }
            infoLinearLayout.setGravity(Gravity.CENTER_VERTICAL);
            infoLinearLayout.setPadding(infoPaddingLeft, infoPaddingTop, infoPaddingRight, infoPaddingBottom);

            if (isVisibleTitle) {
                //标题
                titleTextView = new TextView(context);
                if (textTitleWidth > 0) {
                    LayoutParams layoutParams = new LayoutParams(textTitleWidth, LayoutParams.WRAP_CONTENT);
                    titleTextView.setLayoutParams(layoutParams);
                }
                titleTextView.setText(titleText);
                titleTextView.setTextColor(titleColor);
                titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleFont);
                titleTextView.setPadding(titlePaddingLeft, titlePaddingTop, titlePaddingRight, titlePaddingBottom);
                if (textIsSelectable) {
                    titleTextView.setTextIsSelectable(true);
                    titleTextView.setSelected(true);
                    titleTextView.setEnabled(true);
                    titleTextView.setFocusable(true);
                    titleTextView.setLongClickable(true);
                }
                //添加标题
                infoLinearLayout.addView(titleTextView);
            }

            if (isVisibleContent) {

                LayoutParams layoutParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
                if (getOrientation() == VERTICAL) {
                    layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, 0, 1);
                }
                int imageWidth = (int) typedArray.getDimension(R.styleable.UtilityView_content_image_width, 40);
                int imageHeight = (int) typedArray.getDimension(R.styleable.UtilityView_content_image_height, 30);
                int imageResId = typedArray.getResourceId(R.styleable.UtilityView_content_image_res, 0);
                if (imageResId != 0) {
                    LayoutParams params = new  LayoutParams(imageWidth, imageHeight);
                    params.rightMargin = SizeUtils.dp2px(10);
                    contentImageView = new ImageView(context);
                    contentImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    contentImageView.setLayoutParams(params);
                    contentImageView.setImageResource(imageResId);
                    infoLinearLayout.addView(contentImageView);
                }

                if (viewType == 0) {//显示内容
                    contentTextView = new TextView(context);
                    contentTextView.setGravity(contentGravity);
                    contentTextView.setLayoutParams(layoutParams);
                    if (contentText == null || StringUtils.isBlank(contentText.toString())) {
                        contentTextView.setText(contentHintText);
                        contentTextView.setTextColor(contentColorHint);
                    } else {
                        contentTextView.setText(contentText);
                        contentTextView.setTextColor(contentColor);
                    }
                    contentTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentFont);
                    contentTextView.setTextIsSelectable(textIsSelectable);
                    infoLinearLayout.addView(contentTextView);
                } else if (viewType == 1) {//输入内容
                    inputEditText = new EditText(context);
                    inputEditText.setBackgroundColor(Color.TRANSPARENT);

                    inputEditText.setLayoutParams(layoutParams);
                    inputEditText.setHint(contentHintText);
                    inputEditText.setHintTextColor(contentColorHint);
                    inputEditText.setTextColor(contentColor);
                    inputEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentFont);
                    inputEditText.setGravity(contentGravity);
                    inputEditText.setPadding(0, 1, 0, 0);

                    //输入文本类型
                    int textContentInputType = typedArray.getInt(R.styleable.UtilityView_text_content_input_type, 0);
                    if (textContentInputType == 1) {
                        textContentInputType = InputType.TYPE_CLASS_NUMBER;
                    } else if (textContentInputType == 2) {
                        textContentInputType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL;
                    } else if (textContentInputType == 3) {
                        textContentInputType = InputType.TYPE_CLASS_PHONE;
                    } else if (textContentInputType == 4) {
                        textContentInputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                    } else {
                        textContentInputType = InputType.TYPE_CLASS_TEXT;
                    }
                    inputEditText.setInputType(textContentInputType);
                    if (isMultiLine) {
                        //设置EditText的显示方式为多行文本输入
                        inputEditText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | textContentInputType);
                        inputEditText.setGravity(Gravity.TOP);
                        inputEditText.setSingleLine(false);
                        inputEditText.setHorizontallyScrolling(false);
                    }

                    infoLinearLayout.addView(inputEditText);

                    inputEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (lineView != null) {
                                if (hasFocus) {
                                    if (rightImageResId != 0 && inputEditText.getText().length() > 0) {
                                        rightImageView.setVisibility(View.VISIBLE);
                                    }
                                    lineView.setBackgroundColor(lineColorChecked);
                                } else {
                                    if (rightImageResId != 0) {
                                        rightImageView.setVisibility(View.GONE);
                                    }
                                    lineView.setBackgroundColor(lineColor);
                                }
                            }
                        }
                    });
                }
//                else if (viewType == 2) {//显示图片
//                    int width = (int) typedArray.getDimension(R.styleable.UtilityView_content_image_width, 40);
//                    int height = (int) typedArray.getDimension(R.styleable.UtilityView_content_image_height, 30);
//                    int imageResId = typedArray.getResourceId(R.styleable.UtilityView_content_image_res, 0);
//                    contentImageView = new ImageView(context);
//                    contentImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    contentImageView.setLayoutParams(new LayoutParams(width, height));
//                    if (imageResId != 0) {
//                        contentImageView.setImageResource(imageResId);
//                    }
//                    infoLinearLayout.addView(contentImageView);
//                }

                if (rightImageResId != 0) {
                    //是否显示右侧图片
                    int width = (int) typedArray.getDimension(R.styleable.UtilityView_right_image_width, 0);
                    int height = (int) typedArray.getDimension(R.styleable.UtilityView_right_image_height, 0);
                    if (width == 0) {
                        width = 60;
                    }
                    if (height == 0) {
                        height = 60;
                    }
                    int imagePadding = (int) typedArray.getDimension(R.styleable.UtilityView_right_image_padding, 0);
                    rightImageView = new ImageView(context);
                    rightImageView.setLayoutParams(new LayoutParams(width, height));
                    rightImageView.setImageResource(rightImageResId);
                    rightImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    rightImageView.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
                    infoLinearLayout.addView(rightImageView);

                    //判断是否是输入框，动态显示删除按钮
                    if (rightImageShowByInput && inputEditText != null) {
                        //设置有数据的时候可以删除数据，点击事件
                        rightImageView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                inputEditText.setText("");
                            }
                        });

                        //设置焦点事件，是否显示删除按钮
                        rightImageView.setOnFocusChangeListener(new OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (hasFocus && StringUtils.isNotBlank(inputEditText.getText().toString())) {
                                    inputEditText.setVisibility(View.VISIBLE);
                                } else {
                                    inputEditText.setVisibility(View.GONE);
                                }
                            }
                        });

                        //监听输入的文本变化，显示删除按钮
                        inputEditText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                                if (inputEditText.hasFocus() && StringUtils.isNotBlank(inputEditText.getText().toString())) {
                                    rightImageView.setVisibility(View.VISIBLE);
                                } else {
                                    rightImageView.setVisibility(View.GONE);
                                }
                            }
                        });

                        rightImageView.setVisibility(View.GONE);
                    }
                }
            }

            this.addView(infoLinearLayout);

            this.setOrientation(VERTICAL);
            if (isShowLine) {
                //分割线
                lineView = new View(context);
                lineView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
                lineView.setBackgroundColor(lineColor);
                this.addView(lineView);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }
}
