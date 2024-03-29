package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.FpsMon;

/**
 * fpsmonitorMapper接口
 * 
 * @author sunbin
 * @date 2024-03-29
 */
public interface FpsMonMapper 
{
    /**
     * 查询fpsmonitor
     * 
     * @param id fpsmonitor主键
     * @return fpsmonitor
     */
    public FpsMon selectFpsMonById(String id);

    /**
     * 查询fpsmonitor列表
     * 
     * @param fpsMon fpsmonitor
     * @return fpsmonitor集合
     */
    public List<FpsMon> selectFpsMonList(FpsMon fpsMon);

    /**
     * 新增fpsmonitor
     * 
     * @param fpsMon fpsmonitor
     * @return 结果
     */
    public int insertFpsMon(FpsMon fpsMon);

    /**
     * 修改fpsmonitor
     * 
     * @param fpsMon fpsmonitor
     * @return 结果
     */
    public int updateFpsMon(FpsMon fpsMon);

    /**
     * 删除fpsmonitor
     * 
     * @param id fpsmonitor主键
     * @return 结果
     */
    public int deleteFpsMonById(String id);

    /**
     * 批量删除fpsmonitor
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFpsMonByIds(String[] ids);
}
