package com.covenant.springbootmysql.model.request;

import lombok.Data;

// lombok에서 지원하는 어노테이션이다.
// @Data는 Getter, Setter, RequiredArgsConstructor, ToString, EqualsAndHashCode 어노테이션을 포함한다.
@Data
public class AuthorCreationRequest {
    private String firstName;
    private String lastName;
}
