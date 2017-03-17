package Test;

import Domain.Sarcina;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Utilizator on 07-Nov-16.
 */
public class SarcinaTest {

    private Sarcina s1, s2, s3, s4, s5;

    @Before
    public void setUp() throws Exception {
        s1 = new Sarcina(1, "S1");
        s2 = new Sarcina(2, "S2");
        s3 = new Sarcina(3, "S3");
        s4 = new Sarcina(4, "S4");
        s5 = new Sarcina(1, "S5");

    }

    @Test
    public void getDescription() throws Exception {
        assertEquals(s1.getDescription(),"S1");
        assertEquals(s2.getDescription(),"S2");
        assertEquals(s3.getDescription(),"S3");
        assertEquals(s4.getDescription(),"S4");
        assertEquals(s5.getDescription(),"S5");

    }

    @Test
    public void setDescription() throws Exception {

        s1.setDescription("Sarcina1");
        assertEquals(s1.getDescription(), "Sarcina1");


        s2.setDescription("Sarcina2");
        assertEquals(s2.getDescription(), "Sarcina2");

        s3.setDescription("");
        assertEquals(s3.getDescription(), "");
    }

    @Test
    public void equals() throws Exception {
        assertEquals(s1, s1);
        assertEquals(s1, s5);
        assertNotEquals(s1, s2);
        assertNotEquals(s3, s4);
    }

    @Test
    public void testToString() throws Exception {
        assertEquals(s1.toString(), "1 S1");
        assertEquals(s2.toString(), "2 S2");
        assertEquals(s3.toString(), "3 S3");
    }


    @Test
    public void setID() throws Exception {
        s1.setID(66);
        assertEquals((int)s1.getID(), 66);
    }

    @Test
    public void getID() throws Exception {
        assertEquals((int)s1.getID(), 1);
    }

}