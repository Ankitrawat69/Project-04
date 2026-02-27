package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.FeedbackCategoryBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.FeedbackCategoryModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "FeedbackCategoryCtl", urlPatterns = { "/ctl/FeedbackCategoryCtl" })
public class FeedbackCategoryCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("categoryCode"))) {
			request.setAttribute("categoryCode",
					PropertyReader.getValue("error.require", "Category Code"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("categoryName"))) {
			request.setAttribute("categoryName",
					PropertyReader.getValue("error.require", "Category Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description",
					PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("categoryStatus"))) {
			request.setAttribute("categoryStatus",
					PropertyReader.getValue("error.require", "Category Status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		FeedbackCategoryBean bean = new FeedbackCategoryBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCategoryCode(
				DataUtility.getString(request.getParameter("categoryCode")));
		bean.setCategoryName(
				DataUtility.getString(request.getParameter("categoryName")));
		bean.setDescription(
				DataUtility.getString(request.getParameter("description")));
		bean.setCategoryStatus(
				DataUtility.getString(request.getParameter("categoryStatus")));

		populateDTO(bean, request);

		return bean;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		FeedbackCategoryModel model = new FeedbackCategoryModel();

		if (id > 0) {
			try {
				FeedbackCategoryBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		FeedbackCategoryModel model = new FeedbackCategoryModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			FeedbackCategoryBean bean =
					(FeedbackCategoryBean) populateBean(request);

			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage(
						"Feedback Category added successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(
						"Category Code already exists", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			FeedbackCategoryBean bean =
					(FeedbackCategoryBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage(
						"Feedback Category updated successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(
						"Category Code already exists", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(
					ORSView.FEEDBACK_CATEGORY_LIST_CTL,
					request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(
					ORSView.FEEDBACK_CATEGORY_CTL,
					request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.FEEDBACK_CATEGORY_VIEW;
	}
}