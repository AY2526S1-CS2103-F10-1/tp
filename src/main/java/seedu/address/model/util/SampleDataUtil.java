package seedu.address.model.util;

import static seedu.address.model.person.FlagStatus.DEFAULT_FLAG_STATUS;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.OtherPhones;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final List<Meeting> EMPTY_MEETING_LIST = Collections.emptyList();

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"),
                    new OtherPhones("93217568"),
                    new Email("alexyeoh@example.com (personal) alexyeoh@work.com (work)"),
                new Address("Blk 30 Geylang Street 29, #06-40 (Home) Kent Ridge Drive 20, #09-11 (Work)"),
                getTagSet("boss"),
                EMPTY_MEETING_LIST, DEFAULT_FLAG_STATUS),
            new Person(new Name("Bernice Yu"), new Phone("99272758"),
                    new OtherPhones("93458679"),
                    new Email("berniceyu@work.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "team"),
                    EMPTY_MEETING_LIST, DEFAULT_FLAG_STATUS),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new OtherPhones("93756821"),
                    new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04 (Home)"),
                    getTagSet("client"),
                    EMPTY_MEETING_LIST, DEFAULT_FLAG_STATUS),
            new Person(new Name("David Li"), new Phone("91031282"),
                    new OtherPhones("97568321"),
                    new Email("lidavid@work.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("colleagues"),
                EMPTY_MEETING_LIST, DEFAULT_FLAG_STATUS),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new OtherPhones("93687543"),
                    new Email("irfan@work.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("colleagues"),
                EMPTY_MEETING_LIST, DEFAULT_FLAG_STATUS),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new OtherPhones("93756878"),
                    new Email("royb@work.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"),
                EMPTY_MEETING_LIST, DEFAULT_FLAG_STATUS)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
