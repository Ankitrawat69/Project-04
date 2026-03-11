package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.HolidayBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class HolidayModel {

	/**
	 * Get Next PK
	 */
	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_holiday");

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
	 * Add Holiday
	 */
	public long add(HolidayBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		HolidayBean existBean = findByHolidayCode(bean.getHolidayCode());

		if (existBean != null) {
			throw new DuplicateRecordException("Holiday Code already exists");
		}

		int pk = 0;

		try {

			pk = nextPk();

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_holiday values(?,?,?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setLong(2, bean.getHolidayId());
			pstmt.setString(3, bean.getHolidayCode());
			pstmt.setString(4, bean.getHolidayName());
			pstmt.setDate(5, new java.sql.Date(bean.getHolidayDate().getTime()));
			pstmt.setString(6, bean.getHolidayType());
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
				throw new ApplicationException("Exception: rollback error");
			}

			throw new ApplicationException("Exception in add Holiday");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	/**
	 * Update Holiday
	 */
	public void update(HolidayBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		HolidayBean existBean = findByHolidayCode(bean.getHolidayCode());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Holiday Code already exists");
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(

					"update st_holiday set holiday_id=?, holiday_code=?, holiday_name=?, holiday_date=?, holiday_type=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setLong(1, bean.getHolidayId());
			pstmt.setString(2, bean.getHolidayCode());
			pstmt.setString(3, bean.getHolidayName());
			pstmt.setDate(4, new java.sql.Date(bean.getHolidayDate().getTime()));
			pstmt.setString(5, bean.getHolidayType());
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
				throw new ApplicationException("Rollback error");
			}

			throw new ApplicationException("Exception in update Holiday");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	/**
	 * Delete Holiday
	 */
	public void delete(HolidayBean bean) throws ApplicationException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_holiday where id=?");

			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();

			conn.commit();

			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete rollback error");
			}

			throw new ApplicationException("Exception in delete Holiday");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Find By PK
	 */
	public HolidayBean findByPk(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_holiday where id=?");

		HolidayBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new HolidayBean();

				bean.setId(rs.getLong(1));
				bean.setHolidayId(rs.getLong(2));
				bean.setHolidayCode(rs.getString(3));
				bean.setHolidayName(rs.getString(4));
				bean.setHolidayDate(rs.getDate(5));
				bean.setHolidayType(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));

			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in getting Holiday by PK");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	/**
	 * Find By Holiday Code
	 */
	public HolidayBean findByHolidayCode(String code) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_holiday where holiday_code=?");

		HolidayBean bean = null;

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			pstmt.setString(1, code);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				bean = new HolidayBean();

				bean.setId(rs.getLong(1));
				bean.setHolidayCode(rs.getString(3));
				bean.setHolidayName(rs.getString(4));
				bean.setHolidayDate(rs.getDate(5));
				bean.setHolidayType(rs.getString(6));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in getting Holiday by Code");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	/**
	 * List All
	 */
	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}

	/**
	 * Search Holiday
	 */
	public List search(HolidayBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_holiday where 1=1");

		if (bean != null) {

			if (bean.getHolidayCode() != null && bean.getHolidayCode().length() > 0) {
				sql.append(" and holiday_code like '%" + bean.getHolidayCode() + "%'");
			}

			if (bean.getHolidayName() != null && bean.getHolidayName().length() > 0) {
				sql.append(" and holiday_name like '%" + bean.getHolidayName() + "%'");
			}

			if (bean.getHolidayType() != null && bean.getHolidayType().length() > 0) {
				sql.append(" and holiday_type like '%" + bean.getHolidayType() + "%'");
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

				bean = new HolidayBean();

				bean.setId(rs.getLong(1));
				bean.setHolidayId(rs.getLong(2));
				bean.setHolidayCode(rs.getString(3));
				bean.setHolidayName(rs.getString(4));
				bean.setHolidayDate(rs.getDate(5));
				bean.setHolidayType(rs.getString(6));

				list.add(bean);

			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

			throw new ApplicationException("Exception in search Holiday");

		} finally {

			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

}