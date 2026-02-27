package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.NotificationBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.NotificationModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * NotificationCtl handles add, update, view and navigation
 * operations for Notification.
 * 
 * @author Ankit Rawat
 * @version 1.0
 */
@WebServlet(name = "NotificationCtl", urlPatterns = { "/ctl/NotificationCtl" })
public class NotificationCtl extends BaseCtl {

    /**
     * Validate Notification Form Fields
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("notificationCode"))) {
            request.setAttribute("notificationCode",
                    PropertyReader.getValue("error.require", "Notification Code"));
            pass = false;
        }else if (!DataValidator.isInteger(request.getParameter("notificationCode"))) {
            request.setAttribute("notificationCode",
                    PropertyReader.getValue("error.require", "only numeric value"));
            pass = false;
		}

        if (DataValidator.isNull(request.getParameter("message"))) {
            request.setAttribute("message",
                    PropertyReader.getValue("error.require", "Message"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("sentTo"))) {
            request.setAttribute("sentTo",
                    PropertyReader.getValue("error.require", "Sent To"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("sentTime"))) {
            request.setAttribute("sentTime",
                    PropertyReader.getValue("error.require", "Sent Date"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("sentTime"))) {
            request.setAttribute("sentTime",
                    PropertyReader.getValue("error.date", "Sent Date"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("notificationStatus"))) {
            request.setAttribute("notificationStatus",
                    PropertyReader.getValue("error.require", "Notification Status"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populate NotificationBean from request
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        NotificationBean bean = new NotificationBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setNotificationCode(DataUtility.getString(request.getParameter("notificationCode")));
        bean.setMessage(DataUtility.getString(request.getParameter("message")));
        bean.setSentTo(DataUtility.getString(request.getParameter("sentTo")));
        bean.setSentTime(DataUtility.getDate(request.getParameter("sentTime")));
        bean.setNotificationStatus(DataUtility.getString(request.getParameter("notificationStatus")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles GET request (Edit/View)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        NotificationModel model = new NotificationModel();

        if (id > 0) {
            try {
                NotificationBean bean = model.findByPk(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Handles POST request (Save/Update/Cancel/Reset)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        NotificationModel model = new NotificationModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            NotificationBean bean = (NotificationBean) populateBean(request);

            try {
                model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Notification added successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Notification Code already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            NotificationBean bean = (NotificationBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Notification updated successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Notification Code already exists", request);

            } catch (ApplicationException e) {
                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.NOTIFICATION_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.NOTIFICATION_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns Notification View page
     */
    @Override
    protected String getView() {
        return ORSView.NOTIFICATION_VIEW;
    }
}