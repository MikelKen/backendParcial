package com.parcial.parcialbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.parcial.parcialbackend.DTO.NotificationDTO;
import com.parcial.parcialbackend.DTO.ResponseDTO;
import com.parcial.parcialbackend.entity.Notification;
import com.parcial.parcialbackend.entity.Pacient;
import com.parcial.parcialbackend.entity.TimeBlock;
import com.parcial.parcialbackend.repository.NotificationRepository;
import com.parcial.parcialbackend.repository.PacientRepository;
import com.parcial.parcialbackend.repository.TimeBlockRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationService {
    private NotificationRepository notificationRepository;
    private TimeBlockRepository timeBlockRepository;
    private PacientRepository pacientRepository;

    public ResponseDTO create(NotificationDTO dto, HttpServletRequest request){
        try {
            String doctorId  = (String)request.getAttribute("userId");

            TimeBlock fichaReser = timeBlockRepository.findById (dto.getFichaIdrepro()).orElseThrow(()-> new RuntimeException("La ficha para reprogramar no existe "));

            Integer pacientId = fichaReser.getPacient().getCi();
            fichaReser.setState("CANCELADO");

            Optional<Pacient> optionalPacient = pacientRepository.findByCi(pacientId);
            Pacient pacient = optionalPacient.orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

            TimeBlock fichaNew = timeBlockRepository.findById(dto.getFichaIdNueva()).orElseThrow(()-> new RuntimeException("La ficha nueva no existe"));

            fichaNew.setState("RESERVADO");
            fichaNew.setPacient(pacient);

            timeBlockRepository.save(fichaNew);

            String dateFichaReser = fichaReser.getDate().toString();
            String dayFichaReser = fichaReser.getOpeningHour().getDayWeek();
            String hourFichaReser = fichaReser.getStartTime().toString();

            String dateFichaNew = fichaNew.getDate().toString();
            String dayFichaNew= fichaNew.getOpeningHour().getDayWeek();
            String hourFichaNew = fichaNew.getStartTime().toString();


            String detail = "Su cita medica del dia "+dayFichaReser+" a horas "+ hourFichaReser+", fecha "+dateFichaReser+" fue reprogramado para el dia "+dayFichaNew+" a horas "+hourFichaNew+", fecha "+dateFichaNew;

            System.out.println(detail);
            //crear la notificaicon
            Notification notification = Notification.builder()
                                    .doctorId(Integer.valueOf(doctorId))
                                    .message(dto.getMessage())
                                    .pacient(pacient)
                                    .state(false)
                                    .detail(detail)
                                    .build();

            notificationRepository.save(notification);
            return ResponseDTO.builder()
            .data(notification)
            .success(true)
            .error(false)
            .message("Notification created successfull ")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //retorna todas las notificaciones de una paciente
    public ResponseDTO getNotificationByPacient( HttpServletRequest request){
        try {
            
            String pacientId  = (String)request.getAttribute("userId");

            List<Notification> notifications = notificationRepository.findByPacient_Ci(Integer.valueOf(pacientId));
  
            return ResponseDTO.builder()
            .data(notifications)
            .success(true)
            .error(false)
            .message("Notificaciones obtenidas ")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //actualiza el estado de la notificacion
    public ResponseDTO updateNotificationId( Integer id){
        try {
            
            
            Notification notification = notificationRepository.findById(id).orElseThrow(()-> new RuntimeException("Notificacion no encontrada"));
            notification.setState(true);
            notificationRepository.save(notification);
            return ResponseDTO.builder()
            .data(notification)
            .success(true)
            .error(false)
            .message("Estado modificado")
            .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
