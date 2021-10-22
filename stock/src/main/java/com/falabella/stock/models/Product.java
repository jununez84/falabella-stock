package com.falabella.stock.models;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;


@Entity
@Table(name = "products")
@Builder
@Data
@SequenceGenerator(name="my_seq",sequenceName="MY_SEQ", initialValue=1_000_000, allocationSize=1)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column(unique = true)
    private String sku;

    @NotBlank(message = "Nombre es requerido")
    @Size(min = 3, message = "Nombre debe contener 3 o más caracteres")
    @Size(max = 50, message = "Nombre debe contener máximo 50 caracteres")
    private String name;

    @NotBlank(message = "Marca es requerido")
    @Size(min = 3, message = "La marca debe contener 3 o más caracteres")
    @Size(max = 50, message = "La marca debe contener máximo 50 caracteres")
    private String brand;

    private int size;

    @NotNull(message = "Precio es requerido")
    @DecimalMin(value = "1.00", message = "El precio minimo debe ser $1.00")
    @DecimalMax(value = "99999999.00", message = "El precio maximo debe ser $99999999.00")
    private double price;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="produc_id", nullable=false)
    private List<Image> images;

    @Tolerate
    public Product() {}

}
