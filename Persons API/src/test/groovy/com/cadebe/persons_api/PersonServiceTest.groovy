package groovy.com.cadebe.persons_api

import com.cadebe.persons_api.dao.PersonDaoMockImpl
import com.cadebe.persons_api.service.PersonService
import spock.lang.Specification
import spock.lang.Subject

class PersonServiceTest extends Specification {

    def personDao = new PersonDaoMockImpl()

    @Subject
    PersonService personService = new PersonService(personDao)

    def "PersonService getAllPersons()"() {
        given:

        when: "calling getAllPersons()"
        def result = personService.findAll()

        then: "getAllPersons() has been successfully called"
        result != null
        result.getClass() == ArrayList
    }

    def "PersonService getAllPersonsByColor()"() {
        given:

        when: "calling getAllPersonsByColor()"
        def result = personService.findAllByColorPreference(colorCode)

        then: "getAllPersonsByColor() has been successfully called"
        result != null
        result.size() > 0 == hasSomething
        result.getClass() == ArrayList

        where:
        colorCode | hasSomething
        1     | true
        14    | false
    }
}