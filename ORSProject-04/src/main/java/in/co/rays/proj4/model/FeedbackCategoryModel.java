package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.FeedbackCategoryBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class FeedbackCategoryModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_feedback_category");
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

	public long add(FeedbackCategoryBean bean)
			throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		FeedbackCategoryBean existBean = findByCode(bean.getCategoryCode());
		int pk = 0;

		if (existBean != null) {
			throw new DuplicateRecordException("Category Code already exists");
		}

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"insert into st_feedback_category values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getCategoryCode());
			pstmt.setString(3, bean.getCategoryName());
			pstmt.setString(4, bean.getDescription());
			pstmt.setString(5, bean.getCategoryStatus());
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
				throw new ApplicationException("Exception : add rollback exception");
			}
			throw new ApplicationException("Exception : Exception in add FeedbackCategory");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	public void update(FeedbackCategoryBean bean)
			throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		FeedbackCategoryBean existBean = findByCode(bean.getCategoryCode());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Category Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_feedback_category set category_code=?, category_name=?, description=?, category_status=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getCategoryCode());
			pstmt.setString(2, bean.getCategoryName());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getCategoryStatus());
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
				throw new ApplicationException("Exception : update rollback exception");
			}
			throw new ApplicationException("Exception in updating FeedbackCategory");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(FeedbackCategoryBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("delete from st_feedback_category where id = ?");
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
			throw new ApplicationException("Exception : Exception in delete FeedbackCategory");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public FeedbackCategoryBean findByPk(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer(
				"select * from st_feedback_category where id = ?");
		FeedbackCategoryBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new FeedbackCategoryBean();
				bean.setId(rs.getLong(1));
				bean.setCategoryCode(rs.getString(2));
				bean.setCategoryName(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCategoryStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting FeedbackCategory by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public FeedbackCategoryBean findByCode(String code)
			throws ApplicationException {

		StringBuffer sql = new StringBuffer(
				"select * from st_feedback_category where category_code = ?");
		FeedbackCategoryBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new FeedbackCategoryBean();
				bean.setId(rs.getLong(1));
				bean.setCategoryCode(rs.getString(2));
				bean.setCategoryName(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCategoryStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting FeedbackCategory by Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public List<FeedbackCategoryBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<FeedbackCategoryBean> search(FeedbackCategoryBean bean,
			int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer(
				"select * from st_feedback_category where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getCategoryCode() != null
					&& bean.getCategoryCode().length() > 0) {
				sql.append(" and category_code like '%"
						+ bean.getCategoryCode() + "%'");
			}
			if (bean.getCategoryName() != null
					&& bean.getCategoryName().length() > 0) {
				sql.append(" and category_name like '%"
						+ bean.getCategoryName() + "%'");
			}
			if (bean.getCategoryStatus() != null
					&& bean.getCategoryStatus().length() > 0) {
				sql.append(" and category_status like '%"
						+ bean.getCategoryStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		ArrayList<FeedbackCategoryBean> list = new ArrayList<>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new FeedbackCategoryBean();
				bean.setId(rs.getLong(1));
				bean.setCategoryCode(rs.getString(2));
				bean.setCategoryName(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCategoryStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search FeedbackCategory");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}