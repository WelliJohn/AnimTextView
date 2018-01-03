package wellijohn.org.animtv;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * @author: JiangWeiwei
 * @time: 2017/9/6-14:31
 * @email:
 * @desc:
 */
public class UiUtils {

    /**
     * dip转换px
     */
    public static int dip2px(Context context, float dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * @param paramText  显示的文本
     * @param paramPaint 画笔
     * @return 文本的宽度
     */
    public static float getTextWidth(@NonNull String paramText, Paint paramPaint) {
        return paramPaint.measureText(paramText);
    }

    /**
     * @param paramText  显示的文本
     * @param paramPaint 画笔
     * @return 文本的高度
     */
    public static float getTextHeight(String paramText, Paint paramPaint) {
        if (TextUtils.isEmpty(paramText)) {
            paramText = "佣金";
        }
        Rect rect = new Rect();
        paramPaint.getTextBounds(paramText, 0, paramText.length(), rect);
        return rect.height();
    }


}
