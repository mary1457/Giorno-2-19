package mariapiabaldoin.Giorno_2_19.services;

import mariapiabaldoin.Giorno_2_19.entities.Dipendente;
import mariapiabaldoin.Giorno_2_19.exceptions.UnauthorizedException;
import mariapiabaldoin.Giorno_2_19.payloads.DipendenteLoginDTO;
import mariapiabaldoin.Giorno_2_19.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    @Autowired
    private DipendentiService dipendentiService;

    @Autowired
    private JWT jwt;

    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(DipendenteLoginDTO body) {

        Dipendente found = this.dipendentiService.findByEmail(body.email());

        if (bcrypt.matches(body.password(), found.getPassword())) {

            String accessToken = jwt.createToken(found);

            return accessToken;
        } else {

            throw new UnauthorizedException("Credenziali errate!");
        }
    }

}
