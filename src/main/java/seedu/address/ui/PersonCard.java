package seedu.address.ui;

import java.util.Comparator;

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
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String FLAG_IMAGE_PATH = "/images/flag.png";

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
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
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
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getMeetings().stream()
                .forEach(meeting -> meetings.getChildren().add(new Label(meeting.toString())));

        toggleFlagUI(person);
    }

    public HBox getCardPane() {
        return cardPane;
    }

    public Circle getFlag() {
        return flag;
    }

    /**
     * Toggles the flag UI based on the person's flag status.
     */
    public void toggleFlagUI(Person person) {
        if (person.isFlagged()) {
            Image image = new Image(MainApp.class.getResourceAsStream(FLAG_IMAGE_PATH));
            flag.setFill(new ImagePattern(image));
            flag.setVisible(true);
            cardPane.getStyleClass().add("flagged");
        } else {
            flag.setVisible(false);
            cardPane.getStyleClass().removeAll("flagged");
        }
    }

}
