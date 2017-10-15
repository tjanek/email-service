package pl.tjanek.email

import pl.tjanek.email.endpoint.Contact
import pl.tjanek.email.endpoint.EmailMessage

class EmailMessageBuilder {

    static final Contact JOHN_DOE = new Contact('John Doe', 'john.doe@company.com')
    static final Contact JANE_DOE = new Contact('Jane Doe', 'jane.doe@company.com')

    static EmailMessage anEmailMessage(Contact from, Contact to, String subject, String message) {
        return new EmailMessage(from, to, subject, message)
    }

    static EmailMessage anEmailMessage() {
        return anEmailMessage(JOHN_DOE, JANE_DOE, 'Some subject', 'Some message')
    }
}
