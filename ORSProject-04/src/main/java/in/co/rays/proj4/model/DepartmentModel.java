package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.DepartmentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * DepartmentModel provides CRUD and search operations for DepartmentBean,
 * interacting with the {@code st_department} table via JDBC.
 * 
 * @author Ankit Rawat
 * @version 1.0
 */
public class DepartmentModel {

	/**
	 * Returns next primary key value for st_department table.
	 */
	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_department");
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
	 * Adds a new department.
	 */
	public long add(DepartmentBean bean)
			throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		DepartmentBean existBean = findByCode(bean.getDepartmentCode());
		int pk = 0;

		if (existBean != null) {
			throw new DuplicateRecordException("Department Code already exists");
		}

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_department values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getDepartmentCode());
			pstmt.setString(3, bean.getDepartmentName());
			pstmt.setString(4, bean.getDepartmentHead());
			pstmt.setString(5, bean.getLocation());
			pstmt.setString(6, bean.getStatus());
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
				throw new ApplicationException("Exception : add rollback exception");
			}
			throw new ApplicationException("Exception : Exception in add Department");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	/**
	 * Updates department record.
	 */
	public void update(DepartmentBean bean)
			throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		DepartmentBean existBean = findByCode(bean.getDepartmentCode());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Department Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_department set department_code=?, department_name=?, department_head=?, location=?, status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getDepartmentCode());
			pstmt.setString(2, bean.getDepartmentName());
			pstmt.setString(3, bean.getDepartmentHead());
			pstmt.setString(4, bean.getLocation());
			pstmt.setString(5, bean.getStatus());
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
				throw new ApplicationException("Exception : update rollback exception");
			}
			throw new ApplicationException("Exception in updating Department");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Deletes department.
	 */
	public void delete(DepartmentBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("delete from st_department where id = ?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : delete rollback exception");
			}
			throw new ApplicationException("Exception : Exception in delete Department");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Find department by PK.
	 */
	public DepartmentBean findByPk(long pk) throws ApplicationException {

		DepartmentBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("select * from st_department where id = ?");
			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new DepartmentBean();
				bean.setId(rs.getLong(1));
				bean.setDepartmentCode(rs.getString(2));
				bean.setDepartmentName(rs.getString(3));
				bean.setDepartmentHead(rs.getString(4));
				bean.setLocation(rs.getString(5));
				bean.setStatus(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Department by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Find department by unique code.
	 */
	public DepartmentBean findByCode(String code) throws ApplicationException {

		DepartmentBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("select * from st_department where department_code = ?");
			pstmt.setString(1, code);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new DepartmentBean();
				bean.setId(rs.getLong(1));
				bean.setDepartmentCode(rs.getString(2));
				bean.setDepartmentName(rs.getString(3));
				bean.setDepartmentHead(rs.getString(4));
				bean.setLocation(rs.getString(5));
				bean.setStatus(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Department by Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * List all departments.
	 */
	public List<DepartmentBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	/**
	 * Search departments with pagination.
	 */
	public List<DepartmentBean> search(DepartmentBean bean, int pageNo, int pageSize)
			throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_department where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getDepartmentCode() != null
					&& bean.getDepartmentCode().length() > 0) {
				sql.append(" and department_code like '%"
						+ bean.getDepartmentCode() + "%'");
			}
			if (bean.getDepartmentName() != null
					&& bean.getDepartmentName().length() > 0) {
				sql.append(" and department_name like '%"
						+ bean.getDepartmentName() + "%'");
			}
			if (bean.getDepartmentHead() != null
					&& bean.getDepartmentHead().length() > 0) {
				sql.append(" and department_head like '%"
						+ bean.getDepartmentHead() + "%'");
			}
			if (bean.getLocation() != null
					&& bean.getLocation().length() > 0) {
				sql.append(" and location like '%"
						+ bean.getLocation() + "%'");
			}
			if (bean.getStatus() != null
					&& bean.getStatus().length() > 0) {
				sql.append(" and status like '%"
						+ bean.getStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		ArrayList<DepartmentBean> list = new ArrayList<DepartmentBean>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new DepartmentBean();
				bean.setId(rs.getLong(1));
				bean.setDepartmentCode(rs.getString(2));
				bean.setDepartmentName(rs.getString(3));
				bean.setDepartmentHead(rs.getString(4));
				bean.setLocation(rs.getString(5));
				bean.setStatus(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Department");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}