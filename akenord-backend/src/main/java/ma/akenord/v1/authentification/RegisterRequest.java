package ma.akenord.v1.authentification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Date birthDate;

    private boolean enabled;

    private String email;

    private String password;

}
