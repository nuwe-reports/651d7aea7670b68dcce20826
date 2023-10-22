
package com.example.demo;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;



/** TODO
 * Implement all the unit test in its corresponding class.
 * Make sure to be as exhaustive as possible. Coverage is checked ;)
 */

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllDoctors() throws Exception {
        // DELETE ME
        Doctor doctor1 = new Doctor ("Carina", "Zaray", 49, "c.zaray@hospital.accwe");
        Doctor doctor2 = new Doctor ("Reyna", "Cayetana", 28, "r.cayetana@hospital.accwe");

        List<Doctor> doctors = new ArrayList<Doctor>();
        doctors.add(doctor1);
        doctors.add(doctor2);

        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldGetNoDoctors() throws Exception {
        List<Doctor> doctors = new ArrayList<Doctor>();

        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetDoctorById() throws Exception{

        Doctor doctor = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");

        doctor.setId(1);
        Optional<Doctor> opt = Optional.of(doctor);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(opt);
        mockMvc.perform(get("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk());

    }

    @Test
    void shouldNotGetAnyDoctorById() throws Exception{
        long id = 31;
        mockMvc.perform(get("/api/doctors/" + id))
                .andExpect(status().isNotFound());

    }
    @Test
    void shouldCreateDoctor() throws Exception {
        Doctor doctor = new Doctor("Carina", "Zaray", 49, "c.zaray@hospital.accwe");

        // Define the expected behavior of the mock repository
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);
        mockMvc.perform(post("/api/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Carina"))) // Ensure the created Doctor's first name
                .andExpect(jsonPath("$.lastName", is("Zaray")));
    }
    @Test
    void shouldDeleteDoctorById() throws Exception{


        Doctor doctor = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");

        doctor.setId(1);
        Optional<Doctor> opt = Optional.of(doctor);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(opt);
        mockMvc.perform(delete("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk());

    }
    @Test
    void shouldNotDeleteDoctor() throws Exception{
        long id = 31;
        mockMvc.perform(delete("/api/doctors/" + id))
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldDeleteDoctor() throws Exception{
        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());

    }

}

@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllPatients() throws Exception {
        // DELETE ME
        Patient patient1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        Patient patient2 = new Patient("Mariela", "Eric", 42, "m.eric@email.com");

        List<Patient> patients = new ArrayList<Patient>();
        patients.add(patient1);
        patients.add(patient2);

        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldGetNoPatients() throws Exception {
        List<Patient> patients = new ArrayList<Patient>();

        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetPatientById() throws Exception{

        Patient patient = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");

        patient.setId(1);
        Optional<Patient> opt = Optional.of(patient);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(opt);
        mockMvc.perform(get("/api/patients/" + patient.getId()))
                .andExpect(status().isOk());

    }

    @Test
    void shouldNotGetAnyPatientById() throws Exception{
        long id = 31;
        mockMvc.perform(get("/api/patients/" + id))
                .andExpect(status().isNotFound());

    }
    @Test
    void shouldCreatePatient() throws Exception {
        Patient patient = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        // Define the expected behavior of the mock repository

        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
        mockMvc.perform(post("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Jose Luis"))) // Ensure the created Patient's first name
                .andExpect(jsonPath("$.lastName", is("Olaya")));
    }
    @Test
    void shouldDeletePatientById() throws Exception{


        Patient patient = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");

        patient.setId(1);
        Optional<Patient> opt = Optional.of(patient);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(opt);
        mockMvc.perform(delete("/api/patients/" + patient.getId()))
                .andExpect(status().isOk());

    }
    @Test
    void shouldNotDeletePatient() throws Exception{
        long id = 31;
        mockMvc.perform(delete("/api/patients/" + id))
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldDeletePatient() throws Exception{
        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());

    }



}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllRooms() throws Exception {
        // DELETE ME
        Room room1 = new Room("Dermatology");
        Room room2 = new Room("Oncology");

        List<Room> rooms = new ArrayList<Room>();
        rooms.add(room1);
        rooms.add(room2);

        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldGetNoRooms() throws Exception {
        List<Room> rooms = new ArrayList<>();

        when(roomRepository.findAll()).thenReturn(rooms);

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetRoomByName() throws Exception {
        Room room = new Room("Dermatology");

        Optional<Room> opt = Optional.of(room);

        assertThat(opt).isPresent();
        assertThat(opt.get().getRoomName()).isEqualTo(room.getRoomName());

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(opt);

        mockMvc.perform(get("/api/rooms/" + room.getRoomName()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetAnyRoomByName() throws Exception {
        String name = "Radiology";

        mockMvc.perform(get("/api/rooms/" + name))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateRoom() throws Exception {
        Room room = new Room("Dermatology");

        when(roomRepository.save(any(Room.class))).thenReturn(room);
        mockMvc.perform(post("/api/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomName", is("Dermatology"))); // Cambiar a "roomName"
    }

    @Test
    void shouldDeleteRoomByName() throws Exception {
        Room room = new Room("Dermatology"); // Cambiar a Room

        Optional<Room> opt = Optional.of(room);

        assertThat(opt).isPresent();
        assertThat(opt.get().getRoomName()).isEqualTo(room.getRoomName());

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(opt);
        mockMvc.perform(delete("/api/rooms/" + room.getRoomName()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteRoom() throws Exception {
        String name = "Radiology";

        mockMvc.perform(delete("/api/rooms/" + name))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllRooms() throws Exception {
        mockMvc.perform(delete("/api/rooms"))
                .andExpect(status().isOk());
    }

}




