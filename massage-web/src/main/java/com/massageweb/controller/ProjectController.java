package com.massageweb.controller;

import com.massageservice.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author:WuShuang
 * @date:2020/5/3
 * @ver:1.0
 **/
@RestController
@RequestMapping("/api/project/")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * 项目列表
     *
     *   parentId （父类id  当一级的时候 传入0）
     * 
     * @param  
     * @return java.lang.String
     * @methodName projectList
     * @author WuShunag
     * @date 17:31 
     */
    @GetMapping("projectList")
    public String projectList(@RequestParam(value = "projectName",required = false)String projectName,@RequestParam(value = "parentId",required = false) String parentId){
        return projectService.projectList(projectName,parentId);
    }

    /**
     *  项目查询
     *
     *   projectName （项目名称）
     *
     * @param projectName
     * @return java.lang.String
     * @methodName selectProjectList
     * @author WuShunag
     * @date 21:06
     */
    @GetMapping("selectProjectList")
    public String selectProjectList(String projectName){
        return projectService.selectProjectList(projectName);
    }


    /**
     *  项目详情
     *
     *  projectId （项目id）
     *
     * @param
     * @return java.lang.String
     * @methodName projectDetails
     * @author WuShunag
     * @date 19:27
     */
    @GetMapping("projectDetails")
    public String projectDetails(String projectId){
        return projectService.projectDetails(projectId);
    }


    /**
     *  获取价格区间
     *
     * @param
     * @return java.lang.String
     * @methodName projectDetails
     * @author WuShunag
     * @date 19:27
     */
    @PostMapping("rangeList")
    public String rangeList(double price,String provincialId){
        return projectService.rangeList(price,provincialId);
    }
}
