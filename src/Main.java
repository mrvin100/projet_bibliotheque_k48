
import EmpruntDao;
import LivreDao;
import MembreDao;
import Emprunt;
import Livre;
import Membre;
import Emprunt.StatusEmprunt;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.UUID;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        MembreDao membreDao = new MembreDao();
        LivreDao livreDao = new LivreDao();
        EmpruntDao empruntDao = new EmpruntDao();

        Stream.of("Kamga", "Ndefo", "Ondoua", "Oumarou", "Fotso").forEach( name ->{
            Membre membre = new Membre();
            membre.setId((long) (Math.random()*1000));
            membre.setNom(name);
            membre.setPrenom(name+"cm");
            membre.setEmail(name+"@gmail.com");
            membre.setDateAdhesion(LocalDate.now());

            membreDao.saveMember(membre);
        });

        Membre membre1 = new Membre(109876L, "admin", "user", "adminuser@gmail.com", LocalDate.now());
        Livre livre1 = new Livre(12345L, "In step with my steps", "Jean Samuel Noutchogouin", "Story", 10);
        Emprunt emprunt1 = new Emprunt(1235L, LocalDate.now(), LocalDate.of(2025, 3, 12), null, 109876L, 12346L, StatusEmprunt.EN_COURS );
    }
}