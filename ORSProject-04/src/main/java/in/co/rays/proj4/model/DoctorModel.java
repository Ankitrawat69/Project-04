package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.DoctorBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class DoctorModel {

    public int nextPk() throws DatabaseException {

        int pk = 0;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("select max(id) from st_doctor");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pk = rs.getInt(1);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            throw new DatabaseException("Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    public long add(DoctorBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        int pk = 0;

        DoctorBean existBean = findByMobileNo(bean.getMobileNo());
        if (existBean != null) {
            throw new DuplicateRecordException("Doctor already exists with this mobile number");
        }

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(
            	    "insert into st_doctor (id, name, dob, mobileNo, expertise, createdBy, modifiedBy, createdDatetime, modifiedDatetime) "
            	        + "values (?,?,?,?,?,?,?,?,?)");


            ps.setLong(1, pk);
            ps.setString(2, bean.getName());
            if (bean.getDob() != null) {
                ps.setDate(3, new java.sql.Date(bean.getDob().getTime()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }

            ps.setString(4, bean.getMobileNo());
            ps.setString(5, bean.getExpertise());
            ps.setString(6, bean.getCreatedBy());
            ps.setString(7, bean.getModifiedBy());
            ps.setTimestamp(8, bean.getCreatedDatetime());
            ps.setTimestamp(9, bean.getModifiedDatetime());

            ps.executeUpdate();
            conn.commit();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new ApplicationException(e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    public void update(DoctorBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        DoctorBean existBean = findByMobileNo(bean.getMobileNo());
        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Mobile number already exists");
        }

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(
            	    "update st_doctor set name=?, dob=?, mobileNo=?, expertise=?, "
            	        + "createdBy=?, modifiedBy=?, modifiedDatetime=? where id=?");


            ps.setString(1, bean.getName());
            ps.setDate(2, new java.sql.Date(bean.getDob().getTime()));
            ps.setString(3, bean.getMobileNo());
            ps.setString(4, bean.getExpertise());
            ps.setString(5, bean.getCreatedBy());
            ps.setString(6, bean.getModifiedBy());
            ps.setTimestamp(7, bean.getModifiedDatetime());
            ps.setLong(8, bean.getId());

            ps.executeUpdate();
            conn.commit();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new ApplicationException(e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public void delete(DoctorBean bean) throws ApplicationException {

        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement("delete from st_doctor where id=?");
            ps.setLong(1, bean.getId());
            ps.executeUpdate();

            conn.commit();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new ApplicationException(e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public DoctorBean findByPk(long pk) throws ApplicationException {

        DoctorBean bean = null;
        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from st_doctor where id=?");
            ps.setLong(1, pk);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                bean = new DoctorBean();
                bean.setId(rs.getLong("id"));
                bean.setName(rs.getString("name"));
                bean.setDob(rs.getDate("dob"));
                bean.setMobileNo(rs.getString("mobileNo"));
                bean.setExpertise(rs.getString("expertise"));
                bean.setCreatedBy(rs.getString("createdBy"));
                bean.setModifiedBy(rs.getString("modifiedBy"));
                bean.setCreatedDatetime(rs.getTimestamp("createdDatetime"));
                bean.setModifiedDatetime(rs.getTimestamp("modifiedDatetime"));

            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public DoctorBean findByMobileNo(String mobileNo)
            throws ApplicationException {

        DoctorBean bean = null;
        Connection conn = null;

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM st_doctor WHERE mobileNo = ?");
            ps.setString(1, mobileNo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                bean = new DoctorBean();
                bean.setId(rs.getLong("id"));
                bean.setName(rs.getString("name"));
                bean.setDob(rs.getDate("dob"));
                bean.setMobileNo(rs.getString("mobileNo"));
                bean.setExpertise(rs.getString("expertise"));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public List<DoctorBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    public List<DoctorBean> search(DoctorBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        ArrayList<DoctorBean> list = new ArrayList<>();
        Connection conn = null;

        StringBuffer sql = new StringBuffer("select * from st_doctor where 1=1");

        if (bean != null) {

            if (bean.getName() != null && bean.getName().length() > 0)
                sql.append(" and name like '" + bean.getName() + "%'");

            if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0)
                sql.append(" and mobileNo like '" + bean.getMobileNo() + "%'");

            if (bean.getExpertise() != null && bean.getExpertise().length() > 0)
                sql.append(" and expertise like '" + bean.getExpertise() + "%'");
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        try {

            conn = JDBCDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                DoctorBean b = new DoctorBean();
                b.setId(rs.getLong("id"));
                b.setName(rs.getString("name"));
                b.setDob(rs.getDate("dob"));
                b.setMobileNo(rs.getString("mobileNo"));
                b.setExpertise(rs.getString("expertise"));

                list.add(b);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ApplicationException(e.getMessage());
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}
