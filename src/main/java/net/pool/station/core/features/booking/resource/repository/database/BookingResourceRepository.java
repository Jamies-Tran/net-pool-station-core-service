package net.pool.station.core.features.booking.resource.repository.database;

import net.pool.station.core.features.booking.resource.repository.database.dao.BookingResourceDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingResourceRepository extends JpaRepository<BookingResourceEntity, BookingResourceEntityId> {
    @Query("""
        SELECT 
                sr.resourceCode AS resourceCode,
                sr.resourceName AS resourceName,
                sr.typeCode AS typeCode,
                sr.typeName AS typeName
        FROM BookingResourceEntity br
        INNER JOIN StationResourceEntity sr ON sr.stationResourceId = br.id.stationResourceId
        WHERE br.id.bookingId = :bookingId
        """)
    List<BookingResourceDao> findAllByBookingId(Long bookingId);
}
