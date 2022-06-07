package hu.progmasters.conference.domain;

public enum AcademicRank {
    PROFESSOR_EMERITUS("Professor emeritus"),
    PROFESSOR("Professor"),
    ASSOCIATE_PROFESSOR("Associate professor"),
    SENIOR_LECTURER("Senior lecturer"),
    ASSISTANT_LECTURER("Assistant lecturer"),
    SENIOR_RESEARCH_FELLOW("Senior research fellow"),
    RESEARCH_FELLOW("Research fellow"),
    ASSISTANT_RESEARCH_FELLOW("Assistant research fellow"),
    SCIENTIFIC_ADVISOR("Scientific advisor"),
    RESEARCH_PROFESSOR("Research professor"),
    CANDIDATE("Candidate");

    private final String value;

    AcademicRank(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
