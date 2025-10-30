package seedu.address.list;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.model.person.Person;

/**
 * A list of persons that can be filtered and sorted.
 */
public class PersonList {
    private static final Logger logger = Logger.getLogger(PersonList.class.getName());
    private final FilteredList<Person> filteredPersons;

    /**
     * Creates a PersonList with the given list of persons.
     *
     * @param persons The list of persons.
     */
    public PersonList(ObservableList<Person> persons) {
        requireNonNull(persons);

        this.filteredPersons = new FilteredList<>(persons);
    }

    /**
     * Updates the filter of the person list.
     *
     * @param predicate A vararg of predicates to filter the persons.
     */
    public void updatePersonListFilter(Predicate<Person>... predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(andAll(predicate));
    }

    /**
     * Returns a predicate after combining all the predicates using the and operator.
     * @param predicates a vararg of predicates
     * @return predicate combining all predicates into a single statement
     */
    @SafeVarargs
    public static Predicate<Person> andAll(Predicate<Person>... predicates) {
        Predicate<Person> acc = t -> true;

        assert predicates != null : "Predicates should not be null";

        for (Predicate<Person> p : predicates) {
            if (p != null) {
                acc = acc.and(p::test);
            }
        }
        return acc;
    }

    /**
     * Returns a sorted view of the filtered persons,
     * ordered by flag status and then by their original position.
     *
     * @return sorted observable list of persons
     */
    public ObservableList<Person> getPersonList() {
        logger.fine(() -> "Person list (pre-sort, sample): " + filteredPersons.stream()
                .limit(5)
                .map(p -> p.getName().toString())
                .toList());

        ObservableList<Person> result = new SortedList<>(filteredPersons,
                Comparator.comparing(Person::getFlagStatus)
                        .thenComparing(p -> filteredPersons.getSource().indexOf(p))
        );

        logger.fine(() -> "Person list (post-sort, sample): " + result.stream()
                .limit(5)
                .map(p -> p.getName().toString())
                .toList());

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
