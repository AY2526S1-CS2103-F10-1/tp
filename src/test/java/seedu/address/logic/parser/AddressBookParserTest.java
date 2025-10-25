package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WHEN;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddMeetingCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteMeetingCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditMeetingCommand;
import seedu.address.logic.commands.EditMeetingCommand.EditMeetingDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindMeetingCommand;
import seedu.address.logic.commands.FlagCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.UnflagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.MeetingNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + PREFIX_NAME + keywords.stream().collect(Collectors.joining(" ")));
        List<Predicate<Person>> predicateList = new ArrayList<>();
        predicateList.add(new NameContainsKeywordsPredicate(keywords));
        assertTrue(command.equals(new FindCommand(predicateList)));
    }

    @Test
    public void parseCommand_findMeeting() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindMeetingCommand command = (FindMeetingCommand) parser.parseCommand(
                FindMeetingCommand.COMMAND_WORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindMeetingCommand(new MeetingNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_addMeeting() throws Exception {
        final String meetingName = "Some meeting";
        final String venue = "Some venue";
        final String datetime = "2025-10-11 1400";
        final Meeting meeting = new Meeting(meetingName, venue, datetime);
        AddMeetingCommand command = (AddMeetingCommand) parser.parseCommand(AddMeetingCommand.COMMAND_WORD
                + " " + PREFIX_PERSON_INDEX + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_MEETING + meetingName
                + " " + PREFIX_VENUE + venue + " " + PREFIX_WHEN + datetime);
        assertEquals(new AddMeetingCommand(INDEX_FIRST_PERSON, meeting), command);
    }

    @Test
    public void parseCommand_editMeeting() throws Exception {
        final String updatedMeetingName = "Some meeting";
        final String updatedVenue = "Some venue";
        final String updatedDatetime = "2025-10-11 1400";
        final Meeting updatedMeeting = new Meeting(updatedMeetingName, updatedVenue, updatedDatetime);
        EditMeetingDescriptor descriptor = new EditMeetingDescriptor();
        descriptor.setMeetingName(updatedMeeting.getMeetingName());
        descriptor.setVenue(updatedMeeting.getVenue());
        descriptor.setWhen(updatedMeeting.getWhen());
        EditMeetingCommand command = (EditMeetingCommand) parser.parseCommand(EditMeetingCommand.COMMAND_WORD
                + " " + PREFIX_PERSON_INDEX + INDEX_FIRST_PERSON.getOneBased()
                + " " + PREFIX_MEETING_INDEX + INDEX_FIRST_MEETING.getOneBased()
                + " " + PREFIX_MEETING + updatedMeetingName + " " + PREFIX_VENUE + updatedVenue
                + " " + PREFIX_WHEN + updatedDatetime);
        assertEquals(new EditMeetingCommand(INDEX_FIRST_PERSON, INDEX_FIRST_MEETING, descriptor), command);
    }

    @Test
    public void parseCommand_deleteMeeting() throws Exception {
        DeleteMeetingCommand command = (DeleteMeetingCommand) parser.parseCommand(
                DeleteMeetingCommand.COMMAND_WORD + " "
                        + PREFIX_PERSON_INDEX + INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_MEETING_INDEX + INDEX_FIRST_MEETING.getOneBased());
        assertEquals(new DeleteMeetingCommand(INDEX_FIRST_PERSON, INDEX_FIRST_MEETING), command);
    }

    @Test
    public void parseCommand_flag() throws Exception {
        FlagCommand command = (FlagCommand) parser.parseCommand(
                FlagCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new FlagCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_unflag() throws Exception {
        UnflagCommand command = (UnflagCommand) parser.parseCommand(
                UnflagCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new UnflagCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
