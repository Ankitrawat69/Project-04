package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.GymTrainerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * GymTrainerModel provides CRUD and search operations for GymTrainerBean,
 * interacting with the {@code st_gym_trainer} table via JDBC.
 *
 * @author Your Name
 * @version 1.0
 */
public class GymTrainerModel {

    /**
     * Returns next primary key value.
     */
    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_gym_trainer");
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
     * Adds a new trainer.
     */
    public long add(GymTrainerBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        GymTrainerBean existBean = findByName(bean.getTrainerName());
        int pk = 0;

        if (existBean != null) {
            throw new DuplicateRecordException("Trainer already exists");
        }

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_gym_trainer values(?, ?, ?, ?, ?, ?, ?, ?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getTrainerName());
            pstmt.setString(3, bean.getSpecialization());
            pstmt.setString(4, bean.getSalary());
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
                throw new ApplicationException("Add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in adding Trainer");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    /**
     * Updates trainer.
     */
    public void update(GymTrainerBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        GymTrainerBean existBean = findByName(bean.getTrainerName());

        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Trainer name already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_gym_trainer set trainer_name=?, specialization=?, salary=?, "
                            + "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getTrainerName());
            pstmt.setString(2, bean.getSpecialization());
            pstmt.setString(3, bean.getSalary());
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
                throw new ApplicationException("Update rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Trainer");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Deletes trainer.
     */
    public void delete(GymTrainerBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn
                    .prepareStatement("delete from st_gym_trainer where id=?");
            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Delete rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in deleting Trainer");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Find by PK.
     */
    public GymTrainerBean findByPk(long pk) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_gym_trainer where id=?");
        GymTrainerBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new GymTrainerBean();
                bean.setId(rs.getLong(1));
                bean.setTrainerName(rs.getString(2));
                bean.setSpecialization(rs.getString(3));
                bean.setSalary(rs.getString(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Trainer by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * Find by Trainer Name.
     */
    public GymTrainerBean findByName(String name) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_gym_trainer where trainer_name=?");
        GymTrainerBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new GymTrainerBean();
                bean.setId(rs.getLong(1));
                bean.setTrainerName(rs.getString(2));
                bean.setSpecialization(rs.getString(3));
                bean.setSalary(rs.getString(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Trainer by Name");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /**
     * List all trainers.
     */
    public List<GymTrainerBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Search with pagination.
     */
    public List<GymTrainerBean> search(GymTrainerBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_gym_trainer where 1=1");

        if (bean != null) {

            if (bean.getId() > 0) {
                sql.append(" and id=" + bean.getId());
            }
            if (bean.getTrainerName() != null && bean.getTrainerName().length() > 0) {
                sql.append(" and trainer_name like '%" + bean.getTrainerName() + "%'");
            }
            if (bean.getSpecialization() != null && bean.getSpecialization().length() > 0) {
                sql.append(" and specialization like '%" + bean.getSpecialization() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        ArrayList<GymTrainerBean> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new GymTrainerBean();
                bean.setId(rs.getLong(1));
                bean.setTrainerName(rs.getString(2));
                bean.setSpecialization(rs.getString(3));
                bean.setSalary(rs.getString(4));
                bean.setCreatedBy(rs.getString(5));
                bean.setModifiedBy(rs.getString(6));
                bean.setCreatedDatetime(rs.getTimestamp(7));
                bean.setModifiedDatetime(rs.getTimestamp(8));
                list.add(bean);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in search Trainer");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}