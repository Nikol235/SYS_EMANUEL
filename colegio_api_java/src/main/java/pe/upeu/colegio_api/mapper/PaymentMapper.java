package pe.edu.upeu.colegio_api.mapper;

import org.mapstruct.*;
import pe.edu.upeu.colegio_api.dto.PaymentResponseDTO;
import pe.edu.upeu.colegio_api.entity.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "student.id", source = "student.id")
    @Mapping(target = "student.firstName", source = "student.firstName")
    @Mapping(target = "student.lastName", source = "student.lastName")
    PaymentResponseDTO toResponseDTO(Payment payment);
}
