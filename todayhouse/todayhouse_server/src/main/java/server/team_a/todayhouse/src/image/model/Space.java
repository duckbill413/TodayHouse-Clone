package server.team_a.todayhouse.src.image.model;

import lombok.Getter;

@Getter
public enum Space {
    LIVINGROOM("거실"),
    BEDROOM("침실"),
    KITCHEN("주방"),
    STUDY("서재&작업실"),
    BALCONY("베란다"),
    BATHROOM("욕실"),
    DRESSINGROOM("드레스룸"),
    FURNITURE("가구&소품");
    private String korean;
    Space(String korean){
        this.korean = korean;
    }
}
