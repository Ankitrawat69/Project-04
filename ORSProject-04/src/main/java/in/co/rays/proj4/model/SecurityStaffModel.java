package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.SecurityStaffBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * SecurityStaffModel performs CRUD operations for SecurityStaffBean.
 */

public class SecurityStaffModel {

	/**
	 * Get next primary key
	 */
	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_security_staff");
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
	 * Add Security Staff
	 */
	public long add(SecurityStaffBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {

			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_security_staff values(?,?,?,?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getStaffName());
			pstmt.setString(3, bean.getShift());
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
				throw new ApplicationException("Exception : Add Rollback Exception");
			}

			throw new ApplicationException("Exception : Exception in add Security Staff");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	/**
	 * Update Security Staff
	 */
	public void update(SecurityStaffBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_security_staff set staff_name=?, shift=?, salary=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getStaffName());
			pstmt.setString(2, bean.getShift());
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
				throw new ApplicationException("Exception : Update Rollback Exception");
			}

			throw new ApplicationException("Exception : Exception in update Security Staff");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	/**
	 * Delete Security Staff
	 */
	public void delete(SecurityStaffBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_security_staff where id=?");

			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete Rollback Exception");
			}

			throw new ApplicationException("Exception : Exception in delete Security Staff");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	/**
	 * Find by PK
	 */
	public SecurityStaffBean findByPk(long pk) throws ApplicationException {

		SecurityStaffBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_security_staff where id=?");

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new SecurityStaffBean();

				bean.setId(rs.getLong(1));
				bean.setStaffName(rs.getString(2));
				bean.setShift(rs.getString(3));
				bean.setSalary(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));

			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Security Staff by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	/**
	 * List all Security Staff
	 */
	public List<SecurityStaffBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	/**
	 * Search Security Staff
	 */
	public List<SecurityStaffBean> search(SecurityStaffBean bean, int pageNo, int pageSize)
			throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_security_staff where 1=1");

		if (bean != null) {

			if (bean.getStaffName() != null && bean.getStaffName().length() > 0) {
				sql.append(" and staff_name like '%" + bean.getStaffName() + "%'");
			}

			if (bean.getShift() != null && bean.getShift().length() > 0) {
				sql.append(" and shift like '%" + bean.getShift() + "%'");
			}

			if (bean.getSalary() != null && bean.getSalary().length() > 0) {
				sql.append(" and salary like '%" + bean.getSalary() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList<SecurityStaffBean> list = new ArrayList<SecurityStaffBean>();

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new SecurityStaffBean();

				bean.setId(rs.getLong(1));
				bean.setStaffName(rs.getString(2));
				bean.setShift(rs.getString(3));
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
			throw new ApplicationException("Exception : Exception in search Security Staff");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}