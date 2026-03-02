package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.PassengerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * PassengerModel provides CRUD and search operations for PassengerBean,
 * interacting with the st_passenger table via JDBC.
 */
public class PassengerModel {

    /**
     * Returns next primary key value.
     */
    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_passenger");
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
     * Adds a new passenger.
     */
    public long add(PassengerBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        PassengerBean existBean = findByPassport(bean.getPassportNumber());
        int pk = 0;

        if (existBean != null) {
            throw new DuplicateRecordException("Passport Number already exists");
        }

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_passenger values(?, ?, ?, ?, ?, ?, ?, ?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getName());
            pstmt.setInt(3, bean.getAge());
            pstmt.setString(4, bean.getPassportNumber());
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
                throw new ApplicationException("Exception : Add rollback exception");
            }
            throw new ApplicationException("Exception : Exception in add Passenger");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Updates passenger.
     */
    public void update(PassengerBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        PassengerBean existBean = findByPassport(bean.getPassportNumber());

        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Passport Number already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_passenger set name=?, age=?, passport_number=?, "
                            + "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getName());
            pstmt.setInt(2, bean.getAge());
            pstmt.setString(3, bean.getPassportNumber());
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
                throw new ApplicationException("Exception : Update rollback exception");
            }
            throw new ApplicationException("Exception in updating Passenger");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes passenger.
     */
    public void delete(PassengerBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn
                    .prepareStatement("delete from st_passenger where id=?");

            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Delete rollback exception");
            }
            throw new ApplicationException("Exception : Exception in delete Passenger");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Find by Primary Key.
     */
    public PassengerBean findByPk(long pk) throws ApplicationException {

        PassengerBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn
                    .prepareStatement("select * from st_passenger where id=?");

            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new PassengerBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setAge(rs.getInt(3));
                bean.setPassportNumber(rs.getString(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in getting Passenger by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Find by Passport Number.
     */
    public PassengerBean findByPassport(String passport)
            throws ApplicationException {

        PassengerBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn
                    .prepareStatement("select * from st_passenger where passport_number=?");

            pstmt.setString(1, passport);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new PassengerBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setAge(rs.getInt(3));
                bean.setPassportNumber(rs.getString(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in getting Passenger by Passport");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * List all passengers.
     */
    public List<PassengerBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Search passenger.
     */
    public List<PassengerBean> search(PassengerBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_passenger where 1=1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" and id=" + bean.getId());
            }
            if (bean.getName() != null && bean.getName().length() > 0) {
                sql.append(" and name like '%" + bean.getName() + "%'");
            }
            if (bean.getPassportNumber() != null
                    && bean.getPassportNumber().length() > 0) {
                sql.append(" and passport_number like '%"
                        + bean.getPassportNumber() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        ArrayList<PassengerBean> list = new ArrayList<PassengerBean>();
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new PassengerBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setAge(rs.getInt(3));
                bean.setPassportNumber(rs.getString(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in search Passenger");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return list;
    }
}