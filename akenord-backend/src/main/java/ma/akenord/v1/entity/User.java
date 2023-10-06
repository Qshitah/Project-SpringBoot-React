package ma.akenord.v1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @NotNull(message = "Username is required")
    @Size(min = 3,max = 20,message = "Username length must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$",message = "Username must contain only letters, numbers, underscores, and hyphens")
    @Column(name = "username")
    private String username;

    @NotNull(message = "Email is required")
    @Size(min = 1, message = "Email is required")
    @Email(message = "Email is not valid", regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 6, message = "Password Length more than 6 characters")
    @Column(name = "password")
    private String password;

    @NotNull(message = "First Name is required")
    @Size(min = 1, message = "First Name is required")
    @Column(name = "first_name")
    private String first_name;

    @NotNull(message = "Last Name is required")
    @Size(min = 1, message = "Last Name is required")
    @Column(name = "last_name")
    private String last_name;

    @Column(name = "phone_number")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phone_number;

    @NotNull(message = "Birth Date is required")
    @Column(name = "birth_date")
    private Date birth_date;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @NotNull(message = "Enabled is required")
    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,
            CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "username_user" ),
            inverseJoinColumns = @JoinColumn(name = "name_role")
    )
    private Set<Role> roles;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())) // Prefix roles with "ROLE_" as a convention
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
