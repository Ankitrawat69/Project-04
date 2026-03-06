package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.EventBookingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * EventBookingModel provides CRUD operations for EventBookingBean.
 * It interacts with the st_event_booking table using JDBC.
 * 
 * @author Ankit Rawat
 * @version 1.0
 */
public class EventBookingModel {

    /**
     * Get next primary key
     */
    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_event_booking");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pk = rs.getInt(1);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception : Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    /**
     * Add Event Booking
     */
    public long add(EventBookingBean bean) throws ApplicationException {

        Connection conn = null;
        int pk = 0;

        try {

            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_event_booking values(?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getEventName());
            pstmt.setDate(3, new java.sql.Date(bean.getBookingDate().getTime()));
            pstmt.setInt(4, bean.getSeats());
            pstmt.setString(5, bean.getCreatedBy());
            pstmt.setString(6, bean.getModifiedBy());
            pstmt.setTimestamp(7, bean.getCreatedDatetime());
            pstmt.setTimestamp(8, bean.getModifiedDatetime());

            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Add rollback exception " + ex.getMessage());
            }

            throw new ApplicationException("Exception : Exception in add Event Booking");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    /**
     * Update Event Booking
     */
    public void update(EventBookingBean bean) throws ApplicationException {

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_event_booking set event_name=?, booking_date=?, seats=?, "
                            + "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getEventName());
            pstmt.setDate(2, new java.sql.Date(bean.getBookingDate().getTime()));
            pstmt.setInt(3, bean.getSeats());
            pstmt.setString(4, bean.getCreatedBy());
            pstmt.setString(5, bean.getModifiedBy());
            pstmt.setTimestamp(6, bean.getCreatedDatetime());
            pstmt.setTimestamp(7, bean.getModifiedDatetime());
            pstmt.setLong(8, bean.getId());

            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
            }

            throw new ApplicationException("Exception in updating Event Booking");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Delete Event Booking
     */
    public void delete(EventBookingBean bean) throws ApplicationException {

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("delete from st_event_booking where id=?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
            }

            throw new ApplicationException("Exception in delete Event Booking");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Find by PK
     */
    public EventBookingBean findByPk(long pk) throws ApplicationException {

        EventBookingBean bean = null;
        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from st_event_booking where id=?");
            pstmt.setLong(1, pk);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new EventBookingBean();

                bean.setId(rs.getLong(1));
                bean.setEventName(rs.getString(2));
                bean.setBookingDate(rs.getDate(3));
                bean.setSeats(rs.getInt(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting EventBooking by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    /**
     * List all bookings
     */
    public List<EventBookingBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Search bookings
     */
    public List<EventBookingBean> search(EventBookingBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_event_booking where 1=1");

        if (bean != null) {

            if (bean.getEventName() != null && bean.getEventName().length() > 0) {
                sql.append(" and event_name like '%" + bean.getEventName() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        ArrayList<EventBookingBean> list = new ArrayList<EventBookingBean>();
        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new EventBookingBean();

                bean.setId(rs.getLong(1));
                bean.setEventName(rs.getString(2));
                bean.setBookingDate(rs.getDate(3));
                bean.setSeats(rs.getInt(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));

                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in search EventBooking");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}