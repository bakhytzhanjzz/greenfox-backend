package com.greenfox.backend.entity;

import com.greenfox.backend.entity.embeddable.ContactInfo;
import com.greenfox.backend.entity.embeddable.Coordinates;
import com.greenfox.backend.entity.enums.Amenity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "resorts",
        indexes = {
                @Index(name = "idx_resorts_location", columnList = "location"),
                @Index(name = "idx_resorts_rating", columnList = "rating_avg"),
                @Index(name = "idx_resorts_price", columnList = "price_per_night")
        })
public class Resort extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_resort_owner"))
    private User owner; // PARTNER-владелец (см. раздел «управление курортами (для партнёров)») :contentReference[oaicite:4]{index=4}

    @NotBlank
    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String location; // можно позже разбить на регион/область/район

    @NotNull
    @DecimalMin("0.0")
    @Column(name = "price_per_night", nullable = false, precision = 12, scale = 2)
    private BigDecimal pricePerNight;

    // для быстрых сортировок/фильтров из каталога храним агрегаты
    @Column(name = "rating_avg", nullable = false)
    private double ratingAverage = 0.0;

    @Column(name = "rating_count", nullable = false)
    private long ratingCount = 0;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "resort_images",
            joinColumns = @JoinColumn(name = "resort_id",
                    foreignKey = @ForeignKey(name = "fk_resort_images_resort")))
    @Column(name = "image_url", nullable = false, length = 1000)
    private List<String> images;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "resort_amenities",
            joinColumns = @JoinColumn(name = "resort_id",
                    foreignKey = @ForeignKey(name = "fk_resort_amenities_resort")))
    @Enumerated(EnumType.STRING)
    @Column(name = "amenity", nullable = false, length = 50)
    private Set<Amenity> amenities;

    @Embedded
    private ContactInfo contactInfo; // phone, email, website

    @Column(columnDefinition = "text")
    private String policies;

    @Embedded
    private Coordinates coordinates;
}
