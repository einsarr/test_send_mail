package sendmail.sendmail.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sendmail.sendmail.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
}
