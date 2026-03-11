package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.TrackingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class TrackingModel {

    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {

            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_tracking");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                pk = rs.getInt(1);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {

            throw new DatabaseException("Exception in getting PK");

        } finally {

            JDBCDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    public long add(TrackingBean bean) throws ApplicationException {

        Connection conn = null;
        int pk = 0;

        try {

            pk = nextPk();

            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_tracking values(?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setLong(2, bean.getTrackingId());
            pstmt.setString(3, bean.getTrackingNumber());
            pstmt.setString(4, bean.getCurrentLocation());
            pstmt.setString(5, bean.getStatus());
            pstmt.setString(6, bean.getCreatedBy());
            pstmt.setString(7, bean.getModifiedBy());
            pstmt.setTimestamp(8, bean.getCreatedDatetime());
            pstmt.setTimestamp(9, bean.getModifiedDatetime());

            pstmt.executeUpdate();

            pstmt.close();

        } catch (Exception e) {

            e.printStackTrace();
            throw new ApplicationException("Exception in add Tracking");

        } finally {

            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }
    
    public void update(TrackingBean bean) throws ApplicationException {

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();

            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_tracking set tracking_id=?, tracking_number=?, current_location=?, status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setLong(1, bean.getTrackingId());
            pstmt.setString(2, bean.getTrackingNumber());
            pstmt.setString(3, bean.getCurrentLocation());
            pstmt.setString(4, bean.getStatus());
            pstmt.setString(5, bean.getCreatedBy());
            pstmt.setString(6, bean.getModifiedBy());
            pstmt.setTimestamp(7, bean.getCreatedDatetime());
            pstmt.setTimestamp(8, bean.getModifiedDatetime());
            pstmt.setLong(9, bean.getId());

            pstmt.executeUpdate();

            conn.commit();

            pstmt.close();

        } catch (Exception e) {

            try {
                conn.rollback();
            } catch (Exception ex) {
            }

            e.printStackTrace();

            throw new ApplicationException("Exception in updating Tracking");

        } finally {

            JDBCDataSource.closeConnection(conn);
        }
    }

    public void delete(TrackingBean bean) throws ApplicationException {

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("delete from st_tracking where id=?");

            pstmt.setLong(1, bean.getId());

            pstmt.executeUpdate();

            pstmt.close();

        } catch (Exception e) {

            throw new ApplicationException("Exception in delete Tracking");

        } finally {

            JDBCDataSource.closeConnection(conn);
        }
    }

    public TrackingBean findByPk(long pk) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_tracking where id=?");

        TrackingBean bean = null;

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            pstmt.setLong(1, pk);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new TrackingBean();

                bean.setId(rs.getLong(1));
                bean.setTrackingId(rs.getLong(2));
                bean.setTrackingNumber(rs.getString(3));
                bean.setCurrentLocation(rs.getString(4));
                bean.setStatus(rs.getString(5));

            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {

            throw new ApplicationException("Exception in findByPk");

        } finally {

            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public List search(TrackingBean bean, int pageNo, int pageSize) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_tracking where 1=1");

        if (bean != null) {

            if (bean.getTrackingNumber() != null && bean.getTrackingNumber().length() > 0) {

                sql.append(" and tracking_number like '%" + bean.getTrackingNumber() + "%'");
            }
        }

        if (pageSize > 0) {

            pageNo = (pageNo - 1) * pageSize;

            sql.append(" limit " + pageNo + "," + pageSize);
        }

        ArrayList list = new ArrayList();

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new TrackingBean();

                bean.setId(rs.getLong(1));
                bean.setTrackingId(rs.getLong(2));
                bean.setTrackingNumber(rs.getString(3));
                bean.setCurrentLocation(rs.getString(4));
                bean.setStatus(rs.getString(5));

                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {

            throw new ApplicationException("Exception in search Tracking");

        } finally {

            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}