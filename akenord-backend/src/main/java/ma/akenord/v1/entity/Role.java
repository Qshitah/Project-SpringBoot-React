package ma.akenord.v1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @NotNull(message = "Role Name is required")
    @Size(min = 3,max = 30,message = "Role Name length must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z]{3,30}$",message = "Role Name must contain only letters")
    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users ;

}
