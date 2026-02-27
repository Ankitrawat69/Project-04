package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.NotificationBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * NotificationModel provides CRUD and search operations for NotificationBean,
 * interacting with the {@code st_notification} table via JDBC.
 *
 * @author Ankit Rawat
 * @version 1.0
 */
public class NotificationModel {

    /**
     * Returns next primary key value for st_notification table.
     */
    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_notification");
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
     * Adds a new notification record.
     */
    public long add(NotificationBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        NotificationBean existBean = findByCode(bean.getNotificationCode());
        int pk = 0;

        if (existBean != null) {
            throw new DuplicateRecordException("Notification Code already exists");
        }

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_notification values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getNotificationCode());
            pstmt.setString(3, bean.getMessage());
            pstmt.setString(4, bean.getSentTo());
            pstmt.setDate(5, new java.sql.Date(bean.getSentTime().getTime()));
            pstmt.setString(6, bean.getNotificationStatus());
            pstmt.setString(7, bean.getCreatedBy());
            pstmt.setString(8, bean.getModifiedBy());
            pstmt.setTimestamp(9, bean.getCreatedDatetime());
            pstmt.setTimestamp(10, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception : Exception in add Notification");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Updates an existing notification record.
     */
    public void update(NotificationBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        NotificationBean existBean = findByCode(bean.getNotificationCode());

        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Notification Code already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_notification set notification_code=?, message=?, sent_to=?, sent_time=?, notification_status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getNotificationCode());
            pstmt.setString(2, bean.getMessage());
            pstmt.setString(3, bean.getSentTo());
            pstmt.setDate(4, new java.sql.Date(bean.getSentTime().getTime()));
            pstmt.setString(5, bean.getNotificationStatus());
            pstmt.setString(6, bean.getCreatedBy());
            pstmt.setString(7, bean.getModifiedBy());
            pstmt.setTimestamp(8, bean.getCreatedDatetime());
            pstmt.setTimestamp(9, bean.getModifiedDatetime());
            pstmt.setLong(10, bean.getId());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Notification");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes notification record.
     */
    public void delete(NotificationBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "delete from st_notification where id=?");

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
            throw new ApplicationException("Exception : Exception in delete Notification");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Finds notification by Primary Key.
     */
    public NotificationBean findByPk(long pk) throws ApplicationException {

        NotificationBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "select * from st_notification where id=?");
            pstmt.setLong(1, pk);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = populateBean(rs);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in getting Notification by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Finds notification by notification code.
     */
    public NotificationBean findByCode(String code) throws ApplicationException {

        NotificationBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "select * from st_notification where notification_code=?");
            pstmt.setString(1, code);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = populateBean(rs);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in getting Notification by Code");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Lists all notifications.
     */
    public List<NotificationBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Search with pagination.
     */
    public List<NotificationBean> search(NotificationBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_notification where 1=1");

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" and id=" + bean.getId());
            }

            if (bean.getNotificationCode() != null && bean.getNotificationCode().length() > 0) {
                sql.append(" and notification_code like '%" + bean.getNotificationCode() + "%'");
            }

            if (bean.getSentTo() != null && bean.getSentTo().length() > 0) {
                sql.append(" and sent_to like '%" + bean.getSentTo() + "%'");
            }

            if (bean.getNotificationStatus() != null && bean.getNotificationStatus().length() > 0) {
                sql.append(" and notification_status like '%" + bean.getNotificationStatus() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        ArrayList<NotificationBean> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(populateBean(rs));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception : Exception in search Notification");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    /**
     * Populates bean from ResultSet.
     */
    private NotificationBean populateBean(ResultSet rs) throws Exception {

        NotificationBean bean = new NotificationBean();

        bean.setId(rs.getLong(1));
        bean.setNotificationCode(rs.getString(2));
        bean.setMessage(rs.getString(3));
        bean.setSentTo(rs.getString(4));
        bean.setSentTime(rs.getDate(5));
        bean.setNotificationStatus(rs.getString(6));
        bean.setCreatedBy(rs.getString(7));
        bean.setModifiedBy(rs.getString(8));
        bean.setCreatedDatetime(rs.getTimestamp(9));
        bean.setModifiedDatetime(rs.getTimestamp(10));

        return bean;
    }
}