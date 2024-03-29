package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.FpsMonMapper;
import com.ruoyi.system.domain.FpsMon;
import com.ruoyi.system.service.IFpsMonService;

/**
 * fpsmonitorService业务层处理
 * 
 * @author sunbin
 * @date 2024-03-29
 */
@Service
public class FpsMonServiceImpl implements IFpsMonService 
{
    @Autowired
    private FpsMonMapper fpsMonMapper;

    /**
     * 查询fpsmonitor
     * 
     * @param id fpsmonitor主键
     * @return fpsmonitor
     */
    @Override
    public FpsMon selectFpsMonById(String id)
    {
        return fpsMonMapper.selectFpsMonById(id);
    }

    /**
     * 查询fpsmonitor列表
     * 
     * @param fpsMon fpsmonitor
     * @return fpsmonitor
     */
    @Override
    public List<FpsMon> selectFpsMonList(FpsMon fpsMon)
    {
        return fpsMonMapper.selectFpsMonList(fpsMon);
    }

    /**
     * 新增fpsmonitor
     * 
     * @param fpsMon fpsmonitor
     * @return 结果
     */
    @Override
    public int insertFpsMon(FpsMon fpsMon)
    {
        return fpsMonMapper.insertFpsMon(fpsMon);
    }

    /**
     * 修改fpsmonitor
     * 
     * @param fpsMon fpsmonitor
     * @return 结果
     */
    @Override
    public int updateFpsMon(FpsMon fpsMon)
    {
        return fpsMonMapper.updateFpsMon(fpsMon);
    }

    /**
     * 批量删除fpsmonitor
     * 
     * @param ids 需要删除的fpsmonitor主键
     * @return 结果
     */
    @Override
    public int deleteFpsMonByIds(String[] ids)
    {
        return fpsMonMapper.deleteFpsMonByIds(ids);
    }

    /**
     * 删除fpsmonitor信息
     * 
     * @param id fpsmonitor主键
     * @return 结果
     */
    @Override
    public int deleteFpsMonById(String id)
    {
        return fpsMonMapper.deleteFpsMonById(id);
    }
}
