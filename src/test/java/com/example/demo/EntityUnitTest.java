package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	private Doctor d1;

	private Patient p1;

    private Room r1;

    private Room r;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    private Appointment a4;
    private Appointment a5;
    private Appointment a6;


    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    @BeforeAll
    void setUp() {
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        d1 = new Doctor("Carina", "Zaray", 49, "c.zaray@hospital.accwe");
        r1 = new Room("Dermatology");
        r = new Room();
    }

    @Test
    void testAppointmentOverlap(){

        Patient p2 = new Patient("Mariela", "Eric", 42, "m.eric@email.com");

        Doctor d2 = new Doctor ("Reyna", "Cayetana", 28, "r.cayetana@hospital.accwe");

        Room r2 = new Room("Oncology");

        LocalDateTime startsAt1 = LocalDateTime.parse("15:00 24/04/2023", formatter);
        LocalDateTime startsAt2 = LocalDateTime.parse("17:00 24/04/2023", formatter);
        LocalDateTime startsAt3 = LocalDateTime.parse("16:00 24/04/2023", formatter);

        LocalDateTime finishesAt1 = LocalDateTime.parse("17:00 24/04/2023", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("19:00 24/04/2023", formatter);
        LocalDateTime finishesAt3 = LocalDateTime.parse("18:00 24/04/2023", formatter);

        a1 = new Appointment(p1, d1, r1, startsAt1, finishesAt1);
        a2 = new Appointment(p1, d2, r1, startsAt2, finishesAt2);
        a3 = new Appointment(p2, d1, r1, startsAt3, finishesAt3);
        a4 = new Appointment(p1, d1, r2, startsAt3, finishesAt3);
        a5 = new Appointment(p1, d1, r1, startsAt3, finishesAt2);
        a6 = new Appointment(p1, d1, r1, startsAt1, finishesAt3);

        assertThat(a1.overlaps(a3)).isEqualTo(true);
        assertThat(a2.overlaps(a3)).isEqualTo(true);
        assertThat(a4.overlaps(a3)).isEqualTo(false);
        assertThat(a5.overlaps(a3)).isEqualTo(true);
        assertThat(a6.overlaps(a3)).isEqualTo(true);

    }

    @Test
    public void testAppointmentGettersAndSetters() {

        Patient p2 = new Patient("Mariela", "Eric", 42, "m.eric@email.com");

        Doctor d2 = new Doctor ("Reyna", "Cayetana", 28, "r.cayetana@hospital.accwe");

        Room r2 = new Room("Oncology");

        LocalDateTime startsAt1 = LocalDateTime.parse("15:00 24/04/2023", formatter);
        LocalDateTime startsAt2 = LocalDateTime.parse("17:00 24/04/2023", formatter);

        LocalDateTime finishesAt1 = LocalDateTime.parse("17:00 24/04/2023", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("19:00 24/04/2023", formatter);

        // Crear una instancia de Appointment
        a1 = new Appointment(p1, d1, r1, startsAt1, finishesAt1);


        a1.setId(1L);

        // Use the getter to retrieve the value and assert that it matches the one you set
        assertThat(1L).isEqualTo(a1.getId());
        // Probar los getters y setters
        assertThat(p1).isEqualTo(a1.getPatient());
        a1.setPatient(p2);
        assertThat(p2).isEqualTo(a1.getPatient());

        assertThat(d1).isEqualTo(a1.getDoctor());
        a1.setDoctor(d2);
        assertThat(d2).isEqualTo(a1.getDoctor());

        assertThat(r1).isEqualTo(a1.getRoom());
        a1.setRoom(r2);
        assertThat(r2).isEqualTo(a1.getRoom());

        assertThat(startsAt1).isEqualTo(a1.getStartsAt());
        a1.setStartsAt(startsAt2);
        assertThat(startsAt2).isEqualTo(a1.getStartsAt());

        assertThat(finishesAt1).isEqualTo(a1.getFinishesAt());
        a1.setFinishesAt(finishesAt2);
        assertThat(finishesAt2).isEqualTo(a1.getFinishesAt());

    }

    @Test
    public void testPersonGettersAndSetters() {
        Person person = new Person("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        /*
        atient p2 = new Patient("Mariela", "Eric", 42, "m.eric@email.com");

        Doctor d2 = new Doctor ("Reyna", "Cayetana", 28, "r.cayetana@hospital.accwe");

        Room r2 = new Room("Oncology");
         */
        assertThat("Jose Luis").isEqualTo(person.getFirstName());
        person.setFirstName("Mariela");
        assertThat("Mariela").isEqualTo(person.getFirstName());

        assertThat("Olaya").isEqualTo(person.getLastName());
        person.setLastName("Eric");
        assertThat("Eric").isEqualTo(person.getLastName());

        assertThat(37).isEqualTo(person.getAge());
        person.setAge(43);
        assertThat(43).isEqualTo(person.getAge());

        assertThat("j.olaya@email.com").isEqualTo(person.getEmail());
        person.setEmail("m.eric@email.com");
        assertThat("m.eric@email.com").isEqualTo(person.getEmail());

    }

    @Test
    public void testPatientId(){

        p1.setId(1L);

        // Use the getter to retrieve the value and assert that it matches the one you set
        assertThat(1L).isEqualTo(p1.getId());
    }

    @Test
    public void testDoctorId(){

        d1.setId(1L);

        // Use the getter to retrieve the value and assert that it matches the one you set
        assertThat(1L).isEqualTo(d1.getId());
    }


    public void testRoom(){


        assertThat("Dermatology").isEqualTo(r1.getRoomName());

        assertThat(r).isNotNull();
        assertThat(r.getRoomName()).isNull();



    }

    /** TODO
     * Implement tests for each Entity class: Doctor, Patient, Room and Appointment.
     * Make sure you are as exhaustive as possible. Coverage is checked ;)
     */
}
