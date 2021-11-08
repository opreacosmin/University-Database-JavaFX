package map.repo;
import map.NullValueException;
import map.domain.Person;

import java.util.List;

public class PersonRepo extends InMemoryRepo<Person> {

    public PersonRepo() {
        super();
    }

    @Override
    public List<Person> getAll(){
        return repoList;

    }
    @Override
    public Person create(Person obj) throws NullValueException {
        if (obj == null)
            throw new NullValueException("Invalid person object");
        for (Person person : repoList)
            if (person.getID() == obj.getID())
                return obj;
        repoList.add(obj);
        return null;
    }

    @Override
    public Person update(Person obj) throws  NullValueException {
        if (obj == null)
            throw new NullValueException("Invalid person object");
        for (Person person : repoList)
            if (person.getID() == obj.getID()) {
                repoList.remove(person);
                repoList.add(obj);
                return null;
            }
        return obj;
    }

    @Override
    public Person delete(Long id) throws NullValueException {
        if (id == null)
            throw new NullValueException("Invalid person object");
        for (Person person : repoList)
            if (person.getID()== id) {
                repoList.remove(person);
                return person;
            }
        return null;
    }
}
