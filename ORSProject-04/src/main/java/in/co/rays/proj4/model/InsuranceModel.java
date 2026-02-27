package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.InsuranceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * InsuranceModel provides CRUD and search operations for InsuranceBean,
 * interacting with the {@code st_insurance} table via JDBC.
 *
 * @author Ankit Rawat
 * @version 1.0
 */
public class InsuranceModel {

    /**
     * Returns next primary key value.
     */
    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_insurance");
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

    /**
     * Add Insurance
     */
    public long add(InsuranceBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        InsuranceBean existBean = findByInsuranceCode(bean.getInsuranceCode());

        if (existBean != null) {
            throw new DuplicateRecordException("Insurance Code already exists");
        }

        int pk = 0;

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_insurance values(?,?,?,?,?,?,?,?,?)");

            pstmt.setLong(1, pk);
            pstmt.setString(2, bean.getInsuranceCode());
            pstmt.setString(3, bean.getCarName());
            pstmt.setDate(4, new java.sql.Date(bean.getExpiryDate().getTime()));
            pstmt.setString(5, bean.getProviderName());
            pstmt.setString(6, bean.getCreatedBy());
            pstmt.setString(7, bean.getModifiedBy());
            pstmt.setTimestamp(8, bean.getCreatedDatetime());
            pstmt.setTimestamp(9, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();
            pstmt.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Add rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in add Insurance");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    /**
     * Update Insurance
     */
    public void update(InsuranceBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        InsuranceBean existBean = findByInsuranceCode(bean.getInsuranceCode());

        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Insurance Code already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_insurance set insurance_code=?, car_name=?, expiry_date=?, provider_name=?, "
                            + "created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getInsuranceCode());
            pstmt.setString(2, bean.getCarName());
            pstmt.setDate(3, new java.sql.Date(bean.getExpiryDate().getTime()));
            pstmt.setString(4, bean.getProviderName());
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
                throw new ApplicationException("Update rollback exception " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Insurance");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Delete Insurance
     */
    public void delete(InsuranceBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn
                    .prepareStatement("delete from st_insurance where id=?");

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
            throw new ApplicationException("Exception in delete Insurance");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /**
     * Find by PK
     */
    public InsuranceBean findByPk(long pk) throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_insurance where id=?");
        InsuranceBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = populateBean(rs);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Insurance by PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    /**
     * Find by Insurance Code
     */
    public InsuranceBean findByInsuranceCode(String code)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_insurance where insurance_code=?");
        InsuranceBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = populateBean(rs);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in getting Insurance by Code");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    /**
     * List all
     */
    public List<InsuranceBean> list() throws ApplicationException {
        return search(null, 0, 0);
    }

    /**
     * Search with pagination
     */
    public List<InsuranceBean> search(InsuranceBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_insurance where 1=1");

        if (bean != null) {
            if (bean.getId() > 0) {
                sql.append(" and id=" + bean.getId());
            }
            if (bean.getInsuranceCode() != null && bean.getInsuranceCode().length() > 0) {
                sql.append(" and insurance_code like '%" + bean.getInsuranceCode() + "%'");
            }
            if (bean.getCarName() != null && bean.getCarName().length() > 0) {
                sql.append(" and car_name like '%" + bean.getCarName() + "%'");
            }
            if (bean.getProviderName() != null && bean.getProviderName().length() > 0) {
                sql.append(" and provider_name like '%" + bean.getProviderName() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + "," + pageSize);
        }

        ArrayList<InsuranceBean> list = new ArrayList<>();
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
            throw new ApplicationException("Exception in search Insurance");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    /**
     * Populate Bean
     */
    private InsuranceBean populateBean(ResultSet rs) throws Exception {

        InsuranceBean bean = new InsuranceBean();

        bean.setId(rs.getLong(1));
        bean.setInsuranceCode(rs.getString(2));
        bean.setCarName(rs.getString(3));
        bean.setExpiryDate(rs.getDate(4));
        bean.setProviderName(rs.getString(5));
        bean.setCreatedBy(rs.getString(6));
        bean.setModifiedBy(rs.getString(7));
        bean.setCreatedDatetime(rs.getTimestamp(8));
        bean.setModifiedDatetime(rs.getTimestamp(9));

        return bean;
    }
}