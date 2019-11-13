package cn.sts.base.model.server.request.app;


import java.util.List;
import java.util.Map;

import cn.sts.base.model.server.vo.HelpCenterVO;
import cn.sts.base.model.server.vo.RequestResult;
import cn.sts.base.model.server.vo.VersionVO;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 设置相关的请求
 * Created by sts on 2018/6/21.
 *
 * @author daichao
 */

public interface IAppManageURL {

    /**
     * 检查APP版本
     *
     * @param parameter appName
     *                  versionType 0:安卓，1：iOS，2：硬件，3:WEB
     * @return 最新版本
     */
    @POST("versionManager/loadNewVersion")
    Observable<RequestResult<VersionVO>> getAppLatestVersion(@Body Map parameter);

    /**
     * 获取常见问题类型功能分类列表
     *
     * @param map treeName APP
     */
    @POST("help/queryTypeList")
    Observable<RequestResult<List<HelpCenterVO>>> getHelpTypeList(@Body Map map);

    /**
     * 获取常见问题功能列表
     *
     * @param map typeId 问题类别ID
     */
    @POST("help/queryHelp")
    Observable<RequestResult<List<HelpCenterVO>>> getHelpListByType(@Body Map map);

}
