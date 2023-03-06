import org.junit.Test;
import org.mockito.*;
import org.junit.jupiter.api.Assertions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;

import ru.netology.patient.entity.*;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.alert.SendAlertServiceImpl;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

public class HealthCareServiceTest {
	@Test
	public void MedicalServiceImpl() {
		PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
		Mockito.when(patientInfoRepository.getById("dd058c21-cade-4c6a-b72c-dc609e1dfa43"))
			.thenReturn(new PatientInfo("dd058c21-cade-4c6a-b72c-dc609e1dfa43", 
				"Иван", "Петров", LocalDate.of(1980, 11, 26), 
				new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80))));

		SendAlertService alertService = new SendAlertServiceImpl();
        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, alertService);
		
        BloodPressure currentPressure = new BloodPressure(60, 120);
        medicalService.checkBloodPressure("dd058c21-cade-4c6a-b72c-dc609e1dfa43", currentPressure);

        BigDecimal currentTemperature = new BigDecimal("37.9");
        medicalService.checkTemperature("dd058c21-cade-4c6a-b72c-dc609e1dfa43", currentTemperature);

	}

	@Test
	public void sendAlertServiceTest() {
		SendAlertService alertService = new SendAlertServiceImpl();

		doNothing().when(alertService).sendAlertService("Hello");
   		alertService.send("Hello");
		verify(alertService, times(1)).send("Hello");

	}
}
