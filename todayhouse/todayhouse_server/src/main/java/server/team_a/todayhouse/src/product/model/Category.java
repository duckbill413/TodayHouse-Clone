package server.team_a.todayhouse.src.product.model;

import lombok.Getter;

@Getter
public enum Category {
    ALL("전체상품"),
    FURNITURE("가구"),
    FABRIC("패브릭"),
    DIGITAL("가전·디지털"),
    KITCHEN("주방용품"),
    FOOD("식품"),
    DECO("데코·식물"),
    LAMP("조명"),
    STORAGE("수납·정리"),
    SUPPLY("생활용품"),
    NECESSARY("생필품"),
    KID("유아·아동"),
    PET("반려동물"),
    INDOOR("실내운동"),
    OUTDOOR("캠핑용품"),
    DIY("공구·DIY"),
    INTERIOR("인테리어시공"),
    RENTAL("렌탈");

    String korean;

    Category(String korean) {
        this.korean = korean;
    }
}
