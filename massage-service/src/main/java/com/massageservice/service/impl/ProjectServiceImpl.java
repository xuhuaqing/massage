package com.massageservice.service.impl;

import com.massagecommon.entity.ProjectEntity;
import com.massagecommon.entity.RangeDTO;
import com.massagecommon.util.ResponseUtil;
import com.massagedao.mapper.ProjectMapper;
import com.massageservice.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author:WuShuang
 * @date:2020/5/3
 * @ver:1.0
 **/
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public String projectList(String projectName, String parentId) {
        List<ProjectEntity> projectEntities = projectMapper.projectList(projectName,parentId);
        if(parentId.equals("80")){
            swap(projectEntities,projectEntities.size() -2,projectEntities.size()-1);
        }
        return ResponseUtil.successToClient(projectEntities);
    }

    public static void swap(List<?> list,int i,int j){
        final List l=list;
        l.set(i, l.set(j, l.get(i)));
    }

    @Override
    public String selectProjectList(String projectName) {
       List<ProjectEntity.ProjectOne> list = projectMapper.selectProjectList(projectName);
        return ResponseUtil.successToClient(list);

    }

    @Override
    public String projectDetails(String projectId) {
        ProjectEntity.ProjectDetails projectDetails = projectMapper.projectDetails(projectId);
        String s = projectName(projectId,new StringBuffer());
        projectDetails.setProjectName(s);
        return ResponseUtil.successToClient(projectDetails);
    }

    @Override
    public String rangeList(double price, String provincialId) {
        RangeDTO rangeDTO = projectMapper.rangeList(price,provincialId);
        if(rangeDTO==null){
            return ResponseUtil.errorMsgToClient("请填写正确价格！");
        }
        return ResponseUtil.successToClient(rangeDTO);
    }

    private String projectName(String projectId,StringBuffer stringBuffer){
        ProjectEntity.ProjectTwo projectTwo = projectMapper.projectName(projectId);
        if(projectTwo.getProjectId().equals("0")){
            stringBuffer.insert(0,"-"+projectTwo.getProjectName());
            stringBuffer.delete(0,1);
            return stringBuffer.toString();
        }else {
            stringBuffer.insert(0,"-"+projectTwo.getProjectName());
           return projectName(projectTwo.getProjectId(),stringBuffer);
        }
    }

}
