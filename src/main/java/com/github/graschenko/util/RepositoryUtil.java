package com.github.graschenko.util;

import com.github.graschenko.util.exception.IllegalRequestDataException;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.repository.JpaRepository;

@UtilityClass
public class RepositoryUtil {

    public static <T, K extends Integer> T findById(JpaRepository<T, K> repository, K id) {
        return repository.findById(id).orElseThrow(
                () -> new IllegalRequestDataException("Restaurant with id=" + id + " not found"));
    }
}
