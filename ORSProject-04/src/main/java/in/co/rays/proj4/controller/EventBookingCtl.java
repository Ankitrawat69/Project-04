package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.EventBookingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.EventBookingModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * EventBookingCtl handles add, update, view and navigation
 * operations for Event Booking module.
 * 
 * @author Ankit Rawat
 * @version 1.0
 */
@WebServlet(name = "EventBookingCtl", urlPatterns = { "/ctl/EventBookingCtl" })
public class EventBookingCtl extends BaseCtl {

    /**
     * Validate Event Booking form
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("eventName"))) {
            request.setAttribute("eventName",
                    PropertyReader.getValue("error.require", "Event Name"));
            pass = false;
        }else if (!DataValidator.isName(request.getParameter("eventName"))) {
            request.setAttribute("eventName",
                    PropertyReader.getValue("error.require", "Event Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("bookingDate"))) {
            request.setAttribute("bookingDate",
                    PropertyReader.getValue("error.require", "Booking Date"));
            pass = false;
        } else if (!DataValidator.isDate(request.getParameter("bookingDate"))) {
            request.setAttribute("bookingDate",
                    PropertyReader.getValue("error.date", "Booking Date"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("seats"))) {
            request.setAttribute("seats",
                    PropertyReader.getValue("error.require", "Seats"));
            pass = false;

        } else if (!DataValidator.isInteger(request.getParameter("seats"))) {
            request.setAttribute("seats",
                    PropertyReader.getValue("error.require", "Seats"));
            pass = false;
        }

        return pass;
    }

    /**
     * Populate bean from request
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        EventBookingBean bean = new EventBookingBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setEventName(DataUtility.getString(request.getParameter("eventName")));
        bean.setBookingDate(DataUtility.getDate(request.getParameter("bookingDate")));
        bean.setSeats(DataUtility.getInt(request.getParameter("seats")));

        populateDTO(bean, request);

        return bean;
    }

    /**
     * Handles GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));

        EventBookingModel model = new EventBookingModel();

        if (id > 0) {
            try {

                EventBookingBean bean = model.findByPk(id);
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
     * Handles POST request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        EventBookingModel model = new EventBookingModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            EventBookingBean bean = (EventBookingBean) populateBean(request);

            try {

                long pk = model.add(bean);

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Event Booking added successfully", request);

            } catch (ApplicationException e) {

                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            EventBookingBean bean = (EventBookingBean) populateBean(request);

            try {

                if (id > 0) {
                    model.update(bean);
                }

                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Event Booking updated successfully", request);

            } catch (ApplicationException e) {

                e.printStackTrace();
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.EVENTBOOKING_LIST_CTL, request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.EVENTBOOKING_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Returns view page
     */
    @Override
    protected String getView() {
        return ORSView.EVENTBOOKING_VIEW;
    }
}