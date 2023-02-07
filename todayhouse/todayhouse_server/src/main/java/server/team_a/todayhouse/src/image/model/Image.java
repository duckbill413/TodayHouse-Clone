package server.team_a.todayhouse.src.image.model;

import lombok.*;
import server.team_a.todayhouse.src.base.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class Image extends BaseEntity{
    private long sequence;
    @Column(length = 500, nullable = false)
    private String imageUrl;
    private boolean deleted;
    public void changeImage(String imageUrl){
        this.imageUrl = imageUrl;
    }
}
