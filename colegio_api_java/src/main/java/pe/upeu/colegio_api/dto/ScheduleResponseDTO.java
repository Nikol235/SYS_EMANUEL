package pe.edu.upeu.colegio_api.dto;

import lombok.Data;

@Data
public class ScheduleResponseDTO {
    private Long id;
    private Integer dayOfWeek;
    private String startTime;
    private String endTime;
    private String subject;
    private String color;

    private GradeLevelInfo gradeLevel;
    private TeacherInfo teacher;

    @Data
    public static class GradeLevelInfo {
        private Long id;
        private String name;
        private String section;
    }

    @Data
    public static class TeacherInfo {
        private Long id;
        private String fullName;
    }
}
