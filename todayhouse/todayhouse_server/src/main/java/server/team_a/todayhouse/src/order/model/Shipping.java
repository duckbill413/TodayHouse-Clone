package server.team_a.todayhouse.src.order.model;

public enum Shipping {
    NOT_START("배송 시작전"),
    MOVING("배송중"),
    SHIPPED("배송 완료");
    private String korean;
    Shipping(String korean){
        this.korean = korean;
    }
}
