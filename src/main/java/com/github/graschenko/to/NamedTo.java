package com.github.graschenko.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class NamedTo extends BaseTo{

    @NotBlank
    @Size(min = 2, max = 128)
    protected String name;

    public NamedTo(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
