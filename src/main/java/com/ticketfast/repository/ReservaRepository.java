package com.ticketfast.repository;

import com.ticketfast.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByEventoId(String eventoId);

    @Query("SELECT SUM(CASE WHEN r.zona = 'VIP' THEN r.cantidad * 150000 " +
           "WHEN r.zona = 'General' THEN r.cantidad * 50000 ELSE 0 END) " +
           "FROM Reserva r WHERE r.eventoId = :eventoId")
    Double calcularTotalEvento(@Param("eventoId") String eventoId);
}
