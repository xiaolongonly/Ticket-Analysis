package cn.xiaolong.ticketsystem.group;


import com.standards.library.app.ReturnCode;

import java.util.HashMap;

import cn.xiaolong.ticketsystem.R;

/**
 * <Group加载资源相关>
 *
 * @data: 2015/12/bg_account_disable bg_account_disable:02
 * @version: V1.0
 */
public class LoadingConfig {

    public static LoadEntity createEntity(Scene scene) {
        LoadEntity loadEntity = new LoadEntity();
        loadEntity.codeEntity = new HashMap<>();
        LoadResource emptyResource;
        switch (scene) {
            case DEFAULT:
                emptyResource = new LoadResource();
                emptyResource.setErrorText(R.string.load_empty);
                emptyResource.setImage(R.drawable.ic_load_empty);
                loadEntity.put(ReturnCode.CODE_EMPTY, emptyResource);
                break;
            case TICKET_FAVORITE:
                emptyResource = new LoadResource();
                emptyResource.setErrorText("暂无关注信息~");
                emptyResource.setErrorHint("点击上方添加按钮可添加关注！");
                emptyResource.setImage(R.drawable.ic_load_empty);
                loadEntity.put(ReturnCode.CODE_EMPTY, emptyResource);
                break;
            default:
                break;
        }
        paperChecks(loadEntity);
        return loadEntity;
    }


    //TODO 更换系统错误样式
    private static void paperChecks(LoadEntity loadEntity) {
        //连接超时
        LoadResource gsonLoadResource = new LoadResource();
        gsonLoadResource.setErrorText(R.string.load_time_out);
        gsonLoadResource.setImage(R.drawable.ic_load_empty);
        loadEntity.put(ReturnCode.LOCAL_TIMEOUT_ERROR, gsonLoadResource);

        //无网络
        LoadResource noNetworkResource = new LoadResource();
        noNetworkResource.setErrorText(R.string.load_no_network);
        noNetworkResource.setImage(R.drawable.ic_load_no_network);
        loadEntity.put(ReturnCode.LOCAL_NO_NETWORK, noNetworkResource);

        //系统繁忙
        LoadResource errorResource = new LoadResource();
        errorResource.setErrorText(R.string.load_system_busy);
        errorResource.setImage(R.drawable.ic_load_empty);
        loadEntity.put(ReturnCode.CODE_ERROR, errorResource);
    }

}
