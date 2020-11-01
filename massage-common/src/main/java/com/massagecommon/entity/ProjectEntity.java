package com.massagecommon.entity;

import lombok.Data;

import java.util.List;

/**
 * @author:WuShuang
 * @date:2020/5/3
 * @ver:1.0
 **/
@Data
public class ProjectEntity {
    private String projectId;
    private String projectName;
    private String projectImg;
    @Data
    public static class ProjectDetails {
        private String rotationVideo;
        private String rotationChart;
        private String text;
        private String price;
        private String everyTimes;
        private String totalTimes;
        private String projectName;
    }

    @Data
    public static class ProjectOne{
        private String projectId;
        private String projectName;
        private String projectImg;
    }
    @Data
    public static class ProjectTwo {
        private String projectId;
        private String projectName;
        private String projectImg;
        private List<ProjectThree> projectThrees;
    }

    @Data
    public static class ProjectThree{
        private String projectId;
        private String projectName;
        private String projectImg;
        private List<ProjectFour> projectFours;
    }
    @Data
    public static class ProjectFour {
        private String projectId;
        private String projectName;
        private String projectImg;
        private String rotationChart;
        private String rotationVideo;
        private String totalTimes;
        private String everyTimes;
        private String text;
        private double price;
    }

    }
