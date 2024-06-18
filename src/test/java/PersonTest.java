import com.gifty.application.data.gift.Gift;
import com.gifty.application.data.person.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class PersonTest {

    private Person person;
    private UUID id;
    private String name;

    @BeforeEach
    public void setUp() {
        id = UUID.randomUUID();
        name = "John Doe";
        person = new Person(name);
        person.setId(id);
    }

    @Test
    public void testPersonCreation() {
        assertNotNull(person);
        assertEquals(name, person.getName());
        assertEquals(id, person.getId());
    }

    @Test
    public void testSetName() {
        String newName = "Jane Doe";
        person.setName(newName);
        assertEquals(newName, person.getName());
    }

    @Test
    public void testSetId() {
        UUID newId = UUID.randomUUID();
        person.setId(newId);
        assertEquals(newId, person.getId());
    }

    @Test
    public void testSetGifts() {
        List<Gift> gifts = new ArrayList<>();
        Gift gift = mock(Gift.class);
        gifts.add(gift);
        person.setGifts(gifts);
        assertEquals(gifts, person.getGifts());
    }

    @Test
    public void testGetGifts() {
        List<Gift> gifts = new ArrayList<>();
        Gift gift1 = mock(Gift.class);
        Gift gift2 = mock(Gift.class);
        gifts.add(gift1);
        gifts.add(gift2);
        person.setGifts(gifts);
        List<Gift> retrievedGifts = person.getGifts();
        assertNotNull(retrievedGifts);
        assertEquals(2, retrievedGifts.size());
        assertTrue(retrievedGifts.contains(gift1));
        assertTrue(retrievedGifts.contains(gift2));
    }

    @Test
    public void testEqualsAndHashCode() {
        Person samePerson = new Person(name);
        samePerson.setId(id);
        Person differentPerson = new Person(name);
        differentPerson.setId(UUID.randomUUID());

        assertEquals(person, samePerson);
        assertNotEquals(person, differentPerson);
        assertEquals(person.hashCode(), samePerson.hashCode());
        assertNotEquals(person.hashCode(), differentPerson.hashCode());
    }
}
