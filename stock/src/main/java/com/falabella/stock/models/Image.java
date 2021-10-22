package com.falabella.stock.models;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="images")
@Builder
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank()
    @URL(message = "El formato de la url no es adecuado")
    private String url;

    @Enumerated(EnumType.STRING)
    private ImageType type;

    @Tolerate
    public Image() {}
}
