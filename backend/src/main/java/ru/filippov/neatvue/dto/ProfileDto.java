package ru.filippov.neatvue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.Authentication;
import ru.filippov.neatvue.service.user.UserPrinciple;
import ru.filippov.utils.UtilBase64Image;


@Data
@AllArgsConstructor
public class ProfileDto {

    private final String firstname;
    private final String lastname;
    private final String email;
    private final String username;
    private final ImageDto avatar;

    public static ProfileDto build(Authentication authentication){

        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
        return new ProfileDto(
                userPrincipal.getFirstname(),
                userPrincipal.getLastname(),
                userPrincipal.getUsername(),
                userPrincipal.getEmail(),
                new ImageDto(UtilBase64Image.encoder(userPrincipal.getAvatar()))
        );
    }
    //private final static String type = "Bearer";

}