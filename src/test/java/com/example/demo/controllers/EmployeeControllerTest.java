package com.example.demo.controllers;


import com.example.demo.entities.PersistableEmployee;
import com.example.demo.mappers.EmployeeMapper;
import com.example.demo.models.Employee;
import com.example.demo.services.EmployeeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = EmployeeController.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class)
class EmployeeControllerTest {

    @MockBean
    EmployeeService employeeService;
    @MockBean
    EmployeeMapper employeeMapper;

    PersistableEmployee persistableEmployee;
    List<PersistableEmployee> persistableEmployeeList;

    Employee employee;
    List<Employee> employeeList;


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeAll
    @DisplayName("Setting up data for controller test")
    public void setup() {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        persistableEmployee = new PersistableEmployee();
        persistableEmployee.setId("69acec03-1ebe-44d0-976d-2ae85226ac2d");
        persistableEmployee.setName("Manoj");
        persistableEmployeeList = new ArrayList<>();
        persistableEmployeeList.add(persistableEmployee);

        employee = new Employee();
        employee.setId("69acec03-1ebe-44d0-976d-2ae85226ac2d");
        employee.setName("Manoj");
        employeeList = new ArrayList<>();
        employeeList.add(employee);
    }

    @WithMockUser("spring")
    @Test
    public void testSuccessfulResponse() throws Exception {
        when(employeeService.getEmployees()).thenReturn(persistableEmployeeList);
        when(employeeMapper.toDto(persistableEmployee)).thenReturn(employee);
        mockMvc.perform(get("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":\"69acec03-1ebe-44d0-976d-2ae85226ac2d\",\"name\":\"Manoj\"}]"));
    }
}