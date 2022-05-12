package com.github.graschenko.model;

import com.github.graschenko.util.validation.NoHtml;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractNamedEntity extends AbstractBaseEntity {

    @NotBlank
    @Size(min = 2, max = 128)
    @Column(name = "name", nullable = false)
    @NoHtml
    protected String name;

    public AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + "(" + name + ")";
    }
}
