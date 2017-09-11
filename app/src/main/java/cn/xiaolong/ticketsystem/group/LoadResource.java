package cn.xiaolong.ticketsystem.group;


import com.standards.library.app.AppContext;

/**
 * <加载页对应View的数据>
 *
 * @version: V1.0
 */
public class LoadResource {

    //Loading加载失败时的文本描述
    private String errorText;
    //Loading加载失败时文本的颜色
    private int TextColor;
    //Loading加载失败时的图片
    private int errorImage;
    //loading加载失败时的按钮
    private String errorHint;

    public int getHintColor() {
        return HintColor;
    }

    public void setHintColor(int hintColor) {
        HintColor = hintColor;
    }

    public String getErrorHint() {
        return errorHint;
    }

    public void setErrorHint(String errorHint) {
        this.errorHint = errorHint;
    }

    //Loading加载失败时文本的颜色
    private int HintColor;
    //Loading加载失败时的图片

    public void setErrorText(int stringRes) {
        this.errorText = AppContext.getContext().getString(stringRes);
    }

    public void setErrorText(String string) {
        this.errorText = string;
    }

    public void setTextColor(int color) {
        this.TextColor = color;
    }

    public void setImage(int imageRes) {
        this.errorImage = imageRes;
    }

    public String getErrorText() {
        return errorText;
    }

    public int getTextColor() {
        return TextColor;
    }

    public int getImage() {
        return errorImage;
    }

}

