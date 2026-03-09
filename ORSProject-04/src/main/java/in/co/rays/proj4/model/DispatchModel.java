package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.DispatchBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

/**
 * DispatchModel provides CRUD operations for DispatchBean.
 * It interacts with the st_dispatch table using JDBC.
 * 
 * @author Ankit Rawat
 * @version 1.0
 */
public class DispatchModel {

	/**
	 * Returns next primary key of st_dispatch table
	 */
	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_dispatch");
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
	 * Add Dispatch
	 */
	public long add(DispatchBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		try {

			pk = nextPk();

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_dispatch values(?,?,?,?,?,?,?,?,?)");

					pstmt.setLong(1, pk);
					pstmt.setLong(2, bean.getDispatchId());
					pstmt.setDate(3, new java.sql.Date(bean.getDispatchDate().getTime()));
					pstmt.setString(4, bean.getStatus());
					pstmt.setString(5, bean.getCourierName());
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
				throw new ApplicationException("Exception : Add rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception : Exception in add Dispatch");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	/**
	 * Update Dispatch
	 */
	public void update(DispatchBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_dispatch set dispatch_id=?, dispatch_date=?, status=?, courier_name=?, created_by=?, modified_by=?, modified_datetime=? where id=?");

			pstmt.setLong(1, bean.getDispatchId());
			pstmt.setDate(2, new java.sql.Date(bean.getDispatchDate().getTime()));
			pstmt.setString(3, bean.getStatus());
			pstmt.setString(4, bean.getCourierName());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
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

			throw new ApplicationException("Exception : Exception in update Dispatch");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Delete Dispatch
	 */
	public void delete(DispatchBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_dispatch where id=?");

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

			throw new ApplicationException("Exception : Exception in delete Dispatch");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Find Dispatch By PK
	 */
	public DispatchBean findByPk(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_dispatch where id=?");

		DispatchBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new DispatchBean();

				bean.setId(rs.getLong(1));
				bean.setDispatchId(rs.getLong(2));
				bean.setDispatchDate(rs.getDate(3));
				bean.setStatus(rs.getString(4));
				bean.setCourierName(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));

			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Dispatch by pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	/**
	 * List all Dispatch
	 */
	public List<DispatchBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	/**
	 * Search Dispatch
	 */
	public List<DispatchBean> search(DispatchBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_dispatch where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id=" + bean.getId());
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" and status like '%" + bean.getStatus() + "%'");
			}

			if (bean.getCourierName() != null && bean.getCourierName().length() > 0) {
				sql.append(" and courier_name like '%" + bean.getCourierName() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList<DispatchBean> list = new ArrayList<DispatchBean>();

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new DispatchBean();

				bean.setId(rs.getLong(1));
				bean.setDispatchId(rs.getLong(2));
				bean.setDispatchDate(rs.getDate(3));
				bean.setStatus(rs.getString(4));
				bean.setCourierName(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Dispatch");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}