package com.massagedao.mapper;

import com.massagecommon.entity.ProjectEntity;
import com.massagecommon.entity.RangeDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectMapper {

    /**
     * 搜索  和  点击下一级
     *
     * @param projectName
     * @param parentId
     * @return java.util.List<com.massagecommon.entity.ProjectEntity>
     * @methodName projectList
     * @author WuShunag
     * @date 18:02
     */
    List<ProjectEntity> projectList(@Param("projectName") String projectName, @Param("parentId") String parentId);

    /**
     * 查询所有
     *
     * @param projectName
     * @return java.util.List<com.massagecommon.entity.ProjectEntity.ProjectOne>
     * @methodName selectProjectList
     * @author WuShunag
     * @date 18:43
     */
    List<ProjectEntity.ProjectOne> selectProjectList(@Param("projectName") String projectName);

    /**
     * 查询项目名称
     *
     * @param w
     * @return java.util.List<com.massagecommon.entity.ProjectEntity.ProjectOne>
     * @methodName selectProjectById
     * @author WuShunag
     * @date 19:20
     */
    List<ProjectEntity.ProjectOne> selectProjectById(@Param("w") String w);

    /**
     * 项目详情
     *
     * @param projectId
     * @return com.massagecommon.entity.ProjectEntity.ProjectDetails
     * @methodName projectDetails
     * @author WuShunag
     * @date 19:28
     */
    ProjectEntity.ProjectDetails projectDetails(@Param("projectId") String projectId);

    /**
     * 递归查询
     *
     * @param projectId
     * @return com.massagecommon.entity.ProjectEntity.ProjectTwo
     * @methodName projectName
     * @author WuShunag
     * @date 19:37
     */
    ProjectEntity.ProjectTwo projectName(@Param("projectId") String projectId);

    /**
     * 查询价格区间
     *
     * @param projectId
     * @return com.massagecommon.entity.ProjectEntity.ProjectTwo
     * @methodName projectName
     * @author WuShunag
     * @date 19:37
     */
    RangeDTO rangeList(@Param("price")double price, @Param("provincialId")String provincialId);
}
