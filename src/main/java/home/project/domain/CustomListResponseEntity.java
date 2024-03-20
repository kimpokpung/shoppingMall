package home.project.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpHeaders;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class CustomListResponseEntity<T> extends ResponseEntity<CustomListResponseBody<T>>{
    public CustomListResponseEntity(CustomListResponseBody<T> body, HttpStatus status) {
        super(body, new HttpHeaders(), status);
    }

    public CustomListResponseEntity(List<T> listData, String message, HttpStatus status) {
        super(new CustomListResponseBody<>(listData, message), new HttpHeaders(), status);
    }
}