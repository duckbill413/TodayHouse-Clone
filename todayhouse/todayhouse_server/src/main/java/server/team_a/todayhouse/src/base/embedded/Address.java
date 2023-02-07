package server.team_a.todayhouse.src.base.embedded;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Column(length = 150)
    private String address;
    @Column(length = 10)
    private String zipcode;
}
