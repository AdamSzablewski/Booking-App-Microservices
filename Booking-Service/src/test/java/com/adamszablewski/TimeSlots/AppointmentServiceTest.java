package com.adamszablewski.TimeSlots;

import com.adamszablewski.dao.Dao;
import com.adamszablewski.dto.mapper.Mapper;
import com.adamszablewski.util.AppointmentHelper;
import com.adamszablewski.util.UserValidator;
import com.adamszablewski.messages.MessageSender;
import com.adamszablewski.model.Appointment;
import com.adamszablewski.repository.AppointmentRepository;
import com.adamszablewski.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase
@DataJpaTest(properties = "spring.config.name=application-test")
public class AppointmentServiceTest {
    AppointmentService appointmentService;
    @Mock
    AppointmentRepository appointmentRepository;
    @Mock
    AppointmentHelper appointmentHelper;
    @Mock
    MessageSender messageSender;
    @Mock
    Dao dao;
    @Mock
    UserValidator userValidator;
    @Mock
    Mapper mapper;

    @BeforeEach
    void setup(){
        appointmentService = new AppointmentService(appointmentRepository, appointmentHelper,
                messageSender, dao, userValidator, mapper);
    }

    @Test
    void markAppointmentAsDoneTest_should_work_ok(){
        Appointment appointment = Appointment.builder()
                .build();
                //.

    }



}
