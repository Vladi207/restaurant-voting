package com.github.graschenko.to;

import com.github.graschenko.HasIdAndEmail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserTo extends NamedTo implements HasIdAndEmail, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Email
    @Size(max = 128)
    private String email;

    @NotBlank
    @Size(min = 5, max = 128)
    private String password;

    public UserTo(Integer id, String name, String email, String password) {
        super(id, name);
        this.email = email;
        this.password = password;
    }
}
