package com.samplecompany.teapot.authorization.model;


import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.samplecompany.teapot.authorization.entity.Addon;

@Data @NoArgsConstructor
public class AddonDTO implements Serializable {
    private static final long serialVersionUID = 5325717450024637040L;

    private Long id;
    private String code;
    private Integer quantity;

    public AddonDTO(Addon addon) {
        this.id = addon.getId();
        this.code = addon.getCode();
        this.quantity = addon.getQuantity();
    }

    public Addon toAddon() {
        Addon addon = new Addon();
        addon.setCode(getCode());
        addon.setQuantity(getQuantity());
        return addon;
    }
}
