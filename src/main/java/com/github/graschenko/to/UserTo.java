package com.github.graschenko.to;

import com.github.graschenko.HasIdAndEmail;
import com.github.graschenko.util.validation.NoHtml;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Value
@EqualsAndHashCode(callSuper = true)
public class UserTo extends NamedTo implements HasIdAndEmail, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Email
    @Size(max = 128)
    @NoHtml
    String email;

    @NotBlank
    @Size(min = 5, max = 128)
    String password;

    public UserTo(Integer id, String name, String email, String password) {
        super(id, name);
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserTo:" + id + '[' + email + ']';
    }
}
