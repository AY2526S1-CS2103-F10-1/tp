package seedu.address.ui;

import java.util.Comparator;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.address.MainApp;
import seedu.address.model.person.Person;

/**
 * A UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    private static final String FLAG_IMAGE_PATH = "/images/flag.png";
    private static final String FLAGGED_STYLE_CLASS = "flagged";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label otherphones;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane meetings;
    @FXML
    private Circle flag;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;

        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        otherphones.setText(person.getOtherPhones().numbers);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);

        setTagsUI();
        setMeetingsUI();

        toggleFlagUI(person);
    }

    public HBox getCardPane() {
        return cardPane;
    }

    public Circle getFlag() {
        return flag;
    }

    public FlowPane getMeetings() {
        return meetings;
    }

    public FlowPane getTags() {
        return tags;
    }

    public Label getId() {
        return id;
    }

    public Label getName() {
        return name;
    }

    public Label getPhone() {
        return phone;
    }

    public Label getOtherphones() {
        return otherphones;
    }

    public Label getAddress() {
        return address;
    }

    public Label getEmail() {
        return email;
    }

    /**
     * Populates the tags panel in the UI with the person's tags.
     */
    public void setTagsUI() {
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Populates the meetings panel in the UI with the person's meetings.
     * Each meeting is prefixed with its one-based index number.
     */
    public void setMeetingsUI() {
        IntStream.range(0, person.getMeetingCount())
                .forEach(i -> {
                    int oneBasedMeetingIndex = i + 1;
                    String meetingText = person.getMeetings().get(i).toString();
                    Label meetingLabel = new Label(String.format("%d. %s",
                            oneBasedMeetingIndex, meetingText));

                    meetings.getChildren().add(meetingLabel);
                });
    }

    /**
     * Toggles the flag UI based on the person's flag status.
     * If the person is flagged, shows the flag icon and applies
     * the "flagged" CSS style class; otherwise, hides the icon
     * and removes the style class.
     *
     * @param person The person whose flag status is used to update the UI.
     */
    public void toggleFlagUI(Person person) {
        if (person.isFlagged()) {
            Image image = new Image(MainApp.class.getResourceAsStream(FLAG_IMAGE_PATH));
            flag.setFill(new ImagePattern(image));
            flag.setVisible(true);

            cardPane.getStyleClass().add(FLAGGED_STYLE_CLASS);
        } else {
            flag.setVisible(false);
            cardPane.getStyleClass().removeAll(FLAGGED_STYLE_CLASS);
        }
    }

}
