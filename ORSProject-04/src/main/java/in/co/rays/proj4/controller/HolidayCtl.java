package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.HolidayBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.HolidayModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "HolidayCtl", urlPatterns = { "/ctl/HolidayCtl" })
public class HolidayCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("holidayCode"))) {
			request.setAttribute("holidayCode",
					PropertyReader.getValue("error.require", "Holiday Code"));
			pass = false;
		}else if (!DataValidator.isInteger(request.getParameter("holidayCode"))) {
            request.setAttribute("holidayCode", "Invalid holidayCode");
            pass = false;
        }

		if (DataValidator.isNull(request.getParameter("holidayName"))) {
			request.setAttribute("holidayName",
					PropertyReader.getValue("error.require", "Holiday Name"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("holidayName"))) {
            request.setAttribute("holidayName", "Invalid holidayName");
            pass = false;
        }

		if (DataValidator.isNull(request.getParameter("holidayDate"))) {
			request.setAttribute("holidayDate",
					PropertyReader.getValue("error.require", "Holiday Date"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("holidayType"))) {
			request.setAttribute("holidayType",
					PropertyReader.getValue("error.require", "Holiday Type"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		HolidayBean bean = new HolidayBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setHolidayId(DataUtility.getLong(request.getParameter("holidayId")));
		bean.setHolidayCode(DataUtility.getString(request.getParameter("holidayCode")));
		bean.setHolidayName(DataUtility.getString(request.getParameter("holidayName")));
		bean.setHolidayDate(DataUtility.getDate(request.getParameter("holidayDate")));
		bean.setHolidayType(DataUtility.getString(request.getParameter("holidayType")));

		populateDTO(bean, request);

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		HolidayModel model = new HolidayModel();

		if (id > 0) {

			try {

				HolidayBean bean = model.findByPk(id);

				ServletUtility.setBean(bean, request);

			} catch (ApplicationException e) {

				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));

		HolidayModel model = new HolidayModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			HolidayBean bean = (HolidayBean) populateBean(request);

			try {

				long pk = model.add(bean);

				ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("Holiday added successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);

				ServletUtility.setErrorMessage("Holiday Code already exists", request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			HolidayBean bean = (HolidayBean) populateBean(request);

			try {

				if (id > 0) {
					model.update(bean);
				}

				ServletUtility.setBean(bean, request);

				ServletUtility.setSuccessMessage("Holiday updated successfully", request);

			} catch (DuplicateRecordException e) {

				ServletUtility.setBean(bean, request);

				ServletUtility.setErrorMessage("Holiday Code already exists", request);

			} catch (ApplicationException e) {

				e.printStackTrace();

				ServletUtility.handleException(e, request, response);

				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.HOLIDAY_LIST_CTL, request, response);

			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.HOLIDAY_CTL, request, response);

			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {

		return ORSView.HOLIDAY_VIEW;
	}
}