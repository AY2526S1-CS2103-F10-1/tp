package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingName;
import seedu.address.model.meeting.Venue;
import seedu.address.model.meeting.When;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

@DisabledOnOs(OS.LINUX)
@ExtendWith(ApplicationExtension.class)
public class PersonCardTest {

    private static final int DISPLAY_INDEX_STUB = 1;
    private static final String DISPLAY_NAME_STUB = "Alice";
    private static final String FLAGGED_STYLE_CLASS = "flagged";

    private static final String MEETING_NAME_ONE = "meeting 1";
    private static final String MEETING_NAME_TWO = "meeting 2";
    private static final String VENUE_ONE = "venue 1";
    private static final String VENUE_TWO = "venue 2";
    private static final String WHEN_ONE = "2025-02-02 18:00";
    private static final String WHEN_TWO = "2025-02-02 18:00";

    private static final String TAG_ONE = "zebra";
    private static final String TAG_TWO = "alpha";
    private static final String TAG_THREE = "beta";

    private static final String PHONE_DISPLAY_NAME = "Main number:  ";
    private static final String OTHER_PHONES_DISPLAY_NAME = "Other numbers:  ";
    private static final String ADDRESSES_DISPLAY_NAME = "Addresses:  ";
    private static final String EMAILS_DISPLAY_NAME = "Emails:  ";

    private Meeting meetingOne;
    private Meeting meetingTwo;

    @BeforeEach
    public void setUp() {
        try {
            meetingOne = new Meeting(
                    new MeetingName(MEETING_NAME_ONE),
                    new Venue(VENUE_ONE),
                    new When(WHEN_ONE));
            meetingTwo = new Meeting(
                    new MeetingName(MEETING_NAME_TWO),
                    new Venue(VENUE_TWO),
                    new When(WHEN_TWO));
        } catch (ParseException e) {
            // Stub strings for When are valid, hence no exception would be thrown
        }
    }

    private void showCardInStage(FxRobot robot, PersonCard card) {
        robot.interact(() -> {
            Stage stage = new Stage();
            stage.setScene(new Scene(card.getRoot()));
            stage.show();
        });
    }

    @Test
    public void personCard_displaysAllPersonDetails(FxRobot robot) {
        PersonCard card = new PersonCard(TypicalPersons.AMY,
                INDEX_FIRST_PERSON.getOneBased());
        showCardInStage(robot, card);

        assertEquals(String.format("%d. ", INDEX_FIRST_PERSON.getOneBased()),
                card.getId().getText());
        assertEquals(TypicalPersons.AMY.getName().fullName,
                card.getName().getText());
        assertEquals(PHONE_DISPLAY_NAME + TypicalPersons.AMY.getPhone().value,
                card.getPhone().getText());
        assertEquals(OTHER_PHONES_DISPLAY_NAME + TypicalPersons.AMY.getOtherPhones().numbers,
                card.getOtherphones().getText());
        assertEquals(EMAILS_DISPLAY_NAME + TypicalPersons.AMY.getEmail().value,
                card.getEmail().getText());
        assertEquals(ADDRESSES_DISPLAY_NAME + TypicalPersons.AMY.getAddress().value,
                card.getAddress().getText());
    }

    @Test
    public void personCard_flagged_showsFlagIcon(FxRobot robot) {
        Person flaggedPerson = new PersonBuilder()
                .withName(DISPLAY_NAME_STUB)
                .withFlagStatus(true).build();
        PersonCard card = new PersonCard(flaggedPerson, DISPLAY_INDEX_STUB);
        showCardInStage(robot, card);

        assertTrue(card.getFlag().isVisible());
        assertTrue(card.getCardPane().getStyleClass().contains(FLAGGED_STYLE_CLASS));
    }

    @Test
    public void personCard_notFlagged_hidesFlagIcon(FxRobot robot) {
        Person unflaggedPerson = new PersonBuilder()
                .withName(DISPLAY_NAME_STUB)
                .withFlagStatus(false).build();
        PersonCard card = new PersonCard(unflaggedPerson, DISPLAY_INDEX_STUB);
        showCardInStage(robot, card);

        assertFalse(card.getFlag().isVisible());
        assertFalse(card.getCardPane().getStyleClass().contains(FLAGGED_STYLE_CLASS));
    }

    @Test
    public void personCard_hasTags_showsTagsInSortedOrder(FxRobot robot) {
        Person person = new PersonBuilder()
                .withName(DISPLAY_NAME_STUB)
                .withTags(TAG_ONE, TAG_TWO, TAG_THREE)
                .build();
        PersonCard card = new PersonCard(person, DISPLAY_INDEX_STUB);
        showCardInStage(robot, card);

        List<String> tagTexts = card.getTags().getChildren().stream()
                .map(node -> ((Label) node).getText())
                .toList();

        // Check that tags are sorted in alphabetical order
        assertEquals(List.of(TAG_TWO, TAG_THREE, TAG_ONE), tagTexts);
    }

    @Test
    public void personCard_hasTwoMeetings_showsCorrectMeetingDisplayIndices(FxRobot robot) {
        Person personWithMeeting = new PersonBuilder()
                .withName(DISPLAY_NAME_STUB)
                .withMeetings(meetingOne, meetingTwo)
                .build();
        PersonCard card = new PersonCard(personWithMeeting, DISPLAY_INDEX_STUB);
        showCardInStage(robot, card);

        Label meetingOneLabel = (Label) card
                .getMeetings()
                .getChildren()
                .get(INDEX_FIRST_MEETING.getZeroBased());
        Label meetingTwoLabel = (Label) card
                .getMeetings()
                .getChildren()
                .get(INDEX_SECOND_MEETING.getZeroBased());

        assertEquals(String.format("%d. %s", INDEX_FIRST_MEETING.getOneBased(), meetingOne),
                meetingOneLabel.getText());
        assertEquals(String.format("%d. %s", INDEX_SECOND_MEETING.getOneBased(), meetingTwo),
                meetingTwoLabel.getText());
    }
}
