package pe.edu.upeu.colegio_api.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pe.edu.upeu.colegio_api.dto.PaymentResponseDTO;
import pe.edu.upeu.colegio_api.entity.Payment;
import pe.edu.upeu.colegio_api.entity.Student;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-04T22:18:15-0500",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.13.jar, environment: Java 21.0.11 (Oracle Corporation)"
)
@Component
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public PaymentResponseDTO toResponseDTO(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();

        paymentResponseDTO.setStudent( studentToStudentInfo( payment.getStudent() ) );
        paymentResponseDTO.setId( payment.getId() );
        paymentResponseDTO.setMonth( payment.getMonth() );
        paymentResponseDTO.setYear( payment.getYear() );
        paymentResponseDTO.setAmount( payment.getAmount() );
        paymentResponseDTO.setPaid( payment.getPaid() );
        paymentResponseDTO.setPaidDate( payment.getPaidDate() );

        return paymentResponseDTO;
    }

    protected PaymentResponseDTO.StudentInfo studentToStudentInfo(Student student) {
        if ( student == null ) {
            return null;
        }

        PaymentResponseDTO.StudentInfo studentInfo = new PaymentResponseDTO.StudentInfo();

        studentInfo.setId( student.getId() );
        studentInfo.setFirstName( student.getFirstName() );
        studentInfo.setLastName( student.getLastName() );

        return studentInfo;
    }
}
