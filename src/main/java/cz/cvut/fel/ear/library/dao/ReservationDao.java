package cz.cvut.fel.ear.library.dao;

import cz.cvut.fel.ear.library.model.Reservation;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao extends BaseDao<Reservation> {
    public ReservationDao() {
        super(Reservation.class);
    }
}
