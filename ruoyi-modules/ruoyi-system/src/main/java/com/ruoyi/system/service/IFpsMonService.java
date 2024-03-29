package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.FpsMon;

/**
 * fpsmonitorService接口
 * 
 * @author sunbin
 * @date 2024-03-29
 */
public interface IFpsMonService 
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
     * 批量删除fpsmonitor
     * 
     * @param ids 需要删除的fpsmonitor主键集合
     * @return 结果
     */
    public int deleteFpsMonByIds(String[] ids);

    /**
     * 删除fpsmonitor信息
     * 
     * @param id fpsmonitor主键
     * @return 结果
     */
    public int deleteFpsMonById(String id);
}
