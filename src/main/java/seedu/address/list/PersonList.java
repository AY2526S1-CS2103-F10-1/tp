package seedu.address.list;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.model.person.Person;

public class PersonList {
    private static final Logger logger = Logger.getLogger(PersonList.class.getName());
    private final FilteredList<Person> filteredPersons;

    public PersonList(ObservableList<Person> persons) {
        requireNonNull(persons);

        this.filteredPersons = new FilteredList<>(persons);
    }

    public void updatePersonListFilter(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    public ObservableList<Person> getPersonList() {
        logger.info(filteredPersons.stream().map(p -> p.getName()).toList().toString());
        ObservableList<Person> result = new SortedList<>(filteredPersons,
                Comparator.comparing(Person::getFlagStatus)
                        .thenComparing(p -> filteredPersons.getSource().indexOf(p))
        );
        logger.info(result.stream().map(p -> p.getName()).toList().toString());
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonList)) {
            return false;
        }

        PersonList otherPersonList = (PersonList) other;
        return getPersonList().equals(otherPersonList.getPersonList());
    }


}
