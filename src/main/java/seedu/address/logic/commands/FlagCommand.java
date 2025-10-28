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
 * Flags a person identified using its displayed index from the address book.
 */
public class FlagCommand extends Command {
    public static final String COMMAND_WORD = "flag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Flags the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FLAG_PERSON_SUCCESS = "Flagged Person: %1$s";
    public static final String MESSAGE_INVALID_INDEX = "The index given is invalid.\n"
            + "Either the person list is empty, or the index is out of range of the displayed person list.";
    public static final String MESSAGE_ALREADY_FLAGGED = "This person is already flagged.";
    private static final Boolean FLAGGED_STATUS = true;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Index targetIndex;

    /**
     * Constructs a {@code FlagCommand} to flag the person at the given index.
     * <p>
     * The {@code targetIndex} is guaranteed to be valid and non-negative,
     * as input validation is handled by {@code FlagCommandParser}.
     *
     * @param targetIndex Index of the person in the displayed list to flag.
     */
    public FlagCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        assert(targetIndex.getZeroBased() >= 0) : ": Index in FlagCommand "
                + "is supposed to be non-negative!";

        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToFlag = validateAndGetPerson(model);
        Person flaggedPerson = flagPerson(personToFlag, model);

        // log the flagging transition to ensure flagging took place
        logger.info(String.format("%s flag status changed (%b -> %b)",
                flaggedPerson.getName(),
                personToFlag.isFlagged(),
                flaggedPerson.isFlagged()));

        return new CommandResult(String.format(MESSAGE_FLAG_PERSON_SUCCESS, Messages.format(flaggedPerson)));
    }

    /**
     * Validates the target index and returns the corresponding {@code Person}.
     * <p>
     * Ensures the index is valid and that the person is not flagged already.
     *
     * @return The {@code Person} to flag.
     * @throws CommandException If the index is invalid.
     */
    private Person validateAndGetPerson(Model model) throws CommandException {
        List<Person> lastShownList = model.getPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        Person personToFlag = lastShownList.get(targetIndex.getZeroBased());

        if (personToFlag.isFlagged()) {
            throw new CommandException(MESSAGE_ALREADY_FLAGGED);
        }

        return personToFlag;
    }

    /**
     * Creates a flagged copy of the given person and updates the model.
     *
     * @return The flagged {@code Person}.
     */
    private Person flagPerson(Person personToFlag, Model model) {
        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        descriptor.setFlagStatus(new FlagStatus(FLAGGED_STATUS));
        Person flaggedPerson = createEditedPerson(personToFlag, descriptor);

        model.setPerson(personToFlag, flaggedPerson);

        return flaggedPerson;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FlagCommand)) {
            return false;
        }

        FlagCommand otherFlagPersonCommand = (FlagCommand) other;
        return targetIndex.equals(otherFlagPersonCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
