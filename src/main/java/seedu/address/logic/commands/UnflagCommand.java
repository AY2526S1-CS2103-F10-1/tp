package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.EditCommand.createEditedPerson;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.FlagStatus;
import seedu.address.model.person.Person;

/**
 * Unflags a person identified using its displayed index from the address book.
 */
public class UnflagCommand extends Command {
    public static final String COMMAND_WORD = "unflag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unflags the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNFLAG_PERSON_SUCCESS = "Unflagged Person: %1$s";
    public static final String MESSAGE_INVALID_INDEX = "The index given is invalid.\n"
            + "Either the person list is empty, or the index is out of range of the displayed person list.";
    public static final String MESSAGE_ALREADY_UNFLAGGED = "This person is already unflagged.";
    private static final Boolean UNFLAGGED_STATUS = false;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Index targetIndex;

    /**
     * Constructs a {@code UnflagCommand} to unflag the person at the given index.
     * <p>
     * The {@code targetIndex} is guaranteed to be valid and non-negative,
     * as input validation is handled by {@code UnflagCommandParser}.
     *
     * @param targetIndex Index of the person in the displayed list to unflag.
     */
    public UnflagCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        assert(targetIndex.getZeroBased() >= 0) : "Index in UnflagCommand "
                + "is supposed to be non-negative!";

        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToUnflag = validateAndGetPerson(model);
        Person unflaggedPerson = unflagPerson(model, personToUnflag);

        // log the unflagging transition to ensure unflagging took place
        logger.info(String.format("%s flag status changed (%b -> %b)",
                unflaggedPerson.getName(),
                personToUnflag.isFlagged(),
                unflaggedPerson.isFlagged()));

        return new CommandResult(String.format(MESSAGE_UNFLAG_PERSON_SUCCESS,
                Messages.format(unflaggedPerson)));
    }

    /**
     * Validates the target index and returns the corresponding {@code Person}.
     * <p>
     * Ensures the index is valid and that the person is not unflagged already.
     *
     * @return The {@code Person} to unflag.
     * @throws CommandException If the index is invalid.
     */
    private Person validateAndGetPerson(Model model) throws CommandException {
        List<Person> lastShownList = model.getPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        Person person = lastShownList.get(targetIndex.getZeroBased());
        if (!person.isFlagged()) {
            throw new CommandException(MESSAGE_ALREADY_UNFLAGGED);
        }

        return person;
    }

    /**
     * Creates an unflagged copy of the given person and updates the model.
     *
     * @return The unflagged {@code Person}.
     */
    private Person unflagPerson(Model model, Person personToUnflag) {
        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        descriptor.setFlagStatus(new FlagStatus(UNFLAGGED_STATUS));
        Person unflaggedPerson = createEditedPerson(personToUnflag, descriptor);

        model.setPerson(personToUnflag, unflaggedPerson);

        return unflaggedPerson;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnflagCommand)) {
            return false;
        }

        UnflagCommand otherUnflagPersonCommand = (UnflagCommand) other;
        return targetIndex.equals(otherUnflagPersonCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}

