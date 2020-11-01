package com.massageservice.service;

public interface ProjectService {
    /**
     *  项目列表
     *
     * @param
     * @param projectName
     * @param parentId
     * @return java.lang.String
     * @methodName projectList
     * @author WuShunag
     * @date 17:36
     */
    String projectList(String projectName, String parentId);

    /**
     *  查询项目
     *
     * @param projectName
     * @return java.lang.String
     * @methodName selectProjectList
     * @author WuShunag
     * @date 18:12
     */
    String selectProjectList(String projectName);

    /**
     *  项目详情
     *
     * @param projectId
     * @return java.lang.String
     * @methodName projectDetails
     * @author WuShunag
     * @date 19:27
     */
    String projectDetails(String projectId);
    /**
     *  价格区间
     *
     * @param
     * @return java.lang.String
     * @methodName projectDetails
     * @author WuShunag
     * @date 19:27
     */
    String rangeList(double price, String provincialId);
}
