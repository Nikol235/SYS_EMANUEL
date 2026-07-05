package pe.edu.upeu.colegio_api.dto;

import lombok.Data;

@Data
public class GradeLevelResponseDTO {
    private Long id;
    private String name;
    private String section;
    private String color;
    private String photoUrl;
    private PeriodInfo period;
    private TutorInfo tutor;
    private Long memberCount;

    @Data
    public static class PeriodInfo {
        private Long id;
        private String name;
    }

    @Data
    public static class TutorInfo {
        private Long id;
        private String fullName;
        private String photoUrl;
    }
}
