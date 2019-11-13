package cn.sts.base.view.widget;


import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.MultiAutoCompleteTextView;

/**
 * 邮箱输入自动补全，指定输入@后开始联想
 * Created by weilin on 2018/3/19.
 */
public class EMailInputTokenizer implements MultiAutoCompleteTextView.Tokenizer {
    @Override
    public int findTokenStart(CharSequence charSequence, int cursor) {
        int index = charSequence.toString().indexOf("@");

        if (index < 0) {
            index = charSequence.length();
        }
        if (index >= findTokenEnd(charSequence, cursor)) {
            index = 0;
        }
        return index;
    }

    @Override
    public int findTokenEnd(CharSequence charSequence, int cursor) {
        int i = cursor;
        int len = charSequence.length();
        while (i < len) {
            if (charSequence.charAt(i) == '@') {
                return i;
            } else {
                i++;
            }
        }
        return len;
    }

    @Override
    public CharSequence terminateToken(CharSequence charSequence) {
        int i = charSequence.length();

        while (i > 0 && charSequence.charAt(i - 1) == ' ') {
            i--;
        }

        if (i > 0 && charSequence.charAt(i - 1) == '@') {
            return charSequence;
        } else {
            if (charSequence instanceof Spanned) {
                SpannableString sp = new SpannableString(charSequence);
                TextUtils.copySpansFrom((Spanned) charSequence, 0, charSequence.length(), Object.class, sp, 0);
                return sp;
            } else {
                return charSequence;
            }
        }
    }
}
